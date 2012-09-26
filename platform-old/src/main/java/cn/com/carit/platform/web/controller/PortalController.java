package cn.com.carit.platform.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.AttachmentUtil;
import cn.com.carit.common.utils.ImageUtils;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.bean.Navigation;
import cn.com.carit.platform.bean.PortalAccount;
import cn.com.carit.platform.service.AccountService;
import cn.com.carit.platform.service.NavigationService;
import cn.com.carit.platform.web.CacheManager;

@Controller
@RequestMapping(value="portal")
public class PortalController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private AccountService<Account> accountService;
	@Resource
	private NavigationService<Navigation> navigationService;
	/**
	 * 注册帐号<br>
	 * portal/account/register
	 * @param account
	 * @param result
	 * @return Map<String, Integer>
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：错误；-1：账号为空；0：密码为空；1：注册成功；2：帐号已存在</td></tr>
	 * 	<tr><td>portalAccout</td><td>{@link PortalAccount}</td></tr>
	 * </table>
	 * @throws Exception 
	 */
	@RequestMapping(value="account/register", method=RequestMethod.POST)
	public @ResponseBody Map<String, Integer> register(@ModelAttribute Account account
			,BindingResult result, HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String, Integer> map=new HashMap<String, Integer>();
		if (result.hasErrors()) {
			map.put(Constants.ANSWER_CODE, -2);
			return map;
		}
		if (!StringUtils.hasText(account.getEmail())) {
			log.error("email can't be empty...");
			map.put(Constants.ANSWER_CODE, -1);
			return map;
		}
		if(accountService.checkAccount(account.getEmail(), null)>0){
			log.error("this account["+account.getEmail()+"] is existed...");
			map.put(Constants.ANSWER_CODE, 2);
			return map;
		}
		if (!StringUtils.hasText(account.getPassword())) {
			log.error("password can't be empty...");
			map.put(Constants.ANSWER_CODE, -1);
			return map;
		}
		accountService.register(account);
		// 刷新缓存
		CacheManager.getInstance().refreshAccount(account.getEmail(), account);
		map.put(Constants.ANSWER_CODE, 1);
		PortalAccount portalAccount=new PortalAccount();
		BeanUtils.copyProperties(account, portalAccount);
		req.getSession().setAttribute(Constants.PORTAL_ACCOUNT, portalAccount);
		return map;
	}
	
	/**
	 * portal/account/check?email={email}|nickName={nickName}<br>
	 * 检测帐号是否已经注册，返回1表示该邮箱已经被注册，0表示未注册
	 * @param email
	 * @return
	 */
	@RequestMapping(value="account/check", method=RequestMethod.GET)
	public @ResponseBody int checkAccount(@RequestParam(required=false) 
		String email, @RequestParam(required=false) String nickName){
		if (StringUtils.hasText(email)) {
			return accountService.checkAccount(email, null);
		} else if (StringUtils.hasText(nickName)) {
			return accountService.checkAccount(null ,nickName);
		} else {
			return 0;
		}
	}
	
	/**
	 * 登录 portal/account/login
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-3：密码错误次数太多，临时限制登录；-2：账号被锁定；-1：账号不存在；0：密码错误；1：登录成功</td></tr>
	 * 	<tr><td>portalAccout</td><td>{@link Account}</td></tr>
	 * </table>
	 * @param email
	 * @param password
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(@RequestParam("email") String email
			, @RequestParam("password") String password
			, HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session=req.getSession();
		Object obj=session.getAttribute(Constants.PASSWORD_ERROR_COUNT+email);
		Integer errorCount= obj==null?0:(Integer)obj;
		Map<String,Object> result=new HashMap<String, Object>();
		if (errorCount!=null && errorCount.intValue()>=Constants.MAX_PWD_ERROR_COUNT) {
			log.error("Limit login:password error count("+errorCount+") >="+Constants.MAX_PWD_ERROR_COUNT);
			result.put(Constants.ANSWER_CODE, -3);
			return result;
		}
		Map<String, Object> resultMap=accountService.login(email, password
				, req.getRemoteAddr());
		Integer answerCode=(Integer) resultMap.get(Constants.ANSWER_CODE);
		result.put(Constants.ANSWER_CODE, answerCode);
		PortalAccount portalAccount=new PortalAccount();
		if (answerCode!=null ) {//有响应
			if (answerCode.intValue()==1) {// 登录成功
				// 清除密码错误次数
				Account account=(Account) resultMap.get(email);
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
				BeanUtils.copyProperties(account, portalAccount);
				session.setAttribute(Constants.PORTAL_ACCOUNT, portalAccount);
				result.put(Constants.PORTAL_ACCOUNT, portalAccount);
			}
			if (answerCode.intValue()==0) {// 密码错误
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
			}
		}
		return result;
	}
	
	/**
	 * portal/account/logout
	 * @param req
	 */
	@RequestMapping(value="account/logout", method=RequestMethod.GET)
	@ResponseBody
	public int logout(HttpServletRequest req, HttpServletResponse res){
		HttpSession session=req.getSession();
		PortalAccount account=(PortalAccount) session.getAttribute(Constants.PORTAL_ACCOUNT);
		if (account!=null) {
			session.setAttribute(Constants.PORTAL_ACCOUNT, null);
			session.setAttribute(Constants.PASSWORD_ERROR_COUNT+account.getEmail(), 0);
		}
		return 1;
	}
	/**
	 * 修改密码；请求参数 oldPassword、newPassword<br>
	 * portal/account/changepwd
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-4</td><td>session超时</td></tr>
	 * <tr><td>-3</td><td>密码错误次数太多，半小时内限制修改</td></tr>
	 * 	<tr><td>-2</td><td>参数错误（密码、新密码不能为空）</td></tr>
	 * 	<tr><td>-1</td><td>原密码错误</td></tr>
	 * 	<tr><td>1</td><td>成功</td></tr>
	 * 	<tr><td>其它</td><td>后台异常</td></tr>
	 * </table>
	 * @param oldPassword
	 * @param newPassword
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="account/changepwd", method=RequestMethod.POST)
	@ResponseBody
	public int changePassword(@RequestParam("oldPassword")String oldPassword
			, @RequestParam("newPassword")String newPassword, HttpServletRequest req) throws Exception {
		PortalAccount account=(PortalAccount) req.getSession().getAttribute(Constants.PORTAL_ACCOUNT);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			return -4;
		}
		HttpSession session=req.getSession();
		String email=account.getEmail();
		Object obj=session.getAttribute(Constants.PASSWORD_ERROR_COUNT+email);
		Integer errorCount= obj==null?0:(Integer)obj;
		if (errorCount!=null && errorCount.intValue()>=Constants.MAX_PWD_ERROR_COUNT) {
			log.error("Limit login:password error count("+errorCount+") >="+Constants.MAX_PWD_ERROR_COUNT);
			return -3;
		}
		if (!StringUtils.hasText(oldPassword)||!StringUtils.hasText(newPassword)) {
			log.error("oldPassword and newPassword can't not be empty...");
			return -2;
		}
		int result=accountService.updatePwd(email, oldPassword, newPassword);
		if (result==-1) {//原始密码错误
			session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
		} else {
			session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
		}
		return result;
	}
	
	/**
	 * 修改头像；请求参数 file<br>
	 * portal/account/changephoto
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：session超时；-1：文件上传失败；1：成功；其它：后台异常</td></tr>
	 * 	<tr><td>photo</td><td>新头像路径</td></tr>
	 * 	<tr><td>thumbPhoto</td><td>缩略头像路径</td></tr>
	 * </table>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/changephoto", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modifyPhoto(HttpServletRequest request) throws Exception{
		PortalAccount portalAccount=(PortalAccount) request.getSession().getAttribute(Constants.PORTAL_ACCOUNT);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (portalAccount==null) {
			//session超时
			log.warn("session time out...");
			resultMap.put(Constants.ANSWER_CODE, -2);
			return resultMap;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//页面控件的文件流
        MultipartFile multipartFile = multipartRequest.getFile("file");
        try {
	        if (multipartFile!=null&&multipartFile.getOriginalFilename().length()>0) {
	        	Account account=new Account();
	        	BeanUtils.copyProperties(portalAccount, account);
	        	// 获取文件的后缀 
	        	String suffix = multipartFile.getOriginalFilename().substring(
	        			multipartFile.getOriginalFilename().lastIndexOf("."));
	        	String hostPath="http://"+request.getServerName();
	        	// 随机文件名
	        	String prefix =  "user_"+account.getId()+"_"+System.nanoTime();// 构建文件名称
	        	String fileName=prefix+suffix;
	        	File file = AttachmentUtil.getPhotoFile(fileName);
				multipartFile.transferTo(file);
				account.setPhoto(hostPath+"/"+Constants.BASE_PATH_PHOTOS+fileName);
				// 生成缩略图
				String resultFile=Constants.BASE_PATH_PHOTOS+prefix+"_thumb"+suffix;
				String resultFilePath=AttachmentUtil.getPhotoPath(prefix+"_thumb"+suffix);
				String srcImageFilePath=AttachmentUtil.getPhotoPath(fileName);
				ImageUtils.scale(srcImageFilePath, resultFilePath, 24, 24, false);
				account.setThumbPhoto(hostPath+"/"+resultFile);
				accountService.saveOrUpdate(account);
				Account oldCache=CacheManager.getInstance().getAllAccountCache().get(account.getEmail());
				account.setPassword(oldCache.getPassword());
				CacheManager.getInstance().refreshAccount(account.getEmail(), account);
				resultMap.put("photo", account.getPhoto());
				resultMap.put("thumbPhoto", account.getThumbPhoto());
			}
        } catch (IllegalStateException e) {
        	log.error("upload file IllegalStateException...", e);
        	resultMap.put(Constants.ANSWER_CODE, -1);
        	return resultMap;
        } catch (IOException e) {
        	log.error("upload file IOException...", e);
        	resultMap.put(Constants.ANSWER_CODE, -1);
        	return resultMap;
        } catch (Exception e) {
        	log.error("upload file Exception...", e);
        	resultMap.put(Constants.ANSWER_CODE, -1);
		}
        resultMap.put(Constants.ANSWER_CODE, 1);
    	return resultMap;
	}
	
	/**
	 * 修改资料<br>
	 * portal/account/modify
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：session超时；-1：未知错误；1：成功</td></tr>
	 * 	<tr><td>portalUser</td><td>{@link PortalAccount}</td></tr>
	 * </table>
	 * @param account
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/modify", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modify(@ModelAttribute Account account, 
			BindingResult result, HttpServletRequest request) throws Exception{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			resultMap.put(Constants.ANSWER_CODE, -1);
			return resultMap;
		}
		HttpSession session=request.getSession();
		PortalAccount portalAccount=(PortalAccount) session.getAttribute(Constants.PORTAL_ACCOUNT);
		if (portalAccount==null) {
			//session超时
			log.warn("session time out...");
			resultMap.put(Constants.ANSWER_CODE, -2);
			return resultMap;
		}
		account.setId(portalAccount.getId());
		accountService.saveOrUpdate(account);
		accountService.queryByEmail(portalAccount.getEmail());
    	Account oldCache=CacheManager.getInstance().getAllAccountCache().get(portalAccount.getEmail());
    	// 更新Cache
		if (StringUtils.hasText(account.getNickName())) {
			oldCache.setNickName(account.getNickName());
		}
		if (account.getGender()!=null) {
			oldCache.setGender(account.getGender());
		}
		if (StringUtils.hasText(account.getRealName())) {
			oldCache.setRealName(account.getRealName());
		}
		if (StringUtils.hasText(account.getIdCard())) {
			oldCache.setIdCard(account.getIdCard());
		}
		if (StringUtils.hasText(account.getMobile())) {
			oldCache.setMobile(account.getMobile());
		}
		if (StringUtils.hasText(account.getOfficePhone())) {
			oldCache.setOfficePhone(account.getOfficePhone());
		}
    	CacheManager.getInstance().refreshAccount(portalAccount.getEmail(), oldCache);
    	// 更新session
    	BeanUtils.copyProperties(oldCache, portalAccount);
		resultMap.put(Constants.PORTAL_ACCOUNT, portalAccount);
		session.setAttribute(Constants.PORTAL_ACCOUNT, portalAccount);
		resultMap.put(Constants.ANSWER_CODE, 1);
		return resultMap;
	}
	
	/**
	 * 加入收藏
	 * @param accountId
	 * @param navigationId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="navigation/add_favorite/{accountId}", method=RequestMethod.POST)
	public @ResponseBody int addFavorite(@PathVariable int accountId
			, @RequestParam(required=false) String [] ids, HttpServletRequest request){
		PortalAccount portalAccount=(PortalAccount) request.getSession().getAttribute(Constants.PORTAL_ACCOUNT);
		if (portalAccount==null) {
			//session超时
			log.warn("session time out...");
			return -1;
		}
		if (ids==null) {
			ids= new String[0];
		}
		return navigationService.addFavorite(accountId, ids);
	}
	
	@RequestMapping(value="test", method=RequestMethod.POST)
	public @ResponseBody String test(@RequestParam(value="data") String data){
		log.error("request data:"+data);
		return data;
	}
}

package cn.com.carit.platform.web.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.bean.AdminUser;
import cn.com.carit.platform.bean.Catalog;
import cn.com.carit.platform.service.AdminUserService;
import cn.com.carit.platform.service.CatalogService;
import cn.com.carit.platform.web.CacheManager;

@Controller
public class AdminController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private AdminUserService<AdminUser> adminUserService;
	@Resource
	private CatalogService<Catalog> catalogService;
	
	
	@RequestMapping(value="admin/index", method=RequestMethod.GET)
	public String index(){
		return "admin/index";
	}
	
	@RequestMapping(value="back/loginForm", method=RequestMethod.GET)
	public String loginForm(){
		return "admin/loginForm";
	}
	
	/**
	 * admin/login
	 * 用户登录<br>
	 * <ul>
	 * 	<li>-3密码错误次数太多，半小时内限制登录</li>
	 * 	<li>-2 用户不存在</li>
	 * 	<li>-1  账号已经停用</li>
	 * 	<li>0	密码错误</li>
	 * 	<li>1	登录成功</li>
	 * 	<li>其它  后台异常</li>
	 * </ul>
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="back/login", method=RequestMethod.POST)
	@ResponseBody
	public int login(@RequestParam("email") String email
			, @RequestParam("password") String password
			, HttpServletRequest req) throws Exception{
		HttpSession session=req.getSession();
		Object obj=session.getAttribute(Constants.PASSWORD_ERROR_COUNT+email);
		Integer errorCount= obj==null?0:(Integer)obj;
		if (errorCount!=null && errorCount.intValue()>=Constants.MAX_PWD_ERROR_COUNT) {
			log.error("Limit login:password error count("+errorCount+") >="+Constants.MAX_PWD_ERROR_COUNT);
			return -3;
		}
		Map<String, Object> resultMap=adminUserService.login(email, password, req.getRemoteAddr());
		Integer answerCode=(Integer) resultMap.get(Constants.ANSWER_CODE);
		if (answerCode!=null ) {//有响应
			if (answerCode.intValue()==1) {// 登录成功
				// 清除密码错误次数
				AdminUser adminUser=(AdminUser) resultMap.get(email);
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
				session.setAttribute(Constants.ADMIN_USER, adminUser);
			}
			if (answerCode.intValue()==0) {// 密码错误
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
			}
		}
		return answerCode;
	}
	
	/**
	 * 退出
	 * @param req
	 * @return
	 */
	@RequestMapping(value="back/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest req){
		HttpSession session=req.getSession();
		AdminUser adminUser=(AdminUser) session.getAttribute(Constants.ADMIN_USER);
		if(adminUser!=null){
			session.setAttribute(Constants.ADMIN_USER, null);
			session.setAttribute(Constants.ADMIN_USER+adminUser.getEmail(), 0);
		}
		return "admin/loginForm";
	}
	
	/**
	 * 检测是否已经存在<br>
	 *  back/check/{table}?name=
	 * <strong>table=user时name为email值</strong>
	 * @param table
	 * @param name
	 * @return
	 */
	@RequestMapping(value="back/check/{table}", method=RequestMethod.GET)
	@ResponseBody
	public int checkExisted(@PathVariable String table
			, @RequestParam(required=false) String name
			, @RequestParam(required=false) String nickName) {
		if (!StringUtils.hasText(table)) {
			throw new IllegalArgumentException("table must be not empty...");
		}
		if (!StringUtils.hasText(name) && !StringUtils.hasText(nickName)) {
			throw new IllegalArgumentException("both param[name, nickName] are empty...");
		}
		if (table.equalsIgnoreCase("catalog")) {
			return catalogService.checkExisted(name);
		} else if (table.equalsIgnoreCase("user")) {
			return adminUserService.checkExisted(name, nickName);
		}
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="back/query/all/catalog", method=RequestMethod.GET)
	@ResponseBody
	public List<Catalog> queryAllCatalog(){
		return CacheManager.getInstance().getAllCatalogList();
	}
}

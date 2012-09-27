package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.AttachmentUtil;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.ImageUtils;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.AppCommentAction;
import cn.com.carit.platform.action.AppDownloadLogAction;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.bean.market.AppComment;
import cn.com.carit.platform.bean.market.AppDownloadLog;
import cn.com.carit.platform.bean.market.Application;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.request.account.AccountRequest;
import cn.com.carit.platform.request.account.ApplicationRequest;
import cn.com.carit.platform.request.account.CheckEmailRequest;
import cn.com.carit.platform.request.account.CheckNicknameRequest;
import cn.com.carit.platform.request.account.CommentRequest;
import cn.com.carit.platform.request.account.RegisterAccountRequest;
import cn.com.carit.platform.request.account.SearchAccountRequest;
import cn.com.carit.platform.request.account.UpdateAccountRequest;
import cn.com.carit.platform.request.account.UpdatePasswordRequest;
import cn.com.carit.platform.request.account.UploadUserPhotoRequest;
import cn.com.carit.platform.request.market.SearchSystemMessageRequest;
import cn.com.carit.platform.response.AccountResponse;
import cn.com.carit.platform.response.DownloadResponse;
import cn.com.carit.platform.response.UploadUserPhotoResponse;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.CommonRopResponse;

/**
 * <p>
 * <b>功能说明：</b>账号相关服务接口
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-21
 */
@ServiceMethodBean(version = "1.0")
public class AccountService {
	
	@Resource
	private AccountAction<Account> action;
	@Resource
	private AppDownloadLogAction<AppDownloadLog> appDownloadLogAction;
	@Resource
	private AppCommentAction<AppComment> appCommentAction;
	
	/**
	 * <p>
	 * <b>功能说明：</b>账号登录
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.logon</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.logon",version = "1.0", httpAction=HttpAction.POST)
    public Object logon(AccountRequest request) throws Exception {
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		// 记录本次登录信息
		action.logon(t.getId(), request.getRopRequestContext().getIp());
		
        AccountResponse accountResponse=new AccountResponse();
        BeanUtils.copyProperties(t, accountResponse);
        
        //返回响应
        return  accountResponse;
    }
	/**
	 * <p>
	 * <b>功能说明：</b>账号登出
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.logout</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "account.logout",version = "1.0", httpAction=HttpAction.GET)
	public Object logout(RopRequest request){
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>检测账号。检测email是否已注册
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>email</td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}<td></td><td>是</td><td>是</td></tr>
	 * </table>
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "account.check.email",version = "1.0", httpAction=HttpAction.GET)
	public Object checkEmail(CheckEmailRequest request){
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>检测账号。检测nickName是否已注册
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>nickName</td>@Length(min=3 ,max=50)<td></td><td>是</td><td>是</td></tr>
	 * </table>
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "account.check.nickname",version = "1.0", httpAction=HttpAction.GET)
	public Object checkNickname(CheckNicknameRequest request){
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	
	/**
	 * <p>
	 * <b>功能说明：</b>账号登录
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.register</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>nickName</td><td>字符长度3~50</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.register",version = "1.0", httpAction=HttpAction.POST)
	public Object register(RegisterAccountRequest request) throws Exception{
		String email=request.getEmail();
		String password=request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		action.register(email, password, request.getNickName());
		CacheManager.getInstance().refreshAccounts();
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>修改账号资料
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.update</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>nickName</td><td>字符长度3~50</td><td>是</td><td>否</td></tr>
	 *  <tr><td>gender</td><td>数字0~2</td><td>是</td><td>否</td></tr>
	 *  <tr><td>birthday</td><td>yyyy-MM-dd 格式字符串</td><td>是</td><td>否</td></tr>
	 *  <tr><td>realName</td><td>字符长度3~20</td><td>是</td><td>否</td></tr>
	 *  <tr><td>idCard</td><td>15或18位字符串</td><td>是</td><td>否</td></tr>
	 *  <tr><td>officePhone</td><td>限长18字符</td><td>是</td><td>否</td></tr>
	 *  <tr><td>mobile</td><td>11位手机号码</td><td>是</td><td>否</td></tr>
	 *  <tr><td>address</td><td>限长200字符</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.update",version = "1.0",needInSession = NeedInSessionType.YES, httpAction=HttpAction.POST)
	public Object update(UpdateAccountRequest request) throws Exception{
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		boolean needToUpdate=false;
		Account update=new Account();
		update.setId(t.getId());
		if (request.getAddress()!=null && !request.getAddress().equals(t.getAddress())) {
			update.setAddress(request.getAddress());
			needToUpdate=true;
		}
		if (request.getGender()!=null && request.getGender().equals(t.getGender())) {
			update.setGender(request.getGender());
			needToUpdate=true;
		}
		if (StringUtils.hasText(request.getBirthday())) {
			update.setBirthday(CaritUtils.strToDate(request.getBirthday(), Constants.DATE_FORMATTER));
			needToUpdate=true;
		}
		if (request.getMobile()!=null && !request.getMobile().equals(t.getMobile())) {
			update.setMobile(request.getMobile());
			needToUpdate=true;
		}
		if (request.getIdCard()!=null && !request.getIdCard().equals(t.getIdCard())) {
			update.setIdCard(request.getIdCard());
			needToUpdate=true;
		}
		if (request.getNickName()!=null && !request.getNickName().equals(t.getNickName())) {
			update.setNickName(request.getNickName());
			needToUpdate=true;
		}
		if (request.getOfficePhone()!=null && !request.getOfficePhone().equals(t.getOfficePhone())) {
			update.setOfficePhone(request.getOfficePhone());
			needToUpdate=true;
		}
		if (request.getRealName()!=null && !request.getRealName().equals(t.getRealName())) {
			update.setRealName(request.getRealName());
			needToUpdate=true;
		}
		
		if (needToUpdate) {
			// 更新
			action.update(update);
			// 更新缓存
			CacheManager.refreshAccount(update, t);
		}
		
		AccountResponse accountResponse=new AccountResponse();
        BeanUtils.copyProperties(t, accountResponse);
        
        //返回响应
        return  accountResponse;
	}
	/**
	 * <p>
	 * <b>功能说明：</b>修改账号密码
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.update.password</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>newPassword</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.update.password",version = "1.0",needInSession = NeedInSessionType.YES, httpAction=HttpAction.POST)
	public Object updatePwd(UpdatePasswordRequest request) throws Exception{
		String email=request.getEmail();
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(email);
		String newPassword=request.getNewPassword();
		// 密码加密
		newPassword=MD5Util.md5Hex(newPassword);
		// 二次加密
		newPassword=MD5Util.md5Hex(email+newPassword+MD5Util.DISTURBSTR);
		
		// 更新密码
		action.updatePwd(email, newPassword);
		
		// 更新缓存
		t.setPassword(newPassword);
		
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>修改账号资料
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.upload.photo</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>photo</td><td>文件类型后缀（如png|jpg|gif）@文件二进制字节流串</td><td>否</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Throwable
	 */
	@ServiceMethod(method = "account.upload.photo",version = "1.0",needInSession = NeedInSessionType.YES, httpAction=HttpAction.POST)
	public Object updatePhoto(UploadUserPhotoRequest request) throws Throwable {
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		String fileType = request.getPhoto().getFileType();
        long nanoTime=System.nanoTime();
        // 头像文件名
        String photo=nanoTime+"."+fileType;
        
        FileCopyUtils.copy(request.getPhoto().getContent()
        		, AttachmentUtil.getInstance().getPhotoFile(photo));
        
        // 缩略图文件名
        String thumbPhoto=nanoTime+"_thumb."+fileType;
        // 生成缩略图
        ImageUtils.scale(AttachmentUtil.getInstance().getPhotoPath(photo)
        		, AttachmentUtil.getInstance().getPhotoPath(thumbPhoto)
        		, 24, 24, false);
        String hostPhotoPath = AttachmentUtil.getInstance().getHost() 
				+ Constants.BASE_PATH_PHOTOS +photo;
        String hostThumbPhoto = AttachmentUtil.getInstance().getHost() 
				+ Constants.BASE_PATH_PHOTOS +thumbPhoto;
        // 保存图片
		action.uploadPhoto(t, hostPhotoPath, hostThumbPhoto);
		
		return new UploadUserPhotoResponse(hostPhotoPath, hostThumbPhoto);
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>修改账号资料
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.upload.photo</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>appId</td><td>应用Id</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Throwable
	 */
	@ServiceMethod(method = "account.download.application",version = "1.0",needInSession = NeedInSessionType.YES, httpAction=HttpAction.POST)
	public Object downloadApplicaton(ApplicationRequest request){
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		Application application = CacheManager.getInstance().getApplication(request.getAppId());
		
		// 保存下载记录
		AppDownloadLog log=new AppDownloadLog();
		log.setAccountId(t.getId());
		log.setAppId(application.getId());
		log.setVersion(application.getVersion());
		appDownloadLogAction.add(log);
		// 返回响应
		return new DownloadResponse(AttachmentUtil.getInstance().getHost()+"/"+application.getAppFilePath());
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>发表评论
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.application.addComment</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>appId</td><td>应用Id</td><td>是</td><td>是</td></tr>
	 *  <tr><td>grade</td><td>应用评价(0~10)</td><td>是</td><td>否</td></tr>
	 *  <tr><td>comment</td><td>200字符内</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Throwable
	 */
	@ServiceMethod(method = "account.application.addComment",version = "1.0",needInSession = NeedInSessionType.YES, httpAction=HttpAction.POST)
	public Object addComment(CommentRequest request){
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		
		int appId = Integer.valueOf(request.getAppId());
		if (appDownloadLogAction.hasDownloaded(t.getId(), appId)) {
			// 下载过应用，可以评论
			AppComment comment=new AppComment();
			comment.setAppId(appId);
			comment.setUserId(t.getId());
			comment.setGrade(request.getGrade());
			comment.setComment(request.getComment());
			appCommentAction.add(comment);
			
			return CommonRopResponse.SUCCESSFUL_RESPONSE;
		}
		
		// 没下载过应用
		return new BusinessServiceErrorResponse(
				request.getRopRequestContext().getMethod(), Constants.HAVENT_DOWNLOAD_APPLICTION,
				request.getRopRequestContext().getLocale(), t.getEmail(), request.getAppId());
		
	}
	
	@ServiceMethod(method = "account.query.account.downloaded.log",version = "1.0",httpAction=HttpAction.GET)
	public Object queryAccountDownloadLogs(SearchAccountRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "account.query.sys.msg",version = "1.0",httpAction=HttpAction.GET)
	public Object queryAccountSystemMessage(SearchSystemMessageRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "account.read.sys.msg",version = "1.0",httpAction=HttpAction.GET)
	public Object readAccountSystemMessage(SearchSystemMessageRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "account.delete.sys.msg",version = "1.0",httpAction=HttpAction.GET)
	public Object deleteAccountSystemMessage(SearchSystemMessageRequest request){
		// TODO
		return null;
	}
}

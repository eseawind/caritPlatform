package cn.com.carit.platform.service;

import javax.annotation.Resource;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.request.LogonRequest;
import cn.com.carit.platform.request.RegisterAccountRequest;
import cn.com.carit.platform.request.UpdatePasswordRequest;
import cn.com.carit.platform.response.LogonResponse;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.CommonRopResponse;
import com.rop.response.NotExistErrorResponse;
import com.rop.session.SimpleSession;

@ServiceMethodBean(version = "1.0")
public class AccountService {
	
	@Resource
	private AccountAction<Account> action;
	
	/**
	 * 登录系统
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.logon",version = "1.0",needInSession = NeedInSessionType.NO)
    public Object logon(LogonRequest request) throws Exception {
		String email=request.getEmail();
		// 查询缓存
		Account t = action.queryByEmail(email);
		if (t==null) {// 账号不存在
			 return new NotExistErrorResponse("account","email",email,request.getRopRequestContext().getLocale());
		}
		String password=request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		if (!password.equalsIgnoreCase(t.getPassword())) {
			//密码错误
			return new BusinessServiceErrorResponse(
	                    request.getRopRequestContext().getMethod(), Constants.PASSWORD_ERROR,
	                    request.getRopRequestContext().getLocale(), email);
		}
		if(t.getStatus()!=Constants.STATUS_VALID){
			// 帐号没启用
			return new BusinessServiceErrorResponse(
                    request.getRopRequestContext().getMethod(), Constants.ACCOUNT_LOCKED,
                    request.getRopRequestContext().getLocale(), email);
		}
		// 记录本次登录信息
		action.logon(t.getId(), request.getRopRequestContext().getIp());
		
        //创建一个会话
		SimpleSession session = new SimpleSession();
        session.setAttribute("account",t);
        String sessionId=MD5Util.getMD5String(email+request.getRopRequestContext().getSign());
        request.getRopRequestContext().addSession(sessionId, session);

        //返回响应
        return  new LogonResponse(sessionId, t.getId(), email, t.getNickName(), t.getPhoto(), t.getThumbPhoto());
    }
	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "account.logout",version = "1.0",needInSession = NeedInSessionType.YES, httpAction=HttpAction.GET)
	public Object logout(RopRequest request){
		request.getRopRequestContext().removeSession();
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	@ServiceMethod(method = "account.register",version = "1.0",needInSession = NeedInSessionType.NO)
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
	
	@ServiceMethod(method = "account.update.password",version = "1.0",needInSession = NeedInSessionType.YES)
	public Object updatePwd(UpdatePasswordRequest request) throws Exception{
		String email=request.getEmail();
		// 查询缓存
		Account t = action.queryByEmail(email);
		if (t==null) {// 账号不存在
			 return new NotExistErrorResponse("account","email",email,request.getRopRequestContext().getLocale());
		}
		String oldPassword=request.getOldPassword();
		// 密码加密
		oldPassword=MD5Util.md5Hex(oldPassword);
		// 二次加密
		oldPassword=MD5Util.md5Hex(email+oldPassword+MD5Util.DISTURBSTR);
		if (!oldPassword.equalsIgnoreCase(t.getPassword())) {
			//密码错误
			return new BusinessServiceErrorResponse(
	                    request.getRopRequestContext().getMethod(), Constants.PASSWORD_ERROR,
	                    request.getRopRequestContext().getLocale(), email);
		}
		String newPassword=request.getNewPassword();
		// 密码加密
		newPassword=MD5Util.md5Hex(newPassword);
		// 二次加密
		newPassword=MD5Util.md5Hex(email+newPassword+MD5Util.DISTURBSTR);
		
		// 更新密码
		action.updatePwd(email, newPassword);
		
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
}

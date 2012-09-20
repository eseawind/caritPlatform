package cn.com.carit.platform.service;

import java.util.Calendar;

import javax.annotation.Resource;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.dao.AccountDao;
import cn.com.carit.platform.request.LogonRequest;
import cn.com.carit.platform.request.RegisterAccountRequest;
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
	private AccountDao<Account> dao;
	
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
		Account t = CacheManager.getInstance().getAccountCache().get(email);
		if (t==null) { // 缓存中没找到，查询DB
			t=dao.queryByEmail(email);
			if (t!=null) {
				CacheManager.getInstance().getAccountCache().put(email, t);
			}
		}
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
		
		// 更新登录时间/IP
		Account updateAccount=new Account();
		updateAccount.setId(t.getId());
		updateAccount.setLastLoginIp(request.getRopRequestContext().getIp());
		updateAccount.setLastLoginTime(Calendar.getInstance().getTime());
		dao.update(updateAccount);
		
        //创建一个会话
		SimpleSession session = new SimpleSession();
        session.setAttribute("account",t);
        String sessionId=MD5Util.getMD5String(email+request.getRopRequestContext().getSign());
        request.getRopRequestContext().addSession(sessionId, session);

        //返回响应
        LogonResponse logonResponse = new LogonResponse();
        logonResponse.setSessionId(sessionId);
        return logonResponse;
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
		dao.register(email, password, request.getNickName());
		CacheManager.getInstance().refreshAccounts();
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
}

package cn.com.carit.platform.interceptor;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.cache.CacheManager;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.NotExistErrorResponse;

public class EffectiveAccountInterceptor extends AbstractInterceptor {

	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			String email=ropRequestContext.getParamValue("email");
			// 查询缓存
			Account t = CacheManager.getInstance().getAccount(email);
			if (t==null) {// 账号不存在
				ropRequestContext.setRopResponse(new NotExistErrorResponse("account","email",email,ropRequestContext.getLocale()));
			} else {
				String password=ropRequestContext.getParamValue("password");
				// 密码加密
				password=MD5Util.md5Hex(password);
				// 二次加密
				password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
				if (!password.equalsIgnoreCase(t.getPassword())) {
					//密码错误
					ropRequestContext.setRopResponse(new BusinessServiceErrorResponse(
							ropRequestContext.getMethod(), Constants.PASSWORD_ERROR,
							ropRequestContext.getLocale(), email));
				}
				if(t.getStatus()!=Constants.STATUS_VALID){
					// 帐号没启用
					ropRequestContext.setRopResponse(new BusinessServiceErrorResponse(
							ropRequestContext.getMethod(), Constants.ACCOUNT_LOCKED,
							ropRequestContext.getLocale(), email));
				}
			}
		}
	}

	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		String method=ropRequestContext.getMethod();
		return method.startsWith("account.")&&!"account.logout".equals(method)&&!"account.register".equals(method);
	}

}

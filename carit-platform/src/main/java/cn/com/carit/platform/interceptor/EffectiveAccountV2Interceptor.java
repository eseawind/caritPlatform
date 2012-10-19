package cn.com.carit.platform.interceptor;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.cache.CacheManager;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.NotExistErrorResponse;

public class EffectiveAccountV2Interceptor extends AbstractInterceptor {

	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			String email=ropRequestContext.getParamValue("email");
			// 查询缓存
			Account t = CacheManager.getInstance().getAccount(email);
			if (t==null) {// 账号不存在
				ropRequestContext.setRopResponse(new NotExistErrorResponse("account","email",email,ropRequestContext.getLocale()));
				return;
			}
			if(t.getStatus()!=Constants.STATUS_VALID){
				// 帐号没启用
				ropRequestContext.setRopResponse(new BusinessServiceErrorResponse(
						ropRequestContext.getMethod(), Constants.ACCOUNT_LOCKED,
						ropRequestContext.getLocale(), email));
			}
		}
	}

	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		String method=ropRequestContext.getMethod();
		if (method.startsWith("account.") && "2.0".equals(ropRequestContext.getVersion())) {
			if ("account.logout".equals(method) 
					|| "account.register".equals(method)
					|| "account.check.email".equals(method)
					|| "account.check.nickname".equals(method)) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int getOrder() {
		return 1;
	}

}

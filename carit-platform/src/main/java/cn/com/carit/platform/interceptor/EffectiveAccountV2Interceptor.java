package cn.com.carit.platform.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.cache.CacheManager;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.BusinessServiceErrorResponse;

public class EffectiveAccountV2Interceptor extends AbstractInterceptor {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			String email=ropRequestContext.getParamValue("email");
			// 查询缓存
			Account t = CacheManager.getInstance().getAccount(email);
			if (t==null) {// 账号不存在
				logger.info("账号【"+email+"】不存在");
				ropRequestContext.setRopResponse(
						new BusinessServiceErrorResponse(
								ropRequestContext.getMethod(), Constants.NO_THIS_ACCOUNT,
								ropRequestContext.getLocale(), email));
				return;
			}
			if(t.getStatus()!=Constants.STATUS_VALID){
				logger.info("帐号没启用...");
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
					|| "account.logon".equals(method)
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

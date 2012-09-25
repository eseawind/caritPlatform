package cn.com.carit.platform.interceptor;

import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.cache.CacheManager;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.BusinessServiceErrorResponse;

public class ChangeNicknameInterceptor extends AbstractInterceptor {
	
	/**
     * 在数据绑定后，服务方法调用前执行该拦截方法
     *
     * @param ropRequestContext
     */
	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			String nickName = ropRequestContext.getParamValue("nickName");
			if (StringUtils.hasText(nickName) && CacheManager.getInstance().getNickNameCache().containsKey(nickName)) {
				//设置了RopResponse后，后续的服务将不执行，直接返回这个RopResponse响应
				ropRequestContext.setRopResponse(new BusinessServiceErrorResponse(ropRequestContext.getMethod(), Constants.NICKNAME_REGISTERED, ropRequestContext.getLocale(), nickName));
			}
		}
	}

	 /**
     * 对method为user.update的方法进行拦截，你可以通过methodContext中的信息制定拦截方案
     *
     * @param ropRequestContext
     * @return
     */
	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		return "account.update".equals(ropRequestContext.getMethod());
	}
	
	@Override
	public int getOrder() {
		return 1;
	}
}

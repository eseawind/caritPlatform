package cn.com.carit.platform.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.cache.CacheManager;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.BusinessServiceErrorResponse;

public class ChangeNicknameInterceptor extends AbstractInterceptor {
private final Logger logger = LoggerFactory.getLogger(getClass());
	
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
     * 在服务执行完成后，响应返回前执行该拦截方法
     *
     * @param ropRequestContext
     */
	@Override
	public void beforeResponse(RopRequestContext ropRequestContext) {
		logger.info("beforeResponse");
	}

	 /**
     * 对method为user.update的方法进行拦截，你可以通过methodContext中的信息制定拦截方案
     *
     * @param ropRequestContext
     * @return
     */
	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		if (logger.isDebugEnabled()) {
			logger.error(ropRequestContext.getMethod());
		}
		return "account.update".equals(ropRequestContext.getMethod());
	}
}

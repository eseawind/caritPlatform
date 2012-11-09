package cn.com.carit.platform.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.carit.platform.cache.CacheManager;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.response.NotExistErrorResponse;

/**
 * <pre>
 * 功能说明：应用拦截器
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-22
 */
public class ApplicationInterceptor extends AbstractInterceptor {
	
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			String appId=ropRequestContext.getParamValue("appId");
			if (StringUtils.hasText(appId)) {
				if (CacheManager.getInstance().getApplication(Integer.valueOf(appId))==null) {
					logger.info("应用【"+appId+"】不存在。。。");
					ropRequestContext.setRopResponse(new NotExistErrorResponse("appllication","appId",appId,ropRequestContext.getLocale()));
				}
			}
		}
	}

	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		return "account.download.application".equals(ropRequestContext.getMethod())
				||"account.application.addComment".equals(ropRequestContext.getMethod())
				||"market.view.application".equals(ropRequestContext.getMethod())
				||"market.query.application.versions".equals(ropRequestContext.getMethod())
				||"market.query.application.comments".equals(ropRequestContext.getMethod())
				||"market.application.stat.grade".equals(ropRequestContext.getMethod())
				||"market.application.avg.grade".equals(ropRequestContext.getMethod())
				||"market.application.download.trend.one.month".equals(ropRequestContext.getMethod())
				;
	}

	/**
	 * 让该拦截器在AbstractInterceptor前执行
	 */
	@Override
	public int getOrder() {
		return Integer.MAX_VALUE-1;
	}

}

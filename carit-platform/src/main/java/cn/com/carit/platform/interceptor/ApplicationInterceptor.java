package cn.com.carit.platform.interceptor;

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

	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			String appId=ropRequestContext.getParamValue("appId");
			if (StringUtils.hasText(appId)) {
				if (CacheManager.getInstance().getApplication(Integer.valueOf(appId))==null) {
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

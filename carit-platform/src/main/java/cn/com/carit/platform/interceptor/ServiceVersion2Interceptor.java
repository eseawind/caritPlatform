package cn.com.carit.platform.interceptor;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.carit.platform.service.AccountServiceVersion2;
import cn.com.carit.session.CaritPlatformSession;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;

/**
 * <pre>
 * 功能说明：在 2.0 版本的API，所需要的 sessionId 为 {@link AccountServiceVersion2#logon(cn.com.carit.platform.request.account.LogonRequest)} 接口返回的 sessionId。如果传过来的 sessionId 对应的 session 已过期，需要重建
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-22
 */
public class ServiceVersion2Interceptor extends AbstractInterceptor {
	
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		if (isMatch(ropRequestContext)) {
			logger.info("2.0接口session重建");
	 		CaritPlatformSession session=(CaritPlatformSession) ropRequestContext.getSession();
	 		// 重建session
	 		if (session==null) {
	 			session = new CaritPlatformSession(Calendar.getInstance());
	 			ropRequestContext.addSession(ropRequestContext.getSessionId(), session);
			}
		}
	}

	@Override
	public boolean isMatch(RopRequestContext ropRequestContext) {
		return "2.0".equals(ropRequestContext.getVersion()) && StringUtils.hasText(ropRequestContext.getSessionId());
	}

	/**
	 * 让该拦截器在AbstractInterceptor前执行
	 */
	@Override
	public int getOrder() {
		return Integer.MAX_VALUE-2;
	}

}

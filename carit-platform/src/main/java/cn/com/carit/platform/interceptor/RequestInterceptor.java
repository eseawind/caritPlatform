package cn.com.carit.platform.interceptor;

import java.util.Calendar;

import cn.com.carit.session.CaritPlatformSession;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;

/**
 * <p>
 * <b>功能说明：</b>所有服务接口前置拦截器
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-9-25
 */
public class RequestInterceptor extends AbstractInterceptor {

	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		CaritPlatformSession session=(CaritPlatformSession) ropRequestContext.getSession();
		if (session!=null) {
			session.setUpdateTime(Calendar.getInstance());
			session.setStatus(CaritPlatformSession.SessionStatus.ACTIVATE);
		}
	}
	
	/**
	 * 让该拦截器最先执行
	 */
	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}
}

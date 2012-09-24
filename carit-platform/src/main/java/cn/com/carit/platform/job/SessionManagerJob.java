package cn.com.carit.platform.job;

import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.session.Session;

import cn.com.carit.session.CaritPlatformSession;
import cn.com.carit.session.CaritSessionManager;

/**
 * <b>功能描述：</b>Session管理job。激活状态的session超过半小时内没有连接过的设置为掉线;掉线状态的session超过半小时没连接删除
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-8-16
 */
public class SessionManagerJob {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private CaritSessionManager sessionManager;
	
	public void manager(){
		// 获取到所有session
		Map<String, Session> sessionCache=sessionManager.getSessionCache();
		Calendar beforeHalfAnHour=Calendar.getInstance();
		logger.info("SessionManagerJob start...");
		// 半小时前
		beforeHalfAnHour.add(Calendar.MINUTE, 30);
		for (Map.Entry<String, Session> e: sessionCache.entrySet()) {
			CaritPlatformSession session=(CaritPlatformSession) e.getValue();
			if (session.getUpdateTime().before(beforeHalfAnHour)) {
				// 激活状态的session超过半小时没请求
				if (session.getStatus()==CaritPlatformSession.SessionStatus.ACTIVATE) {
					session.setStatus(CaritPlatformSession.SessionStatus.OFFLINE);
					logger.info("sessionId="+e.getKey()+"的session超过半小时没链接，将其状态设置为掉线。");
				}
				// session掉线超过半小时
				if (session.getStatus()==CaritPlatformSession.SessionStatus.OFFLINE) {
					sessionCache.remove(e.getKey());
					logger.info("sessionId="+e.getKey()+"的session掉线后超过半小时没链接，将其删除。");
				}
			}
		}
		logger.info("SessionManagerJob end...");
	}
}

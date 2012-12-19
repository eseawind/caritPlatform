package cn.com.carit.platform.job;

import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.carit.session.CaritPlatformSession;
import cn.com.carit.session.CaritSessionManager;

import com.rop.session.Session;

/**
 * <b>功能描述：</b>Session管理Job，每10分钟执行一次。激活状态的session超过半小时内没有连接过的设置为掉线;掉线状态的session超过半小时没连接删除
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-8-16
 */
@Service
public class SessionManagerJob {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private CaritSessionManager sessionManager;
	
	@Scheduled(cron="0 0/10 * * * ?")
	public void manager(){
		// 获取到所有session
		Map<String, Session> sessionCache=sessionManager.getSessionCache();
		Calendar beforeHalfAnHour=Calendar.getInstance();
		logger.info("SessionManagerJob start...");
		// 半小时前
		beforeHalfAnHour.add(Calendar.MINUTE, -30);
		for (Map.Entry<String, Session> e: sessionCache.entrySet()) {
			CaritPlatformSession session=(CaritPlatformSession) e.getValue();
			if (logger.isDebugEnabled()) {
				logger.debug("session updateTime: "+session.getUpdateTime().getTime()+", now:"+beforeHalfAnHour.getTime());
			}
			if (session.getUpdateTime().before(beforeHalfAnHour)) {
				// 激活状态的session超过半小时没请求
				if (session.getStatus()==CaritPlatformSession.SessionStatus.ACTIVATE) {
					session.setStatus(CaritPlatformSession.SessionStatus.OFFLINE);
					session.setUpdateTime(Calendar.getInstance());
					logger.info("sessionId="+e.getKey()+"的session超过半小时没链接，将其状态设置为掉线。");
				} else { // session掉线超过半小时
					sessionCache.remove(e.getKey());
					logger.info("sessionId="+e.getKey()+"的session掉线后超过半小时没链接，将其删除。");
				}
			}
		}
		logger.info("SessionManagerJob end...");
	}
}

package cn.com.carit.platform.service;

import java.util.Calendar;

import cn.com.carit.platform.response.LogonResponse;
import cn.com.carit.session.CaritPlatformSession;

import com.rop.RopRequest;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.CommonRopResponse;

@ServiceMethodBean(version = "1.0")
public class PlatformService {

	/**
	 * 登录服务平台
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "platform.getSession",version = "1.0",needInSession=NeedInSessionType.NO)
	public Object getSession(RopRequest request){
		// 创建session
		Calendar now=Calendar.getInstance();
		CaritPlatformSession session = new CaritPlatformSession(now);
		// 生成sessionId
		String sessionId=String.valueOf(now.getTimeInMillis());
		// 保存session
		request.getRopRequestContext().addSession(sessionId, session);
		return new LogonResponse(sessionId);
	}
	
	/**
	 * <b>心跳包</b>
	 * 调用 {@link PlatformService#getSession(RopRequest)} 成功后每间隔5/10分钟调用一次该接口，以保证长链接效果
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "platform.heartbeat",version = "1.0")
	public Object heartbeat(RopRequest request){
		CaritPlatformSession session = (CaritPlatformSession) request.getRopRequestContext().getSession();
		// 更新session请求处理时间
		session.setUpdateTime(Calendar.getInstance());
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
}

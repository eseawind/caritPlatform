package cn.com.carit.session;

import java.util.Calendar;

import com.rop.session.SimpleSession;
/**
 * <pre>
 * 功能说明：服务平台session模型。
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-21
 */
public class CaritPlatformSession extends SimpleSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = -315807991287813026L;
	public static enum SessionStatus{
		ACTIVATE, OFFLINE;

	    public static boolean isIgnoreSign(SessionStatus status) {
	    	return ACTIVATE==status;
	    }
	}
	
	/** session 处理上一次请求时间 */
	private Calendar updateTime;
	/** session 状态：激活（半小时内没请求转为掉线）；掉线（半小时内有请求转为激活；半小时内没请求销毁）*/
	private SessionStatus status;
	
	public CaritPlatformSession() {
		status=SessionStatus.ACTIVATE;
	}
	
	public CaritPlatformSession(Calendar updateTime) {
		status=SessionStatus.ACTIVATE;
		this.updateTime = updateTime;
	}

	public CaritPlatformSession(Calendar updateTime, SessionStatus status) {
		this.updateTime = updateTime;
		this.status = status;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}
	public SessionStatus getStatus() {
		return status;
	}
	public void setStatus(SessionStatus status) {
		this.status = status;
	}

}

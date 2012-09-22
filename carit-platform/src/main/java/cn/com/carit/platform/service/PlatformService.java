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
	 * <pre>
	 * <b>功能说明：</b>登录服务平台
	 * </pre>
	 * <b>参数：</b>
	 * <tabel>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.getSession</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </tabel>
	 * @param request
	 * @return
	 * @throws Exception
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
	 * <pre>
	 * <b>功能说明：</b>心跳包，调用 {@link PlatformService#getSession(RopRequest)} 成功后每间隔5/10分钟调用一次该接口，以保证长链接效果
	 * </pre>
	 * <b>参数：</b>
	 * <tabel>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </tabel>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "platform.heartbeat",version = "1.0")
	public Object heartbeat(RopRequest request){
		CaritPlatformSession session = (CaritPlatformSession) request.getRopRequestContext().getSession();
		// 更新session请求处理时间
		session.setUpdateTime(Calendar.getInstance());
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
}

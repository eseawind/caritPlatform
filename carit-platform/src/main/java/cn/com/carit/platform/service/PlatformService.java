package cn.com.carit.platform.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import cn.com.carit.common.utils.JsonUtil;
import cn.com.carit.platform.action.LocationAction;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.request.LocationRequest;
import cn.com.carit.platform.request.LocationUploadRequest;
import cn.com.carit.platform.request.SearchLoactionRequest;
import cn.com.carit.platform.response.LocationListResponse;
import cn.com.carit.platform.response.LogonResponse;
import cn.com.carit.session.CaritPlatformSession;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.CommonRopResponse;
/**
 * <p>
 * <b>功能说明：</b>平台服务相关接口
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-24
 */
@ServiceMethodBean(version = "1.0")
public class PlatformService {

	@Resource
	private LocationAction<Location> locationAction;
	
	/**
	 * <p>
	 * <b>功能说明：</b>登录服务平台。用于获取sessionId，所有其它开放平台服务接口的前置接口。
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.getSession</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
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
	 * <p>
	 * <b>功能说明：</b>心跳包，调用 {@link PlatformService#getSession(RopRequest)} 成功后每间隔5/10分钟调用一次该接口，以保证长链接效果
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
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
	
	/**
	 * <p>
	 * <b>功能说明：</b>销毁session
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sessionId</td><td>{@link PlatformService#getSession(RopRequest)}获取到的sessionId</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "platform.removeSession",version = "1.0")
	public Object removeSession(RopRequest request){
		request.getRopRequestContext().removeSession();
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>上传位置信息
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>NotEmpty</td><td>是</td><td>是</td></tr>
	 *  <tr><td>lists</td><td>json数据格式</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws Exception
	 */
	@ServiceMethod(method = "platform.location.upload",version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.POST)
	public Object postLocationData(LocationUploadRequest request) throws Exception{
		List<Location> locationList=new ArrayList<Location>();
		List<LocationRequest> lists=JsonUtil.MAPPER.readValue(request.getLists(), new TypeReference<List<LocationRequest>>() {});
		for (LocationRequest location : lists) {
			locationList.add(new Location(request.getDeviceId(), location.getLat(), location.getLng(), location.getTime()));
		}
		locationAction.batchAdd(locationList);
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询位置信息
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>NotEmpty</td><td>是</td><td>是</td></tr>
	 *  <tr><td>type</td><td>查询类型（0：所有；1：当天；2：自定义）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>startTime</td><td>起始时间</td><td>是</td><td>否（type=2时必传）</td></tr>
	 *  <tr><td>endTime</td><td>结束时间</td><td>是</td><td>否（type=2时必传）</td></tr>
	 * </table>
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws Exception
	 */
	@ServiceMethod(method = "platform.location.search",version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
	public Object searchLocation(SearchLoactionRequest request){
		return new LocationListResponse(locationAction.query(request));
	}
}

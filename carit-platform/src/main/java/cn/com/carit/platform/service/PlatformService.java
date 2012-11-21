package cn.com.carit.platform.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.JsonUtil;
import cn.com.carit.platform.action.BluetoothContactAction;
import cn.com.carit.platform.action.EquipmentAction;
import cn.com.carit.platform.action.LocationAction;
import cn.com.carit.platform.action.ObdDataAction;
import cn.com.carit.platform.bean.BluetoothContact;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.bean.ObdData;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.request.LocationRequest;
import cn.com.carit.platform.request.LocationUploadRequest;
import cn.com.carit.platform.request.NewestObdDataRequest;
import cn.com.carit.platform.request.ObdDataUploadRequest;
import cn.com.carit.platform.request.SearchLoactionRequest;
import cn.com.carit.platform.request.SearchObdDataRequest;
import cn.com.carit.platform.request.account.AccountRequest;
import cn.com.carit.platform.request.account.v2.BluetoothContactRequest;
import cn.com.carit.platform.request.account.v2.SearchBluetoothContactRequest;
import cn.com.carit.platform.response.LogonResponse;
import cn.com.carit.platform.response.ObdDataResponse;
import cn.com.carit.platform.response.PageResponse;
import cn.com.carit.session.CaritPlatformSession;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;
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
	
	@Resource
	private ObdDataAction<ObdData> obdDataAction;
	
	@Resource
	private BluetoothContactAction<BluetoothContact> bluetoothContactAction;
	@Resource
	private EquipmentAction<Equipment> equipmentAction;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
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
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>是</td></tr>
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
			locationList.add(new Location(request.getDeviceId(),request.getAccountId(), location.getLat(), location.getLng(), location.getTime()));
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
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>否</td></tr>
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
		return locationAction.query(request);
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>带分页查询位置信息
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.heartbeat</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>2.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>NotEmpty</td><td>是</td><td>是</td></tr>
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>否</td></tr>
	 *  <tr><td>type</td><td>查询类型（0：所有；1：当天；2：自定义）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>startTime</td><td>起始时间</td><td>是</td><td>否（type=2时必传）</td></tr>
	 *  <tr><td>endTime</td><td>结束时间</td><td>是</td><td>否（type=2时必传）</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 * </table>
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws Exception
	 */
	@ServiceMethod(method = "platform.location.search",version = "2.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
	public Object searchLocationForPage(SearchLoactionRequest request){
		PageResponse<Map<String, Object>> pageResponse=new PageResponse<Map<String, Object>>();
		JsonPage<Map<String, Object>> jsonPage=locationAction.queryForPage(request);
		BeanUtils.copyProperties(jsonPage, pageResponse);
		return pageResponse;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>发送OBD数据
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.obd.upload</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>NotEmpty</td><td>是</td><td>是</td></tr>
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>是</td></tr>
	 *  <tr><td>data</td><td>数据，格式如：{"08":"23","09":"24","04":"152047890","05":"19","15":"36","06":"20","16":"37","07":"5653","13":"34","14":"35","01":"5","11":"32","02":"6","12":"33","03":"1800","10":"25"}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>location</td><td>坐标，格式如：22.543099,114.057868</td><td>是</td><td>是</td></tr>
	 *  <tr><td>date</td><td>数据产生时间（时间截毫秒值）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>error</td><td>错误，格式如：["010201","010302"]</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "platform.obd.upload",version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.POST)
	public Object postObdData(ObdDataUploadRequest request) {
		try {
			// 构造Data
			ObdData t = new ObdData(new Date(request.getDate()),
					request.getDeviceId(), request.getAccountId(),
					request.getLocation(), request.getError());
			int index=1;
			Map<String, String> data=JsonUtil.jsonToMap(request.getData());
			for (Entry<String, String> e: data.entrySet()) {
				logger.info("第 "+index+" 个数据:"+e.getKey()+":"+e.getValue());
				if (e.getKey()!=null) {
					t.getValues()[Integer.valueOf(e.getKey())-1]=Integer.valueOf(e.getValue());//传过来的key 1~16
				}
				index++;
			}
			// 保存数据
			obdDataAction.add(t);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return e;
		}
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询OBD数据
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.obd.search</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>设备ID</td><td>是</td><td>否</td></tr>
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>否</td></tr>
	 *  <tr><td>startTime</td><td>起始时间（long型时间截）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>endTime</td><td>结束时间（long型时间截）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sort</td><td>排序字段</td><td>是</td><td>否</td></tr>
	 *  <tr><td>order</td><td>排序规则（desc/asc）</td><td>是</td><td>否</td></tr>
	 * </table>
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "platform.obd.search",version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
	public Object searchObdData(SearchObdDataRequest request){
		PageResponse<ObdData> page=new PageResponse<ObdData>();
		BeanUtils.copyProperties(obdDataAction.query(request), page);
		return page;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>按设备Id查询最新的数据
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.obd.newestData</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>设备ID</td><td>是</td><td>是</td></tr>
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "platform.obd.newestData",version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
	public Object newestDbdData(NewestObdDataRequest request) throws Exception{
		String deviceId=request.getDeviceId();
		if (StringUtils.hasText(deviceId)) {
			ObdData data=obdDataAction.queryNewestData(request.getDeviceId(), request.getAccountId());
			if (data!=null) {
				ObdDataResponse res=new ObdDataResponse();
				res.setDeviceId(data.getDeviceId());
				res.setLocation(data.getLocation());
				res.setDate(data.getDate());
				Map<Integer,Integer> map=new HashMap<Integer,Integer>();
				if (data.getValues()!=null) {
					for (int i = 0; i < data.getValues().length; i++) {
						map.put(i+1, data.getValues()[i]);
					}
				}
				res.setData(map);
				res.setError(JsonUtil.jsonToStrArray(data.getError()));
				return res;
			}
			return null;
		} else {
			return new BusinessServiceErrorResponse(request.getRopRequestContext().getMethod()
					, Constants.DEVICE_ID_IS_EMPTY
					, request.getRopRequestContext().getLocale(), deviceId);
		}
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>按账号查询所有绑定设备的最新数据
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.obd.currentData</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>accountId</td><td>账号ID</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "platform.obd.currentData",version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
	public Object currentData(RopRequest request){
		return obdDataAction.queryCurrentDataByAccount(Integer.valueOf(request
				.getRopRequestContext().getParamValue("accountId")));
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询账号绑定的设备列表
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.equipment.query</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>accountId</td><td></td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "platform.equipment.query", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryEquipments(RopRequest request){
		return equipmentAction.queryByAccount(Integer.valueOf(request.getRopRequestContext().getParamValue("accountId")));
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询账号蓝牙通讯录
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.bluetooth.contact.query</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "platform.bluetooth.contact.query", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryBluetooth(AccountRequest request){
		//查询账号
		Account account=CacheManager.getInstance().getAccount(request.getEmail());
		if (account==null) {// 账号不存在
			return new BusinessServiceErrorResponse(request.getRopRequestContext().getMethod()
					, Constants.NO_THIS_ACCOUNT
					, request.getRopRequestContext().getLocale(), request.getEmail());
		}
		return bluetoothContactAction.queryBluetoothByAccount(account.getId());
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>上传蓝牙通讯录
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.bluetooth.contact.upload</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>2.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *	<tr><td>deviceId</td><td>@NotEmpty</td><td>是</td><td>是</td></tr>
	 *	<tr><td>deviceName</td><td>@NotEmpty</td><td>是</td><td>是</td></tr>
	 *  <tr><td>contacts</td><td>格式：[{"callName":"张三","callNum":"12345678","callNameKey":"zs","callType":"1"}, {"callName":"李四","callNum":"87654321","callNameKey":"ls","callType":"1"}]</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "platform.bluetooth.contact.upload",httpAction=HttpAction.POST, needInSession=NeedInSessionType.NO)
	public Object uploadBluetoothContact(BluetoothContactRequest request) throws Exception {
		//查询账号
		Account account=CacheManager.getInstance().getAccount(request.getEmail());
		if (account==null) {// 账号不存在
			return new BusinessServiceErrorResponse(request.getRopRequestContext().getMethod()
					, Constants.NO_THIS_ACCOUNT
					, request.getRopRequestContext().getLocale(), request.getEmail());
		}
		List<BluetoothContact> list=new ArrayList<BluetoothContact>();
		List<cn.com.carit.platform.request.account.v2.BluetoothContact> contacts=JsonUtil.MAPPER.readValue(request.getContacts(), new TypeReference<List<cn.com.carit.platform.request.account.v2.BluetoothContact>>() {});
		// 构造 list
		for (cn.com.carit.platform.request.account.v2.BluetoothContact temp : contacts) {
			list.add(new BluetoothContact(request.getDeviceId(), account
					.getId(), temp.getCallName(), temp.getCallNum(), temp
					.getCallNameKey(), temp.getCallType()));
		}
		bluetoothContactAction.uploadContact(list, account.getId(),
				request.getDeviceId(), request.getDeviceName());
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>同步蓝牙通讯录
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>platform.bluetooth.contact.sync</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>2.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *	<tr><td>deviceId</td><td>@NotEmpty</td><td>是</td><td>是</td></tr>
	 *	<tr><td>callName</td><td>姓名</td><td>是</td><td>否</td></tr>
	 *	<tr><td>callNameKey</td><td>姓名拼音</td><td>是</td><td>否</td></tr>
	 *	<tr><td>callNum</td><td>电话号码（只能输入数字）</td><td>是</td><td>否</td></tr>
	 *	<tr><td>callType</td><td>号码类型</td><td>是</td><td>否</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sort</td><td>排序字段</td><td>是</td><td>否</td></tr>
	 *  <tr><td>order</td><td>排序规则（desc/asc）</td><td>是</td><td>否</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "platform.bluetooth.contact.sync",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object syncBluetoothContact(SearchBluetoothContactRequest request){
		//查询账号
		Account account=CacheManager.getInstance().getAccount(request.getEmail());
		if (account==null) {// 账号不存在
			return new BusinessServiceErrorResponse(request.getRopRequestContext().getMethod()
					, Constants.NO_THIS_ACCOUNT
					, request.getRopRequestContext().getLocale(), request.getEmail());
		}
		DataGridModel dgm = new DataGridModel();
		dgm.setSort(request.getSort());
		dgm.setOrder(request.getOrder());
		dgm.setPage(request.getPage());
		dgm.setRows(request.getRows());
		
		PageResponse<Map<String, Object>> pageResponse=new PageResponse<Map<String, Object>>();
		JsonPage<Map<String, Object>> jsonPage=bluetoothContactAction.queryByDeviceAndAccount(
				request.getDeviceId(), account.getId(), request.getCallName(),
				request.getCallNameKey(), request.getCallNum(), dgm);
		BeanUtils.copyProperties(jsonPage, pageResponse);
		return pageResponse;
	}
}

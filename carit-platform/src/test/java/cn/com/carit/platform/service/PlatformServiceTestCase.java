package cn.com.carit.platform.service;

import java.util.Calendar;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.LocationUploadRequest;
import cn.com.carit.platform.request.NewestObdDataRequest;
import cn.com.carit.platform.request.ObdDataUploadRequest;
import cn.com.carit.platform.request.SearchLoactionRequest;
import cn.com.carit.platform.request.SearchObdDataRequest;
import cn.com.carit.platform.response.LocationListResponse;
import cn.com.carit.platform.response.ObdDataResponse;
import cn.com.carit.platform.response.PageResponse;

import com.rop.response.CommonRopResponse;

public class PlatformServiceTestCase {

	@Test
	public void testGetSession(){
		RopTestCaseClient.getInstance().getSession();
	}
	
	@Test
	public void testHeartbeat(){
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().heartbeat();
	}
	
	@Test
	public void testPostLocationData(){
		LocationUploadRequest request = new LocationUploadRequest();
		request.setDeviceId("4697DA4F");
		request.setAccountId(21);
		long now=Calendar.getInstance().getTimeInMillis();
		request.setLists("[{\"lat\":22.543099,\"lng\":114.057868,\"time\":"+now+"}, {\"lat\":21.543099,\"lng\":114.057868,\"time\":"+now+"}]");
		RopTestCaseClient.getRopClient().buildClientRequest().post(request, CommonRopResponse.class, "platform.location.upload", "1.0");
	}
	
	@Test
	public void testSearchLocation(){
		SearchLoactionRequest request=new SearchLoactionRequest();
		request.setDeviceId("4697DA4F");
		request.setAccountId(21);
		request.setType(1);
		Calendar today=Calendar.getInstance();
		// 清除时分秒
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		request.setStartTime(today.getTimeInMillis());
		today.add(Calendar.DATE, 1);
		request.setEndTime(today.getTimeInMillis());
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, LocationListResponse.class, "platform.location.search", "1.0");
	}
	
	@Test
	public void testPostObdData(){
		ObdDataUploadRequest request=new ObdDataUploadRequest();
		request.setError("[\"010201\",\"010302\"]");
		request.setDate(Calendar.getInstance().getTimeInMillis());
		request.setLocation("22.543099,114.057868");
		request.setDeviceId("4697DA4F");
		request.setAccountId(21);
		request.setData("{\"08\":\"23\",\"09\":\"24\",\"04\":\"152047890\",\"05\":\"19\",\"15\":\"36\",\"06\":\"20\",\"16\":\"37\",\"07\":\"5653\",\"13\":\"34\",\"14\":\"35\",\"01\":\"5\",\"11\":\"32\",\"02\":\"6\",\"12\":\"33\",\"03\":\"1800\",\"10\":\"25\", \"17\":\"1233\",\"18\":\"435\",\"19\":\"19\"}");
		RopTestCaseClient.getRopClient().buildClientRequest().post(request, CommonRopResponse.class, "platform.obd.upload", "1.0");
	}
	
	@Test
	public void testSearchObdData(){
		SearchObdDataRequest request=new SearchObdDataRequest();
		request.setDeviceId("4697DA4F");
		request.setAccountId(21);
		request.setSort("value_4");
		request.setOrder("desc");
		
//		Calendar calendar=Calendar.getInstance();
//		request.setEndTime(calendar.getTimeInMillis());
//		
//		calendar.add(Calendar.DATE, -1);
//		
//		request.setStartTime(calendar.getTimeInMillis());
		
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, PageResponse.class, "platform.obd.search", "1.0");
	}
	
	@Test
	public void testNewestObdData(){
		NewestObdDataRequest request=new NewestObdDataRequest();
		request.setDeviceId("4697DA4F");
		request.setAccountId(21);
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, ObdDataResponse.class, "platform.obd.newestData", "1.0");
	}
	
}

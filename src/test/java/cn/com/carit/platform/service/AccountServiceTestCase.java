package cn.com.carit.platform.service;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import cn.com.carit.platform.request.LogonRequest;
import cn.com.carit.platform.response.LogonResponse;

import com.rop.client.CompositeResponse;
import com.rop.client.DefaultRopClient;

public class AccountServiceTestCase {
	public static final String SERVER_URL = "http://localhost:8088/router";
	public static final String APP_KEY = "00001";
	public static final String APP_SECRET = "abcdeabcdeabcdeabcdeabcde";
	private DefaultRopClient ropClient = new DefaultRopClient(SERVER_URL,
			APP_KEY, APP_SECRET);

	{
		ropClient.setFormatParamName("messageFormat");
//		ropClient.addRopConvertor(new TelephoneConverter());
	}
	
	public void logon(){
		LogonRequest request=new LogonRequest();
		request.setEmail("xiegc@carit.com.cn");
		request.setPassword("123456");
		
		CompositeResponse response=ropClient.buildClientRequest().post(request, LogonResponse.class, "account.logon", "1.0");
		assertNotNull(response);
		assertTrue(response.isSuccessful());
		assertTrue(response.getSuccessResponse() instanceof LogonResponse);
	}
}

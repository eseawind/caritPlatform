package cn.com.carit.platform.service;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.account.v3.RegisterAccountRequest;

import com.rop.response.CommonRopResponse;

public class AccountServiceV3TestCase {
	
	@Test
	public void testRegister(){
		RegisterAccountRequest request = new RegisterAccountRequest();
    	request.setEmail("test@test.com");
    	request.setPassword("123456");
    	request.setNickName("afagdfgg");
    	request.setDeviceId("69A61F9F");
    	RopTestCaseClient.getInstance().buildClientRequest().post(request, CommonRopResponse.class, "account.register", "3.0");
	}
}

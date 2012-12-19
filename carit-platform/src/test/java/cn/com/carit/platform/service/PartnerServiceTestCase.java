package cn.com.carit.platform.service;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.partner.BindingDeviceRequest;
import cn.com.carit.platform.request.partner.PartnerLogonRequest;
import cn.com.carit.platform.request.partner.PartnerRegisterRequest;
import cn.com.carit.platform.request.partner.PartnerSearchAccountRequest;
import cn.com.carit.platform.request.partner.PartnerUpdatePwdRequest;
import cn.com.carit.platform.request.partner.PartnerUpdateRequest;
import cn.com.carit.platform.response.PageResponse;
import cn.com.carit.platform.response.PartnerResponse;

import com.rop.response.CommonRopResponse;

public class PartnerServiceTestCase {
	
	@Test
	public void testRegister() {
		PartnerRegisterRequest request=new PartnerRegisterRequest();
		request.setAddr("深圳市南山区");
		request.setCity("广东·深圳");
		request.setContactPerson("张三");
		request.setEmail("test@test.com");
		request.setFirmName("测试");
		request.setPassword("123456");
		request.setPhone("123456789");
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, CommonRopResponse.class, "partner.register", "1.0");
	}
	
	@Test
	public void testLogon(){
		PartnerLogonRequest request=new PartnerLogonRequest();
		request.setFirmName("测试");
		request.setPassword("123456");
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, PartnerResponse.class, "partner.logon", "1.0");
	}
	
	@Test
	public void testUpdatePwd(){
		PartnerUpdatePwdRequest request=new PartnerUpdatePwdRequest();
		request.setId(1);
		request.setOldPassword("1234567");
		request.setPassword("123456");
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, CommonRopResponse.class, "partner.update.pwd", "1.0");
	}
	
	@Test
	public void testUpdate(){
		PartnerUpdateRequest request=new PartnerUpdateRequest();
		request.setId(1);
		request.setEmail("test@test.com");
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, CommonRopResponse.class, "partner.update", "1.0");
	}
	
	@Test
	public void testBindingAccount(){
		BindingDeviceRequest request=new BindingDeviceRequest();
		request.setDeviceId("69A61F9F");
		request.setPartnerId(2);
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, CommonRopResponse.class, "partner.device.bingding", "1.0");
	}
	
	@Test
	public void testQueryDevices(){
		PartnerSearchAccountRequest request=new PartnerSearchAccountRequest();
		request.setPartnerId(1);
//		request.setEmail("xiegc@carit.com.cn");
		request.setNickName("谢庚");
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, PageResponse.class, "partner.query.devices", "1.0");
	}
}

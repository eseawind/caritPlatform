package cn.com.carit.platform.service;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;

public class AccountServiceTestCase {
	
	@Test
	public void testLogon(){
		String sessionId=RopTestCaseClient.getInstance().logon("xiegc@carit.com.cn", "123456");
		assertNotNull(sessionId);
	}
	
	@Test
	public void testLogout(){
		RopTestCaseClient.getInstance().logout();
	}
	
	@Test
	public void testRegister(){
		RopTestCaseClient.getInstance().register("xiegc@carit.com", "123456", "風一樣的男子");
	}
}

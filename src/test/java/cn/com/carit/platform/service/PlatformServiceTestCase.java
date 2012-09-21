package cn.com.carit.platform.service;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;

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
}

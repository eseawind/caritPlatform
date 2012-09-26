package cn.com.carit.platform.service;

import java.io.File;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.account.ApplicationRequest;
import cn.com.carit.platform.request.account.CheckEmailRequest;
import cn.com.carit.platform.request.account.CheckNicknameRequest;
import cn.com.carit.platform.request.account.CommentRequest;
import cn.com.carit.platform.request.account.UpdateAccountRequest;
import cn.com.carit.platform.request.account.UploadUserPhotoRequest;
import cn.com.carit.platform.response.AccountResponse;
import cn.com.carit.platform.response.DownloadResponse;
import cn.com.carit.platform.response.UploadUserPhotoResponse;

import com.rop.request.UploadFile;
import com.rop.response.CommonRopResponse;

public class AccountServiceTestCase {
	
	@Test
	public void testLogon(){
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().logon("xiegc@carit.info", "123456");
	}
	
	@Test
	public void testUpdatePwd(){
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().updatePwd("xiegc@carit.com.cn", "123456", "123456");
	}
	
	@Test
	public void testLogout(){
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().logout();
	}
	
	@Test
	public void testRegister(){
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().register("xiegc@carit.com", "123456", "風一樣的男子");
	}
	
	@Test
	public void testUpdate(){
		UpdateAccountRequest request=new UpdateAccountRequest("xiegc@carit.com.cn","123456");
//		request.setNickName("風一樣的男子");
		request.setBirthday("1986-06-08");
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, AccountResponse.class, "account.update", "1.0");
	}
	
	@Test
	public void testUploadPhoto(){
		UploadUserPhotoRequest request=new UploadUserPhotoRequest("xiegc@carit.com.cn","123456");
		request.setPhoto(new UploadFile(new File("D:/market/Sina Weibo.png")));
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, UploadUserPhotoResponse.class, "account.upload.photo", "1.0");
	}
	
	@Test
	public void testDownloadApplicaton(){
		ApplicationRequest request=new ApplicationRequest("xiegc@carit.com.cn", "123456", 6);
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, DownloadResponse.class, "account.download.application", "1.0");
	}
	
	@Test
	public void testAddComment(){
		CommentRequest request=new CommentRequest("xiegc@carit.com.cn", "123456", 8, "很好很强大", 10);
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, CommonRopResponse.class, "account.application.addComment", "1.0");
	}
	
	@Test
	public void testCheckEmail(){
		CheckEmailRequest request=new CheckEmailRequest();
		request.setEmail("xiegc@carit.com.cn");
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, CommonRopResponse.class, "account.check.email", "1.0");
	}
	
	@Test
	public void testCheckNickname(){
		CheckNicknameRequest request=new CheckNicknameRequest();
		request.setNickName("風一樣的男子");
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, CommonRopResponse.class, "account.check.nickname", "1.0");
	}
}

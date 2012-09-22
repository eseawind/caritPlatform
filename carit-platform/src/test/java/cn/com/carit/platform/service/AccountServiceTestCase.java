package cn.com.carit.platform.service;

import java.io.File;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.account.AddApplicationCommentRequest;
import cn.com.carit.platform.request.account.DownloadApplicationRequest;
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
		RopTestCaseClient.getInstance().logon("xiegc@carit.com.cn", "1234564");
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
		DownloadApplicationRequest request=new DownloadApplicationRequest("xiegc@carit.com.cn", "123456", 1);
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, DownloadResponse.class, "account.download.application", "1.0");
	}
	
	@Test
	public void testAddComment(){
		AddApplicationCommentRequest request=new AddApplicationCommentRequest("xiegc@carit.com.cn", "123456", 1, "很好很强大", 10);
		RopTestCaseClient.getInstance().getSession();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, CommonRopResponse.class, "account.application.addComment", "1.0");
	}
}

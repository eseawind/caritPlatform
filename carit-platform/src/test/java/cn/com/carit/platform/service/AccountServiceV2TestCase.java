package cn.com.carit.platform.service;

import java.io.File;
import java.util.List;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.account.AccountRequest;
import cn.com.carit.platform.request.account.CheckEmailRequest;
import cn.com.carit.platform.request.account.CheckNicknameRequest;
import cn.com.carit.platform.request.account.v2.AddEquipmentRequest;
import cn.com.carit.platform.request.account.v2.ApplicationRequest;
import cn.com.carit.platform.request.account.v2.CommentRequest;
import cn.com.carit.platform.request.account.v2.ReadSystemMessageRequest;
import cn.com.carit.platform.request.account.v2.SearchByAccountRequest;
import cn.com.carit.platform.request.account.v2.SearchSystemMessageRequest;
import cn.com.carit.platform.request.account.v2.UpdateAccountRequest;
import cn.com.carit.platform.request.account.v2.UploadUserPhotoRequest;
import cn.com.carit.platform.response.AccountResponse;
import cn.com.carit.platform.response.DownloadResponse;
import cn.com.carit.platform.response.PageResponse;
import cn.com.carit.platform.response.SystemMessageResponse;
import cn.com.carit.platform.response.UploadUserPhotoResponse;

import com.rop.request.UploadFile;
import com.rop.response.CommonRopResponse;

public class AccountServiceV2TestCase {
	
	@Test
	public void testLogon(){
		RopTestCaseClient.getInstance().logon_v2("xiegc@carit.com.cn", "123456");
	}
	
	@Test
	public void testRegister(){
		RopTestCaseClient.getInstance().register_v2("xxx@carit.com.cn", "123456", "xxx");
	}
	
	@Test
	public void testUpdate(){
		UpdateAccountRequest request=new UpdateAccountRequest("xiegc@carit.com.cn");
//		request.setNickName("風一樣的男子");
		request.setBirthday("1986-06-08");
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, AccountResponse.class, "account.update", "2.0");
	}
	
	@Test
	public void testUploadPhoto(){
		UploadUserPhotoRequest request=new UploadUserPhotoRequest("xiegc@carit.com.cn");
		request.setPhoto(new UploadFile(new File("D:/market/Sina Weibo.png")));
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, UploadUserPhotoResponse.class, "account.upload.photo", "2.0");
	}
	
	@Test
	public void testDownloadApplicaton(){
		ApplicationRequest request=new ApplicationRequest("xiegc@carit.com.cn", 1);
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, DownloadResponse.class, "account.download.application", "2.0");
	}
	
	@Test
	public void testAddComment(){
		CommentRequest request=new CommentRequest("xiegc@carit.com.cn", 1, "很好很强大", 10);
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().post(request, CommonRopResponse.class, "account.application.addComment", "2.0");
	}
	
	@Test
	public void testCheckEmail(){
		CheckEmailRequest request=new CheckEmailRequest();
		request.setEmail("xiegc@carit.com.cn");
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, CommonRopResponse.class, "account.check.email", "2.0");
	}
	
	@Test
	public void testCheckNickname(){
		CheckNicknameRequest request=new CheckNicknameRequest();
		request.setNickName("風一樣的男子");
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, CommonRopResponse.class, "account.check.nickname", "2.0");
	}
	
	@Test
	public void testQueryAccountDownloadedApp(){
		SearchByAccountRequest request=new SearchByAccountRequest();
		request.setLanguage("cn");
		request.setEmail("xiegc@carit.com.cn");
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "account.downloaded.application", "2.0");
	}
	
	@Test
	public void testQueryAccountSystemMessage(){
		SearchSystemMessageRequest request=new SearchSystemMessageRequest();
		request.setLanguage("cn");
		request.setEmail("xiegc@carit.com.cn");
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "account.query.sys.msg", "2.0");
	}
	
	@Test
	public void testReadAccountSystemMessage(){
		ReadSystemMessageRequest request=new ReadSystemMessageRequest();
		request.setEmail("xiegc@carit.com.cn");
		request.setMsgId(1);
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, SystemMessageResponse.class, "account.read.sys.msg", "2.0");
	}
	
	@Test
	public void testDeleteAccountSystemMessage(){
		ReadSystemMessageRequest request=new ReadSystemMessageRequest();
		request.setEmail("xiegc@carit.com.cn");
		request.setMsgId(1);
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, CommonRopResponse.class, "account.delete.sys.msg", "2.0");
	}
	
	@Test
	public void testAddEquipment(){
		AddEquipmentRequest request=new AddEquipmentRequest("xiegc@carit.com.cn", "123456", "4697DA4FF");
		testLogon();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, CommonRopResponse.class, "account.equipment.add", "2.0");
	}
	
	@Test
	public void testQueryEquipments(){
		testLogon();
		AccountRequest request=new AccountRequest("xiegc@carit.com.cn");
		RopTestCaseClient.getInstance().buildClientRequest().get(request, List.class, "account.equipment.query", "2.0");
	}
}

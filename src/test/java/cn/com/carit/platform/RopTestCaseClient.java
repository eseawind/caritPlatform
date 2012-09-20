package cn.com.carit.platform;

import cn.com.carit.platform.request.LogonRequest;
import cn.com.carit.platform.request.RegisterAccountRequest;
import cn.com.carit.platform.request.UpdatePasswordRequest;
import cn.com.carit.platform.response.LogonResponse;

import com.rop.MessageFormat;
import com.rop.client.ClientRequest;
import com.rop.client.CompositeResponse;
import com.rop.client.DefaultRopClient;
import com.rop.client.RopClient;
import com.rop.response.CommonRopResponse;

public class RopTestCaseClient {
	private String serverUrl;
	private String appKey;
	private String appSecret;
	private DefaultRopClient ropClient;

	private static class ClientHolder {
		private static final RopTestCaseClient INSTANCE = new RopTestCaseClient();
	}
	
	private RopTestCaseClient(){
		serverUrl = "http://localhost:8088/router";
		appKey = "1";
		appSecret = "abcdeabcdeabcdeabcdeabcde";
		ropClient = new DefaultRopClient(serverUrl, appKey, appSecret);
		ropClient.setFormatParamName("messageFormat");
		ropClient.setMessageFormat(MessageFormat.json);
	}
	
	public static RopClient getRopClient(){
		return ClientHolder.INSTANCE.ropClient;
	}
	
	public static RopTestCaseClient getInstance(){
		return ClientHolder.INSTANCE;
	}
	
	/**
     * 登录系统
     *
     * @return
     */
    public String logon(String email, String password) {
        LogonRequest request = new LogonRequest();
        request.setEmail(email);
        request.setPassword(password);
        CompositeResponse response = buildClientRequest().post(request, LogonResponse.class, "account.logon", "1.0");
        String sessionId = ((LogonResponse) response.getSuccessResponse()).getSessionId();
        ropClient.setSessionId(sessionId);
        return sessionId;
    }

    public void logout() {
    	buildClientRequest().get(CommonRopResponse.class, "account.logout", "1.0");
    }

    public ClientRequest buildClientRequest(){
        return ClientHolder.INSTANCE.ropClient.buildClientRequest();
    }
    
    public void register(String email, String password, String nickName) {
    	RegisterAccountRequest request = new RegisterAccountRequest();
    	request.setEmail(email);
    	request.setPassword(password);
    	request.setNickName(nickName);
    	buildClientRequest().post(request, CommonRopResponse.class, "account.register", "1.0");
    }
    
    public void updatePwd(String email, String oldPassword, String newPassword){
    	UpdatePasswordRequest request = new UpdatePasswordRequest();
    	request.setEmail(email);
    	request.setOldPassword(oldPassword);
    	request.setNewPassword(newPassword);
    	buildClientRequest().post(request, CommonRopResponse.class, "account.update.password", "1.0");
    }
}
package cn.com.carit.platform;

import cn.com.carit.platform.request.account.LogonRequest;
import cn.com.carit.platform.request.account.RegisterAccountRequest;
import cn.com.carit.platform.request.account.UpdatePasswordRequest;
import cn.com.carit.platform.response.AccountResponse;
import cn.com.carit.platform.response.LogonResponse;
import cn.com.carit.platform.response.SessionResponse;

import com.rop.MessageFormat;
import com.rop.client.ClientRequest;
import com.rop.client.CompositeResponse;
import com.rop.client.DefaultRopClient;
import com.rop.client.RopClient;
import com.rop.response.CommonRopResponse;

public class RopTestCaseClient {
	private final String serverUrl;
	private final String appKey;
	private final String appSecret;
	private final DefaultRopClient ropClient;

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
	
	public void getSession(){
		CompositeResponse response=buildClientRequest().get(LogonResponse.class, "platform.getSession", "1.0");
		if (response.isSuccessful()) {
			LogonResponse logonResponse=(LogonResponse) response.getSuccessResponse();
			ropClient.setSessionId(logonResponse.getSessionId());
		}
	}

	public Object heartbeat(){
		return buildClientRequest().get(CommonRopResponse.class, "platform.heartbeat", "1.0").getSuccessResponse();
	}
	
    public Object logon(String email, String password) {
    	LogonRequest request = new LogonRequest();
        request.setEmail(email);
        request.setPassword(password);
        CompositeResponse response=buildClientRequest().post(request, AccountResponse.class, "account.logon", "1.0");
        if (response.isSuccessful()) {
        	return response.getSuccessResponse();
		}
        // 处理错误响应
        return response.getErrorResponse();
    }

    public Object logon_v2(String email, String password) {
    	LogonRequest request = new LogonRequest();
        request.setEmail(email);
        request.setPassword(password);
        CompositeResponse response=buildClientRequest().post(request, SessionResponse.class, "account.logon", "2.0");
        if (response.isSuccessful()) {
        	SessionResponse sessionResponse=(SessionResponse) response.getSuccessResponse();
        	ropClient.setSessionId(sessionResponse.getSessionId());
        	return response.getSuccessResponse();
		}
        // 处理错误响应
        return response.getErrorResponse();
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
    
    public void register_v2(String email, String password, String nickName) {
    	RegisterAccountRequest request = new RegisterAccountRequest();
    	request.setEmail(email);
    	request.setPassword(password);
    	request.setNickName(nickName);
    	buildClientRequest().post(request, CommonRopResponse.class, "account.register", "2.0");
    }
    
    public void updatePwd(String email, String oldPassword, String newPassword){
    	UpdatePasswordRequest request = new UpdatePasswordRequest();
    	request.setEmail(email);
    	request.setPassword(oldPassword);
    	request.setNewPassword(newPassword);
    	buildClientRequest().post(request, CommonRopResponse.class, "account.update.password", "1.0");
    }
    
    
}

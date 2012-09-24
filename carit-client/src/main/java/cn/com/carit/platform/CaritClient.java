package cn.com.carit.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import cn.com.carit.platform.response.AccountResponse;
import cn.com.carit.platform.response.ErrorResponse;
import cn.com.carit.platform.response.LogonResponse;

public class CaritClient {
	
	public enum MessageFormat{
		xml, json;
	}
	
	public enum Local{
		zh_CN, en;
	}
	
	public static final String SYSTEM_PARAM_APPKEY="appKey";
	public static final String SYSTEM_PARAM_MESSAGE_FORMAT="messageFormat";
	public static final String SYSTEM_PARAM_METHOD="method";
	public static final String SYSTEM_PARAM_VERSION="v";
	public static final String SYSTEM_PARAM_LOCALE="locale";
	public static final String SYSTEM_PARAM_SIGN="sign";
	public static final String SYSTEM_PARAM_SESSION_ID="sessionId";
	
	//服务地址
    private String serverUrl;

    //应用键
    private String appKey;

    //应用密钥
    private String appSecret;

    private String sessionId;
    
    private String locale = Locale.SIMPLIFIED_CHINESE.toString();
    
    private String messageFormat=MessageFormat.json.toString();
    
    private static class ClientHolder {
    	public static CaritClient INSTANCE=new CaritClient();
    }
    
    private CaritClient(){
    	Properties prop=new Properties();
    	try {
			prop.load(ClassLoader.getSystemResourceAsStream("config.properties"));
			serverUrl=prop.getProperty("serverUrl");
			appKey=prop.getProperty("appKey");
			appSecret=prop.getProperty("appSecret");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static CaritClient getInstance(){
    	return ClientHolder.INSTANCE;
    }
    
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
    public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public Map<String, String> buildParamValues(String method, String version) {
		Map<String, String> paramValues=new HashMap<String, String>();
		paramValues.put(SYSTEM_PARAM_METHOD, method);
		paramValues.put(SYSTEM_PARAM_APPKEY, ClientHolder.INSTANCE.appKey);
		paramValues.put(SYSTEM_PARAM_VERSION, version);
		paramValues.put(SYSTEM_PARAM_LOCALE, Local.zh_CN.toString());
		paramValues.put(SYSTEM_PARAM_MESSAGE_FORMAT, CaritClient.MessageFormat.json.toString());
		return paramValues;
	}
	
	public Map<String, String> buildParamValues(String method, String version, String sessionId) {
		Map<String, String> paramValues=new HashMap<String, String>();
		paramValues.put(SYSTEM_PARAM_METHOD, method);
		paramValues.put(SYSTEM_PARAM_APPKEY, ClientHolder.INSTANCE.appKey);
		paramValues.put(SYSTEM_PARAM_VERSION, version);
		paramValues.put(SYSTEM_PARAM_LOCALE, Local.zh_CN.toString());
		paramValues.put(SYSTEM_PARAM_MESSAGE_FORMAT, CaritClient.MessageFormat.json.toString());
		paramValues.put(SYSTEM_PARAM_SESSION_ID, sessionId);
		return paramValues;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, String> paramValues=getInstance().buildParamValues("platform.getSession", "1.0");
		// 生成签名
		String sign=ClientUtils.sign(paramValues, getInstance().appSecret);
		// 不需要签名的参数放后面
		paramValues.put(SYSTEM_PARAM_SIGN, sign);
		
		StringBuilder urlStr=new StringBuilder(getInstance().serverUrl);
		for (Entry<String, String> e: paramValues.entrySet()) {
			urlStr.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		HttpURLConnection conn=(HttpURLConnection) new URL(urlStr.toString()).openConnection(); 
		if (conn.getResponseCode() == 200) {
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String msg=reader.readLine();
			try{
				LogonResponse response=(LogonResponse) JsonUtil.jsonToObject(msg, LogonResponse.class);
				getInstance().setSessionId(response.getSessionId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			reader.close();
		}
		conn.disconnect();
		
		
		// 登录
		Map<String, String> paramValues2=getInstance().buildParamValues("account.logon", "1.0", getInstance().getSessionId());
		paramValues2.put("email", "xiegc@carit.com.cn");
		String sign2=ClientUtils.sign(paramValues2, ClientHolder.INSTANCE.appSecret);
		// 不需要签名的参数放后面
		paramValues2.put("sign", sign2);
		paramValues2.put("password", "123456");
		
		
		
		StringBuilder urlStr2=new StringBuilder(ClientHolder.INSTANCE.serverUrl);
		for (Entry<String, String> e: paramValues2.entrySet()) {
			urlStr2.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		HttpURLConnection conn2=(HttpURLConnection) new URL(urlStr2.toString()).openConnection();
		conn2.setRequestMethod("POST");
		if (conn.getResponseCode() == 200) {
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
			String msg=reader.readLine();
			try {
				AccountResponse account=(AccountResponse) JsonUtil.jsonToObject(msg, AccountResponse.class);
				System.out.println(account);
			} catch (Exception e) {
				try {
					ErrorResponse error=(ErrorResponse) JsonUtil.jsonToObject(msg, ErrorResponse.class);
					System.out.println(error);
				} catch (Exception e2) {
					e.printStackTrace();
				}
			}
			
			reader.close();
		}
		conn2.disconnect();
	}
}

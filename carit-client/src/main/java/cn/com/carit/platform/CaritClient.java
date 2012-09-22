package cn.com.carit.platform;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class CaritClient {
	
	public enum MessageFormat{
		xml, json;
	}
	
	public enum Local{
		zh_CN, en;
	}
	
	//服务地址
    private String serverUrl;

    //应用键
    private String appKey;

    //应用密钥
    private String appSecret;

    private String sessionId;
    
    private String locale = Locale.SIMPLIFIED_CHINESE.toString();
    
    private static class ClientHolder {
    	CaritClient INSTANCE=new CaritClient();
    }
    
    private CaritClient(){
    	Properties prop=new Properties();
    	try {
			prop.load(ClassLoader.getSystemResourceAsStream("config..properties"));
			serverUrl=prop.getProperty("serverUrl");
			appKey=prop.getProperty("appKey");
			appSecret=prop.getProperty("appSecret");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
    
}

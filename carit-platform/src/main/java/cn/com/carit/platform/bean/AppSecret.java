package cn.com.carit.platform.bean;


public class AppSecret extends BaseBean {
	private String appSecret;
	private String appName;
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}

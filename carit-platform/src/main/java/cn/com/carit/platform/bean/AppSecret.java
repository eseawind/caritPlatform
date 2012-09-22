package cn.com.carit.platform.bean;


public class AppSecret extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3307902769340955976L;
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

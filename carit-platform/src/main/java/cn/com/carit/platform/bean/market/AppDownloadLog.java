package cn.com.carit.platform.bean.market;

import java.util.Date;

public class AppDownloadLog {
	/**
	 * id
	 */
	private int id;
	/**
	 * accountId
	 */
	private Integer accountId;
	
	private String userName;
	/**
	 * appId
	 */
	private Integer appId;
	
	private String version;

	private String appName;
	
	private String enName;
	
	/**
	 * downloadTime
	 */
	private Date downloadTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}
	
}

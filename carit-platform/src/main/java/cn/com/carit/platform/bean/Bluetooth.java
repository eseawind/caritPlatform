package cn.com.carit.platform.bean;

import java.io.Serializable;

public class Bluetooth implements Serializable {

	private static final long serialVersionUID = -7616921765983978062L;
	
	private String deviceId;
	private Integer accountId;
	private String deviceName;
	
	public Bluetooth() {
		super();
	}
	
	public Bluetooth(String deviceId, Integer accountId, String deviceName) {
		super();
		this.deviceId = deviceId;
		this.accountId = accountId;
		this.deviceName = deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
}

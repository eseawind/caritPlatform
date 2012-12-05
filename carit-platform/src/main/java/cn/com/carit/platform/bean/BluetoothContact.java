package cn.com.carit.platform.bean;

import cn.com.carit.common.Constants;


public class BluetoothContact extends BaseBean {

	private static final long serialVersionUID = -7577356726498367428L;
	
	private String deviceId;
	private Integer accountId;
	private String bluetoothId;
	private String callName;
	private String callNum;
	private String callNameKey;
	private String callType;
	
	public BluetoothContact() {
		super();
	}
	
	public BluetoothContact(String deviceId, Integer accountId, String bluetoothId,
			String callName, String callNum, String callNameKey, String callType) {
		super();
		this.status = Constants.STATUS_VALID;
		this.deviceId = deviceId;
		this.accountId = accountId;
		this.bluetoothId=bluetoothId;
		this.callName = callName;
		this.callNum = callNum;
		this.callNameKey = callNameKey;
		this.callType = callType;
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
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public String getCallNum() {
		return callNum;
	}
	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
	public String getCallNameKey() {
		return callNameKey;
	}
	public void setCallNameKey(String callNameKey) {
		this.callNameKey = callNameKey;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getBluetoothId() {
		return bluetoothId;
	}

	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}

}

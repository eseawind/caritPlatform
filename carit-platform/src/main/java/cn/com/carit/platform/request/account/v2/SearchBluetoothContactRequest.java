package cn.com.carit.platform.request.account.v2;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


public class SearchBluetoothContactRequest extends SearchByAccountRequest {
	@NotEmpty
	private String deviceId;
	
	private String bluetoothId;
	private String callName;
	@Pattern(regexp="\\d*")
	private String callNum;
	private String callNameKey;
	private String callType;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

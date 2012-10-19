package cn.com.carit.platform.request.account.v2;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.platform.request.account.AccountRequest;

public class AddEquipmentRequest extends AccountRequest {
	

	public AddEquipmentRequest() {
		super();
	}

	public AddEquipmentRequest(String email, String password) {
		super(email);
	}

	public AddEquipmentRequest(String email, String password, String deviceId) {
		super(email);
		this.deviceId = deviceId;
	}

	@NotEmpty
	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}

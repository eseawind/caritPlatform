package cn.com.carit.platform.request.account;

import org.hibernate.validator.constraints.NotEmpty;

public class AddEquipmentRequest extends AccountRequest {
	

	public AddEquipmentRequest() {
		super();
	}

	public AddEquipmentRequest(String email, String password) {
		super(email, password);
	}

	public AddEquipmentRequest(String email, String password, String deviceId) {
		super(email, password);
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

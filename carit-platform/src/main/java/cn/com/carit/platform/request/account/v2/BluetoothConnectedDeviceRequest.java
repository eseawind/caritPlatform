package cn.com.carit.platform.request.account.v2;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.platform.request.account.AccountRequest;


public class BluetoothConnectedDeviceRequest extends AccountRequest {
	@NotEmpty
	private String bluetoothId;

	public String getBluetoothId() {
		return bluetoothId;
	}

	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}
	
}

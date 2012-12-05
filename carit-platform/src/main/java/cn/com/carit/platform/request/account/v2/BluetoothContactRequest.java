package cn.com.carit.platform.request.account.v2;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.platform.request.account.AccountRequest;


/**
 * <p>
 * <b>功能说明：</b>上传蓝牙通讯录请求模型
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-11-15
 */
public class BluetoothContactRequest extends AccountRequest {

	/** 车机设备ID */
	@NotEmpty
	private String deviceId;
	@NotEmpty
	private String bluetoothId;
	@NotEmpty
	private String bluetoothName;
	
	/** 通讯录列表 */
	@NotEmpty
	private String contacts;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBluetoothId() {
		return bluetoothId;
	}

	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
}

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

	/** 蓝牙设备ID */
	@NotEmpty
	private String deviceId;
	/** 蓝牙设备名称 */
	@NotEmpty
	private String deviceName;
	
	/** 通讯录列表 */
	@NotEmpty
	private String contacts;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
}

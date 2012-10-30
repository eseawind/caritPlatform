package cn.com.carit.platform.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;

public class NewestObdDataRequest extends AbstractRopRequest {
	@NotEmpty
    private String deviceId;
	
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private int accountId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
}

package cn.com.carit.platform.request.partner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;

public class BindingDeviceRequest extends AbstractRopRequest {

	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer partnerId;
	
	@NotEmpty
	private String deviceId;

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}

package cn.com.carit.platform.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;

public class ObdDataUploadRequest extends AbstractRopRequest {

	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private int accountId;

	@NotEmpty
	private String data;
	@NotEmpty
	private String location;
	@NotEmpty
	private String deviceId;
	@NotNull
	private long date;
	
	private String error;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
}

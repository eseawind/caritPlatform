package cn.com.carit.platform.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;

public class ObdDataUploadRequest extends AbstractRopRequest {

	@NotEmpty
	private String data;
	@NotEmpty
	private String location;
	@NotEmpty
	private String deviceID;
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
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
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
	
}

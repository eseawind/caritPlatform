package cn.com.carit.platform.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchLoactionRequest extends SearchRequest {
	
	public static final int TYPE_TODAY=1;
	public static final int TYPE_CUSTOMIZED=2;
	
	@NotEmpty
    private String deviceId;
	
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer accountId;
	
	/**
	 * 查询数据类型：0 所有；1 当天；2 自定义
	 */
	@NotNull
	@Min(value=0)
	@Max(value=2)
	private int type;
	
	private long startTime;
	
	private long endTime;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
}

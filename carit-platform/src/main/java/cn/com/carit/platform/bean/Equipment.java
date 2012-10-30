package cn.com.carit.platform.bean;

import java.io.Serializable;
import java.util.Date;

public class Equipment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5401439223731511903L;
	
	public static final int MAX_BOUND_ACCOUNT_COUNT=3;
	
	private String deviceId;
	private Integer accountId;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

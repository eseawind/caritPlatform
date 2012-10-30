package cn.com.carit.platform.bean;

import java.util.Arrays;
import java.util.Date;

/**
 * OBD 数据对象
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-10
 */
public class ObdData {

	private int id;

	private Date date;
	private String deviceId;
	private Integer accountId;
	private String location;
	private String error;
	private Date createTime;
	
	public ObdData() {
	}
	
	public ObdData(Date date, String deviceId, Integer accountId,
			String location, String error) {
		this.date = date;
		this.deviceId = deviceId;
		this.accountId = accountId;
		this.location = location;
		this.error = error;
	}


	private int [] values=new int[19];
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int[] getValues() {
		return values;
	}
	public void setValues(int[] values) {
		this.values = values;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	@Override
	public String toString() {
		return "ObdData [id=" + id + ", date=" + date + ", deviceId="
				+ deviceId + ", accountId=" + accountId + ", location="
				+ location + ", error=" + error + ", createTime=" + createTime
				+ ", values=" + Arrays.toString(values) + "]";
	}
	
}

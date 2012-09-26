package cn.com.carit.platform.bean;


public class Location {

	private int id;
	
	private String deviceId;
	
	private Double lat;

	private Double lng;

	private Long createTime;
	
//	private Date updateTime;

	public Location() {
	}
	
	public Location(String deviceId, Double lat, Double lng, Long createTime) {
		super();
		this.deviceId = deviceId;
		this.lat = lat;
		this.lng = lng;
		this.createTime = createTime;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
/*
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
*/
}

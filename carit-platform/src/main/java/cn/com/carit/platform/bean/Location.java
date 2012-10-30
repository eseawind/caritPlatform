package cn.com.carit.platform.bean;


public class Location {

	private int id;
	
	private String deviceId;
	
	private Integer accountId;
	
	private Double lat;

	private Double lng;

	private Long createTime;
	
//	private Date updateTime;

	public Location() {
	}
	
	public Location(String deviceId, Integer accountId, Double lat, Double lng, Long createTime) {
		super();
		this.deviceId = deviceId;
		this.accountId = accountId;
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

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

}

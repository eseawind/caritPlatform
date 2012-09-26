package cn.com.carit.platform.request;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "location")
public class LocationRequest {
	@NotNull
	private double lat;
	@NotNull
	private double lng;
	@NotNull
	private long time;
	
	public LocationRequest() {
	}
	
	public LocationRequest(double lat, double lng, long time) {
		this.lat = lat;
		this.lng = lng;
		this.time = time;
	}

	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "LocationRequest [lat=" + lat + ", lng=" + lng + ", time="
				+ time + "]";
	}
	
}

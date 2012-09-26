package cn.com.carit.platform.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "location")
public class LocationResponse {
	@XmlAttribute
	private double lat;
	@XmlAttribute
	private double lng;
	@XmlAttribute
	private long time;
	
	public LocationResponse() {
		
	}
	
	public LocationResponse(double lat, double lng, long time) {
		super();
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
	
}

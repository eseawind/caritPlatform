package cn.com.carit.platform.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class LocationListResponse {

	@XmlAnyAttribute
	private List<LocationResponse> lists;

	
	public LocationListResponse() {
		super();
	}

	public LocationListResponse(List<LocationResponse> lists) {
		super();
		this.lists = lists;
	}

	public List<LocationResponse> getLists() {
		return lists;
	}

	public void setLists(List<LocationResponse> lists) {
		this.lists = lists;
	}
	
}

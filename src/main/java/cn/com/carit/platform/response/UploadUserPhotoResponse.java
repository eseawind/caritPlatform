package cn.com.carit.platform.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class UploadUserPhotoResponse {

	@XmlAttribute
	private String photo;
	
	@XmlAttribute
	private String thumbPhoto;
	
	public UploadUserPhotoResponse() {
	}
	
	public UploadUserPhotoResponse(String photo, String thumbPhoto) {
		this.photo = photo;
		this.thumbPhoto = thumbPhoto;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getThumbPhoto() {
		return thumbPhoto;
	}

	public void setThumbPhoto(String thumbPhoto) {
		this.thumbPhoto = thumbPhoto;
	}

}

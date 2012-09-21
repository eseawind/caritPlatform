package cn.com.carit.platform.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class UploadUserPhotoResponse {
	@XmlAttribute
	private boolean successful = false;

	@XmlAttribute
	private byte [] photo;
	
	@XmlAttribute
	private byte [] thumbPhoto;

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public byte[] getThumbPhoto() {
		return thumbPhoto;
	}

	public void setThumbPhoto(byte[] thumbPhoto) {
		this.thumbPhoto = thumbPhoto;
	}
	
}

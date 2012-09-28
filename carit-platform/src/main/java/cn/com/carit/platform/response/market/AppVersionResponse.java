package cn.com.carit.platform.response.market;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "version")
public class AppVersionResponse {
	@XmlAttribute
	private int id;
	@XmlAttribute
	private String version;
	@XmlAttribute
	private String size;
	@XmlAttribute
	private String filePath;
	@XmlAttribute
	private String newFeatures;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getNewFeatures() {
		return newFeatures;
	}
	public void setNewFeatures(String newFeatures) {
		this.newFeatures = newFeatures;
	}
	
}

package cn.com.carit.platform.response.market;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "application")
public class ApplicationResponse {
	
	@XmlAttribute
	private int id;
	@XmlAttribute
	private String appName;
	@XmlAttribute
	private String version;
	@XmlAttribute
	private String icon;
	@XmlAttribute
	private String bigIcon;
	@XmlAttribute
	private String developer;
	@XmlAttribute
	private String developerWebsite;
	@XmlAttribute
	private String developerEmail;
	@XmlAttribute
	private String catalogName;
	@XmlAttribute
	private String size;
	@XmlAttribute
	private String appFilePath;
	@XmlAttribute
	private String platform;
	@XmlAttribute
	private String supportLanguages;
	@XmlAttribute
	private Double price;
	@XmlAttribute
	private Integer downCount;
	@XmlAttribute
	private Integer appLevel;
	@XmlAttribute
	private String description;
	@XmlAttribute
	private String permissionDesc;
	@XmlAttribute
	private String features;
	/**
	 * 截图路径，以“;”分隔，Json中不返回此字段，将处理成数组 imageList 返回
	 */
	@XmlAttribute
	private String [] imageList;
	@XmlAttribute
	private Date updateTime;
	
	@XmlAttribute
	private String mainPic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getDeveloperWebsite() {
		return developerWebsite;
	}

	public void setDeveloperWebsite(String developerWebsite) {
		this.developerWebsite = developerWebsite;
	}

	public String getDeveloperEmail() {
		return developerEmail;
	}

	public void setDeveloperEmail(String developerEmail) {
		this.developerEmail = developerEmail;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAppFilePath() {
		return appFilePath;
	}

	public void setAppFilePath(String appFilePath) {
		this.appFilePath = appFilePath;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSupportLanguages() {
		return supportLanguages;
	}

	public void setSupportLanguages(String supportLanguages) {
		this.supportLanguages = supportLanguages;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public Integer getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(Integer appLevel) {
		this.appLevel = appLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String[] getImageList() {
		return imageList;
	}

	public void setImageList(String[] imageList) {
		this.imageList = imageList;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}
	
}

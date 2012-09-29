package cn.com.carit.platform.request.market;

import cn.com.carit.platform.request.SearchRequest;

/**
 * <p>
 * <b>功能说明：</b>应用查询请求模型
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-9-27
 */
public class SearchApplicationRequest extends SearchRequest {
	private int appId;
	
	private String appName;
	private String version;
	private String developer;

	private String developerWebsite;

	private String developerEmail;
	
	/** 分类名称 */
	private Integer catalogId;
	private String catalogName;
	private String size;
	
	/** 适用平台 */
	private String platform;
	/** 支持语言 */
	private String supportLanguages;
	
	/** 下载次数 */
	private Integer downCount;
	/**  应用评级 */
	private Integer appLevel;
	private Double price;
	private Integer status;
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
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
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}

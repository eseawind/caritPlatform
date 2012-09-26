package cn.com.carit.platform.bean;

public class Navigation extends BaseBean {

	private String name;
	private String url;
	private Integer catalogId;
	private Integer displayIndex;
	private String cssClass;
	private String logo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public Integer getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Override
	public String toString() {
		return "Navigation [name=" + name + ", url=" + url + ", catalogId="
				+ catalogId + ", displayIndex=" + displayIndex + ", cssClass="
				+ cssClass + ", logo=" + logo + ", id=" + id + ", status="
				+ status + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
}

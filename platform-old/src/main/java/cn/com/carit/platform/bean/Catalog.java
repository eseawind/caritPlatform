package cn.com.carit.platform.bean;


public class Catalog extends BaseBean {

	private String name;
	private String description;
	private Integer displayIndex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	@Override
	public String toString() {
		return "Catalog [name=" + name + ", description=" + description
				+ ", displayIndex=" + displayIndex + ", id=" + id + ", status="
				+ status + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
}

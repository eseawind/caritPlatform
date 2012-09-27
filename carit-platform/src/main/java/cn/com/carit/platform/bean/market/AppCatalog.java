package cn.com.carit.platform.bean.market;

import cn.com.carit.platform.bean.BaseBean;

public class AppCatalog extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8203411821405953497L;

	private String name;
	
	private String enName;
	
	private String description;
	
	private String enDescription;
	
	private Integer displayIndex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnDescription() {
		return enDescription;
	}

	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}

	public Integer getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	
}

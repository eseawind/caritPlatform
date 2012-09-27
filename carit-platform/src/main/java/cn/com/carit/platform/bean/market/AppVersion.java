package cn.com.carit.platform.bean.market;

import cn.com.carit.platform.bean.BaseBean;

public class AppVersion extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6381164552344308090L;

	private Integer appId;
	
	private String version;
	
	private String size;
	
	private String filePath;
	
	private String newFeatures;
	
	private String enNewFeatures;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
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

	public String getEnNewFeatures() {
		return enNewFeatures;
	}

	public void setEnNewFeatures(String enNewFeatures) {
		this.enNewFeatures = enNewFeatures;
	}
	
}

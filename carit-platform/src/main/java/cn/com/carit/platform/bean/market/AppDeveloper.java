package cn.com.carit.platform.bean.market;

import cn.com.carit.platform.bean.BaseBean;

public class AppDeveloper extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1509721248050365597L;

	private String name;
	
	private String website;
	
	private String email;
	
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

package cn.com.carit.platform.request.market;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class BatchCheckAppUpdatedRequest extends AbstractRopRequest {

	private String language = Constants.DEAFULD_LANGUAGE;

	@NotEmpty
	private String apps;
	
	@NotEmpty
	private String versions;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getApps() {
		return apps;
	}

	public void setApps(String apps) {
		this.apps = apps;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

}

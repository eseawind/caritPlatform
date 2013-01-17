package cn.com.carit.platform.request.market;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class CheckAppUpdatedRequest extends AbstractRopRequest {
	private String language = Constants.DEAFULD_LANGUAGE;
	@NotEmpty
	private String packageName;
	@NotEmpty
	private String version;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}

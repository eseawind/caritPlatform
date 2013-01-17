package cn.com.carit.platform.request.market;

import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;

public class DownloadAppRequest extends AbstractRopRequest {

	@NotEmpty
	private String packageName;
	
	private String email;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}

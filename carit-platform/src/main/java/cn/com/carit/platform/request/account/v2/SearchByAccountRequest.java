package cn.com.carit.platform.request.account.v2;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.request.SearchRequest;

public class SearchByAccountRequest extends SearchRequest {
	
	@NotEmpty
    @Pattern(regexp = Constants.REGEXP_EMAIL)
	protected String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

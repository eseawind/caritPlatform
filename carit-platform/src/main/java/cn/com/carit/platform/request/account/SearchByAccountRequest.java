package cn.com.carit.platform.request.account;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.request.SearchRequest;

import com.rop.annotation.IgnoreSign;

public class SearchByAccountRequest extends SearchRequest {
	
	@NotEmpty
    @Pattern(regexp = Constants.REGEXP_EMAIL)
	protected String email;

    @IgnoreSign
    @NotEmpty
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
    protected String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package cn.com.carit.platform.request.account;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class CheckEmailRequest extends AbstractRopRequest {
	
	@NotEmpty
	@Pattern(regexp = Constants.REGEXP_EMAIL)
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package cn.com.carit.platform.request.account;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

public class UpdatePasswordRequest extends AccountRequest {

	@NotEmpty
	@Pattern(regexp = Constants.REGEXP_PASSWORD)
	private String newPassword;

	public UpdatePasswordRequest() {
	}

	public UpdatePasswordRequest(String email, String password) {
		super(email, password);
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}

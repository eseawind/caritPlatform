package cn.com.carit.platform.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;
import com.rop.request.UploadFile;

public class UploadUserPhotoRequest extends AbstractRopRequest {

	@NotEmpty
	@Pattern(regexp = Constants.REGEXP_EMAIL)
    private String email;

	@NotEmpty
    @IgnoreSign
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
    private String password;

    private UploadFile photo;

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

	public UploadFile getPhoto() {
		return photo;
	}

	public void setPhoto(UploadFile photo) {
		this.photo = photo;
	}
    
}


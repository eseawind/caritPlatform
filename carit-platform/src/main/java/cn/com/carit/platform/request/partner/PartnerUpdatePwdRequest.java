package cn.com.carit.platform.request.partner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

public class PartnerUpdatePwdRequest extends AbstractRopRequest {

	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer id;
	@IgnoreSign
	@NotEmpty
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
	private String oldPassword;
	@NotEmpty
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
	private String password;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

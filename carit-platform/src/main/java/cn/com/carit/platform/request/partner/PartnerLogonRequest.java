package cn.com.carit.platform.request.partner;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

public class PartnerLogonRequest extends AbstractRopRequest {
	@NotEmpty
	private String firmName;
	@IgnoreSign
    @NotEmpty
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
	private String password;
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

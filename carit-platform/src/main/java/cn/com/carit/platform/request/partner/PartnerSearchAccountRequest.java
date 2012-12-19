package cn.com.carit.platform.request.partner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.request.SearchRequest;

public class PartnerSearchAccountRequest extends SearchRequest {

	@NotNull
	@Min(value = 1)
	@Max(value = Integer.MAX_VALUE)
	private Integer partnerId;

	@Pattern(regexp = Constants.REGEXP_EMAIL)
	private String email;
	
	@Length(min = 1, max = 50)
	private String nickName;

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}

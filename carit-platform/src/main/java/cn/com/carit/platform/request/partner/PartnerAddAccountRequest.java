package cn.com.carit.platform.request.partner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.com.carit.platform.request.account.v3.RegisterAccountRequest;

public class PartnerAddAccountRequest extends RegisterAccountRequest {

	@NotNull
	@Min(value=0)
	@Max(value=Integer.MAX_VALUE)
	private Integer partnerId;

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	
}

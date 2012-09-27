package cn.com.carit.platform.request.market;

import javax.validation.constraints.NotNull;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class TopRequest extends AbstractRopRequest {

	private String language=Constants.DEAFULD_LANGUAGE;
	
	@NotNull
	private Integer limit;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
}

package cn.com.carit.platform.request.market;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class TopRequest extends AbstractRopRequest {

	private String language=Constants.DEAFULD_LANGUAGE;
	
	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer limit=10;

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

package cn.com.carit.platform.request.market;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class ViewApplicationRequest extends AbstractRopRequest {

	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private int appId;

	private String language=Constants.DEAFULD_LANGUAGE;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}

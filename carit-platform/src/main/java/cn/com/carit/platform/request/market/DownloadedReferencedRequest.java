package cn.com.carit.platform.request.market;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.com.carit.platform.request.SearchRequest;

public class DownloadedReferencedRequest extends SearchRequest {
	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer appId;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	
}

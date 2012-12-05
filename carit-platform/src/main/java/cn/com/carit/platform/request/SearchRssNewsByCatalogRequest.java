package cn.com.carit.platform.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SearchRssNewsByCatalogRequest extends SearchRequest {

	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private int catalogId;

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	
}

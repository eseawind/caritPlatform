package cn.com.carit.platform.request.market;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.com.carit.platform.request.SearchRequest;

public class AccountDownloadedRequest extends SearchRequest {
	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private int accountId;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
}

package cn.com.carit.platform.request.account;

import cn.com.carit.platform.request.SearchRequest;

public class SearchAccountRequest extends SearchRequest {

	private Integer accountId;
	
	private String email;
	
	private String nickName;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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

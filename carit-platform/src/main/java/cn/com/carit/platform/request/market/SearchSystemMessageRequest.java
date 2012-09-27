package cn.com.carit.platform.request.market;

import javax.validation.constraints.NotNull;

import cn.com.carit.platform.request.SearchRequest;

public class SearchSystemMessageRequest extends SearchRequest {
	
	@NotNull
	private Integer accountId;
	private Integer msgType;
	
	private Integer msgId;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	
}

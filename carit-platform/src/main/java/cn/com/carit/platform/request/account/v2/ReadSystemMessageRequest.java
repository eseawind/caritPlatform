package cn.com.carit.platform.request.account.v2;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.com.carit.platform.request.account.AccountRequest;


public class ReadSystemMessageRequest extends AccountRequest{
	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
    private Integer msgId;

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
    
}

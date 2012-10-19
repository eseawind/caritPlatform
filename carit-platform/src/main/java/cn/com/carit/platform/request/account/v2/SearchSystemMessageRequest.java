package cn.com.carit.platform.request.account.v2;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class SearchSystemMessageRequest extends SearchByAccountRequest {
	
	@Min(value=0)
	@Max(value=1)
	private Integer msgType;
	
	/**状态*/
	@Min(value=0)
	@Max(value=1)
	private Integer status;
	
	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

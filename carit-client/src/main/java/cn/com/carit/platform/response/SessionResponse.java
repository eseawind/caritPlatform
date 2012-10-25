package cn.com.carit.platform.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sessionResponse")
public class SessionResponse {
	
	@XmlAttribute
	private String sessionId;
	
	@XmlElement
	private AccountResponse account;

	
	public SessionResponse() {
		super();
	}

	public SessionResponse(String sessionId, AccountResponse account) {
		super();
		this.sessionId = sessionId;
		this.account = account;
	}



	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public AccountResponse getAccount() {
		return account;
	}

	public void setAccount(AccountResponse account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "SessionResponse [sessionId=" + sessionId + ", account="
				+ account + "]";
	}
	
}

package cn.com.carit.platform.request.account;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.rop.AbstractRopRequest;

public class CheckNicknameRequest extends AbstractRopRequest {
	@NotEmpty
	@Length(min=3 ,max=50)
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}

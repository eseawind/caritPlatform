package cn.com.carit.platform.request.account;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <pre>
 * 功能说明：注册账号请求模型
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-20
 */
public class RegisterAccountRequest extends LogonRequest {
	
    @NotEmpty
    @Length(min=3, max=50)
    protected String nickName;

	public RegisterAccountRequest() {
	}

	public RegisterAccountRequest(String email, String password){
		super(email, password);
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}

package cn.com.carit.platform.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

/**
 * <pre>
 * 功能说明：注册账号请求模型
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-20
 */
public class RegisterAccountRequest extends AbstractRopRequest {
	
	@NotEmpty
	@Pattern(regexp = Constants.REGEXP_EMAIL)
    private String email;

	@NotEmpty
    @IgnoreSign
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
    private String password;
    
    @NotEmpty
    @Length(min=3, max=50)
    private String nickName;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}

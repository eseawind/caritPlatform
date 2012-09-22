package cn.com.carit.platform.request.account;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

/**
 * <pre>
 * 功能说明：账号相关请求模型基类
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-22
 */
public class AccountRequest extends AbstractRopRequest {
	
	@NotEmpty
    @Pattern(regexp = Constants.REGEXP_EMAIL)
	protected String email;

    @IgnoreSign
    @NotEmpty
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
    protected String password;
    
	public AccountRequest() {
	}

	public AccountRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

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
    
}

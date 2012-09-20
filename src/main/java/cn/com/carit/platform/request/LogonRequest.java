package cn.com.carit.platform.request;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <pre>
 * 功能说明：登录请求
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-19
 */
public class LogonRequest extends AbstractRopRequest{
	
	@NotEmpty
    @Pattern(regexp = Constants.REGEXP_EMAIL)
    private String email;

    @IgnoreSign
    @NotEmpty
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
    private String password;
    
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


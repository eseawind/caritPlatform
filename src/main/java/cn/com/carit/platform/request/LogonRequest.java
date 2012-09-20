package cn.com.carit.platform.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

import javax.validation.constraints.Pattern;

/**
 * <pre>
 * 功能说明：登录请求
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-19
 */
public class LogonRequest extends AbstractRopRequest{
	
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    private String email;

    @IgnoreSign
    @Pattern(regexp = "\\w{6,30}")
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


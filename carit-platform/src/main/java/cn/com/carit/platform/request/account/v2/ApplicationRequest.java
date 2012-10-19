package cn.com.carit.platform.request.account.v2;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import cn.com.carit.platform.request.account.AccountRequest;


/**
 * <pre>
 * 功能说明：用户下载应用下载请求模型
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-22
 */
public class ApplicationRequest extends AccountRequest {

	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private int appId;

	
	public ApplicationRequest() {
		super();
	}

	public ApplicationRequest(String email) {
		super(email);
	}

	public ApplicationRequest(String email, int appId) {
		super(email);
		this.appId = appId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
	
}

package cn.com.carit.platform.request.account;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;


/**
 * <pre>
 * 功能说明：用户下载应用下载请求模型
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-22
 */
public class DownloadApplicationRequest extends AccountRequest {

	@Null
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer appId;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	
}

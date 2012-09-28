package cn.com.carit.platform.action;

import cn.com.carit.Action;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.response.market.AppVersionResponse;

public interface AppVersionAction<AppVersion> extends Action<AppVersion> {
	/**
	 * 查询应用历史版本
	 * @param request
	 * @return
	 */
	JsonPage<AppVersionResponse> queryAppVersions(DownloadedReferencedRequest request);
}

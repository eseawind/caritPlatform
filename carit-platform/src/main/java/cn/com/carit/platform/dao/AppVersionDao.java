package cn.com.carit.platform.dao;

import cn.com.carit.Dao;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.response.market.AppVersionResponse;

public interface AppVersionDao<AppVersion> extends Dao<AppVersion>{

	/**
	 * 查询应用历史版本
	 * @param request
	 * @return
	 */
	JsonPage<AppVersionResponse> queryAppVersions(DownloadedReferencedRequest request);
}

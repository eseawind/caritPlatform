package cn.com.carit.platform.action;

import cn.com.carit.Action;

public interface AppDownloadLogAction<AppDownloadLog> extends
		Action<AppDownloadLog> {
	/**
	 * id 为{accountId}的用户是否下载过应用id为{appId}的应用
	 * @param accountId
	 * @param appId
	 * @return
	 */
	boolean hasDownloaded(int accountId, int appId);
}

package cn.com.carit.platform.dao;

import cn.com.carit.Dao;

public interface AppDownloadLogDao<AppDownloadLog> extends Dao<AppDownloadLog> {
	
	/**
	 * id 为{accountId}的用户是否下载过应用id为{appId}的应用
	 * @param accountId
	 * @param appId
	 * @return
	 */
	boolean hasDownloaded(int accountId, int appId);
}

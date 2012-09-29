package cn.com.carit.platform.dao;

import java.util.Date;
import java.util.List;

import cn.com.carit.Dao;
import cn.com.carit.platform.response.market.AppDownStat;

public interface AppDownloadLogDao<AppDownloadLog> extends Dao<AppDownloadLog> {
	
	/**
	 * id 为{accountId}的用户是否下载过应用id为{appId}的应用
	 * @param accountId
	 * @param appId
	 * @return
	 */
	boolean hasDownloaded(int accountId, int appId);
	
	/**
	 * 统计应用从 startDate 到当前时间
	 * @param appId
	 * @param startDate
	 * @return
	 */
	List<AppDownStat> statAppDownlog(int appId, Date startDate);
}

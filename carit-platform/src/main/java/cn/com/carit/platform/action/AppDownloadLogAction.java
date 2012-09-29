package cn.com.carit.platform.action;

import java.util.Map;

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
	
	/**
	 * 统计应用一个月的下载趋势
	 * @param appId
	 * @return map 包含两个key（categories和data），分表代表X轴数据和Y轴数据
	 */
	Map<String, Object> appOneMonthDownTrend(int appId);
}

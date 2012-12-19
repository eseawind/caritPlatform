package cn.com.carit.platform.action;

import java.util.List;
import java.util.Map;

import cn.com.carit.Action;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.request.SearchObdDataRequest;

public interface ObdDataAction<ObdData> extends Action<ObdData> {
	
	/**
	 * 按照设备Id查询最新记录
	 * @param deviceId
	 * @param accountId
	 * @return
	 */
	ObdData queryNewestData(String deviceId, int accountId);
	
	JsonPage<Map<String, Object>> query(SearchObdDataRequest request);
	
	List<ObdData> queryCurrentDataByAccount(int accountId);
	
	/**
	 * 日常清理
	 * @param keepCount 每人每机保留记录数
	 */
	void dailyClear(final int keepCount);
}

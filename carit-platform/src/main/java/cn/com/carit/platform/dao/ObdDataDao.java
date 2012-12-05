package cn.com.carit.platform.dao;

import java.util.List;
import java.util.Map;

import cn.com.carit.Dao;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.request.SearchObdDataRequest;

public interface ObdDataDao<ObdData> extends Dao<ObdData> {
	
	/**
	 * 保存数值
	 * @param dataId
	 * @param values
	 */
	void bathAddValue(int dataId, List<Integer> values);
	
	List<Integer> queryValues(int dataId);

	/**
	 * 按照设备Id查询最新记录
	 * @param deviceId
	 * @param accountId
	 * @return
	 */
	ObdData queryNewestData(String deviceId, int accountId);
	
	/**
	 * 删除同一设备同一上传时间的重复数据（保留最后上传的）
	 */
	void deleteDuplicateData();
	
	JsonPage<Map<String, Object>> query(SearchObdDataRequest request);
	
	List<ObdData> queryCurrentDataByAccount(int accountId);
}

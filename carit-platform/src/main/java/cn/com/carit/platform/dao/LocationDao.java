package cn.com.carit.platform.dao;

import java.util.List;
import java.util.Map;

import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.request.SearchLoactionRequest;

public interface LocationDao {

	int batchAdd(final List<Location> locationList);
	
	/**
	 * 按照查询请求对象查询数据
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> query(SearchLoactionRequest request);
	
	/**
	 * 分页方式查询
	 * @return
	 */
	JsonPage<Map<String, Object>> queryForPage(SearchLoactionRequest request);
	
	/**
	 * 删除同一设备同一上传时间的重复数据（保留最后上传的）
	 */
	void deleteDuplicateData(int accountId);
}

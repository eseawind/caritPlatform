package cn.com.carit.platform.dao;

import java.util.List;

import cn.com.carit.Dao;
import cn.com.carit.platform.request.SearchLoactionRequest;
import cn.com.carit.platform.response.LocationResponse;

public interface LocationDao<Location> extends Dao<Location> {

	int batchAdd(final List<Location> locationList);
	
	/**
	 * 按照查询请求对象查询数据
	 * @param request
	 * @return
	 */
	List<LocationResponse> query(SearchLoactionRequest request);
	
	/**
	 * 删除重复数据
	 */
	void deleteDuplicateData();
}

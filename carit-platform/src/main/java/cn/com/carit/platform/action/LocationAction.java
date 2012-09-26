package cn.com.carit.platform.action;

import java.util.List;

import cn.com.carit.Action;
import cn.com.carit.platform.request.SearchLoactionRequest;
import cn.com.carit.platform.response.LocationResponse;

public interface LocationAction<Location> extends Action<Location> {

	int batchAdd(final List<Location> locationList);
	
	/**
	 * 安装查询请求对象查询数据
	 * @param request
	 * @return
	 */
	List<LocationResponse> query(SearchLoactionRequest request);
}

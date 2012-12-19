package cn.com.carit.platform.action;

import java.util.List;
import java.util.Map;

import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.request.SearchLoactionRequest;

public interface LocationAction {

	int batchAdd(final List<Location> locationList);
	
	/**
	 * 安装查询请求对象查询数据
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> query(SearchLoactionRequest request);
	
	/**
	 * 分页方式查询
	 * @return
	 */
	/**
	 * 分页方式查询
	 * @return
	 */
	JsonPage<Map<String, Object>> queryForPage(SearchLoactionRequest request);
}

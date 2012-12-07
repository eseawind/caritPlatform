package cn.com.carit.platform.dao;

import java.util.List;
import java.util.Map;

public interface RegionDao {

	/**
	 * 获取所有省份
	 * @return
	 */
	List<Map<String, Object>> provinces();
	
	/**
	 * 按父区划ID查询
	 * @param regionId
	 * @return
	 */
	List<Map<String, Object>> queryRegionByParent(final int regionId);
}

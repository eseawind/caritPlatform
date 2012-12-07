package cn.com.carit.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.carit.DaoImpl;
import cn.com.carit.platform.dao.RegionDao;
@Repository
public class RegionDaoImpl extends DaoImpl implements RegionDao {

	private final String sql="select regin_id id, name from t_region where parent_id=?";
	
	@Override
	public List<Map<String, Object>> provinces() {
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return jdbcTemplate.queryForList(sql, 0);
	}

	@Override
	public List<Map<String, Object>> queryRegionByParent(int regionId) {
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return jdbcTemplate.queryForList(sql, regionId);
	}

}

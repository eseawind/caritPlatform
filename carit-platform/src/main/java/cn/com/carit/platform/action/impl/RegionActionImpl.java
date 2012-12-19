package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.com.carit.platform.action.RegionAction;
import cn.com.carit.platform.dao.RegionDao;
@Service
public class RegionActionImpl implements RegionAction {

	private RegionDao dao;
	@Override
	public List<Map<String, Object>> provinces() {
		return dao.provinces();
	}

	@Override
	public List<Map<String, Object>> queryRegionByParent(int regionId) {
		return dao.queryRegionByParent(regionId);
	}

}

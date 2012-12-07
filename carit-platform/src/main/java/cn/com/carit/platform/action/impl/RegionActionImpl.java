package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.platform.action.RegionAction;
import cn.com.carit.platform.dao.RegionDao;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
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

package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.LocationAction;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.dao.LocationDao;
import cn.com.carit.platform.request.SearchLoactionRequest;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class LocationActionImpl implements LocationAction<Location> {
	
	@Resource
	private LocationDao<Location> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(Location t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(Location t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public Location queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<Location> queryByExemple(Location t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Location> queryByExemple(Location t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<Location> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int batchAdd(final List<Location> locationList) {
		int insertRows=0;
		if (locationList!=null && locationList.size()>0) {
			insertRows = dao.batchAdd(locationList);
			// 删除重复数据
			dao.deleteDuplicateData();
		}
		return insertRows;
	}

	@Override
	public List<Map<String, Object>> query(SearchLoactionRequest request) {
		return dao.query(request);
	}

	@Override
	public JsonPage<Map<String, Object>> queryForPage(
			SearchLoactionRequest request) {
		return dao.queryForPage(request);
	}

}

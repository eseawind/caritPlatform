package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.LocationAction;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.dao.LocationDao;
import cn.com.carit.platform.request.SearchLoactionRequest;

@Service
@Transactional(readOnly=true)
public class LocationActionImpl implements LocationAction {
	
	@Resource
	private LocationDao dao;

	@Transactional(readOnly=false)
	@Override
	public int batchAdd(final List<Location> locationList) {
		int insertRows=0;
		if (locationList!=null && locationList.size()>0) {
			insertRows = dao.batchAdd(locationList);
			// 删除重复数据
			dao.deleteDuplicateData(locationList.get(0).getAccountId());
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

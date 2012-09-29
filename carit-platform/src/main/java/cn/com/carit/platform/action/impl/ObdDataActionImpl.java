package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.ObdDataAction;
import cn.com.carit.platform.bean.ObdData;
import cn.com.carit.platform.dao.ObdDataDao;
import cn.com.carit.platform.request.SearchObdDataRequest;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ObdDataActionImpl implements ObdDataAction<ObdData> {
	
	@Resource
	private ObdDataDao<ObdData> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(ObdData t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(ObdData t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public ObdData queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<ObdData> queryByExemple(ObdData t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<ObdData> queryByExemple(ObdData t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<ObdData> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int batchAdd(final List<ObdData> locationList) {
		int insertRows=0;
		if (locationList!=null && locationList.size()>0) {
			insertRows = dao.batchAdd(locationList);
			// 删除重复数据
//			dao.deleteDuplicateData();
		}
		return insertRows;
	}

	@Override
	public ObdData queryLastByDeviceId(String deviceId) {
		return dao.queryLastByDeviceId(deviceId);
	}

	@Override
	public JsonPage<ObdData> query(SearchObdDataRequest request) {
		return dao.query(request);
	}

}

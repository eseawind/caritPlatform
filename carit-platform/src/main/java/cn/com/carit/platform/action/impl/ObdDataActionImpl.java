package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.ObdDataAction;
import cn.com.carit.platform.bean.ObdData;
import cn.com.carit.platform.dao.ObdDataDao;
import cn.com.carit.platform.request.SearchObdDataRequest;

@Service
@Transactional(readOnly=true)
public class ObdDataActionImpl implements ObdDataAction<ObdData> {
	
	@Resource
	private ObdDataDao<ObdData> dao;

	@Transactional(readOnly=false)
	@Override
	public int add(ObdData t) {
		int id = dao.add(t);
		if (id>0) {
			dao.bathAddValue(id, t.getValues());
			dao.deleteDuplicateData();
		}
		return id;
	}

	@Transactional(readOnly=false)
	@Override
	public int update(ObdData t) {
		return dao.update(t);
	}

	@Transactional(readOnly=false)
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

	@Override
	public ObdData queryNewestData(String deviceId, int accountId) {
		ObdData data = dao.queryNewestData(deviceId, accountId);
		if(data!=null){
			data.setValues(dao.queryValues(data.getId()));
		}
		return data;
	}

	@Override
	public JsonPage<Map<String, Object>> query(SearchObdDataRequest request) {
		return dao.query(request);
	}

	@Override
	public List<ObdData> queryCurrentDataByAccount(int accountId) {
		return dao.queryCurrentDataByAccount(accountId);
	}

	@Override
	public void dailyClear(int keepCount) {
		dao.dailyClear(dao.queryAccountDevice(), keepCount);
		dao.deleteInvalidValue();
	}

}

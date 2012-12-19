package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.EquipmentAction;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.dao.EquipmentDao;
import cn.com.carit.platform.response.EquipmentResponse;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class EquipmentActionImpl implements EquipmentAction<Equipment> {
	
	@Resource
	private EquipmentDao<Equipment> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(Equipment t) {
		dao.addReference(t.getAccountId(), t.getDeviceId());
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(Equipment t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public Equipment queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<Equipment> queryByExemple(Equipment t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Equipment> queryByExemple(Equipment t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<Equipment> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<EquipmentResponse> queryByAccount(int accountId) {
		return dao.queryByAccount(accountId);
	}

	@Override
	public int checkBounding(final int accountId, final  String deviceId) {
		return dao.checkBounding(accountId, deviceId);
	}

	@Override
	public int queryAccountCountByDeviceId(String deviceId) {
		return dao.queryAccountCountByDeviceId(deviceId);
	}

	@Override
	public Equipment queryByDeviceId(String deviceId) {
		return dao.queryByDeviceId(deviceId);
	}
	
}

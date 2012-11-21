package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.BluetoothContactAction;
import cn.com.carit.platform.bean.BluetoothContact;
import cn.com.carit.platform.dao.BluetoothContactDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class BluetoothContactActionImpl implements BluetoothContactAction<BluetoothContact> {
	
	@Resource
	private BluetoothContactDao<BluetoothContact> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(BluetoothContact t) {
		// TODO
		return 0;
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(BluetoothContact t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public BluetoothContact queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<BluetoothContact> queryByExemple(BluetoothContact t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<BluetoothContact> queryByExemple(BluetoothContact t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<BluetoothContact> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<Map<String, Object>> queryBluetoothByAccount(int accountId) {
		return dao.queryBluetoothByAccount(accountId);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void delete(int accountId, String deviceId) {
		dao.delete(accountId, deviceId);
		// 删除关联
		dao.deleteReference(accountId, deviceId);
	}

	@Override
	public JsonPage<Map<String, Object>> queryByDeviceAndAccount(final String deviceId,
			final int accountId, 
			final String callName,
			final String callNameKey,
			final String callNum, 
			final DataGridModel dgm) {
		return dao.queryByDeviceAndAccount(deviceId, accountId, callName, callNameKey, callNum, dgm);
	}

	@Override
	public List<Map<String, Object>> queryAllByDeviceAndAccount(
			String deviceId, int accountId) {
		return dao.queryAllByDeviceAndAccount(deviceId, accountId);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void uploadContact(List<BluetoothContact> list, int accountId,
			String deviceId, String deviceName) {
		// 删除旧数据
		dao.delete(accountId, deviceId);
		dao.deleteReference(accountId, deviceId);
		// 批量增加
		dao.batchAdd(list);
		// 增加关联
		dao.addReference(accountId, deviceId, deviceName);
	}

}

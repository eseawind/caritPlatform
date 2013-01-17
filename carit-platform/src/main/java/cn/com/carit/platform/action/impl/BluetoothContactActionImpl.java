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
@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
public class BluetoothContactActionImpl implements BluetoothContactAction<BluetoothContact> {
	
	@Resource
	private BluetoothContactDao<BluetoothContact> dao;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int add(BluetoothContact t) {
		// TODO
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int update(BluetoothContact t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
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

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void delete(int accountId, String deviceId, String bluetoothId) {
		dao.delete(accountId, deviceId, bluetoothId);
		// 删除关联
		dao.deleteReference(accountId, deviceId, bluetoothId);
	}

	@Override
	public JsonPage<Map<String, Object>> query(
			final String deviceId,
			final int accountId, 
			final String bluetoothId,
			final String callName,
			final String callNameKey,
			final String callNum, 
			final DataGridModel dgm) {
		return dao.query(deviceId, accountId, bluetoothId, callName,
				callNameKey, callNum, dgm);
	}

	@Override
	public List<Map<String, Object>> queryAll(
			String deviceId, int accountId, String bluetoothId) {
		return dao.queryAll(deviceId, accountId, bluetoothId);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void uploadContact(List<BluetoothContact> list, int accountId,
			String deviceId, String bluetoothName, String bluetoothId) {
		// 删除旧数据
		dao.delete(accountId, deviceId, bluetoothId);
		dao.deleteReference(accountId, deviceId, bluetoothId);
		// 批量增加
		dao.batchAdd(list);
		// 增加关联
		dao.addReference(accountId, deviceId, bluetoothId, bluetoothName);
	}

	@Override
	public List<Map<String, Object>> queryConnectedDevices(int accountId,
			String bluetoothId) {
		return dao.queryConnectedDevices(accountId, bluetoothId);
	}

}

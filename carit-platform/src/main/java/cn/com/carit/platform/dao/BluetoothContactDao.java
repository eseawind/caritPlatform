package cn.com.carit.platform.dao;

import java.util.List;
import java.util.Map;

import cn.com.carit.Dao;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;

public interface BluetoothContactDao<BluetoothContact> extends Dao<BluetoothContact> {
	
	/**
	 * 按账号Id查询设备列表
	 * @param accountId
	 * @return
	 */
	List<Map<String, Object>> queryBluetoothByAccount(final int accountId);
	
	
	/**
	 * 添加关联
	 * @param accountId
	 * @param deviceId
	 * @param bluetoothId
	 * @param bluetoothName
	 */
	void addReference(final int accountId, final String deviceId, final String bluetoothId, final String bluetoothName);
	
	/**
	 * 按帐号Id+车机设备ID+蓝牙设备Id删除
	 * @param accountId
	 * @param deviceId
	 * @param bluetoothId
	 */
	void delete(final int accountId, final String deviceId, final String bluetoothId);
	
	/**
	 * 按帐号Id+车机设备ID+蓝牙设备Id删除关联记录
	 * @param accountId
	 * @param deviceId
	 * @param bluetoothId
	 */
	void deleteReference(final int accountId, final String deviceId, final String bluetoothId);
	/**
	 * 按设备+帐号+蓝牙分页查询
	 * @param deviceId
	 * @param accountId
	 * @param bluetoothId
	 * @param callName
	 * @param callNameKey
	 * @param callNum
	 * @param dgm
	 * @return
	 */
	JsonPage<Map<String, Object>> query(
			final String deviceId,
			final int accountId, 
			final String bluetoothId,
			final String callName,
			final String callNameKey,
			final String callNum, 
			final DataGridModel dgm);
	
	/**
	 * 按设备+帐号+蓝牙查询
	 * @param deviceId
	 * @param accountId
	 * @param bluetoothId
	 * @return
	 */
	List<Map<String, Object>> queryAll(final String deviceId, final int accountId, final String bluetoothId);
	
	/**
	 * 批量增加
	 * @param request
	 */
	int batchAdd(List<BluetoothContact> list);
	
	/**
	 * 查询蓝牙连接过的设备信息
	 * @param accountId
	 * @param bluetoothId
	 * @return
	 */
	List<Map<String, Object>> queryConnectedDevices(final int accountId, final String bluetoothId);
}

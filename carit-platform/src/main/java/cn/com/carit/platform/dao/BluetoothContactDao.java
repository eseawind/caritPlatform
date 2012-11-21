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
	 * @param deviceName
	 */
	void addReference(final int accountId, final String deviceId, final String deviceName);
	
	/**
	 * 按帐号Id蓝牙设备Id删除记录
	 * @param accountId
	 * @param deviceId
	 */
	void delete(final int accountId, final String deviceId);
	
	/**
	 * 按帐号Id蓝牙设备Id删除关联记录
	 * @param account
	 * @param deviceId
	 */
	void deleteReference(final int accountId, final String deviceId);
	
	/**
	 * 按设备和帐号分页查询
	 * @param deviceId
	 * @param accountId
	 * @param callName
	 * @param callNameKey
	 * @param callNum
	 * @param dgm
	 * @return
	 */
	JsonPage<Map<String, Object>> queryByDeviceAndAccount(
			final String deviceId,
			final int accountId, 
			final String callName,
			final String callNameKey,
			final String callNum, 
			final DataGridModel dgm);
	
	/**
	 * 按设备和帐号查询所有
	 * @param deviceId
	 * @param accountId
	 * @return
	 */
	List<Map<String, Object>> queryAllByDeviceAndAccount(final String deviceId, final int accountId);
	
	/**
	 * 批量增加
	 * @param request
	 */
	int batchAdd(List<BluetoothContact> list);
}

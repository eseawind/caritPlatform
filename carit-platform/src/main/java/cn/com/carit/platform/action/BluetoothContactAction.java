package cn.com.carit.platform.action;

import java.util.List;
import java.util.Map;

import cn.com.carit.Action;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;

public interface BluetoothContactAction<BluetoothContact> extends Action<BluetoothContact> {
	
	/**
	 * 按账号Id查询设备列表
	 * @param accountId
	 * @return
	 */
	List<Map<String, Object>> queryBluetoothByAccount(final int accountId);
	
	
	/**
	 * 按帐号Id蓝牙设备Id删除记录
	 * @param accountId
	 * @param deviceId
	 */
	void delete(final int accountId, final String deviceId);
	
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
	 * 上传记录
	 * @param list
	 * @param accountId
	 * @param deviceId
	 * @param deviceName
	 */
	void uploadContact(final List<BluetoothContact> list, final int accountId, final String deviceId, final String deviceName);
	
}

package cn.com.carit.platform.dao;

import java.util.List;

import cn.com.carit.Dao;
import cn.com.carit.platform.response.EquipmentResponse;

public interface EquipmentDao<Equipment> extends Dao<Equipment> {
	
	/**
	 * 按账号Id查询设备列表
	 * @param accountId
	 * @return
	 */
	List<EquipmentResponse> queryByAccount(final int accountId);
	
	/**
	 * 绑定检测
	 * @param accountId
	 * @param deviceId
	 * @return
	 */
	int checkBounding(final int accountId, final  String deviceId);
	
	/**
	 * 按设备Id查询已绑定账号数量
	 * @param deviceId
	 * @return
	 */
	int queryAccountCountByDeviceId(final String deviceId);
	
	/**
	 * 添加关联
	 * @param accountId
	 * @param deviceId
	 */
	void addReference(final int accountId, final String deviceId);
}

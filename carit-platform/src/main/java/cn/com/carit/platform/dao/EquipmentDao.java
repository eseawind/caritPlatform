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
}

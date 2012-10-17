package cn.com.carit.platform.action;

import java.util.List;

import cn.com.carit.Action;
import cn.com.carit.platform.response.EquipmentResponse;

public interface EquipmentAction<Equipment> extends Action<Equipment> {
	/**
	 * 按账号Id查询设备列表
	 * @param accountId
	 * @return
	 */
	List<EquipmentResponse> queryByAccount(final int accountId);
}

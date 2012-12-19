package cn.com.carit.platform.dao;

import cn.com.carit.Dao;

public interface PartnerDao<Partner> extends Dao<Partner> {
	
	/**
	 * 按名字查询
	 * @param name
	 * @return
	 */
	Partner queryByName(String name);
	
	/**
	 * 检查名称是否存在
	 * @param name
	 * @return
	 */
	boolean checkName(String name);
	
	/**
	 * 查询绑定的4s店
	 * @param deviceId
	 * @return
	 */
	Partner queryBoundingPartner(final String deviceId);
}

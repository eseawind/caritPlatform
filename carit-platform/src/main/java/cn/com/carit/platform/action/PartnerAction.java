package cn.com.carit.platform.action;

import cn.com.carit.Action;

public interface PartnerAction<Partner> extends Action<Partner> {
	
	/**
	 * 按名字查询
	 * @param name
	 * @return
	 */
	Partner queryByName(String name);
	
	/**
	 * 登录
	 * @param id
	 * @param ip
	 */
	void logon(int id, String ip);
	
	/**
	 * 检查名称是否存在
	 * @param name
	 * @return
	 */
	boolean checkName(String name);
	
}

package cn.com.carit.platform.dao;

import java.util.List;

public interface NavigationDao<Navigation> extends BaseDao<Navigation> {
	
	/**
	 * 检查分类是否存在
	 * @param name
	 * @return
	 */
	int checkExisted(String name);
	
	/**
	 * 按账号查询
	 * @param accountId
	 * @return
	 */
	List<Navigation> queryByAccount(int accountId);
	
	/**
	 * 收藏
	 * @param accountId
	 * @param navigationId
	 * @return
	 */
	int addFavorite(int accountId, int navigationId);
	
	/**
	 * 删除收藏
	 * @param accountId
	 * @return
	 */
	int deleteByAccount(int accountId);
	
	/**
	 * 查询有效记录
	 * @return
	 */
	List<Navigation> queryEffective();

}

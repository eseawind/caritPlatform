package cn.com.carit.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface NavigationService<Navigation> extends BaseService<Navigation> {
	
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
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	int addFavorite(int accountId, String [] ids);
	
	/**
	 * 查询有效记录
	 * @return
	 */
	List<Navigation> queryEffective();
}

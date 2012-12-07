package cn.com.carit.platform.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;

public interface RssNewsDao<RssNews> {
	/**
	 * 增加
	 * @param t
	 * @return
	 */
	int add(final RssNews t);
	
	/**
	 * 批量增加
	 * @param list
	 */
	void bathAdd(final Collection<RssNews> list);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(final int id);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	Map<String, Object> queryById(final int id);
	
	/**
	 * 阅读正文
	 * @param id
	 * @return
	 */
	Map<String, Object> readNews(final int id);
	
	/**
	 * 分页的样例查询
	 * @param t
	 * @param dgm
	 * @return
	 */
	JsonPage<Map<String, Object>> queryByExemple(final RssNews t, final DataGridModel dgm);
	
	/**
	 * 不带分页的样例查询
	 * @param t
	 * @return
	 */
	List<Map<String, Object>> queryByExemple(final RssNews t);
	
	/**
	 * 不带分页的样例查询
	 * @param t
	 * @param limit
	 * @return
	 */
	List<Map<String, Object>> queryByExemple(final RssNews t, final int limit);
	
	/**
	 * 构建where子句
	 * @param args
	 * @param argTypes
	 * @param t
	 * @return
	 */
	String buildWhere(List<Object> args, List<Integer> argTypes, RssNews t);
	
	/**
	 * 检查是否已经爬取过
	 * @param sourceUrl
	 * @return
	 */
	boolean existed(final String sourceUrl);
	
}

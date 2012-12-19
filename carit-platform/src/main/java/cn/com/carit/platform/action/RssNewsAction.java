package cn.com.carit.platform.action;

import java.util.List;
import java.util.Map;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;

public interface RssNewsAction<RssNews> {
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
	void bathAdd(final List<RssNews> list);
	
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
	 * 按分类分页查询
	 * @param catalogId
	 * @param dgm
	 * @return
	 */
	JsonPage<Map<String, Object>> queryByCatalogId(final int catalogId, final DataGridModel dgm);
	
	/**
	 * 按分类查询
	 * @param catalogId
	 * @param limit
	 * @return
	 */
	List<Map<String, Object>> queryByCatalogId(final int catalogId, final int limit);
	
	/**
	 * 按分类批量删除数据
	 * @param catalogIds 分类Id类别
	 * @param keepCount 没个分类保留的数据上限
	 */
	void batchDelete(final List<Integer> catalogIds, final int keepCount);
}

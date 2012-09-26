package cn.com.carit.platform.dao;


public interface CatalogDao<Catalog> extends BaseDao<Catalog> {
	
	/**
	 * 检查分类是否存在
	 * @param name
	 * @return
	 */
	int checkExisted(String name);
}

package cn.com.carit.platform.service;

public interface CatalogService<Catalog> extends BaseService<Catalog> {
	/**
	 * 检查分类是否存在
	 * @param name
	 * @return
	 */
	int checkExisted(String name);
}

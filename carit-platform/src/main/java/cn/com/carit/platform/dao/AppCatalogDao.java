package cn.com.carit.platform.dao;

import java.util.List;

import cn.com.carit.Dao;
import cn.com.carit.platform.response.market.AppCatalogResponse;

public interface AppCatalogDao<AppCatalog> extends Dao<AppCatalog>{
	
	/**
	 * 按语言查询
	 * @param language
	 * @return
	 */
	List<AppCatalogResponse> queryByLanguage(String language);
}

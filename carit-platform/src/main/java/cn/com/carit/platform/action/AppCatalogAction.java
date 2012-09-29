package cn.com.carit.platform.action;

import java.util.List;

import cn.com.carit.Action;
import cn.com.carit.platform.response.market.AppCatalogResponse;

public interface AppCatalogAction<AppCatalog> extends Action<AppCatalog> {
	/**
	 * 按语言查询
	 * @param language
	 * @return
	 */
	List<AppCatalogResponse> queryByLanguage(String language);
}

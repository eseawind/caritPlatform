package cn.com.carit.platform.dao;

import java.util.List;

import cn.com.carit.Dao;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.request.market.SearchAppDeveloperRequest;
import cn.com.carit.platform.request.market.SearchApplicationRequest;
import cn.com.carit.platform.request.market.TopRequest;
import cn.com.carit.platform.request.market.ViewApplicationRequest;
import cn.com.carit.platform.response.market.AppDeveloperResponse;
import cn.com.carit.platform.response.market.ApplicationResponse;

public interface ApplicationDao<Application> extends Dao<Application>{

	/**
	 * 带分页的条件查询
	 * @param request
	 * @return
	 */
	JsonPage<ApplicationResponse> queryByExemple(SearchApplicationRequest request);
	
	ApplicationResponse queryAppById(ViewApplicationRequest request);
	
	/**
	 * 查询热门免费应用
	 * @param request
	 * @return
	 */
	List<ApplicationResponse> queryHotFree(TopRequest request);
	
	/**
	 * 查询热门免费新品
	 * @param request
	 * @return
	 */
	List<ApplicationResponse> queryHotNewFree(TopRequest request);
	
	
	/**
	 * 全文检索应用
	 * @param language
	 * @param ids
	 * @param dgm
	 * @return
	 */
	JsonPage<ApplicationResponse> fullTextSearch(String language, String ids, DataGridModel dgm);
	
	/**
	 * 查询用户下载记录
	 * @param accountId
	 * @param language
	 * @param dgm
	 * @return
	 */
	JsonPage<ApplicationResponse> queryAccountDownloadedApp(int accountId,
			String language, DataGridModel dgm);
	
	/**
	 * 查询下该应用的用户还下载过哪些应用
	 * @param request
	 * @return
	 */
	JsonPage<ApplicationResponse> queryDownloadedReferencedApps(DownloadedReferencedRequest request);
	
	/**
	 * 查询应用开发者
	 * @param request
	 * @return
	 */
	JsonPage<AppDeveloperResponse> queryAppDeveloper(SearchAppDeveloperRequest request);
}

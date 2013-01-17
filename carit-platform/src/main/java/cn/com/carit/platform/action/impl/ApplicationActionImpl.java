package cn.com.carit.platform.action.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sphx.api.SphinxException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.SphinxUtil;
import cn.com.carit.platform.action.ApplicationAction;
import cn.com.carit.platform.bean.market.Application;
import cn.com.carit.platform.dao.ApplicationDao;
import cn.com.carit.platform.request.market.CheckAppUpdated;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.request.market.FullTextSearchRequest;
import cn.com.carit.platform.request.market.SearchAppDeveloperRequest;
import cn.com.carit.platform.request.market.SearchApplicationRequest;
import cn.com.carit.platform.request.market.TopRequest;
import cn.com.carit.platform.request.market.ViewApplicationRequest;
import cn.com.carit.platform.response.market.AppDeveloperResponse;
import cn.com.carit.platform.response.market.ApplicationResponse;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ApplicationActionImpl implements ApplicationAction<Application> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private ApplicationDao<Application> dao;

	@Override
	public List<Application> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	@Override
	public int add(Application t) {
		return dao.add(t);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	@Override
	public int update(Application t) {
		return dao.update(t);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public Application queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<Application> queryByExemple(Application t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Application> queryByExemple(Application t) {
		return dao.queryByExemple(t);
	}

	@Override
	public JsonPage<ApplicationResponse> queryByExemple(
			SearchApplicationRequest request) {
		return dao.queryByExemple(request);
	}

	@Override
	public ApplicationResponse queryAppById(ViewApplicationRequest request) {
		return dao.queryAppById(request);
	}

	@Override
	public List<ApplicationResponse> queryHotFree(TopRequest request) {
		return dao.queryHotFree(request);
	}

	@Override
	public List<ApplicationResponse> queryHotNewFree(TopRequest request) {
		return dao.queryHotNewFree(request);
	}

	@Override
	public JsonPage<ApplicationResponse> fullTextSearch(
			FullTextSearchRequest request) {
		JsonPage<ApplicationResponse> jsonPage = null;
		try {
			String ids = SphinxUtil.getInstance().getApplicationIdsAsStr(
					request.getKeyword());
			if (StringUtils.hasText(ids)) {
				DataGridModel dgm = new DataGridModel();
				// dgm.setSort(request.getSort());
				// dgm.setOrder(request.getOrder());
				dgm.setPage(request.getPage());
				dgm.setRows(request.getRows());
				jsonPage = dao.fullTextSearch(request.getLanguage(), ids, dgm);
			}
		} catch (SphinxException e) {
			logger.error("fullTextSearch application error...\r\n"
					+ e.getMessage());
			jsonPage = new JsonPage<ApplicationResponse>();
		}
		return jsonPage;
	}

	@Override
	public JsonPage<ApplicationResponse> queryAccountDownloadedApp(
			int accountId, String language, DataGridModel dgm) {
		return dao.queryAccountDownloadedApp(accountId, language, dgm);
	}

	@Override
	public JsonPage<ApplicationResponse> queryDownloadedReferencedApps(
			DownloadedReferencedRequest request) {
		return dao.queryDownloadedReferencedApps(request);
	}

	@Override
	public JsonPage<AppDeveloperResponse> queryAppDeveloper(
			SearchAppDeveloperRequest request) {
		return dao.queryAppDeveloper(request);
	}

	@Override
	public List<Map<String, Object>> queryTopApps(String language, int limit) {
		return dao.queryTopApps(language, limit);
	}

	@Override
	public Map<String, Object> appUpdated(String language, String packageName,
			String version) {
		return dao.appUpdated(language, packageName, version);
	}

	@Override
	public List<Map<String, Object>> appUpdatedBatchCheck(final String language, final List<CheckAppUpdated> batchList) {
		if (batchList == null || batchList.size() == 0) {
			return Collections.emptyList();
		}
		return dao.appUpdatedBatchCheck(language, batchList);
	}

	@Override
	public Application queryByPackageName(String packageName) {
		if (StringUtils.hasText(packageName)) {
			return dao.queryByPackageName(packageName);
		}
		return null;
	}

}

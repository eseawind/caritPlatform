package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AppVersionAction;
import cn.com.carit.platform.bean.market.AppVersion;
import cn.com.carit.platform.dao.AppVersionDao;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.response.market.AppVersionResponse;

@Service
@Transactional(readOnly=true)
public class AppVersionActionImpl implements AppVersionAction<AppVersion> {

	@Resource
	private AppVersionDao<AppVersion> dao;
	
	@Override
	public List<AppVersion> queryAll() {
		return dao.queryAll();
	}

	@Transactional(readOnly=false)
	@Override
	public int add(AppVersion t) {
		return dao.add(t);
	}

	@Transactional(readOnly=false)
	@Override
	public int update(AppVersion t) {
		return dao.update(t);
	}

	@Transactional(readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public AppVersion queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<AppVersion> queryByExemple(AppVersion t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<AppVersion> queryByExemple(AppVersion t) {
		return dao.queryByExemple(t);
	}

	@Override
	public JsonPage<AppVersionResponse> queryAppVersions(
			DownloadedReferencedRequest request) {
		return dao.queryAppVersions(request);
	}
	
}

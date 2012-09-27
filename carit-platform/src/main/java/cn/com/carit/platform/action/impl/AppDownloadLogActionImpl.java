package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AppDownloadLogAction;
import cn.com.carit.platform.bean.market.AppDownloadLog;
import cn.com.carit.platform.dao.AppDownloadLogDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppDownloadLogActionImpl implements AppDownloadLogAction<AppDownloadLog> {
	
	@Resource
	private AppDownloadLogDao<AppDownloadLog> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(AppDownloadLog t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(AppDownloadLog t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public AppDownloadLog queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<AppDownloadLog> queryByExemple(AppDownloadLog t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<AppDownloadLog> queryByExemple(AppDownloadLog t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<AppDownloadLog> queryAll() {
		return dao.queryAll();
	}

	@Override
	public boolean hasDownloaded(int accountId, int appId) {
		return dao.hasDownloaded(accountId, appId);
	}

}

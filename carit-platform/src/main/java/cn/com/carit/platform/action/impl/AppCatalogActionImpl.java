package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AppCatalogAction;
import cn.com.carit.platform.bean.market.AppCatalog;
import cn.com.carit.platform.dao.AppCatalogDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppCatalogActionImpl implements AppCatalogAction<AppCatalog> {

	@Resource
	private AppCatalogDao<AppCatalog> dao;
	
	@Override
	public List<AppCatalog> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(AppCatalog t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(AppCatalog t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public AppCatalog queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<AppCatalog> queryByExemple(AppCatalog t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<AppCatalog> queryByExemple(AppCatalog t) {
		return dao.queryByExemple(t);
	}
}

package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.ApplicationAction;
import cn.com.carit.platform.bean.market.Application;
import cn.com.carit.platform.dao.ApplicationDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ApplicationActionImpl implements ApplicationAction<Application> {

	@Resource
	private ApplicationDao<Application> dao;
	
	@Override
	public List<Application> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(Application t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(Application t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
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
}

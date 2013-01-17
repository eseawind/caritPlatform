package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AppSecretAction;
import cn.com.carit.platform.bean.AppSecret;
import cn.com.carit.platform.dao.AppSecretDao;

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
public class AppSecretActionImpl implements AppSecretAction<AppSecret> {
	
	@Resource
	private AppSecretDao<AppSecret> dao;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int add(AppSecret t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int update(AppSecret t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public AppSecret queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<AppSecret> queryByExemple(AppSecret t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<AppSecret> queryByExemple(AppSecret t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<AppSecret> queryAll() {
		return dao.queryAll();
	}

}

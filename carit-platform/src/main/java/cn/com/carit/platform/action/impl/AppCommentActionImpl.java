package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AppCommentAction;
import cn.com.carit.platform.bean.market.AppComment;
import cn.com.carit.platform.dao.AppCommentDao;
import cn.com.carit.platform.response.market.AppCommentResponse;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppCommentActionImpl implements AppCommentAction<AppComment> {
	
	@Resource
	private AppCommentDao<AppComment> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(AppComment t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(AppComment t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public AppComment queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<AppComment> queryByExemple(AppComment t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<AppComment> queryByExemple(AppComment t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<AppComment> queryAll() {
		return dao.queryAll();
	}

	@Override
	public JsonPage<AppCommentResponse> queryAppComments(int appId,
			DataGridModel dgm) {
		return dao.queryAppComments(appId, dgm);
	}

}

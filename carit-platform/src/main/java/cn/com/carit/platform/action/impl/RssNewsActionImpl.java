package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.RssNewsAction;
import cn.com.carit.platform.bean.RssNews;
import cn.com.carit.platform.dao.RssNewsDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class RssNewsActionImpl implements RssNewsAction<RssNews> {
	
	@Resource
	private RssNewsDao<RssNews> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(RssNews t) {
		return dao.add(t);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void bathAdd(List<RssNews> list) {
		dao.bathAdd(list);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}


	@Override
	public Map<String, Object> queryById(int id) {
		return dao.queryById(id);
	}
	
	@Override
	public Map<String, Object> readNews(int id) {
		return dao.readNews(id);
	}

	@Override
	public JsonPage<Map<String, Object>> queryByExemple(RssNews t,
			DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Map<String, Object>> queryByExemple(RssNews t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<Map<String, Object>> queryByExemple(RssNews t, int limit) {
		return dao.queryByExemple(t, limit);
	}

	@Override
	public JsonPage<Map<String, Object>> queryByCatalogId(int catalogId,
			DataGridModel dgm) {
		RssNews t = new RssNews();
		t.setCatalogId(catalogId);
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Map<String, Object>> queryByCatalogId(int catalogId, int limit) {
		RssNews t = new RssNews();
		t.setCatalogId(catalogId);
		return dao.queryByExemple(t, limit);
	}

}

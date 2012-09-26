package cn.com.carit.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Navigation;
import cn.com.carit.platform.dao.NavigationDao;
import cn.com.carit.platform.service.NavigationService;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class NavigationServiceImpl implements NavigationService<Navigation> {

	@Resource
	private NavigationDao<Navigation> navigationDao;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int saveOrUpdate(Navigation t) throws Exception {
		if (t.getId()>0) {
			return navigationDao.update(t);
		} else {
			return navigationDao.add(t);
		}
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return navigationDao.delete(id);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int batchDelete(String ids) {
		if (!StringUtils.hasText(ids)) {
			throw new IllegalArgumentException("ids is empty...");
		}
		return navigationDao.batchDelete(ids);
	}

	@Override
	public Navigation queryById(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return navigationDao.queryById(id);
	}

	@Override
	public JsonPage<Navigation> queryByExemple(Navigation t, DataGridModel dgm) {
		if (t==null) {
			throw new NullPointerException("sample is null..");
		}
		if (dgm==null) {
			throw new NullPointerException("DataGridModel is null..");
		}
		return navigationDao.queryByExemple(t, dgm);
	}

	@Override
	public List<Navigation> queryByExemple(Navigation t) {
		if (t==null) {
			throw new NullPointerException("sample is null..");
		}
		return navigationDao.queryByExemple(t);
	}

	@Override
	public List<Navigation> queryAll() {
		return navigationDao.queryAll();
	}

	@Override
	public int checkExisted(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("name is empty...");
		}
		return navigationDao.checkExisted(name);
	}
	
	@Override
	public List<Navigation> queryByAccount(int accountId) {
		if(accountId<=0){
			throw new IllegalArgumentException("accountId must be bigger than 0...");
		}
		return navigationDao.queryByAccount(accountId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public int addFavorite(int accountId, String [] ids) {
		if(accountId<=0){
			throw new IllegalArgumentException("accountId must be bigger than 0...");
		}
		if(ids==null){
			throw new IllegalArgumentException("ids must be not empty...");
		}
		navigationDao.deleteByAccount(accountId);
		for (String navigationId : ids) {
			navigationDao.addFavorite(accountId, Integer.parseInt(navigationId));
		}
		return ids.length;
	}

	@Override
	public List<Navigation> queryEffective() {
		return navigationDao.queryEffective();
	}

}

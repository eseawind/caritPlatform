package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.dao.AccountDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AccountActionImpl implements AccountAction<Account> {
	
	@Resource
	private AccountDao<Account> dao;

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(Account t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(Account t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public Account queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<Account> queryByExemple(Account t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Account> queryByExemple(Account t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<Account> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void register(String email, String password, String nickName) {
		dao.register(email, password, nickName);
	}

	@Override
	public int checkAccount(String email, String nickName) {
		return dao.checkAccount(email, nickName);
	}

	@Override
	public Account queryByEmail(String email) {
		// 查询缓存
		Account t = CacheManager.getInstance().getAccountCache().get(email);
		if (t==null) { // 缓存中没找到，查询DB
			t=dao.queryByEmail(email);
			if (t!=null) {
				CacheManager.getInstance().getAccountCache().put(email, t);
			}
		}
		return t;
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int updatePwd(String email, String password) {
		return dao.updatePwd(email, password);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void logon(int id, String ip) {
		dao.logon(id, ip);
	}
	
}
package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.SystemMessageAction;
import cn.com.carit.platform.bean.account.SystemMessage;
import cn.com.carit.platform.dao.SystemMessageDao;

@Service
@Transactional(readOnly=true)
public class SystemMessageActionImpl implements SystemMessageAction<SystemMessage> {
	
	@Resource
	private SystemMessageDao<SystemMessage> dao;

	@Transactional(readOnly=false)
	@Override
	public int add(SystemMessage t) {
		return dao.add(t);
	}

	@Transactional(readOnly=false)
	@Override
	public int update(SystemMessage t) {
		return dao.update(t);
	}

	@Transactional(readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public SystemMessage queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<SystemMessage> queryByExemple(SystemMessage t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<SystemMessage> queryByExemple(SystemMessage t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<SystemMessage> queryAll() {
		return dao.queryAll();
	}
	
	@Transactional(readOnly=false)
	@Override
	public void readSystemMessage(int id) {
		SystemMessage t=new SystemMessage();
		t.setId(id);
		t.setStatus(SystemMessage.STATUS_READ);
		dao.update(t);
	}

}

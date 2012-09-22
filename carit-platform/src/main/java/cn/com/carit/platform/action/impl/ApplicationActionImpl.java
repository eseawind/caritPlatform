package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.platform.action.ApplicationAction;
import cn.com.carit.platform.bean.Application;
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

}

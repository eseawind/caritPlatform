package cn.com.carit.platform.dao;

import java.util.List;

public interface ApplicationDao<Application> {

	List<Application> queryAll();
}

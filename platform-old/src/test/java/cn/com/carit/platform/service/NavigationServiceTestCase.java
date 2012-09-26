package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.platform.BaseTestCase;
import cn.com.carit.platform.bean.Navigation;

public class NavigationServiceTestCase extends BaseTestCase<Navigation> {
	@Resource
	private NavigationService<Navigation> service;
	
	@Override
	@Test
	public void saveOrUpdate() throws Exception {
		Navigation t=new Navigation();
		t.setName("测试");
		t.setUrl("http://www.sina.com");
		t.setCatalogId(1);
		t.setDisplayIndex(1);
		t.setCssClass("big");
		t.setLogo("dddddddd");
		t.setStatus(1);
		service.saveOrUpdate(t);
		
		t.setId(1);
		t.setName("测试2");
		service.saveOrUpdate(t);
	}

	@Override
	@Test
	public void delete() {
		service.delete(4);
	}

	@Override
	@Test
	public void batchDelete() {
		service.batchDelete("1, 2, 3, 4");
	}

	@Override
	@Test
	public void queryById() {
		System.out.println(service.queryById(1));
	}

	@Override
	@Test
	public void queryByExemple() {
		Navigation t=new Navigation();
		t.setName("测试");
		t.setUrl("http://www.sina.com");
		t.setCatalogId(1);
		t.setDisplayIndex(1);
		t.setCssClass("big");
		t.setLogo("dddddddd");
		t.setStatus(1);
		System.out.println(service.queryByExemple(t, new DataGridModel()));
		
		System.out.println(service.queryByExemple(t));
	}

	@Override
	@Test
	public void queryAll() {
		System.out.println(service.queryAll());
	}
	
	@Test
	public void checkExisted(){
		Assert.isTrue(0==service.checkExisted("测试"));
	}
}

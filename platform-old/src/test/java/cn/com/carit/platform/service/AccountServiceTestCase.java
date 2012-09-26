package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.platform.BaseTestCase;
import cn.com.carit.platform.bean.Account;

public class AccountServiceTestCase extends BaseTestCase<Account> {
	@Resource
	private AccountService<Account> service;
	
	@Override
	@Test
	public void saveOrUpdate() throws Exception {
		Account t=new Account();
		t.setEmail("test@test.com");
		t.setPassword("123456");
		t.setNickName("test");
		t.setGender((byte)0);
		t.setStatus(1);
		service.saveOrUpdate(t);
		
		t.setId(1);
		t.setNickName("测试2");
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
		Account t=new Account();
		t.setEmail("test@test.com");
		t.setNickName("test");
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
		Assert.isTrue(0==service.checkAccount("test@test.com", null));
		Assert.isTrue(0==service.checkAccount(null, "测试2"));
	}
	
	@Test
	public void register() throws Exception{
		Account t=new Account();
		t.setEmail("test@test.com");
		t.setNickName("test");
		t.setPassword("123456");
		service.register(t);
	}
	
	@Test
	public void lockAccount(){
		service.lockAccount(1);
	}
	
	@Test
	public void unLockAccount(){
		service.unLockAccount(1);
	}
	
	@Test
	public void batchLockAccount(){
		service.batchLockAccount("1, 2, 3");
	}
	
	@Test
	public void batchUnLockAccount(){
		service.batchUnLockAccount("1, 2, 3");
	}
}

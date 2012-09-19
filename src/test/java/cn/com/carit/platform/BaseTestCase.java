package cn.com.carit.platform;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;

@TransactionConfiguration(defaultRollback=true) // 测试通过后事务回滚
@SpringApplicationContext("com/rop/config/simplestRopConfig.xml")
public abstract class BaseTestCase<T> extends UnitilsTestNG {

	@Test
	public void saveOrUpdate() throws Exception{}
	
	@Test
	public void delete(){}
	
	@Test
	public void batchDelete(){}
	
	@Test
	public void queryById(){}
	
	@Test
	public void queryByExemple(){}
	
	@Test
	public void queryAll(){}
	
}

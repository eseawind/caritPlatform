package cn.com.carit.platform.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.dao.AccountDao;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	/*Dao */
	private AccountDao<Account> accountDao;
	
	/**账号缓存，以邮箱地址为key*/
	private Map<String, Account> accountCache;
	
	private static class CacheHolder {
		private static final CacheManager INSTANCE = new CacheManager();
	}
	
	@SuppressWarnings("unchecked")
	private CacheManager() {
		logger.info(" init cache start...");
		// init bean
		WebApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
		accountDao =  (AccountDao<Account>) ctx.getBean("accountDaoImpl");
		
		accountCache = new ConcurrentHashMap<String, Account>();
		refreshAccounts();
		logger.info(" init cache end ...");
	}
	
	public static CacheManager getInstance(){
		return CacheHolder.INSTANCE;
	}
	
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		refreshAccounts();
	}
	
	
	/**
	 * 刷新所有账号缓存
	 */
	public void refreshAccounts(){
		accountCache.clear();
		List<Account> allAccountList=accountDao.queryAll();
		for (Account t : allAccountList) {
			accountCache.put(t.getEmail(), t);
		}
	}
	
	/**
	 * 刷新账号{email}的缓存
	 * @param email
	 * @param t
	 */
	public void refreshAccount(String email, Account t){
		accountCache.put(email, t);
	}

	public Map<String, Account> getAccountCache() {
		return accountCache;
	}
	
}
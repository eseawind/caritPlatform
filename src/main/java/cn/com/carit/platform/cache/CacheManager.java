package cn.com.carit.platform.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.AppSecretAction;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.bean.AppSecret;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	/* Action */
	private AccountAction<Account> accountAction;
	private AppSecretAction<AppSecret> appSecretAction;
	
	/**账号缓存，以邮箱地址为key*/
	private Map<String, Account> accountCache;
	
	/**昵称缓存*/
	private Map<String, String> nickNameCache;
	
	private Map<String, String> appKeySecretCache;
	
	private static class CacheHolder {
		private static final CacheManager INSTANCE = new CacheManager();
	}
	
	@SuppressWarnings("unchecked")
	private CacheManager() {
		logger.info(" init cache start...");
		// init bean
		WebApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
		accountAction =  (AccountAction<Account>) ctx.getBean("accountActionImpl");
		appSecretAction = (AppSecretAction<AppSecret>) ctx.getBean("appSecretActionImpl");
		
		accountCache = new ConcurrentHashMap<String, Account>();
		nickNameCache = new ConcurrentHashMap<String, String>();
		
		appKeySecretCache = new ConcurrentHashMap<String, String>();
		refreshAccounts();
		refreshAppKeySecretCache();
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
		nickNameCache.clear();
		List<Account> allAccountList=accountAction.queryAll();
		for (Account t : allAccountList) {
			accountCache.put(t.getEmail(), t);
			nickNameCache.put(t.getNickName(), t.getEmail());
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
	
	/**
	 * 修改昵称时更新昵称缓存
	 * @param oldNickName
	 * @param newNickName
	 */
	public void refreshNickName(String oldNickName, String newNickName){
		nickNameCache.put(newNickName, nickNameCache.get(oldNickName));
		nickNameCache.remove(oldNickName);
	}

	public Map<String, Account> getAccountCache() {
		return accountCache;
	}
	
	public Map<String, String> getNickNameCache() {
		return nickNameCache;
	}

	public void refreshAppKeySecretCache(){
		appKeySecretCache.clear();
		List<AppSecret> list=appSecretAction.queryAll();
		for (AppSecret appSecret : list) {
			appKeySecretCache.put(String.valueOf(appSecret.getId()), appSecret.getAppSecret());
		}
	}

	public Map<String, String> getAppKeySecretCache() {
		return appKeySecretCache;
	}
	
}
package cn.com.carit.platform.web;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.bean.Catalog;
import cn.com.carit.platform.bean.Navigation;
import cn.com.carit.platform.service.AccountService;
import cn.com.carit.platform.service.CatalogService;
import cn.com.carit.platform.service.NavigationService;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	/*service */
	private CatalogService<Catalog> catalogService;
	private AccountService<Account> accountService;
	private NavigationService<Navigation> navigationService;
	
	private List<Catalog> allCatalogList;
	
	private List<Navigation> allNavigationList;
	
	/**账号缓存，以邮箱地址为key*/
	private Map<String, Account> allAccountCache;
	
	private static class CacheHolder {
		private static final CacheManager INSTANCE = new CacheManager();
	}
	
	@SuppressWarnings("unchecked")
	private CacheManager() {
		logger.info(" init cache start...");
		// init bean
		WebApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
		catalogService = (CatalogService<Catalog>) ctx.getBean("catalogServiceImpl");
		accountService = (AccountService<Account>) ctx.getBean("accountServiceImpl");
		navigationService = (NavigationService<Navigation>) ctx.getBean("navigationServiceImpl");
		
		refreshNavigations();
		refreshCatalogs();
		allAccountCache = new ConcurrentHashMap<String, Account>();
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
		refreshCatalogs();
		refreshNavigations();
		refreshAccounts();
	}
	
	/**
	 * 刷新分类
	 */
	public void refreshCatalogs(){
		Catalog sample=new Catalog();
		sample.setStatus(Constants.STATUS_VALID);
		allCatalogList=catalogService.queryByExemple(sample);
	}

	public List<Catalog> getAllCatalogList() {
		return allCatalogList;
	}
	
	/**
	 * 刷新所有账号缓存
	 */
	public void refreshAccounts(){
		allAccountCache.clear();
		List<Account> allAccountList=accountService.queryAll();
		for (Account t : allAccountList) {
			allAccountCache.put(t.getEmail(), t);
		}
	}
	
	/**
	 * 刷新账号{email}的缓存
	 * @param email
	 * @param t
	 */
	public void refreshAccount(String email, Account t){
		allAccountCache.put(email, t);
	}

	public Map<String, Account> getAllAccountCache() {
		return allAccountCache;
	}
	
	/**
	 * 刷新地址导航
	 */
	public void refreshNavigations(){
		allNavigationList=navigationService.queryEffective();
	}

	public List<Navigation> getAllNavigationList() {
		return allNavigationList;
	}
	
}
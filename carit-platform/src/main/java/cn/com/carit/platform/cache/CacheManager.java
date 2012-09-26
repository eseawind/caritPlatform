package cn.com.carit.platform.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.AppSecretAction;
import cn.com.carit.platform.action.ApplicationAction;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.bean.AppSecret;
import cn.com.carit.platform.bean.Application;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	/* Action */
	private final AccountAction<Account> accountAction;
	private final AppSecretAction<AppSecret> appSecretAction;
	private final ApplicationAction<Application> applicationAction;
	
	/**账号缓存，以邮箱地址为key*/
	private final Map<String, Account> accountCache;
	
	/**昵称缓存*/
	private final Map<String, String> nickNameCache;
	
	private final Map<String, String> appKeySecretCache;
	
	private final Map<Integer, Application> applicationCache;
	
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
		applicationAction = (ApplicationAction<Application>) ctx.getBean("applicationActionImpl");
		
		accountCache = new HashMap<String, Account>();
		nickNameCache = new HashMap<String, String>();
		
		appKeySecretCache = new HashMap<String, String>();
		applicationCache = new HashMap<Integer, Application>();
		
		refreshAccounts();
		refreshAppKeySecretCache();
		refreshApplicationCache();
		logger.info(" init cache end ...");
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static CacheManager getInstance(){
		return CacheHolder.INSTANCE;
	}
	
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		refreshAccounts();
		refreshAppKeySecretCache();
		refreshApplicationCache();
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
	 * 刷新账号{email}的缓存,如果不存在则把 {t}新增至缓存
	 * @param email
	 * @param t
	 */
	public void refreshAccount(String email, Account t){
		accountCache.put(email, t);
		nickNameCache.put(t.getNickName(), email);
	}
	
	/**
	 * 修改昵称时更新昵称缓存
	 * @param oldNickName
	 * @param newNickName
	 */
	public void refreshNickName(String oldNickName, String newNickName){
		if (nickNameCache.get(oldNickName)!=null) {
			nickNameCache.put(newNickName, nickNameCache.get(oldNickName));
			nickNameCache.remove(oldNickName);
		}
	}

	public Map<String, Account> getAccountCache() {
		return accountCache;
	}
	
	public Map<String, String> getNickNameCache() {
		return nickNameCache;
	}

	/**
	 * 按源更新
	 * @param source
	 * @param target
	 */
	public static void refreshAccount(Account source, Account target){
		if (source.getAddress()!=null) {
			target.setAddress(source.getAddress());
		}
		if (source.getGender()!=null) {
			target.setGender(source.getGender());
		}
		if (source.getBirthday()!=null) {
			target.setBirthday(source.getBirthday());
		}
		if (StringUtils.hasText(source.getMobile())) {
			target.setMobile(source.getMobile());
		}
		if (StringUtils.hasText(source.getIdCard())) {
			target.setIdCard(source.getIdCard());
		}
		if (StringUtils.hasText(source.getNickName()) 
				&& source.getNickName().equals(target.getNickName())) {
			// 更新昵称缓存
			getInstance().refreshNickName(target.getNickName(), source.getNickName());
			target.setNickName(source.getNickName());
		}
		if (StringUtils.hasText(source.getOfficePhone())) {
			target.setOfficePhone(source.getOfficePhone());
		}
		if (StringUtils.hasText(source.getRealName())) {
			target.setRealName(source.getRealName());
		}
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
	
	public Account getAccount(String email){
		return getAccountCache().get(email);
	}
	
	public void refreshApplicationCache(){
		applicationCache.clear();
		List<Application> list=applicationAction.queryAll();
		for (Application t : list) {
			applicationCache.put(t.getId(), t);
		}
	}

	public Map<Integer, Application> getApplicationCache() {
		return applicationCache;
	}
	
}
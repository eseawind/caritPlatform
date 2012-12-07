package cn.com.carit.platform.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.AppSecretAction;
import cn.com.carit.platform.action.ApplicationAction;
import cn.com.carit.platform.action.RegionAction;
import cn.com.carit.platform.bean.AppSecret;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.bean.market.Application;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	/* Action */
	private final AccountAction<Account> accountAction;
	private final AppSecretAction<AppSecret> appSecretAction;
	private final ApplicationAction<Application> applicationAction;
	private final RegionAction regionAction;
	
	/**账号缓存，以邮箱地址为key*/
	private final Map<String, Account> accountCache;
	
	/**昵称缓存*/
	private final Map<String, String> nickNameCache;
	
	private final Map<String, String> appKeySecretCache;
	
	private final Map<Integer, Application> applicationCache;
	
	private final List<Map<String, Object>> provinces;
	
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
		regionAction = (RegionAction) ctx.getBean("regionActionImpl");
		
		accountCache = new ConcurrentHashMap<String, Account>();
		nickNameCache = new ConcurrentHashMap<String, String>();
		
		appKeySecretCache = new HashMap<String, String>();
		applicationCache = new ConcurrentHashMap<Integer, Application>();
		provinces = regionAction.provinces();
		
		refreshAccounts();
		refreshAppKeySecretCache();
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
	}
	
	
	/**
	 * 刷新所有账号缓存
	 */
	public void refreshAccounts(){
		accountCache.clear();
		nickNameCache.clear();
	}
	
	/**
	 * 刷新账号{email}的缓存,如果不存在则把 {t}新增至缓存
	 * @param email
	 * @param t
	 */
	public void refreshAccount(String email, Account t){
		accountCache.put(email, t);
		nickNameCache.put(t.getNickName(), t.getNickName());
	}
	
	/**
	 * 修改昵称时更新昵称缓存
	 * @param oldNickName
	 * @param newNickName
	 */
	public void refreshNickName(String oldNickName, String newNickName){
		if (nickNameCache.get(oldNickName)!=null) {
			nickNameCache.put(newNickName, newNickName);
			nickNameCache.remove(oldNickName);
		}
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

	public String getAppSecret(String appKey){
		return appKeySecretCache.get(appKey);
	}
	
	public Account getAccount(String email){
		Account t=accountCache.get(email);
		if(t==null){
			t=accountAction.queryByEmail(email);
		}
		return t;
	}
	
	public Application getApplication(int appId) {
		Application t=applicationCache.get(appId);
		if (t==null) {
			t=applicationAction.queryById(appId);
			if (t!=null) {
				applicationCache.put(appId, t);
			}
		}
		return t;
	}
	
	public boolean checkNickname(String nickname){
		if (nickNameCache.containsKey(nickname)) {
			return true;
		}
		if(accountAction.checkAccount(null, nickname)>0){
			nickNameCache.put(nickname, nickname);
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> getProvinces() {
		return provinces;
	}
	
}
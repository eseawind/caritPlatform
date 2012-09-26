package cn.com.carit.platform.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.AttachmentUtil;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.dao.AccountDao;
import cn.com.carit.platform.service.AccountService;
import cn.com.carit.platform.web.CacheManager;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AccountServiceImpl implements AccountService<Account> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private AccountDao<Account> accountDao;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int saveOrUpdate(Account t) throws Exception {
		String password=t.getPassword();
		if (StringUtils.hasText(password)) {
			// 加密密码，加密规则 MD5Util.md5Hex(MD5Util.md5Hex(password){email}disturbStr)
			t.setPassword(MD5Util.md5Hex(t.getEmail()+MD5Util.md5Hex(password)+MD5Util.DISTURBSTR));
		}
		if (t.getId()>0) {
			if (StringUtils.hasText(t.getPhoto())) {
				// 更新了头像
				Account old=accountDao.queryById(t.getId());
				AttachmentUtil.deletePhoto(old.getPhoto());
				if (StringUtils.hasText(t.getThumbPhoto())) {
					AttachmentUtil.deletePhoto(old.getThumbPhoto());
				}
			}
			return accountDao.update(t);
		} else {
			return accountDao.add(t);
		}
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountDao.delete(id);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int batchDelete(String ids) {
		if (!StringUtils.hasText(ids)) {
			throw new IllegalArgumentException("ids is empty...");
		}
		return accountDao.batchDelete(ids);
	}

	@Override
	public Account queryById(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountDao.queryById(id);
	}

	@Override
	public JsonPage<Account> queryByExemple(Account t, DataGridModel dgm) {
		if (t==null) {
			throw new NullPointerException("sample is null..");
		}
		if (dgm==null) {
			throw new NullPointerException("DataGridModel is null..");
		}
		return accountDao.queryByExemple(t, dgm);
	}

	@Override
	public List<Account> queryByExemple(Account t) {
		if (t==null) {
			throw new NullPointerException("sample is null..");
		}
		return accountDao.queryByExemple(t);
	}

	@Override
	public List<Account> queryAll() {
		return accountDao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public Account register(Account t) throws Exception {
		// 加密密码，加密规则 MD5Util.md5Hex(MD5Util.md5Hex(password){email}disturbStr)
		t.setPassword(MD5Util.md5Hex(t.getEmail()+MD5Util.md5Hex(
				t.getPassword())+MD5Util.DISTURBSTR));
		accountDao.register(t);
		return t;
	}

	@Override
	public int checkAccount(String email, String nickName) {
		if (!StringUtils.hasText(email) && !StringUtils.hasText(nickName)) {
			throw new IllegalArgumentException("both email and nickName are empty...");
		}
		return accountDao.checkAccount(email, nickName);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int lockAccount(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountDao.lockAccount(id);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int unLockAccount(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountDao.unLockAccount(id);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int batchLockAccount(String ids) {
		if (!StringUtils.hasText(ids)) {
			throw new IllegalArgumentException("ids is empty...");
		}
		return accountDao.batchLockAccount(ids);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int batchUnLockAccount(String ids) {
		if (!StringUtils.hasText(ids)) {
			throw new IllegalArgumentException("ids is empty...");
		}
		return accountDao.batchUnLockAccount(ids);
	}

	@Override
	public Map<String, Object> login(String email, String password, String ip)
			throws Exception {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException("email must be not empty...");
		}
		if (!StringUtils.hasText(password)) {
			throw new IllegalArgumentException("password must be not empty...");
		}
		Map<String, Object> resultMap=new HashMap<String, Object>();
		// 查询缓存
		Account account = queryByEmail(email);
		if (account==null) {
			log.error("User["+email+"] does not exist...");
			resultMap.put(Constants.ANSWER_CODE, -1);
			return resultMap;
		}
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		if (!password.equalsIgnoreCase(account.getPassword())) {
			//密码错误
			resultMap.put(Constants.ANSWER_CODE, 0);
			return resultMap;
		}
		if(account.getStatus()!=Constants.STATUS_VALID){
			// 帐号没启用
			log.error("User["+email+"]  not Enabled...");
			resultMap.put(Constants.ANSWER_CODE, -2);
			return resultMap;
		}
		
		// 更新登录时间/IP
		Account updateAccount=new Account();
		updateAccount.setId(account.getId());
		updateAccount.setLastLoginIp(ip);
		updateAccount.setLastLoginTime(Calendar.getInstance().getTime());
		accountDao.update(updateAccount);
		resultMap.put(Constants.ANSWER_CODE, 1);
		resultMap.put(email, account);
		return resultMap;
	}

	@Override
	public Account queryByEmail(String email) {
		// 查询缓存
		Account account = CacheManager.getInstance().getAllAccountCache().get(email);
		if (account==null) { // 缓存中没找到，查询DB
			account=accountDao.queryByEmail(email);
			CacheManager.getInstance().getAllAccountCache().put(email, account);
		}
		return account;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public int updatePwd(String email, String oldPassword, String newPassword)
			throws Exception {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException("email must be not empty...");
		}
		if (!StringUtils.hasText(newPassword)) {
			throw new IllegalArgumentException("newPassword must be not empty...");
		}
		Account account=queryByEmail(email);
		//加密
		oldPassword=MD5Util.md5Hex(account.getEmail()+MD5Util.md5Hex(oldPassword)+MD5Util.DISTURBSTR);
		if(!oldPassword.equalsIgnoreCase(account.getPassword())){
			//密码错误
			log.error("password is incorrect ....");
			return -1;
		}
		newPassword=MD5Util.md5Hex(account.getEmail()+MD5Util.md5Hex(newPassword)+MD5Util.DISTURBSTR);
		int updated=accountDao.updatePwd(email, newPassword);
		if (updated>0) {
			account.setPassword(newPassword);
			// 刷新缓存
			CacheManager.getInstance().refreshAccount(account.getEmail(), account);
		}
		return updated;
	}

}

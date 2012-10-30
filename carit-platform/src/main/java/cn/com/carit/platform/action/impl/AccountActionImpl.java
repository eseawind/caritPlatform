package cn.com.carit.platform.action.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.AttachmentUtil;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.dao.AccountDao;
import cn.com.carit.platform.dao.EquipmentDao;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AccountActionImpl implements AccountAction<Account> {
	
	@Resource
	private AccountDao<Account> dao;

	@Resource
	private EquipmentDao<Equipment> equipmentDao;
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int add(Account t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int update(Account t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public Account queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<Account> queryByExemple(Account t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<Account> queryByExemple(Account t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<Account> queryAll() {
		return dao.queryAll();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void register(String email, String password, String nickName) {
		int id=dao.register(email, password, nickName);
		CacheManager.getInstance().getAccountCache().put(email
				, new Account(id, email, password, nickName));
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void register(String email, String password, String nickName,
			String deviceId, boolean flag) {
		int id=dao.register(email, password, nickName);
		CacheManager.getInstance().getAccountCache().put(email
				, new Account(id, email, password, nickName));
		// 设备还不存在
		if (flag) {
			Equipment t=new Equipment();
			t.setAccountId(id);
			t.setDeviceId(deviceId);
			equipmentDao.add(t);
		}
		// 插入关联关系
		equipmentDao.addReference(id, deviceId);
	}

	@Override
	public int checkAccount(String email, String nickName) {
		return dao.checkAccount(email, nickName);
	}

	@Override
	public Account queryByEmail(String email) {
		// 查询缓存
		Account t = CacheManager.getInstance().getAccountCache().get(email);
		if (t==null) { // 缓存中没找到，查询DB
			t=dao.queryByEmail(email);
			if (t!=null) {
				CacheManager.getInstance().getAccountCache().put(email, t);
			}
		}
		return t;
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int updatePwd(String email, String password) {
		return dao.updatePwd(email, password);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public void logon(int id, String ip) {
		dao.logon(id, ip);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	@Override
	public int uploadPhoto(Account t, String photoPath, String thumbPhotoPath) {
		if (StringUtils.hasText(photoPath)&&StringUtils.hasText(thumbPhotoPath)) {
			// 删除原来的图片
			AttachmentUtil.getInstance().deletePhoto(t.getPhoto().replaceFirst(
					AttachmentUtil.getInstance().getHost() + Constants.BASE_PATH_PHOTOS, ""));
			AttachmentUtil.getInstance().deletePhoto(t.getThumbPhoto().replaceFirst(
					AttachmentUtil.getInstance().getHost() + Constants.BASE_PATH_PHOTOS, ""));
			
			return dao.uploadPhoto(t.getId(), photoPath, thumbPhotoPath);
		}
		return 0;
	}
	
}

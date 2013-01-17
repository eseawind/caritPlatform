package cn.com.carit.platform.action.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.common.utils.AttachmentUtil;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.JavaMailSenderService;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.dao.AccountDao;
import cn.com.carit.platform.dao.EquipmentDao;

@Service
@Transactional(readOnly=true)
public class AccountActionImpl implements AccountAction<Account> {
	
	@Resource
	private AccountDao<Account> dao;

	@Resource
	private EquipmentDao<Equipment> equipmentDao;
	
	@Resource
	private JavaMailSenderService mailSenderService;
	
	@Transactional(readOnly=false)
	@Override
	public int add(Account t) {
		return dao.add(t);
	}

	@Transactional(readOnly=false)
	@Override
	public int update(Account t) {
		return dao.update(t);
	}

	@Transactional(readOnly=false)
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

	@Transactional(readOnly=false)
	@Override
	public void register(String email, String password, String nickName) {
		dao.register(email, password, nickName);
	}

	@Transactional(readOnly=false)
	@Override
	public void register(String email, String password, String nickName,
			String deviceId) {
		int id=dao.register(email, password, nickName);
		// 设备还不存在
		Equipment e=equipmentDao.queryByDeviceId(deviceId);
		if (e==null) {
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
		return dao.queryByEmail(email);
	}

	@Transactional(readOnly=false)
	@Override
	public int updatePwd(String email, String password) {
		return dao.updatePwd(email, password);
	}
	
	@Transactional(readOnly=false)
	@Override
	public void logon(int id, String ip) {
		dao.logon(id, ip);
	}
	
	@Transactional(readOnly=false)
	@Override
	public int uploadPhoto(Account t, String photoPath, String thumbPhotoPath) {
		if (StringUtils.hasText(photoPath)&&StringUtils.hasText(thumbPhotoPath)) {
			// 删除原来的图片
			AttachmentUtil.getInstance().deletePhoto(t.getPhoto().replaceFirst(
					AttachmentUtil.getInstance().getHost() + "/", ""));
			AttachmentUtil.getInstance().deletePhoto(t.getThumbPhoto().replaceFirst(
					AttachmentUtil.getInstance().getHost() + "/", ""));
			
			return dao.uploadPhoto(t.getId(), photoPath, thumbPhotoPath);
		}
		return 0;
	}
	
	@Transactional(readOnly=false)
	@Override
	public void getBackPassword(String email, String newPassword,
			String subject, String content) {
		dao.updatePwd(email, newPassword);
		mailSenderService.sendHtmlMail(email, subject, content);
	}

	@Override
	public JsonPage<Map<String, Object>> queryByPartner(int partnerId,
			String email, String nickname, DataGridModel dgm) {
		return dao.queryByPartner(partnerId, email, nickname, dgm);
	}
	
}

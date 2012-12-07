package cn.com.carit.platform.dao;

import cn.com.carit.Dao;

public interface AccountDao<Account> extends Dao<Account> {

	/**
	 * 注册账号
	 * @param email
	 * @param password
	 * @param nickName
	 * @return
	 */
	int register(final String email, final String password, final String nickName);
	
	/**
	 * 合作方添加客户账号
	 * @param email
	 * @param password
	 * @param nickName
	 * @param partnerId
	 * @return
	 */
	int partnerAdd(final String email, final String password, final String nickName, final int partnerId);
	
	/**
	 * 按邮箱or昵称检测是否存在
	 * @param email
	 * @param nickName
	 * @return
	 */
	int checkAccount(String email, String nickName);
	
	Account queryByEmail(String email);
	
	/**
	 * 修改密码
	 * @param email
	 * @param password
	 * @return
	 */
	int updatePwd(String email, String password);
	
	/**
	 * 登录
	 * @param id
	 * @param ip
	 */
	void logon(int id, String ip);
	
	/**
	 * 上传头像
	 * @param id
	 * @param photoPath
	 * @param thumbPhotoPath
	 */
	int uploadPhoto(int id, String photoPath, String thumbPhotoPath);
}

package cn.com.carit.platform.action;

import cn.com.carit.Action;

public interface AccountAction<Account> extends Action<Account> {
	/**
	 * 注册账号
	 * @param email
	 * @param password
	 * @param nickName
	 */
	void register(String email, String password, String nickName);
	
	/**
	 * 注册账号并绑定设备
	 * @param email
	 * @param password
	 * @param nickName
	 * @param deviceId
	 */
	void register(String email, String password, String nickName, String deviceId);
	
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
	 * @param t
	 * @param photoPath
	 * @param thumbPhotoPath
	 */
	int uploadPhoto(Account t, String photoPath, String thumbPhotoPath);
	
	/**
	 * 找回密码
	 * @param email
	 * @param newPassword
	 * @param subject
	 * @param content
	 */
	void getBackPassword(String email, String newPassword, String subject, String content);
}

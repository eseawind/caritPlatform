package cn.com.carit.platform.action;

import cn.com.carit.Action;

public interface AccountAction<Account> extends Action<Account> {
	/**
	 * 注册账号
	 * @param t
	 * @return
	 */
	void register(String email, String password, String nickName);
	
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
}

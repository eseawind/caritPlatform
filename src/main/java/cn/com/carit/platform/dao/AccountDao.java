package cn.com.carit.platform.dao;

public interface AccountDao<Account> extends BaseDao<Account> {

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
	
	/**
	 * 锁定账号
	 * @param id
	 * @return
	 */
	int lockAccount(int id);
	
	
	/**
	 * 解封账号
	 * @param id
	 * @return
	 */
	int unLockAccount(int id);
	
	int batchLockAccount(String ids);
	
	int batchUnLockAccount(String ids);
	
	Account queryByEmail(String email);
	
	/**
	 * 修改密码
	 * @param email
	 * @param password
	 * @return
	 */
	int updatePwd(String email, String password);
}

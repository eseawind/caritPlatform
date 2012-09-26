package cn.com.carit.platform.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService<Account> extends BaseService<Account> {
	/**
	 * 注册账号
	 * @param t
	 * @return
	 */
	Account register(final Account t) throws Exception;
	
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
	
	/**
	 * 登录
	 * @param email
	 * @param password
	 * @param ip
	 * @return {@link Map} 
	 * 返回的 Map 包含两个key， 一个是key为 answerCode 的响应值，值描述如下：<br>
	 *  <ul>
	 * <li>-2  帐号被锁定</li>
	 * <li>-1  用户不存在</li>
	 * <li>0	密码错误</li>
	 * <li>1	登录成功</li>
	 * <li>其它  后台异常</li>
	 * </ul>
	 * 另外一个是一emai为lkey，值为用户{@link BaseUser} 对象。如果响应值不为1，不需要处理该值
	 * @throws Exception
	 */
	Map<String, Object> login(String email, String password, String ip) throws Exception;
	
	Account queryByEmail(String email);
	
	/**
	 * 修改密码
	 * @param email
	 * @param newPassword
	 * @param oldPassword
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=false)
	int updatePwd(String email, String oldPassword, String newPassword) throws Exception;
}

package cn.com.carit.platform.service;

import javax.annotation.Resource;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.EquipmentAction;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.request.account.v3.RegisterAccountRequest;

import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.CommonRopResponse;


/**
 * <p>
 * <b>功能说明：</b>账号相关服务接口3.0 版本
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-21
 */
@ServiceMethodBean(version = "3.0")
public class AccountServiceVersion3 {
	
	@Resource
	private AccountAction<Account> action;
	
	@Resource
	private EquipmentAction<Equipment> equipmentAction;
	
	/**
	 * <p>
	 * <b>功能说明：</b>注册账号
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.register</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>2.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>email</td><td>[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}</td><td>是</td><td>是</td></tr>
	 *  <tr><td>password</td><td>\\w{6,30}</td><td>否</td><td>是</td></tr>
	 *  <tr><td>nickName</td><td>字符长度3~50</td><td>是</td><td>是</td></tr>
	 *  <tr><td>deviceId</td><td>设备Id</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.register", httpAction=HttpAction.POST, needInSession=NeedInSessionType.NO)
	public Object register(RegisterAccountRequest request) throws Exception{
		int count=equipmentAction.queryAccountCountByDeviceId(request.getDeviceId());
		if (count>=Equipment.MAX_BOUND_ACCOUNT_COUNT) {
			return new BusinessServiceErrorResponse(request.getRopRequestContext().getMethod()
					, Constants.EQUIPMENT_BINDING_ACCOUNT_TO_UPPER_LIMIT
					, request.getRopRequestContext().getLocale()
					, Equipment.MAX_BOUND_ACCOUNT_COUNT);
		}
		String email=request.getEmail();
		String password=request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		action.register(email, password, request.getNickName(), request.getDeviceId());
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
}

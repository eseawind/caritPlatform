package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.cache.CacheManager;
import cn.com.carit.platform.request.LogonRequest;
import cn.com.carit.platform.request.RegisterAccountRequest;
import cn.com.carit.platform.request.UpdateAccountRequest;
import cn.com.carit.platform.request.UpdatePasswordRequest;
import cn.com.carit.platform.request.UploadUserPhotoRequest;
import cn.com.carit.platform.response.AccountResponse;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.CommonRopResponse;
import com.rop.response.NotExistErrorResponse;

/**
 * <pre>
 * 功能说明：账号相关服务接口
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-21
 */
@ServiceMethodBean(version = "1.0")
public class AccountService {
	
	@Resource
	private AccountAction<Account> action;
	
	/**
	 * 登录系统
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ServiceMethod(method = "account.logon",version = "1.0")
    public Object logon(LogonRequest request) throws Exception {
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		// 记录本次登录信息
		action.logon(t.getId(), request.getRopRequestContext().getIp());
		
        AccountResponse accountResponse=new AccountResponse();
        BeanUtils.copyProperties(t, accountResponse);
        
        //返回响应
        return  accountResponse;
    }
	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "account.logout",version = "1.0", httpAction=HttpAction.GET)
	public Object logout(RopRequest request){
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	@ServiceMethod(method = "account.register",version = "1.0")
	public Object register(RegisterAccountRequest request) throws Exception{
		String email=request.getEmail();
		String password=request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		action.register(email, password, request.getNickName());
		CacheManager.getInstance().refreshAccounts();
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	@ServiceMethod(method = "account.update",version = "1.0",needInSession = NeedInSessionType.YES)
	public Object update(UpdateAccountRequest request) throws Exception{
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		boolean needToUpdate=false;
		Account update=new Account();
		update.setId(t.getId());
		if (request.getAddress()!=null && !request.getAddress().equals(t.getAddress())) {
			update.setAddress(request.getAddress());
			needToUpdate=true;
		}
		if (request.getGender()!=null && request.getGender().equals(t.getGender())) {
			update.setGender(request.getGender());
			needToUpdate=true;
		}
		if (StringUtils.hasText(request.getBirthday())) {
			update.setBirthday(CaritUtils.strToDate(request.getBirthday(), Constants.DATE_FORMATTER));
			needToUpdate=true;
		}
		if (request.getMobile()!=null && !request.getMobile().equals(t.getMobile())) {
			update.setMobile(request.getMobile());
			needToUpdate=true;
		}
		if (request.getIdCard()!=null && !request.getIdCard().equals(t.getIdCard())) {
			update.setIdCard(request.getIdCard());
			needToUpdate=true;
		}
		if (request.getNickName()!=null && !request.getNickName().equals(t.getNickName())) {
			update.setNickName(request.getNickName());
			needToUpdate=true;
		}
		if (request.getOfficePhone()!=null && !request.getOfficePhone().equals(t.getOfficePhone())) {
			update.setOfficePhone(request.getOfficePhone());
			needToUpdate=true;
		}
		if (request.getRealName()!=null && !request.getRealName().equals(t.getRealName())) {
			update.setRealName(request.getRealName());
			needToUpdate=true;
		}
		
		if (needToUpdate) {
			// 更新
			action.update(update);
			// 更新缓存
			CacheManager.refreshAccount(update, t);
		}
		
		AccountResponse accountResponse=new AccountResponse();
        BeanUtils.copyProperties(t, accountResponse);
        
        //返回响应
        return  accountResponse;
	}
	
	@ServiceMethod(method = "account.update.password",version = "1.0",needInSession = NeedInSessionType.YES)
	public Object updatePwd(UpdatePasswordRequest request) throws Exception{
		String email=request.getEmail();
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(email);
		String newPassword=request.getNewPassword();
		// 密码加密
		newPassword=MD5Util.md5Hex(newPassword);
		// 二次加密
		newPassword=MD5Util.md5Hex(email+newPassword+MD5Util.DISTURBSTR);
		
		// 更新密码
		action.updatePwd(email, newPassword);
		
		// 更新缓存
		t.setPassword(newPassword);
		
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	/*public Object updatePhoto(UploadUserPhotoRequest request){
		// 查询缓存
		Account t = CacheManager.getInstance().getAccount(request.getEmail());
		
		
		action.up
	}*/
	
}

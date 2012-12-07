package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.PartnerAction;
import cn.com.carit.platform.bean.Partner;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.request.partner.PartnerAddAccountRequest;
import cn.com.carit.platform.request.partner.PartnerLogonRequest;
import cn.com.carit.platform.request.partner.PartnerRegisterRequest;
import cn.com.carit.platform.request.partner.PartnerUpdatePwdRequest;
import cn.com.carit.platform.request.partner.PartnerUpdateRequest;
import cn.com.carit.platform.response.PartnerResponse;

import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;
import com.rop.response.CommonRopResponse;

@ServiceMethodBean(version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
public class PartnerService {

	@Resource
	private PartnerAction<Partner> action;
	@Resource
	private AccountAction<Account> accountAction;
	
	@ServiceMethod(method = "partner.register")
	public Object register(PartnerRegisterRequest request) {
		// 已经注册
		if (action.checkName(request.getFirmName())) {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.PARTNER_NAME_REGISTERED, request
							.getRopRequestContext().getLocale(), request.getFirmName());
		}
		Partner t=new Partner();
		BeanUtils.copyProperties(request, t);
		// 加密密码
		String password = request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(request.getFirmName()+password+MD5Util.DISTURBSTR);
		t.setPassword(password);
		action.add(t);
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	@ServiceMethod(method = "partner.logon")
	public Object logon(PartnerLogonRequest request){
		Partner t=action.queryByName(request.getFirmName());
		if (t==null) {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.PARTNER_NOT_EXIST, request
							.getRopRequestContext().getLocale(), request.getFirmName());
		}
		// 加密密码
		String password = request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(request.getFirmName()+password+MD5Util.DISTURBSTR);
		if (password.equals(t.getPassword())) {
			PartnerResponse response=new PartnerResponse();
			BeanUtils.copyProperties(t, response);
			return response;
		} else {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.PASSWORD_ERROR, request
							.getRopRequestContext().getLocale(), request.getFirmName());
		}
	}
	
	@ServiceMethod(method = "partner.update.pwd")
	public Object updatePwd(PartnerUpdatePwdRequest request){
		Partner t=action.queryById(request.getId());
		if (t==null) {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.PARTNER_NOT_EXIST, request
							.getRopRequestContext().getLocale(), request.getId());
		}
		// 加密密码
		String oldPassword = request.getOldPassword();
		// 密码加密
		oldPassword=MD5Util.md5Hex(oldPassword);
		// 二次加密
		oldPassword=MD5Util.md5Hex(t.getFirmName()+oldPassword+MD5Util.DISTURBSTR);
		if (oldPassword.equals(t.getPassword())) {
			String password = request.getPassword();
			password=MD5Util.md5Hex(password);
			password=MD5Util.md5Hex(t.getFirmName()+password+MD5Util.DISTURBSTR);
			t.setPassword(password);
			action.update(t);
			return CommonRopResponse.SUCCESSFUL_RESPONSE;
		} else {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.PASSWORD_ERROR, request
							.getRopRequestContext().getLocale());
		}
	}
	
	@ServiceMethod(method = "partner.update")
	public Object update(PartnerUpdateRequest request){
		Partner t=action.queryById(request.getId());
		if (t==null) {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.PARTNER_NOT_EXIST, request
							.getRopRequestContext().getLocale(), request.getId());
		}
		Partner update = new Partner();
		BeanUtils.copyProperties(request, update);
		action.update(update);
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	@ServiceMethod(method = "partner.account.add")
	public Object addAccount(PartnerAddAccountRequest request){
		String email=request.getEmail();
		String password=request.getPassword();
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		accountAction.partnerAdd(email, password, request.getNickName(),
				request.getDeviceId(), request.getPartnerId());
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
}

package cn.com.carit.platform.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.action.AccountAction;
import cn.com.carit.platform.action.EquipmentAction;
import cn.com.carit.platform.action.PartnerAction;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.bean.Partner;
import cn.com.carit.platform.bean.account.Account;
import cn.com.carit.platform.request.partner.BindingDeviceRequest;
import cn.com.carit.platform.request.partner.PartnerLogonRequest;
import cn.com.carit.platform.request.partner.PartnerRegisterRequest;
import cn.com.carit.platform.request.partner.PartnerSearchAccountRequest;
import cn.com.carit.platform.request.partner.PartnerUpdatePwdRequest;
import cn.com.carit.platform.request.partner.PartnerUpdateRequest;
import cn.com.carit.platform.response.PageResponse;
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
	@Resource
	private EquipmentAction<Equipment> equipmentAction;
	
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
			action.logon(t.getId(), request.getRopRequestContext().getIp());
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
			Partner update = new Partner();
			update.setId(t.getId());
			update.setPassword(password);
			action.update(update);
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

	@ServiceMethod(method = "partner.device.bingding")
	public Object bindingAccount(BindingDeviceRequest request){
		//查询设备
		Partner partner=action.queryBoundingPartner(request.getDeviceId());
		if (partner!=null) {
			if (partner.getId()!=request.getPartnerId().intValue()) {
				return new BusinessServiceErrorResponse(request
						.getRopRequestContext().getMethod(),
						Constants.EQUIPMENT_HAS_BOUND_WITH_OTHER_PARTNER, request.getRopRequestContext()
						.getLocale(), request.getDeviceId());
			} else {//绑定过，直接返回
				return CommonRopResponse.SUCCESSFUL_RESPONSE;
			}
		}
		
		Equipment t=equipmentAction.queryByDeviceId(request.getDeviceId());
		if (t==null) {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.NO_THIS_EQUIPMENT, request.getRopRequestContext()
							.getLocale(), request.getDeviceId());
		}
		action.bindingAccount(request.getPartnerId(),  request.getDeviceId());
		return CommonRopResponse.SUCCESSFUL_RESPONSE;
	}
	
	@ServiceMethod(method = "partner.query.devices")
	public Object queryDevices(PartnerSearchAccountRequest request){
		DataGridModel dgm = new DataGridModel();
		if (request.getPage()>0) {
			dgm.setPage(request.getPage());
		}
		if (request.getRows()>0) {
			dgm.setRows(request.getRows());
		}
		if (StringUtils.hasText(request.getOrder())) {
			dgm.setOrder(request.getOrder());
		}
		if (StringUtils.hasText(request.getSort())) {
			dgm.setSort(request.getSort());
		}
		JsonPage<Map<String, Object>> jsonPage = accountAction.queryByPartner(
				request.getPartnerId(), request.getEmail(),
				request.getNickName(), dgm);
		PageResponse<Map<String, Object>> pageResponse= new PageResponse<Map<String, Object>>();
		BeanUtils.copyProperties(jsonPage, pageResponse);
		return pageResponse;
	}
	
}

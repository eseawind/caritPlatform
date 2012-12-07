package cn.com.carit.platform.request.partner;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.platform.request.account.LogonRequest;

public class PartnerRegisterRequest extends LogonRequest {
	
	@NotEmpty
	private String firmName;
	@NotEmpty
	private String city;
	@NotEmpty
	private String addr;
	@NotEmpty
	private String contactPerson;
	@NotEmpty
	private String phone;
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}

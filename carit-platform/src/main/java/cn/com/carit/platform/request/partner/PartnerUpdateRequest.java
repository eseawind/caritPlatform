package cn.com.carit.platform.request.partner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class PartnerUpdateRequest extends AbstractRopRequest {
	@NotNull
	@Min(value=1)
	@Max(value=Integer.MAX_VALUE)
	private Integer id;
	private String city;
	private String addr;
	private String contactPerson;
	private String phone;
	@Pattern(regexp = Constants.REGEXP_EMAIL)
	protected String email;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

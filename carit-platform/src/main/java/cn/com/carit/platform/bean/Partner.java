package cn.com.carit.platform.bean;

import java.util.Date;

public class Partner extends BaseBean {

	private static final long serialVersionUID = 6784096114273931298L;
	private String firmName;
	private String password;
	private String city;
	private String addr;
	private String contactPerson;
	private String phone;
	private String email;
	private String lastLoginIp;
	private Date lastLoginTime;
	
	public Partner() {
	}
	
	public Partner(int id, String lastLoginIp, Date lastLoginTime) {
		this.id = id;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
	}
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}

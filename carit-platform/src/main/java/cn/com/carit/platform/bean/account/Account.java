package cn.com.carit.platform.bean.account;

import java.util.Date;

import cn.com.carit.platform.bean.BaseBean;

/**
 * AccountInfo
 * Auto generated Code
 */
public class Account extends BaseBean {
	private static final long serialVersionUID = 4504537661413613532L;
	private String email;
	private String password;
	private String nickName;
	private Byte gender;
	private Date birthday;
	private String photo;
	private String thumbPhoto;
	private Double balance;
	private String realName;
	private String idCard;
	private String officePhone;
	private String mobile;
	private String address;
	private String lastLoginIp;
	private Date lastLoginTime;
	
	public Account() {
		
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getThumbPhoto() {
		return thumbPhoto;
	}
	public void setThumbPhoto(String thumbPhoto) {
		this.thumbPhoto = thumbPhoto;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

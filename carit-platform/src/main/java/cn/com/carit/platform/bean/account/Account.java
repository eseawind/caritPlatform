package cn.com.carit.platform.bean.account;

import java.util.Date;

import cn.com.carit.platform.bean.BaseBean;

/**
 * AccountInfo
 * Auto generated Code
 */
public class Account extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4504537661413613532L;
	/**
	 * email
	 */
	private String email;
	/**
	 * password 密码不会返回给前端
	 */
	private String password;
	/**
	 * nickName
	 */
	private String nickName;
	/**
	 * gender
	 */
	private Byte gender;
	/**
	 * birthday
	 */
	private Date birthday;
	/**
	 * photo
	 */
	private String photo;
	/**缩略头像*/
	private String thumbPhoto;
	/**
	 * balance
	 */
	private Double balance;
	/**
	 * realName
	 */
	private String realName;
	/**
	 * idCard
	 */
	private String idCard;
	/**
	 * officePhone
	 */
	private String officePhone;
	/**
	 * mobile
	 */
	private String mobile;
	/**
	 * address
	 */
	private String address;
	/**
	 * lastLoginIp
	 */
	private String lastLoginIp;
	/**
	 * lastLoginTime
	 */
	private Date lastLoginTime;
	
	public Account() {
		
	}
	
	
	public Account(int id, String email, String password, String nickName) {
		this.id=id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
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

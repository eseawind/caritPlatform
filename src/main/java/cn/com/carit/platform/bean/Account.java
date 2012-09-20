package cn.com.carit.platform.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * AccountInfo
 * Auto generated Code
 */
public class Account extends BaseBean implements Serializable{
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
	/**缩略头像路径*/
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

	public void setEmail(String value) {
		this.email = value;
	}
	public String getEmail() {
		return this.email;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public Byte getGender() {
		return this.gender;
	}
	public void setBirthday(Date value) {
		this.birthday = value;
	}
	public Date getBirthday() {
		return this.birthday;
	}
	public void setPhoto(String value) {
		this.photo = value;
	}
	public String getPhoto() {
		return this.photo;
	}
	
	public String getThumbPhoto() {
		return thumbPhoto;
	}
	public void setThumbPhoto(String thumbPhoto) {
		this.thumbPhoto = thumbPhoto;
	}
	public void setBalance(Double value) {
		this.balance = value;
	}
	public Double getBalance() {
		return this.balance;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setIdCard(String value) {
		this.idCard = value;
	}
	public String getIdCard() {
		return this.idCard;
	}
	public void setOfficePhone(String value) {
		this.officePhone = value;
	}
	public String getOfficePhone() {
		return this.officePhone;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	public String getMobile() {
		return this.mobile;
	}
	public void setAddress(String value) {
		this.address = value;
	}
	public String getAddress() {
		return this.address;
	}
	public void setLastLoginIp(String value) {
		this.lastLoginIp = value;
	}
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
	public void setLastLoginTime(Date value) {
		this.lastLoginTime = value;
	}
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
}
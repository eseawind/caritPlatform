package cn.com.carit.platform.response;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class AccountResponse {
	
	@XmlAttribute
	private int id;

	@XmlAttribute
	private String email;

	@XmlAttribute
	private String nickName;

	/** 性别：0 女；1 男；2 保密 */
	@XmlAttribute
	private Byte gender;
	
	@XmlAttribute
	private Date birthday;
	
	@XmlAttribute
	private String photo;

	@XmlAttribute
	private String thumbPhoto;
	
	@XmlAttribute
	private Double balance;

	@XmlAttribute
	private String realName;
	
	@XmlAttribute
	private String idCard;
	
	@XmlAttribute
	private String officePhone;
	
	@XmlAttribute
	private String mobile;

	@XmlAttribute
	private String address;

	@XmlAttribute
	private String lastLoginIp;
	
	@XmlAttribute
	private Date lastLoginTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "AccountResponse [id=" + id + ", email=" + email + ", nickName="
				+ nickName + ", gender=" + gender + ", birthday=" + birthday
				+ ", photo=" + photo + ", thumbPhoto=" + thumbPhoto
				+ ", balance=" + balance + ", realName=" + realName
				+ ", idCard=" + idCard + ", officePhone=" + officePhone
				+ ", mobile=" + mobile + ", address=" + address
				+ ", lastLoginIp=" + lastLoginIp + ", lastLoginTime="
				+ lastLoginTime + "]";
	}
	
}

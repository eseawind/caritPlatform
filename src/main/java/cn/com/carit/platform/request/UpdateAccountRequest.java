package cn.com.carit.platform.request;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;
import com.rop.annotation.IgnoreSign;

/**
 * <pre>
 * 功能说明：修改账号资料请求模型
 * </pre>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-21
 */
public class UpdateAccountRequest extends AbstractRopRequest {

	@NotEmpty
	@Pattern(regexp = Constants.REGEXP_EMAIL)
    private String email;

	@NotEmpty
    @IgnoreSign
    @Pattern(regexp = Constants.REGEXP_PASSWORD)
    private String password;
	
	@Length(min=3, max=50)
	private String nickName;

	@Max(value=2)
	@Min(value=0)
	private Byte gender;
	
	@Valid
	@DateTimeFormat(iso=ISO.DATE)
	private String birthday;

	@Length(min=3, max=20)
	private String realName;

	@Length(min=15, max=18)
	private String idCard;

	private String officePhone;

	@Pattern(regexp=Constants.REGEXT_MOBILE)
	private String mobile;
	
	private String address;

	
	public UpdateAccountRequest() {
		
	}

	public UpdateAccountRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	
}

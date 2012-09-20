/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-6-21
 */
package cn.com.carit.platform.response;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class LogonResponse{

    @XmlAttribute
    private String sessionId;
    
    @XmlAttribute
    private int id;
    
    @XmlAttribute
    private String email;
    
    @XmlAttribute
    private String nickName;
    
    @XmlAttribute
	private String photo;
    
    @XmlAttribute
	private String thumbPhoto;

	public LogonResponse() {

	}

	public LogonResponse(String sessionId, int id, String email,
			String nickName, String photo, String thumbPhoto) {
		super();
		this.sessionId = sessionId;
		this.id = id;
		this.email = email;
		this.nickName = nickName;
		this.photo = photo;
		this.thumbPhoto = thumbPhoto;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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
    
}


package cn.com.carit.platform.bean;

public class AppComment extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3854745319196108033L;
	
	/**
	 * appId
	 */
	private Integer appId;
	
	private String appName;
	
	private String enName;
	/**
	 * userId
	 */
	private Integer userId;
	
	private String userName;
	/**
	 * grade
	 */
	private Integer grade;
	/**
	 * comment
	 */
	private String comment;
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

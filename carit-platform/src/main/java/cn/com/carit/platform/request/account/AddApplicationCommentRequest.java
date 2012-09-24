package cn.com.carit.platform.request.account;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public class AddApplicationCommentRequest extends DownloadApplicationRequest {

	@Length(max=200)
	private String comment;
	
	@Min(value=0)
	@Max(value=10)
	private Integer grade;

	
	public AddApplicationCommentRequest() {
		super();
	}

	public AddApplicationCommentRequest(String email, String password) {
		super(email, password);
	}
	
	public AddApplicationCommentRequest(String email, String password,
			Integer appId) {
		super(email, password, appId);
	}

	public AddApplicationCommentRequest(String email, String password,
			Integer appId, String comment, Integer grade) {
		super(email, password, appId);
		this.comment = comment;
		this.grade = grade;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}

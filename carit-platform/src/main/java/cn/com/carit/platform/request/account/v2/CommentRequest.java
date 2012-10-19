package cn.com.carit.platform.request.account.v2;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public class CommentRequest extends ApplicationRequest {

	@Length(max=200)
	private String comment;
	
	@Min(value=0)
	@Max(value=10)
	private int grade;

	
	public CommentRequest() {
		super();
	}

	public CommentRequest(String email) {
		super(email);
	}
	
	public CommentRequest(String email,int appId) {
		super(email, appId);
	}

	public CommentRequest(String email,
			int appId, String comment, int grade) {
		super(email, appId);
		this.comment = comment;
		this.grade = grade;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}

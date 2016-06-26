package activity.sostv.com.model;

import java.sql.Timestamp;

public class SosComment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String resourceId;
	private String commentUser;
	private Timestamp commentTime;
	private String commentContent;


	public SosComment() {
	}


	public SosComment(String resourceId, String commentUser,
			Timestamp commentTime, String commentContent) {
		this.resourceId = resourceId;
		this.commentUser = commentUser;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getCommentUser() {
		return this.commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}

	public Timestamp getCommentTime() {
		return this.commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return this.commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
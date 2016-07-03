package activity.sostv.com.model;

import java.io.Serializable;
import java.util.Date;


/**
 * sos_feedback:用户反馈信息表; InnoDB free: 11264 kB
 */
public class SosFeedback implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String userName;
	private String userCname;
	private String content;
	private Date createTime;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCname() {
		return userCname;
	}
	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

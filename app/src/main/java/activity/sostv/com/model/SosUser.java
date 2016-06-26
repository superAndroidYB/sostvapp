package activity.sostv.com.model;

import java.io.Serializable;


public class SosUser implements Serializable {

	private static final long serialVersionUID = 1L;


	private int id;


	private String userName;


	private String userCname;

	private String userPassword;
	
	private String imageUrl;

	private String level;

	private String salt;

	private String loginOrReg;

	private String email;

	public SosUser() {
		super();
	}

	public SosUser(int id, String userName, String userCname,
			String userPassword, String level) {
		super();
		this.id = id;
		this.userName = userName;
		this.userCname = userCname;
		this.userPassword = userPassword;
		this.level = level;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}

	public String getUserCname() {
		return userCname;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLoginOrReg() {
		return loginOrReg;
	}

	public void setLoginOrReg(String loginOrReg) {
		this.loginOrReg = loginOrReg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return "SosUser [id=" + id + ",userName=" + userName + ",userCname="
				+ userCname + ",userPassword=" + userPassword + ",level="
				+ level + "]";
	}

}

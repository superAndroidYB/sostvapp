package activity.sostv.com.model;

import java.io.Serializable;

public class SosHome implements Serializable{

	private String id;
	private String title;
	private String subhead;
	private String ownType;
	private String content;
	private String imageUrl;
	private String lookNum;
	private long loveNum;
	private long collectNum;
	private String date;
	private String link;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public String getOwnType() {
		return ownType;
	}

	public void setOwnType(String ownType) {
		this.ownType = ownType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLookNum() {
		return lookNum;
	}

	public void setLookNum(String lookNum) {
		this.lookNum = lookNum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getLoveNum() {
		return loveNum;
	}

	public void setLoveNum(long loveNum) {
		this.loveNum = loveNum;
	}

	public long getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(long collectNum) {
		this.collectNum = collectNum;
	}

}

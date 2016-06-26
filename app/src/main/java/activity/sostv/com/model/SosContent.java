package activity.sostv.com.model;

import java.io.Serializable;

/**
 * sos_content:InnoDB free: 11264 kB
 */

public class SosContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String content;

	private String refId;

	public SosContent() {
		super();
	}

	public SosContent(String id, String content, String refId) {
		super();
		this.id = id;
		this.content = content;
		this.refId = refId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefId() {
		return refId;
	}

	public String toString() {
		return "SosContent [id=" + id + ",content=" + content + ",refId="
				+ refId + "]";
	}

}

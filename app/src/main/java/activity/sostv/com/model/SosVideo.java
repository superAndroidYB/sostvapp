package activity.sostv.com.model;

import java.io.Serializable;
import java.util.Date;

/**
 * sos_video:视频资源表; InnoDB free: 4096 kB
 */
public class SosVideo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID_:
	 */
	private int id;

	/**
	 * VIDEO_NAME_:
	 */
	private String videoName;

	/**
	 * VEDIO_IMAGE_:
	 */
	private String vedioImage;

	/**
	 * VIDEO_HD_URL_:
	 */
	private String videoHdUrl;

	/**
	 * VIDEO_SD_URL_:
	 */
	private String videoSdUrl;

	/**
	 * PUB_DATE_:
	 */
	private String pubDate;

	/**
	 * LOVE_NUM_:
	 */
	private int loveNum;

	/**
	 * LOOK_NUM_:
	 */
	private int lookNum;

	/**
	 * TYPE_LEVEL1_:
	 */
	private String typeLevel1;

	/**
	 * TYPE_LEVEL2_:
	 */
	private String typeLevel2;

	public SosVideo() {
		super();
	}

	public SosVideo(int id, String videoName, String vedioImage,
					String videoHdUrl, String videoSdUrl, Date pubDate, int loveNum,
					int lookNum, String typeLevel1, String typeLevel2) {
		super();
		this.id = id;
		this.videoName = videoName;
		this.vedioImage = vedioImage;
		this.videoHdUrl = videoHdUrl;
		this.videoSdUrl = videoSdUrl;
//		this.pubDate = pubDate;
		this.loveNum = loveNum;
		this.lookNum = lookNum;
		this.typeLevel1 = typeLevel1;
		this.typeLevel2 = typeLevel2;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVedioImage(String vedioImage) {
		this.vedioImage = vedioImage;
	}

	public String getVedioImage() {
		return vedioImage;
	}

	public void setVideoHdUrl(String videoHdUrl) {
		this.videoHdUrl = videoHdUrl;
	}

	public String getVideoHdUrl() {
		return videoHdUrl;
	}

	public void setVideoSdUrl(String videoSdUrl) {
		this.videoSdUrl = videoSdUrl;
	}

	public String getVideoSdUrl() {
		return videoSdUrl;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setLoveNum(int loveNum) {
		this.loveNum = loveNum;
	}

	public int getLoveNum() {
		return loveNum;
	}

	public void setLookNum(int lookNum) {
		this.lookNum = lookNum;
	}

	public int getLookNum() {
		return lookNum;
	}

	public void setTypeLevel1(String typeLevel1) {
		this.typeLevel1 = typeLevel1;
	}

	public String getTypeLevel1() {
		return typeLevel1;
	}

	public void setTypeLevel2(String typeLevel2) {
		this.typeLevel2 = typeLevel2;
	}

	public String getTypeLevel2() {
		return typeLevel2;
	}

	public String toString() {
		return "SosVideo [id=" + id + ",videoName=" + videoName
				+ ",vedioImage=" + vedioImage + ",videoHdUrl=" + videoHdUrl
				+ ",videoSdUrl=" + videoSdUrl + ",pubDate="
				+ ",loveNum=" + loveNum + ",lookNum=" + lookNum
				+ ",typeLevel1=" + typeLevel1 + ",typeLevel2=" + typeLevel2
				+ "]";
	}

}

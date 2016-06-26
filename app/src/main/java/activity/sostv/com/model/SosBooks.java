package activity.sostv.com.model;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * sos_books:�鼮��Դ��; InnoDB free: 4096 kB
 */
@Table(name = "SOS_BOOKS")
public class SosBooks implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID_:
	 */
	@Id(column="ID_")
	private int id;

	/**
	 * BOOK_NAME_:
	 */
	@Column(column = "BOOK_NAME_")
	private String bookName;

	/**
	 * BOOK_IMAGE_:
	 */
	@Column(column = "BOOK_IMAGE_")
	private String bookImage;

	/**
	 * BOOK_LINK_:
	 */
	@Column(column = "BOOK_LINK_")
	private String bookLink;

	/**
	 * LOVE_NUM_:
	 */
	@Column(column = "LOVE_NUM_")
	private int loveNum;

	/**
	 * LOOK_NUM_:
	 */
	@Column(column = "LOOK_NUM_")
	private int lookNum;

	/**
	 * TYPE_LEVEL1_:
	 */
	@Column(column = "TYPE_LEVEL1_")
	private String typeLevel1;

	/**
	 * TYPE_LEVEL2_:
	 */
	@Column(column = "TYPE_LEVEL2_")
	private String typeLevel2;

	public SosBooks() {
		super();
	}

	public SosBooks(int id, String bookName, String bookImage, String bookLink,
			int loveNum, int lookNum, String typeLevel1, String typeLevel2) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.bookImage = bookImage;
		this.bookLink = bookLink;
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

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookLink(String bookLink) {
		this.bookLink = bookLink;
	}

	public String getBookLink() {
		return bookLink;
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
		return "SosBooks [id=" + id + ",bookName=" + bookName + ",bookImage="
				+ bookImage + ",bookLink=" + bookLink + ",loveNum=" + loveNum
				+ ",lookNum=" + lookNum + ",typeLevel1=" + typeLevel1
				+ ",typeLevel2=" + typeLevel2 + "]";
	}

}

package cn.com.carit.common;



/**
 * <p>
 * <b>功能说明：</b>新浪RSS分类
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-11-29
 */
public class RssCatalog {
	private int id;
	private int parentId;
	private String title;
	private String url;
	
	public RssCatalog() {
		super();
	}
	public RssCatalog(int id, int parentId, String title,
			String url) {
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

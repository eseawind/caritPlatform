package cn.com.carit.platform.bean;

import java.util.Date;


public class RssNews extends BaseBean {

	private static final long serialVersionUID = -440330194260935809L;

	private Integer catalogId;
	private String title;
	private String sourceUrl;
	private Date pubDate;
	private String description;
	private String imgSrc;
	private String content;
	
	private Date startDate;
	private Date endDate;
	
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

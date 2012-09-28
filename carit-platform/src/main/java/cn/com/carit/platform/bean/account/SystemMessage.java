package cn.com.carit.platform.bean.account;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.common.jackjson.CustomDateSerializer;
import cn.com.carit.platform.bean.BaseBean;

public class SystemMessage  extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7024186513398819659L;
	public static final int CATALOG_SYSTEM_PUSH=0;
	public static final int CATALOG_APP_UPDATED=1;
	
	public static final int STATUS_UNREAD=0;
	public static final int STATUS_READ=1;
	/**账号Id*/
	private Integer accountId;
	/**消息类别*/
	private Integer catalog;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**状态*/
	private Integer status;
	
	public SystemMessage() {
	}
	public SystemMessage(Integer accountId) {
		super();
		this.accountId = accountId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getCatalog() {
		return catalog;
	}
	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public Integer getStatus() {
		return status;
	}
	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() {
		return super.getUpdateTime();
	}
	@Override
	public String toString() {
		return "SystemMessage [accountId=" + accountId + ", catalog=" + catalog
				+ ", title=" + title + ", content=" + content + ", status="
				+ status + "]";
	}
}

package cn.com.carit.platform.request.market;

import org.hibernate.validator.constraints.NotEmpty;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

public class FullTextSearchRequest extends AbstractRopRequest {
	
	protected String language=Constants.DEAFULD_LANGUAGE;
	protected int page=Constants.PAGE_START; //当前页,名字必须为page
	protected int rows=Constants.PAGE_SIZE ; //每页大小,名字必须为rows
	@NotEmpty
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
}

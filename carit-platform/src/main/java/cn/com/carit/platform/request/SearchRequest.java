package cn.com.carit.platform.request;

import cn.com.carit.common.Constants;

import com.rop.AbstractRopRequest;

/**
 * <p>
 * <b>功能说明：</b>分页查询请求模型基类
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-9-27
 */
public class SearchRequest extends AbstractRopRequest{
	
	
	protected String language=Constants.DEAFULD_LANGUAGE;
	protected int page=Constants.PAGE_START; //当前页,名字必须为page
	protected int rows=Constants.PAGE_SIZE ; //每页大小,名字必须为rows
	protected String sort; //排序字段
	protected String order; //排序规则
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}

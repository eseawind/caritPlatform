package cn.com.carit.platform.service;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.SearchRssNewsByCatalogRequest;
import cn.com.carit.platform.response.PageResponse;

import com.rop.AbstractRopRequest;
import com.rop.RopRequest;
import com.rop.RopRequestContext;

public class RssServiceTestCase {

	@Test
	public void testQueryParentCatalogs(){
		RopRequest request=new AbstractRopRequest(){};
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, List.class, "rss.parent.catalogs", "1.0");
	}
	
	@Test
	public void testAllSubCatalogs(){
		RopRequest request=new AbstractRopRequest(){};
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, List.class, "rss.all.sub.catalogs", "1.0");
	}
	
	@Test
	public void testQuerySubCatalogs(){
		RopTestCaseClient.getRopClient().buildClientRequest().get(new RopRequest() {
			private final int parentId=1;
			@Override
			public RopRequestContext getRopRequestContext() {
				return null;
			}
		}, List.class, "rss.sub.catalogs", "1.0");
	}
	
	@Test
	public void testQueryNewsByCatalog(){
		SearchRssNewsByCatalogRequest request=new SearchRssNewsByCatalogRequest();
		request.setCatalogId(101);
		request.setRows(10);
		RopTestCaseClient.getRopClient().buildClientRequest().get(request, PageResponse.class, "rss.query.news.by.catalog", "1.0");
	}
	
	@Test
	public void testQueryById(){
		RopTestCaseClient.getRopClient().buildClientRequest().get(new RopRequest() {
			private final int id=66;
			@Override
			public RopRequestContext getRopRequestContext() {
				return this.getRopRequestContext();
			}
		}, Map.class, "rss.view.news", "1.0");
	}
}

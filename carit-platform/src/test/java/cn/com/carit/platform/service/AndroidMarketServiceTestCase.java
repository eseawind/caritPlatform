package cn.com.carit.platform.service;

import java.util.List;

import org.testng.annotations.Test;

import cn.com.carit.platform.RopTestCaseClient;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.request.market.FullTextSearchRequest;
import cn.com.carit.platform.request.market.SearchAppDeveloperRequest;
import cn.com.carit.platform.request.market.SearchApplicationRequest;
import cn.com.carit.platform.request.market.TopRequest;
import cn.com.carit.platform.request.market.ViewApplicationRequest;
import cn.com.carit.platform.response.PageResponse;
import cn.com.carit.platform.response.market.ApplicationResponse;


public class AndroidMarketServiceTestCase {

	@Test
	public void testQueryApplication(){}{
		SearchApplicationRequest request=new SearchApplicationRequest();
		request.setLanguage("cn");
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "market.query.application", "1.0");
	}
	
	@Test
	public void testFullTextSearch(){
		FullTextSearchRequest request=new FullTextSearchRequest();
		request.setLanguage("cn");
		request.setKeyword("12");
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "market.full.text.search.application", "1.0");
	}
	
	@Test
	public void testViewApplication(){
		ViewApplicationRequest request=new ViewApplicationRequest();
		request.setLanguage("cn");
		request.setAppId(6);
		RopTestCaseClient.getInstance().buildClientRequest().get(request, ApplicationResponse.class, "market.view.application", "1.0");
	}
	
	@Test
	public void testQueryAppVersions(){
		DownloadedReferencedRequest request=new DownloadedReferencedRequest();
		request.setLanguage("cn");
		request.setAppId(6);
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "market.query.application.versions", "1.0");
	}
	
	@Test
	public void testQueryAppComments(){
		DownloadedReferencedRequest request=new DownloadedReferencedRequest();
		request.setLanguage("cn");
		request.setAppId(6);
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "market.query.application.comments", "1.0");
	}
	
	@Test
	public void testQueryHotFreeApplication(){
		TopRequest request=new TopRequest();
		request.setLanguage("cn");
		request.setLimit(10);
		RopTestCaseClient.getInstance().buildClientRequest().get(request, List.class, "market.application.hot.free", "1.0");
	}
	
	@Test
	public void testQueryHotNewFreeApplication(){
		TopRequest request=new TopRequest();
		request.setLanguage("cn");
		request.setLimit(10);
		RopTestCaseClient.getInstance().buildClientRequest().get(request, List.class, "market.application.hot.new.free", "1.0");
	}
	
	@Test
	public void testQueryDownloadedReferencedApps(){
		DownloadedReferencedRequest request=new DownloadedReferencedRequest();
		request.setLanguage("cn");
		request.setAppId(6);
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "market.application.downloaded.referenced", "1.0");
	}
	
	@Test
	public void testQueryDeveloper(){
		SearchAppDeveloperRequest request=new SearchAppDeveloperRequest();
		RopTestCaseClient.getInstance().buildClientRequest().get(request, PageResponse.class, "market.developer.query", "1.0");
	}
}

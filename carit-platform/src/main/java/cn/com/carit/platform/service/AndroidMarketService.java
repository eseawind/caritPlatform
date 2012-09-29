package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.platform.action.AppCatalogAction;
import cn.com.carit.platform.action.AppCommentAction;
import cn.com.carit.platform.action.AppDownloadLogAction;
import cn.com.carit.platform.action.AppVersionAction;
import cn.com.carit.platform.action.ApplicationAction;
import cn.com.carit.platform.bean.market.AppCatalog;
import cn.com.carit.platform.bean.market.AppComment;
import cn.com.carit.platform.bean.market.AppDownloadLog;
import cn.com.carit.platform.bean.market.AppVersion;
import cn.com.carit.platform.bean.market.Application;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.request.market.FullTextSearchRequest;
import cn.com.carit.platform.request.market.SearchAppDeveloperRequest;
import cn.com.carit.platform.request.market.SearchApplicationRequest;
import cn.com.carit.platform.request.market.TopRequest;
import cn.com.carit.platform.request.market.ViewApplicationRequest;
import cn.com.carit.platform.response.PageResponse;
import cn.com.carit.platform.response.market.AppCatalogResponse;
import cn.com.carit.platform.response.market.AppDeveloperResponse;
import cn.com.carit.platform.response.market.AppVersionResponse;
import cn.com.carit.platform.response.market.ApplicationResponse;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;

/**
 * <p>
 * <b>功能说明：</b>Android Market 服务
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-21
 */
@ServiceMethodBean(version = "1.0")
public class AndroidMarketService {
	
	private final static String LANGUAGE_PARAM_NAME="language";
	
	@Resource
	private ApplicationAction<Application> applicationAction;
	@Resource
	private AppCatalogAction<AppCatalog> appCatalogAction;
	@Resource
	private AppVersionAction<AppVersion> appVersionAction;
	@Resource
	private AppDownloadLogAction<AppDownloadLog> appDownloadLogAction;
	@Resource
	private AppCommentAction<AppComment> appCommentAction;
	
	/**
	 * <p>
	 * <b>功能说明：</b>获取所有有效应用分类
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.all.application.catalog</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.all.application.catalog",version = "1.0", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object allApplicationCatalog(RopRequest request){
		String language=request.getRopRequestContext().getParamValue(LANGUAGE_PARAM_NAME);
		if (!StringUtils.hasText(language)) {
			language=Constants.DEAFULD_LANGUAGE;
		}
		return appCatalogAction.queryByLanguage(language);
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询应用
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.query.application</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sort</td><td>排序字段</td><td>是</td><td>否</td></tr>
	 *  <tr><td>order</td><td>排序规则（desc/asc）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>appName</td><td>应用名称</td><td>是</td><td>否</td></tr>
	 *  <tr><td>version</td><td>版本</td><td>是</td><td>否</td></tr>
	 *  <tr><td>developer</td><td>开发者</td><td>是</td><td>否</td></tr>
	 *  <tr><td>catalogId</td><td>分类Id</td><td>是</td><td>否</td></tr>
	 *  <tr><td>catalogName</td><td>分类名称</td><td>是</td><td>否</td></tr>
	 *  <tr><td>size</td><td>应用大小</td><td>是</td><td>否</td></tr>
	 *  <tr><td>platform</td><td>适用平台</td><td>是</td><td>否</td></tr>
	 *  <tr><td>supportLanguages</td><td>支持语言</td><td>是</td><td>否</td></tr>
	 *  <tr><td>downCount</td><td>下载次数</td><td>是</td><td>否</td></tr>
	 *  <tr><td>appLevel</td><td>评级</td><td>是</td><td>否</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.query.application",version = "1.0", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryApplication(SearchApplicationRequest request){
		PageResponse<ApplicationResponse> page=new PageResponse<ApplicationResponse>();
		BeanUtils.copyProperties(applicationAction.queryByExemple(request), page);
		return page;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>全文检索应用应用
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.logon</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>keyword</td><td> @NotNull </td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.full.text.search.application",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object fullTextSearch(FullTextSearchRequest request){
		PageResponse<ApplicationResponse> page=new PageResponse<ApplicationResponse>();
		BeanUtils.copyProperties(applicationAction.fullTextSearch(request), page);
		return page;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查看应用
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.logon</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.view.application",version = "1.0", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object viewApplication(ViewApplicationRequest request){
		return applicationAction.queryAppById(request);
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询应用版本
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.query.application.versions</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sort</td><td>排序字段</td><td>是</td><td>否</td></tr>
	 *  <tr><td>order</td><td>排序规则（desc/asc）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.query.application.versions",version = "1.0", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryAppVersions(DownloadedReferencedRequest request){
		PageResponse<AppVersionResponse> page=new PageResponse<AppVersionResponse>();
		BeanUtils.copyProperties(appVersionAction.queryAppVersions(request), page);
		return page;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询应用评论
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.query.application.comments</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.query.application.comments",version = "1.0", httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryAppComments(DownloadedReferencedRequest request){
		DataGridModel dgm = new DataGridModel();
		dgm.setSort(request.getSort());
		dgm.setOrder(request.getOrder());
		dgm.setPage(request.getPage());
		dgm.setRows(request.getRows());
		PageResponse<AppCatalogResponse> page=new PageResponse<AppCatalogResponse>();
		BeanUtils.copyProperties(appCommentAction.queryAppComments(request.getAppId(), dgm), page);
		return page;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>热门免费应用
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.application.hot.free</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>limit</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.application.hot.free",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryHotFreeApplication(TopRequest request){
		return applicationAction.queryHotFree(request);
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>热门免费新应用
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.application.hot.new.free</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>limit</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.application.hot.new.free",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryHotNewFreeApplication(TopRequest request){
		return applicationAction.queryHotNewFree(request);
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>应用评级统计
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.application.stat.grade</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.application.stat.grade",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object statApplicationCommentGrade(RopRequest request){
		return appCommentAction.statCommentGrade(Integer.valueOf(request.getRopRequestContext().getParamValue("appId")));
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询应用平均评分
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.application.avg.grade</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.application.avg.grade",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryApplicationAvgGrade(RopRequest request){
		return appCommentAction.statComment(Integer.valueOf(request.getRopRequestContext().getParamValue("appId")));
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询应用一个月内的下载趋势
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.application.download.trend.one.month</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.application.download.trend.one.month",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object applicationOneMonthDownTrend(RopRequest request){
		return appDownloadLogAction.appOneMonthDownTrend(Integer.valueOf(request.getRopRequestContext().getParamValue("appId")));
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询下载过该应用的用户还下载过那些应用
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>account.logon</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>language</td><td>语言(cn/en)</td><td>是</td><td>否（默认cn）</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sort</td><td>排序字段</td><td>是</td><td>否</td></tr>
	 *  <tr><td>order</td><td>排序规则（desc/asc）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>appId</td><td> @NotNull @Min(value=1) @Max(value=Integer.MAX_VALUE)</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.application.downloaded.referenced",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryDownloadedReferencedApps(DownloadedReferencedRequest request){
		PageResponse<ApplicationResponse> page=new PageResponse<ApplicationResponse>();
		BeanUtils.copyProperties(applicationAction.queryDownloadedReferencedApps(request), page);
		return page;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询应用开发者
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>market.application.download.trend.one.month</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml</td><td>是</td><td>否</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>email</td><td>开发者邮箱</td><td>是</td><td>否</td></tr>
	 *  <tr><td>name</td><td>开发者名称</td><td>是</td><td>否</td></tr>
	 *  <tr><td>website</td><td>开发者网站</td><td>是</td><td>否</td></tr>
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.developer.query",version = "1.0",httpAction=HttpAction.GET, needInSession=NeedInSessionType.NO)
	public Object queryDeveloper(SearchAppDeveloperRequest request){
		PageResponse<AppDeveloperResponse> page=new PageResponse<AppDeveloperResponse>();
		BeanUtils.copyProperties(applicationAction.queryAppDeveloper(request), page);
		return page;
	}
	
}

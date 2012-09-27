package cn.com.carit.platform.service;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.action.AppCommentAction;
import cn.com.carit.platform.action.AppDownloadLogAction;
import cn.com.carit.platform.bean.market.AppComment;
import cn.com.carit.platform.bean.market.AppDownloadLog;
import cn.com.carit.platform.request.account.SearchAccountRequest;
import cn.com.carit.platform.request.market.SearchApplicationRequest;
import cn.com.carit.platform.request.market.SearchDeveloperRequest;
import cn.com.carit.platform.request.market.TopRequest;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
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
	private AppDownloadLogAction<AppDownloadLog> appDownloadLogAction;
	@Resource
	private AppCommentAction<AppComment> appCommentAction;
	
	/**
	 * <p>
	 * <b>功能说明：</b>账号登录
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
	 * </table>
	 * @return
	 */
	@ServiceMethod(method = "market.all.application.catalog",version = "1.0", httpAction=HttpAction.GET)
	public Object allApplicationCatalog(RopRequest request){
		String language=request.getRopRequestContext().getParamValue(LANGUAGE_PARAM_NAME);
		if (!StringUtils.hasText(language)) {
			language=Constants.DEAFULD_LANGUAGE;
		}
		return null;
	}
	
	@ServiceMethod(method = "market.query.application",version = "1.0", httpAction=HttpAction.GET)
	public Object queryApplication(SearchApplicationRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.view.application",version = "1.0", httpAction=HttpAction.GET)
	public Object viewApplication(SearchApplicationRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.query.application.versions",version = "1.0", httpAction=HttpAction.GET)
	public Object queryAppVersions(SearchApplicationRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.query.application.comments",version = "1.0", httpAction=HttpAction.GET)
	public Object queryAppComments(SearchApplicationRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.hot.free",version = "1.0",httpAction=HttpAction.GET)
	public Object queryHotFreeApplication(TopRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.hot.new.free",version = "1.0",httpAction=HttpAction.GET)
	public Object queryHotNewFreeApplication(TopRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.stat.grade",version = "1.0",httpAction=HttpAction.GET)
	public Object statApplicationCommentGrade(SearchApplicationRequest request){
		//TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.avg.grade",version = "1.0",httpAction=HttpAction.GET)
	public Object queryApplicationAvgGrade(SearchApplicationRequest request){
		//TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.download.trend.one.month",version = "1.0",httpAction=HttpAction.GET)
	public Object applicationOneMonthDownTrend(SearchApplicationRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.account.downloaded",version = "1.0",httpAction=HttpAction.GET)
	public Object queryAccountDownloadedApp(SearchAccountRequest request) {
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.application.downloaded.referenced",version = "1.0",httpAction=HttpAction.GET)
	public Object queryDownloadedReferencedApps(SearchApplicationRequest request){
		// TODO
		return null;
	}
	
	@ServiceMethod(method = "market.developer.query",version = "1.0",httpAction=HttpAction.GET)
	public Object queryDeveloper(SearchDeveloperRequest request){
		// TODO
		return null;
	}
	
}

package cn.com.carit.platform.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.JsoupUtils;
import cn.com.carit.platform.action.RssNewsAction;
import cn.com.carit.platform.bean.RssNews;
import cn.com.carit.platform.request.SearchRssNewsByCatalogRequest;
import cn.com.carit.platform.response.PageResponse;

import com.rop.RopRequest;
import com.rop.annotation.HttpAction;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.BusinessServiceErrorResponse;

@ServiceMethodBean(version = "1.0", needInSession=NeedInSessionType.NO, httpAction=HttpAction.GET)
public class RssService {

	@Resource
	private RssNewsAction<RssNews> action;
	
	/**
	 * <p>
	 * <b>功能说明：</b>查询RSS父级分类
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>rss.parent.catalogs</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return [{"id":1,"title":"新闻中心"},{"id":2,"title":"博客频道"},{"id":3,"title":"体育频道"},{"id":4,"title":"科技频道"},{"id":5,"title":"财经频道"},{"id":6,"title":"影音娱乐"},{"id":7,"title":"读书频道"},{"id":8,"title":"女性频道"},{"id":9,"title":"男性频道"},{"id":10,"title":"军事频道"},{"id":11,"title":"汽车新闻"},{"id":12,"title":"教育频道"},{"id":13,"title":"家居"},{"id":14,"title":"游戏频道"},{"id":15,"title":"星座频道"}]
	 */
	@ServiceMethod(method = "rss.parent.catalogs")
	public Object queryParentCatalogs(RopRequest request){
		return CaritUtils.rssCatalogsToResponse(JsoupUtils.getInstance()
				.getParents());
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>按RSS父分类ID查询子分类
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>rss.sub.catalogs</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>parentId</td><td>NotEmpty</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return [{"id":201,"title":"文化"},{"id":202,"title":"独家"},{"id":203,"title":"情感"},{"id":204,"title":"八卦"},{"id":205,"title":"生活纪录"},{"id":206,"title":"观点"},{"id":207,"title":"财经"},{"id":208,"title":"股票"},{"id":209,"title":"娱乐"},{"id":210,"title":"女性"},{"id":211,"title":"IT"},{"id":212,"title":"房产"},{"id":213,"title":"教育"},{"id":214,"title":"星座"},{"id":215,"title":"汽车"},{"id":216,"title":"游戏"},{"id":217,"title":"体育"},{"id":218,"title":"美食"},{"id":219,"title":"家居"},{"id":220,"title":"育儿"},{"id":221,"title":"健康"},{"id":222,"title":"军事"}]
	 */
	@ServiceMethod(method = "rss.sub.catalogs")
	public Object querySubCatalogs(RopRequest request){
		String parentId=request.getRopRequestContext().getParamValue("parentId");
		if (!StringUtils.hasText(parentId)) {
			return new BusinessServiceErrorResponse(request
					.getRopRequestContext().getMethod(),
					Constants.CATALOG_ID_IS_EMPTY, request
							.getRopRequestContext().getLocale(), parentId);
		}
		return CaritUtils.rssCatalogsToResponse(JsoupUtils.getInstance()
				.getSubCatalogs(Integer.valueOf(parentId)));
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>所有子分类
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>rss.sub.catalogs</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>1.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 * </table>
	 * @return [{"id":201,"title":"文化"},{"id":202,"title":"独家"},{"id":203,"title":"情感"},{"id":204,"title":"八卦"},{"id":205,"title":"生活纪录"},{"id":206,"title":"观点"},{"id":207,"title":"财经"},{"id":208,"title":"股票"},{"id":209,"title":"娱乐"},{"id":210,"title":"女性"},{"id":211,"title":"IT"},{"id":212,"title":"房产"},{"id":213,"title":"教育"},{"id":214,"title":"星座"},{"id":215,"title":"汽车"},{"id":216,"title":"游戏"},{"id":217,"title":"体育"},{"id":218,"title":"美食"},{"id":219,"title":"家居"},{"id":220,"title":"育儿"},{"id":221,"title":"健康"},{"id":222,"title":"军事"}]
	 */
	@ServiceMethod(method = "rss.all.sub.catalogs")
	public Object queryAllSubCatalogs(RopRequest request){
		return CaritUtils.rssCatalogsToResponse(JsoupUtils.getInstance()
				.allSubCatalogs());
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>按分类查询
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>rss.query.news.by.catalog</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>2.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>catalogId</td><td>NotNull</td><td>是</td><td>是</td></tr>
	 *  <tr><td>page</td><td>起始页（默认1）</td><td>是</td><td>否</td></tr>
	 *  <tr><td>rows</td><td>每页显示记录数（默认10）</td><td>是</td><td>否</td></tr>
	 * </table>
	 * @return {"total":22,"rows":[{"title":"[财经]霍比特人为新西兰增加28%游客(11/30 10:49)","pubDate":1354243769000,"description":"　　文/新浪财经新西兰特约观察员 张政业[微博] 　　11月28日，电影《霍比特人》在惠灵顿举行了盛大的全球首映礼，新西兰的旅游产业也随之正式进入剧中人物生活的“中土”世界。电影《霍比特人》将为新西兰带来巨大的经济效益，预计六年内来到新西兰的游客将会增加28%。而中国...."},{"title":"[科技]苹果iPhone 5现货充足 线上发货时间缩短(11/30 10:45)","pubDate":1354243554000,"description":"　　新浪科技讯 北京时间11月30日上午消息，美国投资银行派杰与花旗分析师实施的最新调查发现，iPhone 5供货已经得到全面改善，苹果零售店内iPhone 5现货充足，消费者不需要再排队购买。 　　派杰分析师吉恩・蒙斯特(Gene Munster)对美国20家苹果零售店进行了调查，他表示现在...."},{"title":"[体育]林志杰无奈换鞋脚跟磨肿 商业利益球员健康需平衡(11/30 10:41)","pubDate":1354243310000,"description":"　　《2012－2013中国男子篮球职业联赛纪律处罚规定》第二章第十条中规定，球员在开幕式、赛前热身、比赛现场、新闻发布会等场合必须穿着联赛指定装备及配饰(不包括外籍球员的球鞋)，不得穿着、使用或展示联赛装备商竞争品牌的产品。外籍球员穿着非联赛装备商的球鞋时，须对球鞋上...."},{"title":"[体育]市民可办卡游崂山期内不限进山次数(11/30 10:26)","pubDate":1354242390000,"description":"　　半岛网11月29消息日前，记者从崂山风景区管理局获悉，为助推全民登山健身运动，崂山首次推出短期版登山卡产品――“欢乐健康卡”。据了解，此卡每张价格为100元，有效使用期为自办卡之日至2013年3月31日，持卡人不但可无限次畅游崂山，还可享受免费乘坐观光车的优惠。 　　...."},{"title":"[体育]主帅年薪排行:穆帅第一里皮压弗格森 前10中国2席(11/30 10:24)","pubDate":1354242246000,"description":"　　时近2012年的年末，在即将跨入新的一年之前，《每日邮报》统计了一份最新的数据名单，名单中收录了世界足坛主教练们的年薪工资，并且做出了一个最新的排行榜单。在这份排名中，皇马[微博]主帅穆里尼奥以1230万英镑的年薪排在了第一位，前三名中居然还有一位来自中超[微博]联赛...."},{"title":"[科技]微软确认Surface Pro续航时间仅4小时(11/30 10:17)","pubDate":1354241863000,"description":"微软Surface平板电脑 　　新浪科技讯 北京时间11月30日上午消息，据美国科技资讯网站TheNextWeb报道，微软Surface Pro平板电脑的电池续航只有4小时，仅为Surface RT的一半。微软官方和Surface总经理帕诺斯・帕奈(Panos Panay)均证实了这一消息。 　　微软今天公布了Surface...."},{"title":"[体育]英国游客被困广西无名山 警民成功施救(11/30 10:08)","pubDate":1354241327000,"description":"　　龙虎网讯 “走进阳朔公园，见到一座纪念碑，向左走二三十米，再向右走十几米，步行100多级台阶上山，到了一处空地，前方无路可走，开始攀岩，三个小时后，爬上山顶……” 　　11月27日下午，英国游客格雷(音译)被困山上，他不仅无法准确描述自己所在的位置，甚至连爬的是哪...."},{"title":"[财经]夫妻种地攒钱为儿买房 背15万零钞付首付(图)(11/30 10:00)","pubDate":1354240823000,"description":"有8万元是10元和20元的零钞用皮筋扎起来。图/房产公司提供 　　亚心网讯 5块、10块、20块……15万整整数了3个小时。28日，来自玛纳斯的张先生背着两大包钱，来到位于乌鲁木齐市卫星广场附近的一家售楼中心买房，15万的首付款中有8万元是零钱，这可让售楼中心工作人员好一顿忙活...."},{"title":"[财经]中国企业高管薪酬10年增长3.5倍 居全球之首(11/30 09:49)","pubDate":1354240140000,"description":"　　每经实习记者 李彪 发自北京 　　一项基于全球2万多家公司、超过1400万名员工薪酬数据的最新研究结果显示：在过去10年中，中国企业高层管理者的平均总现金水平(基本工资和奖金)增长速度达到3.5倍，居全球榜首。 　　《每日经济新闻》记者获悉，昨天(11月29日)，全球管理...."},{"title":"[财经]马自达10月对华出口降为零 昼夜两班制改一班(11/30 09:45)","pubDate":1354239900000,"description":"　　【《财经》综合报道】据日本新闻网报道，日本各大汽车制造商29日发布10月份对中国出口汽车的业绩报告，报告显示，马自达汽车10月份对中国出口已经降为零，丰田汽车也大幅减少了90%。 　　马自达公司29日发表的业绩报告说，今年9月，马自达公司向中国出口了1065辆汽车，10月...."}],"totalPage":3}
	 */
	@ServiceMethod(method = "rss.query.news.by.catalog")
	public Object queryNewsByCatalog(SearchRssNewsByCatalogRequest request){
		DataGridModel dgm = new DataGridModel();
		if (request.getPage()>0) {
			dgm.setPage(request.getPage());
		}
		if (request.getRows()>0) {
			dgm.setRows(request.getRows());
		}
		if (StringUtils.hasText(request.getOrder())) {
			dgm.setOrder(request.getOrder());
		}
		if (StringUtils.hasText(request.getSort())) {
			dgm.setSort(request.getSort());
		}
		PageResponse<Map<String, Object>> pageResponse=new PageResponse<Map<String, Object>>();
		JsonPage<Map<String, Object>> jsonPage = action.queryByCatalogId(request.getCatalogId(), dgm);
		BeanUtils.copyProperties(jsonPage, pageResponse);
		return pageResponse;
	}
	
	/**
	 * <p>
	 * <b>功能说明：</b>阅读新闻
	 * </p>
	 * @param request
	 * <table border='1'>
	 * 	<tr><th>参数名</th><th>规则/值</th><th>是否需要签名</th><th>是否必须</th></tr>
	 *  <tr><td>appKey</td><td>申请时的appKey</td><td>是</td><td>是</td></tr>
	 *  <tr><td>method</td><td>rss.view.news</td><td>是</td><td>是</td></tr>
	 *  <tr><td>v</td><td>2.0</td><td>是</td><td>是</td></tr>
	 *  <tr><td>locale</td><td>zh_CN/en</td><td>是</td><td>是</td></tr>
	 *  <tr><td>messageFormat</td><td>json/xml（可选，默认xml）</td><td>是</td><td>是</td></tr>
	 *  <tr><td>sign</td><td>所有需要签名的参数按签名规则生成sign</td><td>否</td><td>是</td></tr>
	 *  <tr><td>id</td><td>NotNull</td><td>是</td><td>是</td></tr>
	 * </table>
	 * @return {@link String}
	 */
	@ServiceMethod(method = "rss.view.news")
	public Object queryById(RopRequest request){
		String id=request.getRopRequestContext().getParamValue("id");
		if (StringUtils.hasText(id)) {
			return action.readContent(Integer.valueOf(id));
		}
		return new Object();
	}
}

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
	 * @return {"total":62,"rows":[{"id":3383,"title":"标题","pubDate":1354516479000,"description":"描述"},{"id":3384,"title":"标题","pubDate":1354516328000,"description":"描述"},{"id":3385,"title":"标题","pubDate":1354516091000,"description":"描述"},{"id":3386,"title":"标题","pubDate":1354516028000,"description":"描述"},{"id":3387,"title":"标题","pubDate":1354515357000,"description":"描述"}],"totalPage":7}
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
	 * @return {"imgSrc":"http://i3.sinaimg.cn/ty/outdoor/idx/2012/1130/U8451P6T1024D3239F30636DT20121130093656.jpg","content":"　　几天前，一场突如其来的暴风雪袭击了位于太白县鳌山的最高点导航架，阻止了10名驴友的脚步，3人倒在暴风雪中，7人严重冻伤。 　　悲剧之后，太白县下了“逐客令”：严禁户外爱好者私自攀登太白山和鳌山。可是县域面积2780平方公里的太白县每一座山、每一条沟都是上太白、登鳌山的路径，如何能阻止驴友们的热情呢？面对10年来太白山、鳌山发生最严重的一次“山难”，如何科学登山、理性穿越成了一道严峻而深刻的课题。 　　穿越之魅 　　“那原始的山水充满着诱惑”“攀登高山，让你觉得生机勃勃” 　　秦岭独特的地理位置造就了其得天独厚的生物、自然资源，山中松涛阵阵、高涧飞瀑，高山草甸绿草如毯、太白雪莲盛开皎洁；让人神往的莫过于登顶太白山最高峰拔仙台和号称西太白的鳌山，迷雾重重的山顶，满眼石海的第四冰川遗迹；鳌山东行，山顶的40里跑马梁更是漫无边际，令人向往。 　　十多年前开始兴起的户外探险运动，将人们的好奇和这座神奇的大山联系在一起。从事登山、攀岩、户外运动的专业队教练陈铮，早年是户外运动的积极推动者。他供职的东方登山队曾代表陕西省参加国际、国内大赛，并多次为省内外大型山地竞赛、野外活动等承担技术安全设计工作。期间，东方登山队装备店成了驴友们探讨户外经验、分享喜悦的沙龙。 　　“山行者要心静如水的情怀，他们逃离烦嚣，隐遁山水，追求大自然所带给的那种质朴、简单、恬淡、纯粹的感觉。”陈铮这样描绘多年来山地从业感受，“行走在山里，攀登高山的累并快乐着的感觉，让你觉得生机勃勃，充满活力。但是对于毫无训练就贸然进山的驴友来说，那是和自己的生命过不去，山地的艰险和气候异常并非想象。”他形容贸然进山的驴友是“刀剑上的舞者。” 　　户外爱好者“大崔”是一名医生，几乎每周末都会和好友相约进山。“走得多了，眼睛便也有些挑剔。那原始的山水，没有人工的痕迹，总是对我充满着诱惑；越是人迹罕至的地方，越有着强烈的召唤力。”大崔说，“每次进山都会总结上次的经验，我们不能心存侥幸，因为危险始终是和徒步相伴的。” 　　“春天的山是活跃的，人是跳跃的，阳光会透过茂密葱翠的树林，疏疏密密地画在地上，你一抬头，太阳都是跳跃的。”网友描述的山上美景让网友“默默”心动不已，于去年加入到徒步者行列。她身材娇小，却有着异常的毅力和感悟。每次徒步远行，都会收获一篇游记攻略。“默默”说：“一年四季的山，有一年四季的美。在四季更替中感悟人生，就像不知终点的跋涉，只有经历苦与乐、明和暗，才能明白善意和勇气是浮躁社会里逐渐消退的基因。” 　　时常在周末看到，街头身背行囊的男男女女，手拄登山杖，不是在去登山的路上，就是在登山后回家的路上。 　　于是，山里喧嚣了，打破了往昔的宁静。 　　穿越之痛 　　近3年鳌太穿越事故十余起，今年已有4人亡 　　眼前这座山此刻安静极了，涓涓溪流犹如细语；而有时她又暴躁得让你无比恐惧，怒吼的松涛和肆虐的暴雪，撕扯着我们的想象，留下伤痛。 　　1992年4月29日，咸阳一工厂6名员工结伴到太白山旅游。途中与西北大学两位大学生相遇。于是，一行8人开始了这段不寻常的旅程：没有带导游图和药品，甚至所带食品也甚少。行前，他们对太白山险恶复杂的地形和变化无常的气候几乎毫无所知。5月2日，雨雾交织，他们一口气登上太白山的顶峰拔仙台，合影后，疲惫不堪地准备下山。然而云雾弥漫，能见度极低，他们迷路了。寒风交加中，他们在荒芜的山巅上走了几日后，以3人死亡的代价震惊了世人。 　　2001年7月28日，南开大学的张小新一行7人在筹划浪漫的暑期之旅时，万万也想不到，张小新会从太白山顶悬崖坠落。仔细察看过跌落现场的高山向导王高明回忆，导致张小新致命的因素可能是他穿的那双橡胶底足球鞋。“这种鞋底带有橡胶疙瘩的足球鞋，踩在干燥路面上十分牢靠，可踩上潮湿、或是有苔藓的石面时则非常湿滑。”在出事的寨堡峰顶上，有一段长约10米斜度45度的斜坡，估计遇难者当时缓缓移到崖边时，脚下的落叶松软湿泥打滑，使他失去重心。 　　2002年5月1日，上海游客华峥嵘只身前往太白山自然保护区探险，音讯全无。5月10日，近300名警民展开了历时5天5夜艰苦卓绝的大搜救，无果。在华峥嵘随身携带的小本上，记录着他在遇难前的留言：“我不知道我会怎样……我想你……我的兄弟朋友们，祝你们都好运！爸妈你们保重！”“今天起山顶上又大雪重重，浓重的雾水夹杂着小冰雹，前面的路也看不清，我又迷路了……”从越来越淡的笔迹中推断，华峥嵘在5月5日因为饥寒交迫，发生不幸。 　　据陕西曙光应急救援队副总指挥罗裕春介绍，近3年来，发生在鳌太穿越的遇险事故高达十余起，其中今年已经发生事故5次，4人死亡。一幕幕悲剧重演，警示着人们，却无法阻止人们对山的迷恋。 　　2011年春节，29岁的西安女驴友“踏雪”只身一人，进入太白雪域超过20天无音讯，引发一场大搜救。最终“踏雪”安全返回，但网络上质疑不断。 　　就在3个多月前，2012年8月19日，9驴友登鳌山5人失踪，200余人搜救至23日，1驴友遭遇不幸。 　　穿越之险 　　“猎奇探险心理加大危险系数，高估自己能力，而山上瞬息万变充满迷惑性” 　　鳌太线路西侧为海拔3456米的鳌山，东侧为海拔3767.2米的太白山，是秦岭山梁东西最高程的穿越。之间直线距离约40公里，徒步距离约120公里，大多数穿越路线在海拔3000米以上。 　　2010年元月，太白县人民政府正式对外发布公告，禁止所有户外运动爱好者随意组织和发起登山活动。 　　对于频频发生的事故，有着10年户外经验的“大山你好”说，“户外最大危险是落单。”以上走失或死亡者，多数是因为落单迷失在原始森林中。 　　“过高估计自己能力，对太白山复杂的地理环境、动植物分布和恶劣的气候条件估计不足，也是伤亡的重要原因。”曾只身在鳌太线救出14人的向导王高明说，“我在山里长大，对每座山脊都熟记在心，而对于驴友来说，山里的风景有很大的迷惑性。” 　　太白山河流密布，气候瞬息万变，素有“一日历四季，十里不同天”之说。部分游客上山前没做好准备，甚至有人穿着短衣短裤，进山才发现，温度远不是天气预报所说。 　　悉数近年来太白山和鳌山穿越“山难”，猎奇和探险心理某种程度加大了穿越的危险系数。“进山后不少人适应不了气候的异常变化��稀薄的空气，诱发疾病。”身为医生的户外爱好者“大崔”说，“有的滑坠跌成重伤、体力衰竭而亡；有的脱水致电解质失衡以及身体失温致死；也有缺乏野外生存装备和经验被困而亡的。” 　　虽然明文禁止，但仍不能阻止户外爱好者的脚步。太白县生态办原副主任高宝宏介绍，部分游客为逃避保护区门票，分别从南坡的龙洞沟、大莽河，猪娃沟，北坡的三岔峡、白云峡、塘口等登山，而南坡的龙洞沟是太白山重点保护野生动物大熊猫活动重点区域，人为活动过于频繁会对大熊猫生存繁衍造成影响；白云峡、塘口等根本就没有路，属原始林区，个别地段沿河床行走，随时存在迷失方向、被野生动物伤害、冻饿致死、摔伤和被大水冲走的危险。 　　穿越之思 　　“别这样做，我们都不是一个人！” 　　最近西安多家户外俱乐部暂停了鳌太穿越活动，进行反省。回想起去年春节的“踏雪事件”，西安户外救援队队长001鲨鱼说：“别这样做，我们都不是一个人！” 　　“踏雪”安全归来后，也成为冬季单人完成鳌太穿越的第一名女性，但她22天的穿越揪着众人的心。 　　日前10名驴友穿越鳌太3人遇险事故发生后，在网上召集此次活动的“鳌山太保”说，几日来，一直在县公安局配合调查和协助救援。面部有冻伤痕迹的“鳌山太保”穿越鳌太线已经3年，每年平均要穿越70~80次。 　　提起这次事故，“鳌山太保”几度眼睛湿润，“现在很后悔组织这次活动，如果以后还登山，我会去做高山救援。” 　　每次出现意外，来自西安、宝鸡、咸阳、商洛各地的救援队总是马不停蹄地赶往救援。这些由志愿者组成的民间团体需要耗着时间，贴着油钱，花着自己的腰包，来实现“生命第一”的愿景。 　　“如果能组建一支有医疗救治条件、有经费保障的专业、强有力的救援队伍，对户外爱好者来说的确是个福音。”参加过多次救援的“大河”说，“我经常是向单位请假来执行救援，如果是专业搜救队员，可以随叫随到，为救援争取更多时间。” 　　据介绍，每次救援队花销不菲，其中每搜救一天，就要花去5000元费用，而这些钱都是救援队队友自掏腰包。 　　有网友指出，每一次穿越事故后搜救消耗大量人力物力，不如移到对穿越活动的规范和必要培训上来。 　　11月27日，太白县政府针对10驴友穿越鳌太3人遇难事故召开专题会议，强调进山公告的严肃性。通告在原有基础上增加了“积雪深厚，气温接近零下三十度，贸然登山极易引发意外事故”的内容，并决定在县城塘口登山口设置铁丝隔离网，封闭进山道路，并且在塘口和太洋公路23公里处设立警示牌，阻止随意登山行为。通告重申，确需登山的团队，必须由具有独立法人资格的单位组织，并配有资质的专业领队和装备，提前一周向太白县生态办提出书面申请，经审批备案后方可登山。违反规定的组织单位或个人，一经发现查实，将按有关规定处罚。对引发重大责任事故的，将依法追究组织单位的相关责任。 　　太白县此举看似“不近人情”，其实是从驴友安全的角度考虑，也是对登山穿越活动的强制性规范。 　　面对驴友们屡屡难以停下的穿越之心，仅凭地方政府的一些强制性规定，显然远远不够。而更为遗憾的是，对穿越活动的必要培训、穿越人员素质的检查，几乎是空白。 　　>>记者手记 　　学会敬畏大自然 　　当徒步和穿越成为一种时髦时，我们更需谨慎地对待眼前的这座大山，怀着敬畏之心。我们如此热爱自然，渴望走进丛林，回味那宁静、清新，延续我们丛林生存的基因断码，那么依靠一些残存的、拼凑的经验就能再回到自然中吗？答案是否定的。 　　当我们要回到自然中去，第一堂课就是学会敬畏这座山，遵循它的丛林法则；还需经历艰苦的锻炼，才能逐渐恢复我们的体能和丰满我们的经验。这样，你才可以背起行囊，眼前的大山才可以接纳你。 　　本报记者赵航 　　关注新浪户外(微博)，了解更多户外资讯。"}
	 */
	@ServiceMethod(method = "rss.view.news")
	public Object queryById(RopRequest request){
		String id=request.getRopRequestContext().getParamValue("id");
		if (StringUtils.hasText(id)) {
			return action.readNews(Integer.valueOf(id));
		}
		return new Object();
	}
}

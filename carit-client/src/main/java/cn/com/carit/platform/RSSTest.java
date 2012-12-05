package cn.com.carit.platform;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RSSTest {

	
	private final static int TIME_OUT=30*1000;
	private final static String USER_AGENT="Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";
	
	public static void main(String[] args) throws Exception {
		Document doc = get("http://rss.sina.com.cn/sina_news_opml.xml");
		Elements rssList=doc.getElementsByAttributeValue("type", "rss");
		for(Element e: rssList){
			System.out.println("打开 【"+e.attr("title")+"】");
			Document rssDoc = get(e.attr("xmlUrl"));
			Elements items=rssDoc.getElementsByTag("item");
			for (Element item : items) {
				String link=item.select("guid").html();
				if (link.contains("video.")||link.contains("slide.")) {//排除视频和图集
					System.out.println("====================================================================================================="+link);
					continue;
				}
				System.out.println("title:"+item.select("title").text().replaceAll("\\<\\!\\[CDATA\\[|\\]\\]\\>", ""));
				System.out.println("link:"+link);
				System.out.println("pubDate:"+item.select("pubDate").text());
				System.out.println("comments:"+item.select("comments").text());
				System.out.println("description:"+item.select("description").text());
				Document news=get(link);
				Element body=news.select("#artibody").first();
				Element temp=body.select(".img_wrapper").first();
				System.out.println(body.select("p").text());
//				System.out.println(body.select("p").html());
				// 普通新闻有图片
				if (temp!=null && temp.hasText()) {
					System.out.println("img_wrapper："+temp.select("img").attr("src"));
					continue;
				}
				// 
				temp=body.select(".blk_hd_pic").first();
				if (temp!=null && temp.hasText()) {
					System.out.println("blk_hd_pic："+temp.select("img").attr("src"));
					continue;
				}
				// 股票新闻图片
				temp=body.select(".ct_hqimg").first();
				if (temp!=null && temp.hasText()) {
					System.out.println("ct_hqimg："+temp.select("img").attr("src"));
					continue;
				}
			}
		}
	}

	public static Document get(String url) throws Exception{
		Connection conn=Jsoup.connect(url);
		// 伪装成Google Chrome的访问
		conn.userAgent(USER_AGENT);
		return conn.timeout(TIME_OUT).get();
	}
	
	public static Document post(String url) throws Exception{
		Connection conn=Jsoup.connect(url);
		// 伪装成Google Chrome的访问
		conn.userAgent(USER_AGENT);
		return conn.timeout(TIME_OUT).post();
	}
}

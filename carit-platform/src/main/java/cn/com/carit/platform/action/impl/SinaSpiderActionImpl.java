package cn.com.carit.platform.action.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.carit.common.RssCatalog;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.JsoupUtils;
import cn.com.carit.platform.action.SpiderAction;
import cn.com.carit.platform.bean.RssNews;
import cn.com.carit.platform.dao.RssNewsDao;

@Service
public class SinaSpiderActionImpl implements SpiderAction{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private RssNewsDao<RssNews> dao;
	
	private final Map<String, Boolean> cache=new ConcurrentHashMap<String, Boolean>();
	
	@Override
	public void spiderRssNews(List<RssCatalog> list) throws Exception {
		cache.clear();
		for (RssCatalog rssCatalog : list) {
			List<RssCatalog> subList = JsoupUtils.getInstance().getSubCatalogs(
					rssCatalog.getId());
			for (RssCatalog subCatalog : subList) {
				dao.bathAdd(spiderCatalog(subCatalog));
			}
		}
	}


	private Collection<RssNews> spiderCatalog(RssCatalog rssCatalog){
		Map<String, RssNews> rssNewsCache=new HashMap<String, RssNews>();
		try {
			Document rssDoc = JsoupUtils.getInstance().get(rssCatalog.getUrl());
			Elements items=rssDoc.getElementsByTag("item");
			for (Element item : items) {
				String link=item.select("guid").html();
				//排除
				if (link.contains("video.")
						||link.contains("slide.")
						||link.contains("weibo.com")
						||link.contains("blog.sina.com")
						||link.contains("vip.book")) {
					continue;
				}
				String [] array = link.split("=");
				String sourceUrl= link;
				if (array.length>1) {
					sourceUrl=array[1].split("\\?")[0];
				}
				if (rssNewsCache.containsKey(sourceUrl) || dao.existed(sourceUrl)) {
					logger.info("###############跳过已爬取内容###################");
					cache.put(sourceUrl, Boolean.TRUE);
					continue;
				}
				String title=item.select("title").text().replaceAll("\\<\\!\\[CDATA\\[|\\]\\]\\>", "");
				Document contentDoc=null;
				try {
					contentDoc=JsoupUtils.getInstance().get(link);
				} catch (Exception e) {
					// 再一次尝试
					try {
						contentDoc=JsoupUtils.getInstance().get(link);
					} catch (Exception ex) {
						// 第二次尝试失败，跳过
						logger.warn("抓取【"+rssCatalog.getTitle()+"】分类下的【"+title+"】失败...");
					}
				}
				if (contentDoc!=null) {
					try {
						RssNews rssNews=new RssNews();
						rssNews.setCatalogId(rssCatalog.getId());
						rssNews.setTitle(title);
						rssNews.setSourceUrl(sourceUrl);
						cache.put(sourceUrl, Boolean.TRUE);
						rssNews.setPubDate(CaritUtils.gmtStrToDate(item.select("pubDate").text()));
						rssNews.setDescription(item.select("description").text().trim());
						Element body=contentDoc.select("#artibody").first();
						rssNews.setContent(body.select("p").text().trim());
						Element temp=body.select(".img_wrapper").first();
						// 普通新闻有图片
						if (temp!=null && temp.hasText()) {
							rssNews.setImgSrc(temp.select("img").attr("src"));
//							rssNewsList.add(rssNews);
							rssNewsCache.put(sourceUrl, rssNews);
							continue;
						}
						// 
						temp=body.select(".blk_hd_pic").first();
						if (temp!=null && temp.hasText()) {
							rssNews.setImgSrc(temp.select("img").attr("src"));
//							rssNewsList.add(rssNews);
							rssNewsCache.put(sourceUrl, rssNews);
							continue;
						}
						// 股票新闻图片
						temp=body.select(".ct_hqimg").first();
						if (temp!=null && temp.hasText()) {
							rssNews.setImgSrc(temp.select("img").attr("src"));
//							rssNewsList.add(rssNews);
							rssNewsCache.put(sourceUrl, rssNews);
							continue;
						}
						// 没有上面是图片元素
//						rssNewsList.add(rssNews);
						rssNewsCache.put(sourceUrl, rssNews);
					} catch (Exception e) {
						logger.warn("爬取正文出错，跳过:"+sourceUrl, e);
						continue;
					}
				}
			}
//			// 小于50或剩余少于50
//			if (rssNewsList.size()>0) {
//				dao.bathAdd(rssNewsList);
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return rssNewsCache.values();
	}

}

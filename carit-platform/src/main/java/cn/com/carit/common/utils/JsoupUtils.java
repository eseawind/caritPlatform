package cn.com.carit.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import cn.com.carit.common.RssCatalog;

public class JsoupUtils {

	private final Logger logger = LoggerFactory.getLogger(JsoupUtils.class);
	
	private final int TIME_OUT=30*1000;
	private final String USER_AGENT="Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";
	
	private final List<RssCatalog> catalogs;
	
	private final Map<Integer, List<RssCatalog>> subCatalogCache;
	
	private static class CacheHolder {
		private static final JsoupUtils INSTANCE = new JsoupUtils();
	}
	
	private JsoupUtils(){
		catalogs=new ArrayList<RssCatalog>();
		subCatalogCache=new ConcurrentHashMap<Integer, List<RssCatalog>>();
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document doc = reader.read(ContextLoader
					.getCurrentWebApplicationContext().getClassLoader()
					.getResourceAsStream("sina_rss.xml"));
			Element root = doc.getRootElement();
			Element catalogElement;
			for (Iterator<?> i = root.elementIterator("catalog"); i.hasNext();) {
				catalogElement = (Element) i.next();
				int id = Integer.valueOf(catalogElement.attributeValue("id"));
				catalogs.add(new RssCatalog(id, 0, catalogElement
						.attributeValue("title"), ""));
				@SuppressWarnings("unchecked")
				List<Element> subElementList=catalogElement.elements();
				List<RssCatalog> subList = new ArrayList<RssCatalog>();
				for (Element subCatalogElement:subElementList) {
					RssCatalog catalog=new RssCatalog(Integer
							.valueOf(subCatalogElement.attributeValue("id")),
							id, subCatalogElement.attributeValue("title"),
							subCatalogElement.attributeValue("xmlUrl"));
					subList.add(catalog);
				}
				subCatalogCache.put(id, subList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static JsoupUtils getInstance(){
		return CacheHolder.INSTANCE;
	}
	
	public List<RssCatalog> getParents() {
		return catalogs;
	}

	public List<RssCatalog> getSubCatalogs(int parentId){
		return subCatalogCache.get(parentId);
	}
	
	public List<RssCatalog> allSubCatalogs(){
		List<RssCatalog> list=new ArrayList<RssCatalog>();
		for (List<RssCatalog> rssCatalogs : subCatalogCache.values()) {
			list.addAll(rssCatalogs);
		}
		return list;
	}
	
	public Document get(String url) throws Exception{
		Connection conn=Jsoup.connect(url);
		// 伪装成Google Chrome的访问
		conn.userAgent(USER_AGENT);
		return conn.timeout(TIME_OUT).get();
	}
	
	public Document post(String url) throws Exception{
		Connection conn=Jsoup.connect(url);
		// 伪装成Google Chrome的访问
		conn.userAgent(USER_AGENT);
		return conn.timeout(TIME_OUT).post();
	}

}

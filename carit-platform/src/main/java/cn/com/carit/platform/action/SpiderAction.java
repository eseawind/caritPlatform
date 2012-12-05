package cn.com.carit.platform.action;

import java.util.List;

import cn.com.carit.common.RssCatalog;

public interface SpiderAction {
	
	long timeout=200000;//20s
	void spiderRssNews(List<RssCatalog> list) throws Exception;
}

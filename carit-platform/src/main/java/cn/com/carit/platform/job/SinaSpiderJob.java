package cn.com.carit.platform.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carit.common.utils.JsoupUtils;
import cn.com.carit.platform.action.SpiderAction;
/**
 * <p>
 * <b>功能说明：</b>
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-11-29
 */
public class SinaSpiderJob {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Resource
	private SpiderAction action;
	
	public void spider() throws Exception{
		logger.info("新浪网络爬虫Job开始...");
		action.spiderRssNews(JsoupUtils.getInstance().getParents());
		logger.info("新浪网络爬虫Job结束...");
	}

}

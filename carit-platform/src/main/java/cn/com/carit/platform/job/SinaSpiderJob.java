package cn.com.carit.platform.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.carit.common.utils.JsoupUtils;
import cn.com.carit.platform.action.SpiderAction;
/**
 * <p>
 * <b>功能说明：</b>新浪爬虫Job，每三小时执行一次
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-11-29
 */
@Service
public class SinaSpiderJob {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	@Resource
	private SpiderAction action;
	
	@Scheduled(cron="0 0 0/3 * * ?")
	public void spider() throws Exception{
		logger.info("新浪网络爬虫Job开始...");
		action.spiderRssNews(JsoupUtils.getInstance().getParents());
		logger.info("新浪网络爬虫Job结束...");
	}

}

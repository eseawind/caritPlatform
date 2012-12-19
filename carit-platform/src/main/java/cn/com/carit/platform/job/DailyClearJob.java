package cn.com.carit.platform.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.com.carit.common.RssCatalog;
import cn.com.carit.common.utils.JsoupUtils;
import cn.com.carit.platform.action.ObdDataAction;
import cn.com.carit.platform.action.RssNewsAction;
import cn.com.carit.platform.bean.ObdData;
import cn.com.carit.platform.bean.RssNews;
/**
 * <p>
 * <b>功能说明：</b>日常清理job，每天凌晨三点执行
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-12-12
 */
@Service
public class DailyClearJob {
	
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	private RssNewsAction<RssNews> rssNewsAction;
	@Resource
	private ObdDataAction<ObdData> obdDataAction;
	
	@Scheduled(cron="0 0 3 * * ?")
	protected void work() {
		logger.info("DailyClearJob start..");
		List<Integer> catalogIds=new ArrayList<Integer>();
		List<RssCatalog> list=JsoupUtils.getInstance().allSubCatalogs();
		for (RssCatalog rssCatalog : list) {
			catalogIds.add(rssCatalog.getId());
		}
		// 清理新闻
		rssNewsAction.batchDelete(catalogIds, 27);
		// 清理OBD数据
		obdDataAction.dailyClear(30);
		logger.info("DailyClearJob end..");
	}
	
}

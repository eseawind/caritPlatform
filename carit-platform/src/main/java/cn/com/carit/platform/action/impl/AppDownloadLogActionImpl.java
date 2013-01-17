package cn.com.carit.platform.action.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.action.AppDownloadLogAction;
import cn.com.carit.platform.bean.market.AppDownloadLog;
import cn.com.carit.platform.dao.AppDownloadLogDao;
import cn.com.carit.platform.response.market.AppDownStat;

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
public class AppDownloadLogActionImpl implements AppDownloadLogAction<AppDownloadLog> {
	
	@Resource
	private AppDownloadLogDao<AppDownloadLog> dao;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int add(AppDownloadLog t) {
		return dao.add(t);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int update(AppDownloadLog t) {
		return dao.update(t);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int delete(int id) {
		return dao.delete(id);
	}

	@Override
	public AppDownloadLog queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public JsonPage<AppDownloadLog> queryByExemple(AppDownloadLog t, DataGridModel dgm) {
		return dao.queryByExemple(t, dgm);
	}

	@Override
	public List<AppDownloadLog> queryByExemple(AppDownloadLog t) {
		return dao.queryByExemple(t);
	}

	@Override
	public List<AppDownloadLog> queryAll() {
		return dao.queryAll();
	}

	@Override
	public boolean hasDownloaded(int accountId, int appId) {
		return dao.hasDownloaded(accountId, appId);
	}
	@Override
	public Map<String, Object> appOneMonthDownTrend(int appId) {
		Calendar calendar=Calendar.getInstance();// 当前时间
		calendar.add(Calendar.DATE, -30);
		Date startDate=calendar.getTime();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		List<AppDownStat> downLogs=dao.statAppDownlog(appId, startDate);
		List<String> categories=new ArrayList<String>();
		List<Integer> data=new ArrayList<Integer>();
		if (downLogs.size()>=30) {//刚好有30天完整记录
			for (AppDownStat stat : downLogs) {
				categories.add(CaritUtils.dateToStr(stat.getDate(), Constants.DATE_SHORT_FORMATTER));
				data.add(stat.getCount());
			}
		} else { // 不够30天的数据
			int count=0;
			for (int i = 0; i < 30; i++) {
				calendar.add(Calendar.DATE, +1);
				String dateStr=CaritUtils.dateToStr(calendar.getTime()
						, Constants.DATE_SHORT_FORMATTER);
				categories.add(dateStr);
				for (AppDownStat stat : downLogs) {
					if (dateStr.equals(CaritUtils.dateToStr(stat.getDate()
							, Constants.DATE_SHORT_FORMATTER))) {
						count=stat.getCount();
						break;
					} else {
						count=0;
					}
				}
				data.add(count);
			}
		}
		resultMap.put("categories", categories);
		resultMap.put("data", data);
		return resultMap;
	}
}

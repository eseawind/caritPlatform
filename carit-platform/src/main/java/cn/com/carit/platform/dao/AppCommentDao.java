package cn.com.carit.platform.dao;

import java.util.List;
import java.util.Map;

import cn.com.carit.Dao;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.response.market.AppCommentResponse;

public interface AppCommentDao<AppCommnet> extends Dao<AppCommnet> {
	/**
	 * 查询应用评论
	 * @param request
	 * @return
	 */
	JsonPage<AppCommentResponse> queryAppComments(int appId, DataGridModel dgm);
	
	/**
	 * 统计评论每个评论级别的评论人数
	 * @param appId
	 * @return
	 */
	List<Map<String, Object>> statCommentGrade(int appId);
	
	/**
	 * 统计评论
	 * @param appId
	 * @return
	 */
	Map<String,Object> statComment(int appId);
}

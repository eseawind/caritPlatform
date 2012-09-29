package cn.com.carit.platform.action;

import java.util.List;
import java.util.Map;

import cn.com.carit.Action;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.response.market.AppCommentResponse;

public interface AppCommentAction<AppComment> extends Action<AppComment> {

	/**
	 * 查询应用评论
	 * @param request
	 * @return
	 */
	JsonPage<AppCommentResponse> queryAppComments(int appId, DataGridModel dgm);
	
	/**
	 * 统计评论级别
	 * @param appId
	 * @return
	 */
	List<Map<String, Object>>  statCommentGrade(int appId);
	
	/**
	 * 统计评论
	 * @param appId
	 * @return
	 */
	Map<String,Object> statComment(int appId);
}

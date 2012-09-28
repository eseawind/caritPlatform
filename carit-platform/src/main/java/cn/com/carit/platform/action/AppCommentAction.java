package cn.com.carit.platform.action;

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
}

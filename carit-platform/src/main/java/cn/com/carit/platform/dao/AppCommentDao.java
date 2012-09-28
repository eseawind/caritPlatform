package cn.com.carit.platform.dao;

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
}

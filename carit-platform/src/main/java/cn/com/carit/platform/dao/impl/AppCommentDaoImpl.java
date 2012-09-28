package cn.com.carit.platform.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.DaoImpl;
import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.market.AppComment;
import cn.com.carit.platform.dao.AppCommentDao;
import cn.com.carit.platform.response.market.AppCommentResponse;

@Repository
public class AppCommentDaoImpl extends DaoImpl implements AppCommentDao<AppComment> {

	private final RowMapper<AppComment> rowMapper = new RowMapper<AppComment>() {

		@Override
		public AppComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			AppComment t = new AppComment();
			t.setId(rs.getInt("id"));
			t.setAppId(rs.getInt("app_id"));
			t.setUserId(rs.getInt("user_id"));
			t.setGrade(rs.getInt("grade"));
			t.setComment(rs.getString("comment"));
			t.setStatus(rs.getInt("status"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			t.setAppName(rs.getString("app_name"));
			t.setEnName(rs.getString("en_name"));
			t.setUserName(rs.getString("nick_name"));
			return t;
		}
	};
	
	@Override
	public int add(AppComment t) {
		String sql = "insert into t_app_comment (app_id, user_id, grade, comment, create_time, update_time) values (?, ?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
				, t.getAppId()
				, t.getUserId()
				, t.getGrade()
				, t.getComment()
			);
	}

	@Override
	public int update(AppComment t) {
		StringBuilder sql=new StringBuilder("update t_app_comment set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (t.getAppId() != null) {
			sql.append(", app_id=?");
			args.add(t.getAppId());
		}
		if (t.getUserId() != null) {
			sql.append(", user_id=?");
			args.add(t.getUserId());
		}
		if (t.getGrade() != null) {
			sql.append(", grade=?");
			args.add(t.getGrade());
		}
		if (StringUtils.hasText(t.getComment())) {
			sql.append(", comment=?");
			args.add(t.getComment());
		}
		if (t.getStatus() != null) {
			sql.append(", status=?");
			args.add(t.getStatus());
		}
		sql.append(" where id=?");
		args.add(t.getId());
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_app_comment where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public AppComment queryById(int id) {
		String sql = "select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<AppComment> queryByExemple(AppComment t, DataGridModel dgm) {
		JsonPage<AppComment> jsonPage = new JsonPage<AppComment>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder("select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql="select count(1) from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where 1=1"+whereSql;
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", countSql));
		}
		int totalRow = queryForInt(countSql, args, argTypes);
		// 更新
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(CaritUtils.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());

		} else {
			sql.append(" order by update_time desc");
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
		return jsonPage;
	}

	@Override
	public List<AppComment> queryByExemple(AppComment t) {
		StringBuilder sql = new StringBuilder("select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<AppComment> queryAll() {
		String sql="select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			AppComment t) {
		StringBuilder sql=new StringBuilder();
		if (t.getAppId() != null) {
			sql.append(" and a.app_id=?");
			args.add(t.getAppId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(t.getAppName())){
			sql.append(" and b.app_name like CONCAT('%',?,'%')");
			args.add(t.getAppName());
			argTypes.add(12);// java.sql.Types type
		}
		if(StringUtils.hasText(t.getEnName())){
			sql.append(" and  b.en_name like CONCAT('%',?,'%')");
			args.add(t.getEnName());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getUserId() != null) {
			sql.append(" and a.user_id=?");
			args.add(t.getUserId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(t.getUserName())){
			sql.append(" and  c.nick_name like CONCAT('%',?,'%')");
			args.add(t.getUserName());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getGrade() != null) {
			sql.append(" and a.grade=?");
			args.add(t.getGrade());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getComment())) {
			sql.append(" and a.comment like CONCAT('%',?,'%')");
			args.add(t.getComment());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getStatus() != null) {
			sql.append(" and a.status=?");
			args.add(t.getStatus());
			argTypes.add(4);// java.sql.Types type
		}
		return sql.toString();
	}

	@Override
	public JsonPage<AppCommentResponse> queryAppComments(int appId,
			DataGridModel dgm) {
		StringBuilder sql = new StringBuilder(
				"select a.grade, a.comment, a.update_time, b.nick_name from t_app_comment a left join t_account_info b on a.user_id=b.id where a.status=? and a.app_id=?");
		String countSql = "select count(1) from t_app_comment a left join t_account_info b on a.user_id=b.id where a.status=? and a.app_id=?";
		JsonPage<AppCommentResponse> jsonPage = new JsonPage<AppCommentResponse>(dgm.getPage(), dgm.getRows());
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		args.add(Constants.STATUS_VALID);
		argTypes.add(Types.INTEGER);
		args.add(appId);
		argTypes.add(Types.INTEGER);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(CaritUtils.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
		} else {
			sql.append(" order by a.update_time desc");
		}
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow = queryForInt(countSql, args, argTypes);
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		// 更新
		jsonPage.setTotal(totalRow);
		log.debug(String.format("\n%1$s\n", sql));
		jsonPage.setRows(query(sql.toString(), args, argTypes, new RowMapper<AppCommentResponse>() {

			@Override
			public AppCommentResponse mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AppCommentResponse t=new AppCommentResponse();
				t.setGrade(rs.getInt("grade"));
				t.setComment(rs.getString("comment"));
				t.setNickName(rs.getString("nick_name"));
				t.setUpdateTime(rs.getTimestamp("update_time"));
				return t;
			}
		}));
		return jsonPage;
	}

}

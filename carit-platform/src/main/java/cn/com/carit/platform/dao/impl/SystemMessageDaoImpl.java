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
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.account.SystemMessage;
import cn.com.carit.platform.dao.SystemMessageDao;

@Repository
public class SystemMessageDaoImpl extends DaoImpl implements SystemMessageDao<SystemMessage> {

	private final RowMapper<SystemMessage> rowMapper = new RowMapper<SystemMessage>() {

		@Override
		public SystemMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			SystemMessage t = new SystemMessage();
			t.setId(rs.getInt("id"));
			t.setAccountId(rs.getInt("account_id"));
			t.setCatalog(rs.getInt("catalog"));
			t.setTitle(rs.getString("title"));
			t.setContent(rs.getString("content"));
			t.setStatus(rs.getInt("status"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			return t;
		}
	};
	
	@Override
	public int add(final SystemMessage t) {
		String sql="insert into t_sys_message(account_id, catalog,title, content, create_time, update_time)"
				+" values (?, ?,?, ?, now(), now())";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql
				, t.getAccountId()
				, t.getCatalog()
				, t.getTitle()
				, t.getContent());
	}

	@Override
	public int update(SystemMessage t) {
		StringBuilder sql = new StringBuilder(
				"update t_sys_message set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (t.getAccountId()!=null) {
			sql.append(", account_id=?");
			args.add(t.getAccountId());
		}
		if (t.getCatalog()!=null) {
			sql.append(", catalog=?");
			args.add(t.getCatalog());
		}
		if (StringUtils.hasText(t.getTitle())) {
			sql.append(", title=?");
			args.add(t.getTitle());
		}
		if (t.getStatus()!=null) {
			sql.append(", status=?");
			args.add(t.getStatus());
		}
		if (StringUtils.hasText(t.getContent())) {
			sql.append(", content=?");
			args.add(t.getContent());
		}
		sql.append(" where id=?");
		args.add(t.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_upload_location where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public SystemMessage queryById(int id) {
		String sql = "select * from t_upload_location where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<SystemMessage> queryByExemple(SystemMessage t, DataGridModel dgm) {
		JsonPage<SystemMessage> jsonPage = new JsonPage<SystemMessage>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_upload_location where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql = "select count(1) from t_upload_location where 1=1"
				+ whereSql;
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
	public List<SystemMessage> queryByExemple(SystemMessage t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_upload_location where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<SystemMessage> queryAll() {
		String sql="select * from t_upload_location";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			SystemMessage t) {
		StringBuilder sql=new StringBuilder();
		if (t.getAccountId()!=null) {
			sql.append(" and account_id=?");
			args.add(t.getAccountId());
			argTypes.add(Types.INTEGER);
		}
		if (t.getCatalog()!=null) {
			sql.append(" and catalog=?");
			args.add(t.getCatalog());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(t.getTitle())) {
			sql.append(" and title like CONCAT('%',?,'%')");
			args.add(t.getTitle());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getStatus()!=null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(t.getContent())) {
			sql.append(" and content like CONCAT('%',?,'%')");
			args.add(t.getContent());
			argTypes.add(Types.VARCHAR);
		}
		return sql.toString();
	}

}

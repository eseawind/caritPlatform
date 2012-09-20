package cn.com.carit.platform.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.DaoImpl;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.AppSecret;
import cn.com.carit.platform.dao.AppSecretDao;

@Repository
public class AppSecretDaoImpl extends DaoImpl implements AppSecretDao<AppSecret> {

	private final RowMapper<AppSecret> rowMapper = new RowMapper<AppSecret>() {

		@Override
		public AppSecret mapRow(ResultSet rs, int rowNum) throws SQLException {
			AppSecret t = new AppSecret();
			t.setId(rs.getInt("id"));
			t.setAppName(rs.getString("app_name"));
			t.setAppSecret(rs.getString("app_secret"));
			t.setStatus(rs.getInt("status"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			return t;
		}
	};
	
	@Override
	public int add(final AppSecret t) {
		final String sql = "insert into t_app_secret (app_secret, app_name, status, update_time, create_time) " 
				+ "values (?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		KeyHolder gkHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				int index=1;
				ps.setString(index++, t.getAppSecret());
				ps.setString(index++, t.getAppSecret());
				ps.setString(index++, t.getAppSecret());
				return ps;
			}
		}, gkHolder);
		int id=gkHolder.getKey().intValue();
		t.setId(id);
		return id;
	}

	@Override
	public int update(AppSecret t) {
		StringBuilder sql=new StringBuilder("update t_app_secret set update_time=now()");
		List<Object> val = new ArrayList<Object>();
		if (StringUtils.hasText(t.getAppName())) {
			sql.append(", app_name=?");
			val.add(t.getAppName());
		}
		if (t.getStatus() != null) {
			sql.append(", status=(status|?)");
			val.add(t.getStatus());
		}
		sql.append(" where id=?");
		val.add(t.getId());
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql.toString(), val.toArray());
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_app_secret where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public AppSecret queryById(int id) {
		String sql = "select * from t_app_secret where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<AppSecret> queryByExemple(AppSecret t, DataGridModel dgm) {
		JsonPage<AppSecret> jsonPage = new JsonPage<AppSecret>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_app_secret where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql = "select count(1) from t_app_secret where 1=1"
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
	public List<AppSecret> queryByExemple(AppSecret t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_app_secret where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<AppSecret> queryAll() {
		String sql="select * from t_app_secret";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			AppSecret t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getAppName())) {
			sql.append(" and app_name like CONCAT('%',?,'%')");
			args.add(t.getAppName());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getStatus() != null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(-6);// java.sql.Types type
		}
		if (t.getUpdateTime() != null) {
			sql.append(" and update_time=?");
			args.add(t.getUpdateTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (t.getCreateTime() != null) {
			sql.append(" and create_time=?");
			args.add(t.getCreateTime());
			argTypes.add(93);// java.sql.Types type
		}
		return sql.toString();
	}


}

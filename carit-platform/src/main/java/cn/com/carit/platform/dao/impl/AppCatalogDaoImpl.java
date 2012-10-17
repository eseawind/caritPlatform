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
import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.market.AppCatalog;
import cn.com.carit.platform.dao.AppCatalogDao;
import cn.com.carit.platform.response.market.AppCatalogResponse;

@Repository
public class AppCatalogDaoImpl extends DaoImpl implements AppCatalogDao<AppCatalog> {

	
	private final RowMapper<AppCatalog> rowMapper = new RowMapper<AppCatalog>() {

		@Override
		public AppCatalog mapRow(ResultSet rs, int rowNum) throws SQLException {
			AppCatalog t = new AppCatalog();
			t.setId(rs.getInt("id"));
			t.setName(rs.getString("name"));
			t.setEnName(rs.getString("en_name"));
			t.setDescription(rs.getString("description"));
			t.setEnDescription(rs.getString("en_description"));
			t.setStatus(rs.getInt("status"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			return t;
		}
	};

	@Override
	public int add(final AppCatalog t) {
		final String sql = "insert into t_catalog (name, en_name, description"
				+", en_description, display_index, status"
				+", create_time, update_time) values (?, ?, ?, ?, ?, ? now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		KeyHolder gkHolder = new GeneratedKeyHolder(); 
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i=1;
				ps.setString(i++, t.getName());
				ps.setString(i++, t.getEnName());
				ps.setString(i++, t.getDescription());
				ps.setString(i++, t.getEnDescription());
				ps.setInt(i++, t.getDisplayIndex());
				ps.setInt(i++, t.getStatus());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int update(AppCatalog t) {
		StringBuilder sql = new StringBuilder(
				"update t_catalog set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.hasText(t.getName())) {
			sql.append(", name=?");
			args.add(t.getName());
		}
		if (StringUtils.hasText(t.getEnName())) {
			sql.append(", en_name=?");
			args.add(t.getEnName());
		}
		if (StringUtils.hasText(t.getDescription())) {
			sql.append(", description=?");
			args.add(t.getDescription());
		}
		if (StringUtils.hasText(t.getEnDescription())) {
			sql.append(", en_description=?");
			args.add(t.getEnDescription());
		}
		if (t.getStatus() != null) {
			sql.append(", status=?");
			args.add(t.getStatus());
		}
		if (t.getDisplayIndex() != null) {
			sql.append(", display_index=?");
			args.add(t.getDisplayIndex());
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
		String sql = "delete from t_catalog where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public AppCatalog queryById(int id) {
		String sql = "select * from t_catalog where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}
	
	@Override
	public JsonPage<AppCatalog> queryByExemple(AppCatalog t, DataGridModel dgm) {
		JsonPage<AppCatalog> jsonPage = new JsonPage<AppCatalog>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_catalog where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql="select count(1) from t_catalog where 1=1"+whereSql;
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
					.append(" ").append(dgm.getOrder()).append(", update_time desc");
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
	public List<AppCatalog> queryByExemple(AppCatalog t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_catalog where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<AppCatalog> queryAll() {
		String sql="select * from t_catalog where status>0";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			AppCatalog t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getName())) {
			sql.append(" and name like CONCAT('%',?,'%')");
			args.add(t.getName());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getEnName())) {
			sql.append(" and en_name like CONCAT('%',?,'%')");
			args.add(t.getEnName());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getDescription())) {
			sql.append(" and description like CONCAT('%',?,'%')");
			args.add(t.getDescription());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getEnDescription())) {
			sql.append(" and  en_description like CONCAT('%',?,'%')");
			args.add(t.getEnDescription());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getStatus() != null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(Types.INTEGER);
		}
		if (t.getDisplayIndex() != null) {
			sql.append(" and display_index=?");
			args.add(t.getDisplayIndex());
			argTypes.add(Types.INTEGER);
		}
		return sql.toString();
	}

	@Override
	public List<AppCatalogResponse> queryByLanguage(String language) {
		String viewName="v_app_catalog_cn";
		if (!Constants.DEAFULD_LANGUAGE.equals(language)) {
			viewName="v_app_catalog_en";
		}
		String sql="select * from "+viewName;
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, new RowMapper<AppCatalogResponse>(){

			@Override
			public AppCatalogResponse mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AppCatalogResponse t=new AppCatalogResponse();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				t.setDescription(rs.getString("description"));
				return t;
			}});
	}
	
}

package cn.com.carit.platform.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.StringUtil;
import cn.com.carit.platform.bean.Navigation;
import cn.com.carit.platform.dao.NavigationDao;
@Repository
public class NavigationDaoImpl extends BaseDaoImpl implements
		NavigationDao<Navigation> {

	private final RowMapper<Navigation> rowMapper=new RowMapper<Navigation>() {
		
		@Override
		public Navigation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Navigation t=new Navigation();
			t.setId(rs.getInt("id"));
			t.setName(rs.getString("name"));
			t.setUrl(rs.getString("url"));
			t.setCatalogId(rs.getInt("catalog_id"));
			t.setDisplayIndex(rs.getInt("display_index"));
			t.setCssClass(rs.getString("css_class"));
			t.setLogo(rs.getString("logo"));
			t.setStatus(rs.getInt("status"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			return t;
		}
	};
	
	@Override
	public int add(Navigation t) {
		String sql = "insert into t_navigation (name, url, catalog_id"
				+", display_index, css_class, logo, status, update_time, create_time)"
				+" values (?, ?, ?, ?, ?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
					, t.getName()
					, t.getUrl()
					, t.getCatalogId()
					, t.getDisplayIndex() 
					, t.getCssClass()
					, t.getLogo()
					, t.getStatus()
				);
	}

	@Override
	public int update(Navigation t) {
		StringBuilder sql=new StringBuilder("update t_navigation set update_time=now()");
		List<Object> val = new ArrayList<Object>();
		if (StringUtils.hasText(t.getName())) {
			sql.append(", name=?");
			val.add(t.getName());
		}
		if (StringUtils.hasText(t.getUrl())) {
			sql.append(", url=?");
			val.add(t.getUrl());
		}
		if (t.getCatalogId()!=null) {
			sql.append(", catalog_id=?");
			val.add(t.getCatalogId());
		}
		if (t.getDisplayIndex()!=null) {
			sql.append(", display_index=?");
			val.add(t.getDisplayIndex());
		}
		if (StringUtils.hasText(t.getCssClass())) {
			sql.append(", css_class=?");
			val.add(t.getCssClass());
		}
		if (StringUtils.hasText(t.getLogo())) {
			sql.append(", logo=?");
			val.add(t.getLogo());
		}
		if (t.getStatus()!=null) {
			sql.append(", status=?");
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
		String sql="delete from t_navigation where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int batchDelete(String ids) {
		String sql="delete from t_navigation where id in("+ids+")";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql);
	}

	@Override
	public Navigation queryById(int id) {
		String sql = "select * from t_navigation where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<Navigation> queryByExemple(Navigation t, DataGridModel dgm) {
		JsonPage<Navigation> jsonPage = new JsonPage<Navigation>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_navigation where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql = "select count(1) from t_navigation where 1=1"
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
					.append(StringUtil.splitFieldWords(dgm.getSort()))
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
	public List<Navigation> queryByExemple(Navigation t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_navigation where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<Navigation> queryAll() {
		String sql="select * from t_navigation";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			Navigation t) {
		StringBuilder sql = new StringBuilder();
		if (StringUtils.hasText(t.getName())) {
			sql.append(" and name like CONCAT('%',?,'%')");
			args.add(t.getName());
			argTypes.add(Types.VARBINARY);
		}
		if (StringUtils.hasText(t.getUrl())) {
			sql.append(" and url like CONCAT('%',?,'%')");
			args.add(t.getUrl());
			argTypes.add(Types.VARBINARY);
		}
		if (t.getCatalogId()!=null) {
			sql.append(" and catalog_id=?");
			args.add(t.getCatalogId());
			argTypes.add(Types.INTEGER);
		}
		if (t.getDisplayIndex()!=null) {
			sql.append(" and display_index=?");
			args.add(t.getDisplayIndex());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(t.getCssClass())) {
			sql.append(" and css_class=?");
			args.add(t.getCssClass());
			argTypes.add(Types.VARBINARY);
		}
		if (StringUtils.hasText(t.getLogo())) {
			sql.append(" and logo=?");
			args.add(t.getLogo());
			argTypes.add(Types.VARBINARY);
		}
		if (t.getStatus()!=null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(Types.INTEGER);
		}
		return sql.toString();
	}

	@Override
	public int checkExisted(String name) {
		String sql="select 1 from t_navigation where name=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return checkExisted(sql, name);
	}

	@Override
	public List<Navigation> queryByAccount(int accountId) {
		String sql="select a.* from t_navigation a left join t_favorite b on a.id=b.navigation_id where b.account_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, new Object[]{accountId}, rowMapper);
	}

	@Override
	public int addFavorite(int accountId, int navigationId) {
		String sql="insert into t_favorite values(?, ?)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, accountId, navigationId);
	}

	@Override
	public int deleteByAccount(int accountId) {
		String sql="delete from t_favorite where account_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, accountId);
	}

	@Override
	public List<Navigation> queryEffective() {
		String sql="select * from t_navigation where status>?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper, Constants.STATUS_INVALID);
	}
	
}

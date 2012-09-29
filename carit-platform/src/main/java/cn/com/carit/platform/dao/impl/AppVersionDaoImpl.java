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
import cn.com.carit.platform.bean.market.AppVersion;
import cn.com.carit.platform.dao.AppVersionDao;
import cn.com.carit.platform.request.market.DownloadedReferencedRequest;
import cn.com.carit.platform.response.market.AppVersionResponse;

@Repository
public class AppVersionDaoImpl extends DaoImpl implements AppVersionDao<AppVersion> {

	
	private final RowMapper<AppVersion> rowMapper = new RowMapper<AppVersion>() {

		@Override
		public AppVersion mapRow(ResultSet rs, int rowNum) throws SQLException {
			AppVersion t = new AppVersion();
			t.setId(rs.getInt("id"));
			t.setAppId(rs.getInt("app_id"));
			t.setVersion(rs.getString("version"));
			t.setSize(rs.getString("size"));
			t.setFilePath(rs.getString("file_path"));
			t.setNewFeatures(rs.getString("new_features"));
			t.setEnNewFeatures(rs.getString("en_new_features"));
			t.setStatus(rs.getInt("status"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			return t;
		}
	};

	@Override
	public int add(final AppVersion t) {
		final String sql = "insert into t_app_version_file (app_id, version, size"
				+", file_path, new_features, en_new_features, status"
				+", create_time, update_time) values (?, ?, ?, ?, ?, ?, ? now(), now())";
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
				ps.setInt(i++, t.getAppId());
				ps.setString(i++, t.getVersion());
				ps.setString(i++, t.getSize());
				ps.setString(i++, t.getFilePath());
				ps.setString(i++, t.getNewFeatures());
				ps.setString(i++, t.getEnNewFeatures());
				ps.setInt(i++, t.getStatus());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int update(AppVersion t) {
		StringBuilder sql = new StringBuilder(
				"update t_app_version_file set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (t.getAppId()!=null) {
			sql.append(", app_id=?");
			args.add(t.getAppId());
		}
		if (StringUtils.hasText(t.getVersion())) {
			sql.append(", version=?");
			args.add(t.getVersion());
		}
		if (StringUtils.hasText(t.getSize())) {
			sql.append(", size=?");
			args.add(t.getSize());
		}
		if (StringUtils.hasText(t.getFilePath())) {
			sql.append(", file_path=?");
			args.add(t.getFilePath());
		}
		if (StringUtils.hasText(t.getNewFeatures())) {
			sql.append(", new_features=?");
			args.add(t.getNewFeatures());
		}
		if (StringUtils.hasText(t.getEnNewFeatures())) {
			sql.append(", en_new_features=?");
			args.add(t.getNewFeatures());
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
		String sql = "delete from t_app_version_file where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public AppVersion queryById(int id) {
		String sql = "select * from t_app_version_file where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}
	
	@Override
	public JsonPage<AppVersion> queryByExemple(AppVersion t, DataGridModel dgm) {
		JsonPage<AppVersion> jsonPage = new JsonPage<AppVersion>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_app_version_file where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql="select count(1) from t_app_version_file where 1=1"+whereSql;
		log.debug(String.format("\n%1$s\n", countSql));
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
	public List<AppVersion> queryByExemple(AppVersion t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_app_version_file where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<AppVersion> queryAll() {
		String sql="select * from t_app_version_file where status>0";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			AppVersion t) {
		StringBuilder sql=new StringBuilder();
		if (t.getAppId()!=null) {
			sql.append(" and app_id=?");
			args.add(t.getAppId());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(t.getVersion())) {
			sql.append(" and version like CONCAT('%',?,'%')");
			args.add(t.getVersion());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getSize())) {
			sql.append(" and size=?");
			args.add(t.getSize());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getFilePath())) {
			sql.append(" and file_path=?");
			args.add(t.getFilePath());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getNewFeatures())) {
			sql.append(" and new_features like CONCAT('%',?,'%')");
			args.add(t.getNewFeatures());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getEnNewFeatures())) {
			sql.append(" and en_new_features like CONCAT('%',?,'%')");
			args.add(t.getNewFeatures());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getStatus() != null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(Types.INTEGER);
		}
		return sql.toString();
	}
	
	@Override
	public JsonPage<AppVersionResponse> queryAppVersions(
			DownloadedReferencedRequest request) {
		String viewName="v_app_version_file_cn";
		if (!Constants.DEAFULD_LANGUAGE.equalsIgnoreCase(request.getLanguage())) {
			viewName="v_app_version_file_en";
		}
		DataGridModel dgm = new DataGridModel();
		dgm.setSort(request.getSort());
		dgm.setOrder(request.getOrder());
		dgm.setPage(request.getPage());
		dgm.setRows(request.getRows());
		JsonPage<AppVersionResponse> jsonPage = new JsonPage<AppVersionResponse>(dgm.getPage(), dgm.getRows());
		StringBuilder sql=new StringBuilder("select * from ").append(viewName).append(" where app_id=?");
		StringBuilder countSql=new StringBuilder("select count(1) from ").append(viewName).append(" where app_id=?");
		List<Object> args = new ArrayList<Object>();
		args.add(request.getAppId());
		List<Integer> argTypes = new ArrayList<Integer>();
		argTypes.add(Types.INTEGER);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", countSql));
		}
		int totalRow = queryForInt(countSql.toString(), args, argTypes);
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(CaritUtils.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
		}
		sql.append(" limit ?, ?");
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		jsonPage.setRows(query(sql.toString(), args, argTypes, new RowMapper<AppVersionResponse>() {

			@Override
			public AppVersionResponse mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AppVersionResponse t=new AppVersionResponse();
				t.setId(rs.getInt("id"));
				t.setVersion(rs.getString("version"));
				t.setSize(rs.getString("size"));
				t.setFilePath(rs.getString("file_path"));
				t.setNewFeatures(rs.getString("new_features"));
				return t;
			}
		}));
		return jsonPage;
	}
	
}

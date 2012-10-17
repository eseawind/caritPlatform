package cn.com.carit.platform.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.DaoImpl;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.market.AppDownloadLog;
import cn.com.carit.platform.dao.AppDownloadLogDao;
import cn.com.carit.platform.response.market.AppDownStat;

@Repository
public class AppDownloadLogDaoImpl extends DaoImpl implements AppDownloadLogDao<AppDownloadLog> {

	private final RowMapper<AppDownloadLog> rowMapper = new RowMapper<AppDownloadLog>() {

		@Override
		public AppDownloadLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			AppDownloadLog t = new AppDownloadLog();
			t.setId(rs.getInt("id"));
			t.setAccountId(rs.getInt("account_id"));
			t.setAppId(rs.getInt("app_id"));
			t.setVersion(rs.getString("version"));
			t.setDownloadTime(rs.getTimestamp("download_time"));
			t.setAppName(rs.getString("app_name"));
			t.setEnName(rs.getString("en_name"));
			t.setUserName(rs.getString("nick_name"));
			return t;
		}
	};
	
	@Override
	public int add(AppDownloadLog t) {
		String sql = "insert into t_app_download_log (account_id, app_id, version, download_time) values (?, ?, ?, now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
				, t.getAccountId()
				, t.getAppId()
				, t.getVersion()
			);
	}

	@Override
	public int update(AppDownloadLog t) {
		return 0;
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_app_download_log where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public AppDownloadLog queryById(int id) {
		String sql = "select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<AppDownloadLog> queryByExemple(AppDownloadLog t, DataGridModel dgm) {
		JsonPage<AppDownloadLog> jsonPage = new JsonPage<AppDownloadLog>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql="select count(1) from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id where 1=1"+whereSql;
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
	public List<AppDownloadLog> queryByExemple(AppDownloadLog t) {
		StringBuilder sql = new StringBuilder(
				"select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<AppDownloadLog> queryAll() {
		String sql="select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			AppDownloadLog t) {
		StringBuilder sql=new StringBuilder();
		if (t.getAccountId() != null) {
			sql.append(" and a.account_id=?");
			args.add(t.getAccountId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(t.getUserName())){
			sql.append(" and  c.nick_name like CONCAT('%',?,'%')");
			args.add(t.getUserName());
			argTypes.add(12);// java.sql.Types type
		}
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
		if (t.getDownloadTime() != null) {
			sql.append(" and a.download_time=?");
			args.add(t.getDownloadTime());
			argTypes.add(93);// java.sql.Types type
		}
		return sql.toString();
	}

	@Override
	public boolean hasDownloaded(int accountId, int appId) {
		String sql="select count(1) from t_app_download_log where account_id=? and app_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		try {
			return jdbcTemplate.queryForInt(sql, accountId, appId)>0;
		} catch (Exception e) {
			log.warn("not exist record of accountId["+accountId+"] or appId["+appId+"]", e);
		}
		return false;
	}
	
	@Override
	public List<AppDownStat> statAppDownlog(int appId, Date startDate) {
		String sql="select download_time date, count(1) count" 
				+ " from t_app_download_log where app_id=? and download_time>?"
				+ " group by date_format(download_time,'%Y-%c-%d')";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, new Object[]{appId, startDate}
		, new int[]{Types.INTEGER, Types.TIMESTAMP}, new RowMapper<AppDownStat>() {
			@Override
			public AppDownStat mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppDownStat stat=new AppDownStat();
				stat.setDate(rs.getDate("date"));
				stat.setCount(rs.getInt("count"));
				return stat;
			}
		});
	}
	
}

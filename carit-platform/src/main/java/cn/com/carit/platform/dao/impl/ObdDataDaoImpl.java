package cn.com.carit.platform.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
import cn.com.carit.platform.bean.ObdData;
import cn.com.carit.platform.dao.ObdDataDao;
import cn.com.carit.platform.request.SearchObdDataRequest;

@Repository
public class ObdDataDaoImpl extends DaoImpl implements ObdDataDao<ObdData> {

	private final RowMapper<ObdData> rowMapper = new RowMapper<ObdData>() {

		@Override
		public ObdData mapRow(ResultSet rs, int rowNum) throws SQLException {
			ObdData t=new ObdData();
			t.setId(rs.getInt("id"));
			t.setDate(rs.getTimestamp("date"));
			t.setDeviceId(rs.getString("device_id"));
			t.setLocation(rs.getString("location"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setError(rs.getString("error"));
			t.setAccountId(rs.getInt("account_id"));
			return t;
		}
	};
	
	@Override
	public int add(final ObdData t) {
		final String sql="insert into t_obd_data(date,device_id,account_id,location,create_time,error"
				+") values(?,?,?,?,now(),?"
				+")";
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
				int i=1;
				ps.setTimestamp(i++, new Timestamp(t.getDate().getTime()));
				ps.setString(i++, t.getDeviceId());
				ps.setInt(i++, t.getAccountId());
				ps.setString(i++, t.getLocation());
				ps.setString(i++, t.getError());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int update(ObdData t) {
		StringBuilder sql=new StringBuilder("update t_obd_data set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.hasText(t.getDeviceId())) {
			sql.append(", device_id=?");
			args.add(t.getDeviceId());
		}
		if (t.getAccountId()!=null) {
			sql.append(",account_id=?");
			args.add(t.getAccountId());
		}
		if (StringUtils.hasText(t.getLocation())) {
			sql.append(", location=?");
			args.add(t.getLocation());
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
		String sql = "delete from t_obd_data where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public ObdData queryById(int id) {
		String sql = "select * from t_obd_data where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<ObdData> queryByExemple(ObdData t, DataGridModel dgm) {
		JsonPage<ObdData> jsonPage = new JsonPage<ObdData>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_obd_data where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql = "select count(1) from t_obd_data where 1=1"
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
	public List<ObdData> queryByExemple(ObdData t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_obd_data where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<ObdData> queryAll() {
		String sql="select * from t_obd_data";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			ObdData t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getDeviceId())) {
			sql.append(" and device_id=?");
			args.add(t.getDeviceId());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getAccountId()!=null){
			sql.append(" and account_id=?");
			args.add(t.getAccountId());
			argTypes.add(Types.INTEGER);
		}
//		if (t.getStartDate()!=null) {
//			sql.append(" and date>=?");
//			args.add(t.getStartDate());
//			argTypes.add(Types.DATE);
//		}
//		if (t.getEndDate()!=null) {
//			sql.append(" and date<=?");
//			args.add(t.getEndDate());
//			argTypes.add(Types.DATE);
//		}
		if (StringUtils.hasText(t.getLocation())) {
			sql.append(" and location like CONCAT('%',?,'%')");
			args.add(t.getLocation());
			argTypes.add(Types.VARCHAR);
		}
		return sql.toString();
	}

	@Override
	public ObdData queryNewestData(String deviceId, int accountId) {
		String sql="select * from t_obd_data where device_id=? and account_id=? order by create_time desc limit 1";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{deviceId, accountId}, rowMapper);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void deleteDuplicateData() {
		String sql="delete from a using t_obd_data a,t_obd_data b where a.device_id=b.device_id and a.date=b.date and a.id<b.id";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql);
	}

	@Override
	public JsonPage<Map<String, Object>> query(SearchObdDataRequest request) {
		DataGridModel dgm = new DataGridModel();
		dgm.setSort(request.getSort());
		dgm.setOrder(request.getOrder());
		dgm.setPage(request.getPage());
		dgm.setRows(request.getRows());
		JsonPage<Map<String, Object>> jsonPage = new JsonPage<Map<String, Object>>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select a.date, a.location, b.value from t_obd_data a left join t_obd_data_value b on a.id=b.data_id where b.index=?");
		StringBuilder countSql = new StringBuilder("select count(1) from t_obd_data a left join t_obd_data_value b on a.id=b.data_id where b.index=?");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		args.add(request.getIndex());
		argTypes.add(Types.INTEGER);
		if (StringUtils.hasText(request.getDeviceId())) {
			sql.append(" and device_id=?");
			countSql.append(" and device_id=?");
			args.add(request.getDeviceId());
			argTypes.add(Types.VARCHAR);
		}
		if (request.getAccountId()!=null){
			sql.append(" and account_id=?");
			countSql.append(" and account_id=?");
			args.add(request.getAccountId());
			argTypes.add(Types.INTEGER);
		}
		if (request.getStartTime()!=null) {
			sql.append(" and a.date>=?");
			countSql.append(" and a.date>=?");
			args.add(new Date(request.getStartTime()));
			argTypes.add(Types.DATE);
		}
		if (request.getEndTime()!=null) {
			sql.append(" and a.date<=?");
			countSql.append(" and a.date<=?");
			args.add(new Date(request.getEndTime()));
			argTypes.add(Types.DATE);
		}
		if (StringUtils.hasText(request.getLocation())) {
			sql.append(" and a.location like CONCAT('%',?,'%')");
			countSql.append(" and a.location like CONCAT('%',?,'%')");
			args.add(request.getLocation());
			argTypes.add(Types.VARCHAR);
		}
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", countSql));
		}
		int totalRow = queryForInt(countSql.toString(), args, argTypes);
		// 更新
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(CaritUtils.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());

		} else {
			sql.append(" order by a.date desc");
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jsonPage.setRows(queryForList(sql.toString(), args, argTypes));
		return jsonPage;
	}

	@Override
	public List<ObdData> queryCurrentDataByAccount(
			int accountId) {
		String sql="SELECT * FROM (SELECT * FROM t_obd_data WHERE account_id=? ORDER BY create_time DESC) t GROUP BY device_id, account_id";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		try {
			return jdbcTemplate.query(sql, new Object[]{accountId}, rowMapper);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void bathAddValue(final int dataId, final List<Integer> values) {
		String sql = "insert into t_obd_data_value(data_id, `index`, `value`) values(?, ?, ?)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, dataId);
				ps.setInt(2, (i+1));
				ps.setInt(3, values.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return values.size();
			}
		});
	}

	@Override
	public List<Integer> queryValues(int dataId) {
		String sql = "select `value` from t_obd_data_value where data_id=? order by `index` asc";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql, new Object[]{dataId}, new int []{Types.INTEGER}, Integer.class);
	}

	@Override
	public void dailyClear(final List<Map<String, Object>> args, final int keepCount) {
		String sql="delete from t_obd_data where account_id=? and  device_id=? and id not in (select id from (select id from t_obd_data where account_id=? and  device_id=? order by date desc limit ?) t)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int index=1;
				ps.setInt(index++, (Integer)args.get(i).get("account_id"));
				ps.setString(index++, args.get(i).get("device_id").toString());
				ps.setInt(index++, (Integer)args.get(i).get("account_id"));
				ps.setString(index++, args.get(i).get("device_id").toString());
				ps.setInt(index++, keepCount);
			}
			
			@Override
			public int getBatchSize() {
				return args.size();
			}
		});
	}

	@Override
	public void deleteInvalidValue() {
		String sql="delete from t_obd_data_value where data_id not in (select id from t_obd_data)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql);
	}

	@Override
	public List<Map<String, Object>> queryAccountDevice() {
		String sql="select account_id, device_id from t_obd_data group by device_id, account_id";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql);
	}

}

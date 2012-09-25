package cn.com.carit.platform.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.dao.LocationDao;
import cn.com.carit.platform.request.LocationRequest;

@Repository
public class LocationDaoImpl extends DaoImpl implements LocationDao<Location> {

	private final RowMapper<Location> rowMapper = new RowMapper<Location>() {

		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location t = new Location();
			t.setId(rs.getInt("id"));
			t.setDeviceId(rs.getString("device_id"));
			t.setLat(rs.getDouble("lat"));
			t.setLng(rs.getDouble("lng"));
			t.setCreateTime(rs.getTimestamp("create_time").getTime());
//			t.setUpdateTime(rs.getDate("update_time"));
			return t;
		}
	};
	
	@Override
	public int add(final Location t) {
		final String sql = "insert into t_upload_location (device_id, lat, lng, create_time) " 
				+ "values (?, ?, ?, ?)";
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
				ps.setString(index++, t.getDeviceId());
				ps.setDouble(index++, t.getLat());
				ps.setDouble(index++, t.getLng());
				ps.setDate(index++, new Date(t.getCreateTime()));
				return ps;
			}
		}, gkHolder);
		int id=gkHolder.getKey().intValue();
		t.setId(id);
		return id;
	}

	@Override
	public int update(Location t) {
		StringBuilder sql=new StringBuilder("update t_upload_location set update_time=now()");
		List<Object> val = new ArrayList<Object>();
		if (StringUtils.hasText(t.getDeviceId())) {
			sql.append(", device_id=?");
			val.add(t.getDeviceId());
		}
		if (t.getLat() != null) {
			sql.append(", lat");
			val.add(t.getLat());
		}
		if (t.getLng() != null) {
			sql.append(", lng");
			val.add(t.getLng());
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
		String sql = "delete from t_upload_location where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Location queryById(int id) {
		String sql = "select * from t_upload_location where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<Location> queryByExemple(Location t, DataGridModel dgm) {
		JsonPage<Location> jsonPage = new JsonPage<Location>(dgm.getPage(), dgm.getRows());
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
	public List<Location> queryByExemple(Location t) {
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
	public List<Location> queryAll() {
		String sql="select * from t_upload_location";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			Location t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getDeviceId())) {
			sql.append(" and device_id CONCAT('%',?,'%')");
			args.add(t.getDeviceId());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getLat() != null) {
			sql.append(" and lat=?");
			args.add(t.getLat());
			argTypes.add(Types.DOUBLE);
		}
		if (t.getLng() != null) {
			sql.append(" and lng=?");
			args.add(t.getLng());
			argTypes.add(Types.DOUBLE);
		}
		
		if (t.getCreateTime() != null) {
			sql.append(" and create_time>=?");
			args.add(new Date(t.getCreateTime()));
			argTypes.add(93);// java.sql.Types type
		}
		return sql.toString();
	}

	@Override
	public int batchAdd(final List<LocationRequest> locationList, final String deviceId) {
		final String sql = "insert into t_upload_location (device_id, lat, lng, create_time) " 
				+ "values (?, ?, ?, ?)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int index=1;
				ps.setString(index++, deviceId);
				ps.setDouble(index++, locationList.get(i).getLat());
				ps.setDouble(index++, locationList.get(i).getLng());
				ps.setDate(index++, new Date(locationList.get(i).getTime()));
			}
			
			@Override
			public int getBatchSize() {
				return locationList.size();
			}
		}).length;
	}

}

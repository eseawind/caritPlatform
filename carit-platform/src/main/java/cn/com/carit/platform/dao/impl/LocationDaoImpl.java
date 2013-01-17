package cn.com.carit.platform.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.com.carit.DaoImpl;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Location;
import cn.com.carit.platform.dao.LocationDao;
import cn.com.carit.platform.request.SearchLoactionRequest;

@Repository
public class LocationDaoImpl extends DaoImpl implements LocationDao {

	private final int TABLE_COUNT = 10; //分表数
	
	@Override
	public int batchAdd(final List<Location> locationList) {
		final String sql = "insert into "+getTable(locationList.get(0).getAccountId())+" (device_id, account_id, lat, lng, create_time) " 
				+ "values (?, ?, ?, ?, ?)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int index=1;
				Location location=locationList.get(i);
				ps.setString(index++, location.getDeviceId());
				ps.setInt(index++, location.getAccountId());
				ps.setDouble(index++, location.getLat());
				ps.setDouble(index++, location.getLng());
				ps.setTimestamp(index++, new Timestamp(location.getCreateTime()));
			}
			
			@Override
			public int getBatchSize() {
				return locationList.size();
			}
		}).length;
	}

	@Override
	public List<Map<String, Object>> query(SearchLoactionRequest request) {
		StringBuilder sql = new StringBuilder(
				"select lat, lng, create_time time from "+getTable(request.getAccountId())+" where device_id=? and account_id=?");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		args.add(request.getDeviceId());
		argTypes.add(Types.VARCHAR);
		
		args.add(request.getAccountId());
		argTypes.add(Types.INTEGER);
		if (request.getType()==SearchLoactionRequest.TYPE_TODAY) {
			Calendar today=Calendar.getInstance();
			// 清除时分秒
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);
			
			sql.append(" and create_time>=?");
			args.add(today.getTime());
			argTypes.add(Types.TIMESTAMP);
			
			// 加一天
			today.add(Calendar.DATE, 1);
			sql.append(" and create_time<?");
			args.add(today.getTime());
			argTypes.add(Types.TIMESTAMP);
		}
		if (request.getType()==SearchLoactionRequest.TYPE_CUSTOMIZED){
			sql.append(" and create_time>=?");
			args.add(new Date(request.getStartTime()));
			argTypes.add(Types.TIMESTAMP);
			
			sql.append(" and create_time<?");
			args.add(new Date(request.getEndTime()));
			argTypes.add(Types.TIMESTAMP);
		}
		sql.append(" order by create_time asc");
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return queryForList(sql.toString(), args, argTypes);
	}
	

	@Override
	public JsonPage<Map<String, Object>> queryForPage(SearchLoactionRequest request) {
		StringBuilder sql = new StringBuilder(
				"select lat, lng, create_time time from "+getTable(request.getAccountId())+" where device_id=? and account_id=?");
		StringBuilder countSql = new StringBuilder(
				"select count(1) from "+getTable(request.getAccountId())+" where device_id=? and account_id=?");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		args.add(request.getDeviceId());
		argTypes.add(Types.VARCHAR);
		
		args.add(request.getAccountId());
		argTypes.add(Types.INTEGER);
		if (request.getType()==SearchLoactionRequest.TYPE_TODAY) {
			Calendar today=Calendar.getInstance();
			// 清除时分秒
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);
			
			sql.append(" and create_time>=?");
			countSql.append(" and create_time>=?");
			args.add(today.getTime());
			argTypes.add(Types.TIMESTAMP);
			
			// 加一天
			today.add(Calendar.DATE, 1);
			sql.append(" and create_time<?");
			countSql.append(" and create_time<?");
			args.add(today.getTime());
			argTypes.add(Types.TIMESTAMP);
		}
		if (request.getType()==SearchLoactionRequest.TYPE_CUSTOMIZED){
			sql.append(" and create_time>=?");
			countSql.append(" and create_time>=?");
			args.add(new Date(request.getStartTime()));
			argTypes.add(Types.TIMESTAMP);
			
			sql.append(" and create_time<?");
			countSql.append(" and create_time<?");
			args.add(new Date(request.getEndTime()));
			argTypes.add(Types.TIMESTAMP);
		}
//		DataGridModel dgm = new DataGridModel(request.getPage(),
//				request.getRows(), request.getSort(), request.getOrder());
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", countSql));
		}
		int totalRow = queryForInt(countSql.toString(), args, argTypes);
		JsonPage<Map<String, Object>> jsonPage = new JsonPage<Map<String, Object>>(
				request.getPage(), request.getRows());
		// 更新
		jsonPage.setTotal(totalRow);
		// 排序
//		if (StringUtils.hasText(dgm.getOrder())
//				&& StringUtils.hasText(dgm.getSort())) {
//			sql.append(" order by ")
//					.append(CaritUtils.splitFieldWords(dgm.getSort()))
//					.append(" ").append(dgm.getOrder());
//
//		} else {
			sql.append(" order by create_time asc");
//		}
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
	public void deleteDuplicateData(int accountId) {
		String sql="delete from a using "+getTable(accountId)+" a,"+getTable(accountId)+" b where a.id<b.id and a.create_time=b.create_time";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql);
	}
	
	private String getTable(int accountId) {
		return "t_upload_location_"+(accountId%TABLE_COUNT);
	}
}

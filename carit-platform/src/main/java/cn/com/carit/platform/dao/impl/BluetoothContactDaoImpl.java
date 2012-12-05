package cn.com.carit.platform.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.DaoImpl;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.BluetoothContact;
import cn.com.carit.platform.dao.BluetoothContactDao;

@Repository
public class BluetoothContactDaoImpl extends DaoImpl implements BluetoothContactDao<BluetoothContact> {

	private final RowMapper<BluetoothContact> rowMapper = new RowMapper<BluetoothContact>() {

		@Override
		public BluetoothContact mapRow(ResultSet rs, int rowNum) throws SQLException {
			BluetoothContact t = new BluetoothContact();
			t.setId(rs.getInt("id"));
			t.setDeviceId(rs.getString("device_id"));
			t.setAccountId(rs.getInt("account_id"));
			t.setCallName(rs.getString("call_name"));
			t.setCallNameKey(rs.getString("call_name_key"));
			t.setCallNum(rs.getString("call_num"));
			t.setCallType(rs.getString("call_type"));
			t.setStatus(rs.getInt("status"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			return t;
		}
	};
	
	@Override
	public int add(final BluetoothContact t) {
		String sql="insert into t_bluetooth_contact(device_id, account_id, call_name, call_num, call_name_key, call_type, status, create_time, update_time)"
				+" values (?, ?, ?, ?, ?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
				, t.getDeviceId()
				, t.getAccountId()
				, t.getCallName()
				, t.getCallNum()
				, t.getCallNameKey()
				, t.getCallType()
				, t.getStatus());
	}

	@Override
	public int update(BluetoothContact t) {
		StringBuilder sql = new StringBuilder(
				"update t_bluetooth_contact set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.hasText(t.getCallName())) {
			sql.append(", call_name=?");
			args.add(t.getCallName());
		}
		if (StringUtils.hasText(t.getCallNameKey())) {
			sql.append(", call_name_key=?");
			args.add(t.getCallNameKey());
		}
		if (StringUtils.hasText(t.getCallNum())) {
			sql.append(", call_num=?");
			args.add(t.getCallNum());
		}
		if (StringUtils.hasText(t.getCallType())) {
			sql.append(", call_type=?");
			args.add(t.getCallType());
		}
		if (t.getStatus()!=null) {
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
		String sql = "delete from t_bluetooth_contact where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public BluetoothContact queryById(int id) {
		String sql = "select * from t_bluetooth_contact where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<BluetoothContact> queryByExemple(BluetoothContact t, DataGridModel dgm) {
		JsonPage<BluetoothContact> jsonPage = new JsonPage<BluetoothContact>(dgm.getPage(), dgm.getRows());
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		StringBuilder sql = new StringBuilder("select * from t_bluetooth_contact where 1=1");
		StringBuilder countSql = new StringBuilder("select count(1) from t_equipment where 1=1");
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		countSql.append(whereSql);
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
	public List<BluetoothContact> queryByExemple(BluetoothContact t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_bluetooth_contact where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<BluetoothContact> queryAll() {
		String sql="select * from t_bluetooth_contact";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			BluetoothContact t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getCallName())) {
			sql.append(" and call_name like CONCAT('%',?,'%')");
			args.add(t.getCallName());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getCallNameKey())) {
			sql.append(" and call_name_key like CONCAT(?,'%')");
			args.add(t.getCallNameKey());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getCallNum())) {
			sql.append(" and call_num like CONCAT('%',?,'%')");
			args.add(t.getCallNum());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getCallType())) {
			sql.append(" and call_type=?");
			args.add(t.getCallType());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getStatus()!=null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(Types.INTEGER);
		}
		return sql.toString();
	}

	@Override
	public List<Map<String, Object>> queryBluetoothByAccount(final int accountId) {
		String sql = "select bluetooth_id bluetoothId, bluetooth_name bluetoothName from t_bluetooth where account_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql, accountId);
	}

	@Override
	public void addReference(int accountId, String deviceId, String bluetoothId, String bluetoothName) {
		String sql = "insert into t_bluetooth(account_id,device_id, bluetooth_id, bluetooth_name) values(?,?,?,?)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql, accountId, deviceId, bluetoothId, bluetoothName);
	}
	
	@Override
	public void delete(int accountId, String deviceId, String bluetoothId) {
		String sql = "delete from t_bluetooth_contact where account_id=? and device_id=? and bluetooth_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql, accountId, deviceId, bluetoothId);
	}

	@Override
	public void deleteReference(int accountId, String deviceId, String bluetoothId) {
		String sql = "delete from t_bluetooth where account_id=? and device_id=? and bluetooth_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql, accountId, deviceId, bluetoothId);
	}

	@Override
	public JsonPage<Map<String, Object>> query(
			final String deviceId,
			final int accountId,
			final String bluetoothId,
			final String callName,
			final String callNameKey,
			final String callNum, 
			final DataGridModel dgm) {
		JsonPage<Map<String, Object>> jsonPage = new JsonPage<Map<String, Object>>(dgm.getPage(), dgm.getRows());
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		StringBuilder sql = new StringBuilder("select call_name callName, call_num callNum, call_name_key callNameKey, call_type callType from t_bluetooth_contact where device_id=? and account_id=?");
		StringBuilder countSql = new StringBuilder("select count(1) from t_bluetooth_contact where device_id=? and account_id=?");
		args.add(deviceId);
		args.add(accountId);
		argTypes.add(Types.VARCHAR);
		argTypes.add(Types.INTEGER);
		
		if (StringUtils.hasText(bluetoothId)) {
			sql.append(" and bluetooth_id=?");
			countSql.append(" and bluetooth_id=?");
			args.add(bluetoothId);
			argTypes.add(Types.VARCHAR);
		}
		
		if (StringUtils.hasText(callName)) {
			sql.append(" and call_name like CONCAT('%',?,'%')");
			countSql.append(" and call_name like CONCAT('%',?,'%')");
			args.add(callName);
			argTypes.add(Types.VARCHAR);
		}
		
		if (StringUtils.hasText(callNameKey)) {
			sql.append(" and call_name_key like CONCAT('%',?,'%')");
			countSql.append(" and call_name like CONCAT('%',?,'%')");
			args.add(callNameKey);
			argTypes.add(Types.VARCHAR);
		}
		
		if (StringUtils.hasText(callNum)) {
			sql.append(" and call_num like CONCAT('%',?,'%')");
			countSql.append(" and call_name like CONCAT('%',?,'%')");
			args.add(callNum);
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
		jsonPage.setRows(queryForList(sql.toString(), args, argTypes));
		return jsonPage;
	}

	@Override
	public List<Map<String, Object>> queryAll(
			String deviceId, int accountId, String bluetoothId) {
		String sql="select call_name callName, call_num callNum, call_name_key callNameKey, call_type callType from t_bluetooth_contact where device_id=? and account_id=? and bluetooth_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql, deviceId, accountId, bluetoothId);
	}

	@Override
	public int batchAdd(final List<BluetoothContact> list) {
		String sql="insert into t_bluetooth_contact(device_id, account_id, bluetooth_id, call_name, call_num, call_name_key, call_type, status, create_time, update_time)"
				+" values (?, ?, ?, ?, ?, ?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				int index=1;
				BluetoothContact contact=list.get(i);
				ps.setString(index++, contact.getDeviceId());
				ps.setInt(index++, contact.getAccountId());
				ps.setString(index++, contact.getBluetoothId());
				ps.setString(index++, contact.getCallName());
				ps.setString(index++, contact.getCallNum());
				ps.setString(index++, contact.getCallNameKey());
				ps.setString(index++, contact.getCallType());
				ps.setInt(index++, contact.getStatus());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		}).length;
	}

	@Override
	public List<Map<String, Object>> queryConnectedDevices(int accountId,
			String bluetoothId) {
		String sql="select device_id deviceId from t_bluetooth where account_id=? and bluetooth_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql, accountId, bluetoothId);
	}
	
}

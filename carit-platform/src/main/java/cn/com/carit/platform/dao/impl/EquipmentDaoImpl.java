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
import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Equipment;
import cn.com.carit.platform.dao.EquipmentDao;
import cn.com.carit.platform.response.EquipmentResponse;

@Repository
public class EquipmentDaoImpl extends DaoImpl implements EquipmentDao<Equipment> {

	private final RowMapper<Equipment> rowMapper = new RowMapper<Equipment>() {

		@Override
		public Equipment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Equipment t = new Equipment();
			t.setDeviceId(rs.getString("device_id"));
//			t.setAccountId(rs.getInt("account_id"));
			t.setStatus(rs.getInt("status"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			return t;
		}
	};
	
	@Override
	public int add(final Equipment t) {
		String sql="insert into t_equipment(device_id, status, create_time, update_time)"
				+" values (?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
				, t.getDeviceId()
				, Constants.STATUS_VALID);
	}

	@Override
	public int update(Equipment t) {
		StringBuilder sql = new StringBuilder(
				"update t_equipment set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (t.getStatus()!=null) {
			sql.append(", status=?");
			args.add(t.getStatus());
		}
		sql.append(" where device_id=?");
		args.add(t.getDeviceId());
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_equipment where device_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Equipment queryById(int id) {
		String sql = "select * from t_equipment where device_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<Equipment> queryByExemple(Equipment t, DataGridModel dgm) {
		JsonPage<Equipment> jsonPage = new JsonPage<Equipment>(dgm.getPage(), dgm.getRows());
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		StringBuilder sql = null;
		StringBuilder countSql = null;
		if (t.getAccountId()!=null) {
			sql = new StringBuilder("select a.* from t_equipment a, t_account_equipment b where a.device_id=b.device_id and b.account_id=?");
			countSql = new StringBuilder("select count(1) from t_equipment a, t_account_equipment b where a.device_id=b.device_id and b.account_id=?");
			args.add(t.getAccountId());
			argTypes.add(Types.INTEGER);
		} else {
			sql = new StringBuilder("select a.* from t_equipment a where 1=1");
			countSql = new StringBuilder("select count(1) from t_equipment a where 1=1");
		}
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
			sql.append(" order by a.update_time desc");
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
	public List<Equipment> queryByExemple(Equipment t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_equipment where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<Equipment> queryAll() {
		String sql="select * from t_equipment";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			Equipment t) {
		StringBuilder sql=new StringBuilder();
		if (t.getStatus()!=null) {
			sql.append(" and a.status=?");
			args.add(t.getStatus());
			argTypes.add(Types.INTEGER);
		}
		return sql.toString();
	}

	private final RowMapper<EquipmentResponse> responseRowMapper=new RowMapper<EquipmentResponse>() {
		
		@Override
		public EquipmentResponse mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			EquipmentResponse t=new EquipmentResponse();
			t.setDeviceId(rs.getString("device_id"));
			return t;
		}
	};
	
	@Override
	public List<EquipmentResponse> queryByAccount(final int accountId) {
		String sql = "select a.* from t_equipment a, t_account_equipment b where a.device_id=b.device_id and b.account_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, new Object[]{accountId}, responseRowMapper);
	}

	@Override
	public int checkBounding(final int accountId, final  String deviceId) {
		String sql="select count(1) from t_account_equipment a, t_account_info b where a.account_id=b.id and b.id=? and a.device_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForInt(sql, new Object []{accountId, deviceId}, new int []{Types.INTEGER, Types.VARCHAR});
	}

	@Override
	public int queryAccountCountByDeviceId(String deviceId) {
		String sql="select count(1) from t_account_equipment a, t_equipment b where a.device_id=b.device_id and a.device_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForInt(sql, new Object []{deviceId}, new int []{Types.VARCHAR});
	}

	@Override
	public void addReference(int accountId, String deviceId) {
		String sql = "insert into t_account_equipment(account_id,device_id) values(?,?)";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql, accountId, deviceId);
	}

	
}

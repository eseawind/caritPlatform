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
import cn.com.carit.platform.bean.Partner;
import cn.com.carit.platform.dao.PartnerDao;
@Repository
public class PartnerDaoImpl extends DaoImpl implements PartnerDao<Partner> {

	private final RowMapper<Partner> rowMapper = new RowMapper<Partner>() {

		@Override
		public Partner mapRow(ResultSet rs, int rowNum) throws SQLException {
			Partner t = new Partner();
			t.setId(rs.getInt("id"));
			t.setFirmName(rs.getString("firm_name"));
			t.setPassword(rs.getString("password"));
			t.setCity(rs.getString("city"));
			t.setAddr(rs.getString("addr"));
			t.setContactPerson(rs.getString("contact_person"));
			t.setEmail(rs.getString("email"));
			t.setPhone(rs.getString("phone"));
			t.setStatus(rs.getInt("status"));
			t.setLastLoginIp(rs.getString("last_login_ip"));
			t.setLastLoginTime(rs.getTimestamp("last_login_time"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			return t;
		}
	};
	
	@Override
	public int add(Partner t) {
		String sql="insert into t_partner(firm_name, password, city, addr, contact_person, phone, email, status, create_time, update_time) values(?, ?, ?, ?, ?, ?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
				, t.getFirmName()
				, t.getPassword()
				, t.getCity()
				, t.getAddr()
				, t.getContactPerson()
				, t.getPhone()
				, t.getEmail()
				, Constants.STATUS_VALID
			);
	}

	@Override
	public int update(Partner t) {
		StringBuilder sql = new StringBuilder("update t_partner set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.hasText(t.getCity())) {
			sql.append(", city=?");
			args.add(t.getCity());
		}
		if (StringUtils.hasText(t.getPassword())) {
			sql.append(", password=?");
			args.add(t.getPassword());
		}
		if (StringUtils.hasText(t.getAddr())) {
			sql.append(", addr=?");
			args.add(t.getAddr());
		}
		if (StringUtils.hasText(t.getContactPerson())) {
			sql.append(", contact_person=?");
			args.add(t.getContactPerson());
		}
		if (StringUtils.hasText(t.getPhone())) {
			sql.append(", phone=?");
			args.add(t.getPhone());
		}
		if (StringUtils.hasText(t.getEmail())) {
			sql.append(", email=?");
			args.add(t.getEmail());
		}
		if (StringUtils.hasText(t.getFirmName())) {
			sql.append(", firm_name=?");
			args.add(t.getFirmName());
		}
		if (t.getStatus()!=null) {
			sql.append(", status=?");
			args.add(t.getStatus());
		}
		if (StringUtils.hasText(t.getLastLoginIp())) {
			sql.append(", last_login_ip=?");
			args.add(t.getLastLoginIp());
		}
		if (t.getLastLoginTime() != null) {
			sql.append(", last_login_time=?");
			args.add(t.getLastLoginTime());
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
		String sql="delete from t_partner where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Partner queryById(int id) {
		String sql="select * from t_partner where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<Partner> queryByExemple(Partner t, DataGridModel dgm) {
		JsonPage<Partner> jsonPage = new JsonPage<Partner>(dgm.getPage(), dgm.getRows());
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		StringBuilder sql = new StringBuilder("select * from t_partner where 1=1");
		StringBuilder countSql = new StringBuilder("select count(1) from t_partner where 1=1");
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
	public List<Partner> queryByExemple(Partner t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_partner where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<Partner> queryAll() {
		String sql="select * from t_partner";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			Partner t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getCity())) {
			sql.append(" and city like CONCAT(?,'%')");
			args.add(t.getCity());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getAddr())) {
			sql.append(" and addr like CONCAT(?,'%')");
			args.add(t.getAddr());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getContactPerson())) {
			sql.append(" and contact_person like CONCAT(?,'%')");
			args.add(t.getContactPerson());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getPhone())) {
			sql.append(" and phone like CONCAT(?, '%')");
			args.add(t.getPhone());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getEmail())) {
			sql.append(" and email like CONCAT(?,'%')");
			args.add(t.getEmail());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getFirmName())) {
			sql.append(" and firm_name like CONCAT(?,'%')");
			args.add(t.getFirmName());
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
	public Partner queryByName(String name) {
		String sql="select * from t_partner where firm_name=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, name, rowMapper);
	}

	@Override
	public boolean checkName(String name) {
		String sql="select 1 from t_partner where firm_name=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return checkExisted(sql, name)>0;
	}

	@Override
	public Partner queryBoundingPartner(String deviceId) {
		String sql="select b.* from t_partner_equipment a, t_partner b where a.device_id=? LIMIT 1";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, deviceId, rowMapper);
	}

}

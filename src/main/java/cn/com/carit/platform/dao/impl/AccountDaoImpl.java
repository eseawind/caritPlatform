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
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.dao.AccountDao;

@Repository
public class AccountDaoImpl extends DaoImpl implements AccountDao<Account> {

	private final RowMapper<Account> rowMapper = new RowMapper<Account>() {

		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account t = new Account();
			t.setId(rs.getInt("id"));
			t.setEmail(rs.getString("email"));
			t.setPassword(rs.getString("password"));
			t.setNickName(rs.getString("nick_name"));
			t.setGender(rs.getByte("gender"));
			t.setBirthday(rs.getTimestamp("birthday"));
			t.setPhoto(rs.getString("photo"));
			t.setThumbPhoto(rs.getString("thumb_photo"));
			t.setBalance(rs.getDouble("balance"));
			t.setRealName(rs.getString("real_name"));
			t.setIdCard(rs.getString("id_card"));
			t.setOfficePhone(rs.getString("office_phone"));
			t.setMobile(rs.getString("mobile"));
			t.setAddress(rs.getString("address"));
			t.setLastLoginIp(rs.getString("last_login_ip"));
			t.setLastLoginTime(rs.getTimestamp("last_login_time"));
			t.setStatus(rs.getInt("status"));
			t.setUpdateTime(rs.getTimestamp("update_time"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			return t;
		}
	};
	
	@Override
	public void register(String email, String password, String nickName) {
		String sql = "insert into t_account (email, password, nick_name"
				+ ", update_time, create_time) " 
				+ "values (?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql, email, password, nickName);
	}

	@Override
	public int add(Account t) {
		String sql = "insert into t_account (email, password, nick_name, gender"
				+ ", birthday, photo, balance, real_name, id_card"
				+ ", office_phone, mobile, address, update_time, create_time) " 
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql
				, t.getEmail()
				, t.getPassword()
				, t.getNickName()
				, t.getGender()
				, t.getBirthday()
				, t.getPhoto()
				, t.getBalance()
				, t.getRealName()
				, t.getIdCard()
				, t.getOfficePhone()
				, t.getMobile()
				, t.getAddress()
			);
	}

	@Override
	public int update(Account t) {
		StringBuilder sql=new StringBuilder("update t_account set update_time=now()");
		List<Object> val = new ArrayList<Object>();
		if (StringUtils.hasText(t.getEmail())) {
			sql.append(", email=?");
			val.add(t.getEmail());
		}
		if (StringUtils.hasText(t.getPassword())) {
			sql.append(", password=?");
			val.add(t.getPassword());
		}
		if (StringUtils.hasText(t.getNickName())) {
			sql.append(", nick_name=?");
			val.add(t.getNickName());
		}
		if (t.getGender() != null) {
			sql.append(", gender=?");
			val.add(t.getGender());
		}
		if (t.getBirthday() != null) {
			sql.append(", birthday=?");
			val.add(t.getBirthday());
		}
		if (StringUtils.hasText(t.getPhoto())) {
			sql.append(", photo=?");
			val.add(t.getPhoto());
		}
		if (StringUtils.hasText(t.getThumbPhoto())) {
			sql.append(", thumb_photo=?");
			val.add(t.getThumbPhoto());
		}
		if (t.getBalance() != null) {
			sql.append(", balance=?");
			val.add(t.getBalance());
		}
		if (StringUtils.hasText(t.getRealName())) {
			sql.append(", real_name=?");
			val.add(t.getRealName());
		}
		if (StringUtils.hasText(t.getIdCard())) {
			sql.append(", id_card=?");
			val.add(t.getIdCard());
		}
		if (StringUtils.hasText(t.getOfficePhone())) {
			sql.append(", office_phone=?");
			val.add(t.getOfficePhone());
		}
		if (StringUtils.hasText(t.getMobile())) {
			sql.append(", mobile=?");
			val.add(t.getMobile());
		}
		if (StringUtils.hasText(t.getAddress())) {
			sql.append(", address=?");
			val.add(t.getAddress());
		}
		if (StringUtils.hasText(t.getLastLoginIp())) {
			sql.append(", last_login_ip=?");
			val.add(t.getLastLoginIp());
		}
		if (t.getLastLoginTime() != null) {
			sql.append(", last_login_time=?");
			val.add(t.getLastLoginTime());
		}
		if (t.getStatus() != null) {
			sql.append(", status=(status|?)");
			val.add(t.getStatus());
		}
		if (t.getUpdateTime() != null) {
			sql.append(", update_time=?");
			val.add(t.getUpdateTime());
		}
		if (t.getCreateTime() != null) {
			sql.append(", create_time=?");
			val.add(t.getCreateTime());
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
		String sql = "delete from t_account where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Account queryById(int id) {
		String sql = "select * from t_account where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public JsonPage<Account> queryByExemple(Account t, DataGridModel dgm) {
		JsonPage<Account> jsonPage = new JsonPage<Account>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_account where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql = "select count(1) from t_account where 1=1"
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
	public List<Account> queryByExemple(Account t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_account where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public List<Account> queryAll() {
		String sql="select * from t_account";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			Account t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getEmail())) {
			sql.append(" and email like CONCAT('%',?,'%')");
			args.add(t.getEmail());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getPassword())) {
			sql.append(" and password like CONCAT('%',?,'%')");
			args.add(t.getPassword());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getNickName())) {
			sql.append(" and nick_name like CONCAT('%',?,'%')");
			args.add(t.getNickName());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getGender() != null) {
			sql.append(" and gender=?");
			args.add(t.getGender());
			argTypes.add(-6);// java.sql.Types type
		}
		if (t.getBirthday() != null) {
			sql.append(" and birthday=?");
			args.add(t.getBirthday());
			argTypes.add(91);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getPhoto())) {
			sql.append(" and photo like CONCAT('%',?,'%')");
			args.add(t.getPhoto());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getBalance() != null) {
			sql.append(" and balance=?");
			args.add(t.getBalance());
			argTypes.add(8);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getRealName())) {
			sql.append(" and real_name like CONCAT('%',?,'%')");
			args.add(t.getRealName());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getIdCard())) {
			sql.append(" and id_card like CONCAT('%',?,'%')");
			args.add(t.getIdCard());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getOfficePhone())) {
			sql.append(" and office_phone like CONCAT('%',?,'%')");
			args.add(t.getOfficePhone());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getMobile())) {
			sql.append(" and mobile like CONCAT('%',?,'%')");
			args.add(t.getMobile());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getAddress())) {
			sql.append(" and address like CONCAT('%',?,'%')");
			args.add(t.getAddress());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(t.getLastLoginIp())) {
			sql.append(" and last_login_ip like CONCAT('%',?,'%')");
			args.add(t.getLastLoginIp());
			argTypes.add(12);// java.sql.Types type
		}
		if (t.getLastLoginTime() != null) {
			sql.append(" and last_login_time=?");
			args.add(t.getLastLoginTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (t.getStatus() != null) {
			sql.append(" and status=?");
			args.add(t.getStatus());
			argTypes.add(-6);// java.sql.Types type
		}
		if (t.getUpdateTime() != null) {
			sql.append(" and update_time=?");
			args.add(t.getUpdateTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (t.getCreateTime() != null) {
			sql.append(" and create_time=?");
			args.add(t.getCreateTime());
			argTypes.add(93);// java.sql.Types type
		}
		return sql.toString();
	}

	@Override
	public int checkAccount(String email, String nickName) {
		StringBuilder sql=new StringBuilder("select 1 from t_account where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		if(StringUtils.hasText(email)) {
			sql.append(" and email=?");
			args.add(email);
			argTypes.add(Types.VARCHAR);
		}
		if(StringUtils.hasText(nickName)){
			sql.append(" and nick_name=?");
			args.add(nickName);
			argTypes.add(Types.VARCHAR);
		}
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		try {
			return queryForInt(sql.toString(), args, argTypes);
		} catch (Exception e) {
			log.warn("not exist record of email["+email+"] or nickName["+nickName+"]");
			log.warn(e.getMessage());
		}
		return 0;
	}

	@Override
	public Account queryByEmail(String email) {
		String sql = "select * from t_account where email=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, email, rowMapper);
	}

	@Override
	public int updatePwd(String email, String password) {
		String sql = "update t_account set update_time=now(), password=? where email=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, password, email);
	}

	@Override
	public void logon(int id, String ip) {
		String sql = "update t_account set update_time=now(), last_login_time=now(), last_login_ip=? where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jdbcTemplate.update(sql, ip, id);
	}

	
}

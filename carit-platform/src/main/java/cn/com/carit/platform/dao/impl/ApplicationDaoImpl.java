package cn.com.carit.platform.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.com.carit.DaoImpl;
import cn.com.carit.platform.bean.Application;
import cn.com.carit.platform.dao.ApplicationDao;

@Repository
public class ApplicationDaoImpl extends DaoImpl implements ApplicationDao<Application> {

	
	private final RowMapper<Application> rowMapper = new RowMapper<Application>() {

		@Override
		public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
			Application t = new Application();
			t.setId(rs.getInt("id"));
			t.setVersion(rs.getString("version"));
			t.setAppFilePath(rs.getString("app_file_path"));
			return t;
		}
	};
	
	@Override
	public List<Application> queryAll() {
		String sql="select id, version, app_file_path from t_application where status>0";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

}

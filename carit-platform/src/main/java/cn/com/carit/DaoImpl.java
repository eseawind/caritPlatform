package cn.com.carit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.com.carit.common.utils.CaritUtils;

@Repository
public class DaoImpl {
	@Resource(name = "jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	public <T> T query(String sql, List<Object> args, List<Integer> argTypes, ResultSetExtractor<T> rse) {
		return jdbcTemplate.query(sql, args.toArray(), CaritUtils.listToIntArray(argTypes), rse);
	}
	
	public void query(String sql, List<Object> args, List<Integer> argTypes, RowCallbackHandler rch) {
		jdbcTemplate.query(sql, args.toArray(), CaritUtils.listToIntArray(argTypes), rch);
	}
	
	public <T> List<T> query(String sql, List<Object> args, List<Integer> argTypes, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(sql, args.toArray(), CaritUtils.listToIntArray(argTypes), rowMapper);
	}
	
	/**
	 * 按唯一字段查询，唯一字段只能是Integer/Long/String
	 * @param sql
	 * @param id
	 * @param rowMapper
	 * @return
	 */
	public <T> T query(String sql, Object id, RowMapper<T> rowMapper){
		int idType=Types.INTEGER;
		T t = null;
		if (id instanceof Long) {
			idType=Types.BIGINT;
		}
		if (id instanceof String) {
			idType=Types.VARCHAR;
		}
		try {
			t = jdbcTemplate.queryForObject(sql, new Object[]{id}, new int[]{idType}, rowMapper);
		} catch (Exception e) {
			log.warn("no record existed...");
		}
		return t;
	}
	
	public <T> T queryForObject(String sql, List<Object> args, List<Integer> argTypes, RowMapper<T> rowMapper) {
		return jdbcTemplate.queryForObject(sql, args.toArray(), CaritUtils.listToIntArray(argTypes), rowMapper);
	}
	
	public <T> T queryForObject(String sql, List<Object> args, List<Integer> argTypes, Class<T> requiredType) {
		return jdbcTemplate.queryForObject(sql, args.toArray(), CaritUtils.listToIntArray(argTypes), requiredType);
	}
	
	public Map<String, Object> queryForMap(String sql, List<Object> args, List<Integer> argTypes) {
		return jdbcTemplate.queryForMap(sql, args.toArray(), CaritUtils.listToIntArray(argTypes));
	}
	
	public long queryForLong(String sql, List<Object> args, List<Integer> argTypes) {
		return jdbcTemplate.queryForLong(sql, args.toArray(), CaritUtils.listToIntArray(argTypes));
	}
	
	public int queryForInt(String sql, List<Object> args, List<Integer> argTypes) {
		return jdbcTemplate.queryForInt(sql, args.toArray(), CaritUtils.listToIntArray(argTypes));
	}
	
	public <T> List<T> queryForList(String sql, List<Object> args, List<Integer> argTypes, Class<T> elementType) {
		return jdbcTemplate.queryForList(sql, args.toArray(), CaritUtils.listToIntArray(argTypes), elementType);
	}
	
	public List<Map<String, Object>> queryForList(String sql, List<Object> args, List<Integer> argTypes) {
		return jdbcTemplate.queryForList(sql, args.toArray(), CaritUtils.listToIntArray(argTypes));
	}
	
	public SqlRowSet queryForRowSet(String sql, List<Object> args, List<Integer> argTypes) {
		return jdbcTemplate.queryForRowSet(sql, args.toArray(), CaritUtils.listToIntArray(argTypes));
	}
	
	public int update(String sql, List<Object> args, List<Integer> argTypes) {
		return jdbcTemplate.update(sql, args.toArray(), CaritUtils.listToIntArray(argTypes));
	}
	/**
	 * 批量删除
	 * @param sql
	 * @param ids
	 * @return
	 */
	public int[] batchDeleteById(String sql, final String [] ids){
		List<Object[]> batchArgs=new ArrayList<Object[]>();
		batchArgs.add(ids);
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(ids[i]));
			}
			@Override
			public int getBatchSize() {
				return ids.length;
			}
		});
	}
	
	/**
	 * 检测存不存在
	 * @param sql
	 * @param args
	 * @return
	 */
	public int checkExisted(String sql, Object ...args) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		try {
			return jdbcTemplate.queryForInt(sql, args);
		} catch (Exception e) {
			log.warn("no record existed...");
		}
		return 0;
	}
}

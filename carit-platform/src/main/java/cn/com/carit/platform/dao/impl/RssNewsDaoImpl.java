package cn.com.carit.platform.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.DaoImpl;
import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.RssNews;
import cn.com.carit.platform.dao.RssNewsDao;

@Repository
public class RssNewsDaoImpl extends DaoImpl implements RssNewsDao<RssNews> {

	private final String select="select title, pub_date pubDate, description from t_rss_news";
	private final String insert="insert into t_rss_news(catalog_id, title, source_url, pub_date, description, img_src, content, status, create_time, update_time) values(?, ?, ?, ?, ?, ?, ?, ?, now(), now())";
	
	@Override
	public int add(RssNews t) {
		if (log.isDebugEnabled()) {
			log.debug(insert);
		}
		return jdbcTemplate.update(insert
				, t.getCatalogId()
				, t.getTitle()
				, t.getSourceUrl()
				, t.getPubDate()
				, t.getDescription()
				, t.getImgSrc()
				, t.getContent()
				, Constants.STATUS_VALID
			);
	}
	

	@Override
	public void bathAdd(final Collection<RssNews> list) {
		if (log.isDebugEnabled()) {
			log.debug(insert);
		}
		jdbcTemplate.batchUpdate(insert, list, 100, new ParameterizedPreparedStatementSetter<RssNews>() {

			@Override
			public void setValues(PreparedStatement ps, RssNews t)
					throws SQLException {
				int index=1;
				ps.setInt(index++, t.getCatalogId());
				ps.setString(index++, t.getTitle());
				ps.setString(index++, t.getSourceUrl());
				ps.setTimestamp(index++, new Timestamp(t.getPubDate().getTime()));
				ps.setString(index++, t.getDescription());
				ps.setString(index++, t.getImgSrc());
				ps.setString(index++, t.getContent());
				ps.setInt(index++, Constants.STATUS_VALID);
			}
		});
	}



	@Override
	public int delete(int id) {
		String sql="delete from t_rss_news where id=?";
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public Map<String, Object> queryById(int id) {
		String sql="select title, pub_date pubDate, description, img_src imgSrc, content from t_rss_news where id=?";
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return jdbcTemplate.queryForMap(sql, id);
	}
	

	@Override
	public String readContent(int id) {
		String sql="select content from t_rss_news where id=?";
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		return queryForObject(sql, id, String.class);
	}

	@Override
	public JsonPage<Map<String, Object>> queryByExemple(RssNews t,
			DataGridModel dgm) {
		JsonPage<Map<String, Object>> page = new JsonPage<Map<String, Object>>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(select);
		sql.append(" where 1=1");
		StringBuilder countSql = new StringBuilder("select count(1) from t_rss_news where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, t);
		sql.append(whereSql);
		countSql.append(whereSql);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", countSql));
		}
		int totalRow = queryForInt(countSql.toString(), args, argTypes);
		// 更新
		page.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(CaritUtils.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());

		} else {
			sql.append(" order by pub_date desc");
		}
		sql.append(" limit ?, ?");
		args.add(page.getStartRow());
		args.add(page.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		page.setRows(queryForList(sql.toString(), args, argTypes));
		return page;
	}

	@Override
	public List<Map<String, Object>> queryByExemple(RssNews t) {
		StringBuilder sql = new StringBuilder(select);
		sql.append(" where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		sql.append(" order by pub_date desc");
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return queryForList(sql.toString(), args, argTypes);
	}
	
	

	@Override
	public List<Map<String, Object>> queryByExemple(RssNews t, int limit) {
		StringBuilder sql = new StringBuilder(select);
		sql.append(" where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		sql.append(" order by pub_date desc");
		sql.append(" limit ?");
		args.add(limit);
		argTypes.add(Types.INTEGER);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return queryForList(sql.toString(), args, argTypes);
	}


	@Override
	public String buildWhere(List<Object> args, List<Integer> argTypes,
			RssNews t) {
		StringBuilder sql=new StringBuilder();
		if (t.getCatalogId()!=null) {
			sql.append(" and catalog_id=?");
			args.add(t.getCatalogId());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(t.getTitle())) {
			sql.append(" and title like CONCAT('%',?,'%')");
			args.add(t.getTitle());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(t.getDescription())) {
			sql.append(" and description like CONCAT('%',?,'%')");
			args.add(t.getDescription());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getStartDate()!=null) {
			sql.append(" and pub_date >=?");
			args.add(t.getStartDate());
			argTypes.add(Types.TIMESTAMP);
		}
		if (t.getEndDate()!=null) {
			sql.append(" and pub_date <?");
			args.add(t.getEndDate());
			argTypes.add(Types.TIMESTAMP);
		}
		return sql.toString();
	}


	@Override
	public boolean existed(String sourceUrl) {
		String sql="select 1 from t_rss_news where source_url=?";
		return checkExisted(sql, sourceUrl)>0;
	}

}

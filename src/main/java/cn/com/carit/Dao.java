package cn.com.carit;

import java.util.List;

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;

public interface Dao<T> {
	
	/**
	 * 增加
	 * @param t
	 * @return
	 */
	int add(final T t);
	
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	int update(final T t);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(final int id);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	T queryById(final int id);
	
	/**
	 * 分页的样例查询
	 * @param t
	 * @param dgm
	 * @return
	 */
	JsonPage<T> queryByExemple(final T t, final DataGridModel dgm);
	
	/**
	 * 不带分页的样例查询
	 * @param t
	 * @return
	 */
	List<T> queryByExemple(final T t);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<T> queryAll();
	
	/**
	 * 构建where子句
	 * @param args
	 * @param argTypes
	 * @param t
	 * @return
	 */
	String buildWhere(List<Object> args, List<Integer> argTypes, T t);
}

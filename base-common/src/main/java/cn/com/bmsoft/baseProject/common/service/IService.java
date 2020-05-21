package cn.com.bmsoft.baseProject.common.service;

import java.util.List;

/**
 * 通用业务逻辑接口
 * @param <T>
 */
public interface IService<T> {

	/**
	 * 获取实体列表
	 * @param example
	 * @return
	 */
	List<T> selectByExample(Object example);

	/**
	 * 获取实体数量
	 * @param example
	 * @return
	 */
	int countByExample(Object example);

	/**
	 * 获取实体
	 * @param key
	 * @return
	 */
	T selectByKey(Object key);

	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	int save(T entity);

	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * 删除实体
	 * @param key
	 * @return
	 */
	int delete(Object key);

	/**
	 * 批量删除实体
	 * @param list
	 * @param property
	 * @param clazz
	 * @return
	 */
	int batchDelete(List list, String property, Class<T> clazz);
}
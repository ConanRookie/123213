package cn.com.bmsoft.baseProject.common.service.impl;

import cn.com.bmsoft.baseProject.common.exception.ExceptionCast;
import cn.com.bmsoft.baseProject.common.model.response.CommonCode;
import cn.com.bmsoft.baseProject.common.service.IService;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用业务逻辑实现
 * @param <T>
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> selectByExample(Object example) {
        if (example == null) {
            return this.mapper.selectAll();
        }
        return this.mapper.selectByExample(example);
    }

    @Override
    public int countByExample(Object example) {
        return this.mapper.selectCountByExample(example);
    }

    @Override
    public T selectByKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    @Override
    @Transactional
    public int save(T entity) {
        if (this.validateEntity(entity)) {
            return this.mapper.insert(entity);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public int update(T entity) {
        Object key = null;
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                try {
                    key = field.get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (key == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        T t = this.mapper.selectByPrimaryKey(key);
        if (t == null) {
            ExceptionCast.cast(CommonCode.OBJECT_NOTEXISTS);
        }
        if (this.validateEntity(entity)) {
            return this.mapper.updateByPrimaryKeySelective(entity);
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public int delete(Object key) {
        return this.mapper.deleteByPrimaryKey(key);
    }

    @Override
    @Transactional
    public int batchDelete(List list, String property, Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 获取符合查询条件的记录
     *
     * @param valueField   key
     * @param labelField value
     * @param example   查询参数
     * @return Map数组
     * @throws IllegalAccessException
     */
    public List<Map<String, Object>> list(String valueField, String labelField, Object example) throws IllegalAccessException {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        List<T> list = this.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Map<String, Object> item = new HashMap<String, Object>();
            item.put(valueField, FieldUtils.readField(t, valueField, true));
            item.put(labelField, FieldUtils.readField(t, labelField, true));
            items.add(item);
        }
        return items;
    }

    /**
     * 校验实体对象
     * @param entity
     * @return
     */
    protected boolean validateEntity(T entity) {
        return true;
    }
}

package cn.roothub.bbs.common.dao.metadata;

import cn.roothub.bbs.common.util.StringUtil;

import java.lang.reflect.Field;

import static cn.roothub.bbs.common.dao.util.StringPool.HASH_LEFT_BRACE;
import static cn.roothub.bbs.common.dao.util.StringPool.RIGHT_BRACE;

/**
 * TableFieldInfo 存储了数据库表的所有字段信息
 * @Author: miansen.wang
 * @Date: 2019/9/1 15:28
 */
public class TableFieldInfo {

    /**
     * 数据库字段名
     */
    private final String column;

    /**
     * 实体类属性名
     */
    private final String property;

    /**
     * 属性类型
     */
    private final Class<?> propertyType;

    /**
     * 实体类型
     */
    private final Class<?> modelClass;

    /**
     * 是否查询该字段
     */
    private boolean select = true;

    public TableFieldInfo(Field field) {
        this.column = StringUtil.camelToUnderline(field.getName());
        this.property = field.getName();
        this.propertyType = field.getType();
        this.modelClass = field.getDeclaringClass();
    }

    public String getColumn() {
        return column;
    }

    public String getProperty() {
        return property;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public Class<?> getModelClass() {
        return modelClass;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    /**
     * 获取插入时的属性
     * <p>比如某个对象有个 name 的属性，在 mybatis 可以用 #{name} 获取
     * @return
     */
    public String getInsertProperty() {
        return HASH_LEFT_BRACE + property + RIGHT_BRACE;
    }
}

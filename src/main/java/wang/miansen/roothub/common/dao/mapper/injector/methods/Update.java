package wang.miansen.roothub.common.dao.mapper.injector.methods;

import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;

import wang.miansen.roothub.common.dao.mapper.enums.SqlMethod;
import wang.miansen.roothub.common.dao.mapper.metadata.TableInfo;

/**
 * 更新满足条件的数据
 *
 * @author miansen.wang
 * @date 2019-11-19 14:24
 */
public class Update extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod update = SqlMethod.UPDATE;
        String sqlScript = String.format(update.getSql(), tableInfo.getTableName(), tableInfo.getSetSegment(), getWrapperScript());
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sqlScript, modelClass);
        return this.addMappedStatement(mapperClass, update.getMethod(), sqlSource, SqlCommandType.UPDATE, String.class, null,
            Integer.class, new NoKeyGenerator(), tableInfo.getPrimaryKeyPropertyName(), tableInfo.getPrimaryKeyColumnName());
    }
}

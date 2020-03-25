package org.chwin.firefighting.apiserver.core.dao;

import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.spring.SpringUtil;
import org.chwin.firefighting.apiserver.core.spring.jdbc.JdbcAdapter;
import org.chwin.firefighting.apiserver.core.spring.jdbc.adapter.MysqlAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liming on 2017/9/15.
 */

@Repository
public class JdbcDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcAdapter adapter=null;

    private synchronized JdbcAdapter initAdapter(){
        if(adapter==null) {
            adapter = new MysqlAdapter();
            adapter.setJdbcTemplate(jdbcTemplate);
            adapter.setDbname(SpringUtil.getProp("spring.datasource.database"));
        }
        return adapter;
    }

    public Map update(String tbl, Map entity){
        return initAdapter().updateEntity(tbl,entity);
    }

    public void del(String tbl,Map entity){
        initAdapter().delEntity(tbl,entity);
    }//逻辑删除

    public void remove(String tbl,Map entity){
        initAdapter().removeEntity(tbl,entity);
    }//物理删除
    public void remove(String tbl,Map entity,String key){ initAdapter().removeEntityByCol(tbl,entity,key);
    }//物理删除

    public Map add(String tbl, Map entity){
        return initAdapter().addEntity(tbl,entity);
    }

    public List addBatch(String tbl, List entitys) {
        return initAdapter().addBatch(tbl, entitys);
    }

    public List loadListByMap(String sql, Map param){
        return initAdapter().loadListByMap(sql,param);
    }
    public Map loadPage(String code,Map param,Map pg){
        return initAdapter().loadPage(code, param, pg);
    }

    public Map loadRowByMap(String sql,Map param){
        return initAdapter().loadRowByMap(sql,param);
    }

    public List loadListByCode(String code,Map param){
        return initAdapter().loadListByCode(code,param);
    }

    public Map loadRowByCode(String code,Map param){
        return initAdapter().loadRowByCode(code,param);
    }

    public Map loadRowById(String code, String id) {
        Map<String, Object> param = new HashMap<>(1);
        param.put(CONSTANTS.C_PK, id);
        return initAdapter().loadRowByCode(code, param);
    }

    public Map loadRT(String tbl,String idx){
        return initAdapter().loadRT(tbl,idx);
    }

    public Map excuteByCode(String code,Map param){
        return initAdapter().executeByCode(code,param);
    }

    /**
     * 获取表字段
     *
     * @param tableName 表名
     * @return
     */
    public Map getTableColumn(String tableName) {
        return initAdapter().findTblInfo(tableName);
    }


    /**
     * 根据指定字段获取是否已经存在记录
     *
     * @param tableName 表名
     * @param field     字段名字
     * @param value     值
     */
    public Map loadRowByField(String tableName, String field, Object value) {

        HashMap param = new HashMap(2) {{
            put("FIELD", field);
            put("VALUE", value);
        }};
        return initAdapter().loadRowByField(tableName, param);
    }
}

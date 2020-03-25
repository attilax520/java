package org.chwin.firefighting.apiserver.core.spring.jdbc;

import org.apache.commons.lang.StringUtils;
import org.chwin.firefighting.apiserver.core.CONSTANTS;
import org.chwin.firefighting.apiserver.core.spring.mvc.ThreadVariable;
import org.chwin.firefighting.apiserver.core.util.DataPreset;
import org.chwin.firefighting.apiserver.core.util.DateUtil;
import org.chwin.firefighting.apiserver.core.util.JSONParser;
import org.chwin.firefighting.apiserver.core.util.SqlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;

/**
 * Created by liming on 2017/9/15.
 */

public abstract class JdbcAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected NamedParameterJdbcTemplate template = null;
    protected JdbcTemplate jdbcTemplate;
    private String dbname;

    public abstract String getSql4Tbl();

    public abstract String getSql4TbPK();

    public abstract Map convertPageParam(Map param, Map pg);

    public Map executeByCode(String code, Map param) {
        if (param == null) param = new HashMap();

        Map uinfo=(Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if(uinfo!=null){
            if(!uinfo.get("IS_SUPER_ADMIN").equals("3")){
                param.put(CONSTANTS.TENANT_ID,uinfo.get(CONSTANTS.TENANT_ID));
            }
        }
        Map map = SqlParser.getInstance().loadConfByCode(code, param);
        String sql = (String) map.get("sql");
        return excuteSql(sql, param);
    }

    public Map excuteSql(String sql, Map param) {
        SqlParameterSource ps = new MapSqlParameterSource(param);
        final Map result = new HashMap();
        getTemplate().execute(sql, ps, ps1 -> {
            boolean has_more = true;
            int key = 0;
            ps1.execute();
            while (has_more) {
                ResultSet rs = ps1.getResultSet();
                int count = 0;
                key++;
                if (rs == null) {
                    count = ps1.getUpdateCount();
                    result.put("key" + key, count);
                } else {
                    ResultSetMetaData rm = rs.getMetaData();
                    List list = new ArrayList();
                    int clumn = rm.getColumnCount();
                    while (rs.next()) {
                        count++;
                        Map row = new HashMap();
                        for (int i = 1; i <= clumn; i++) {
                            String label = rm.getColumnLabel(i);
                            if (StringUtils.isBlank(label)) label = rm.getColumnName(i);
                            row.put(label, rs.getString(i));
                        }
                        list.add(row);
                    }
                    result.put("key" + key, list);
                }

                try {
                    has_more = ps1.getMoreResults(Statement.CLOSE_ALL_RESULTS);
                } catch (Exception e) {
                    has_more = false;
                    logger.error("exception:", e);
                } finally {
                }
            }
            return null;
        });

        return result;
    }

    public Map loadRT(String tbl, String idx) {
        String sql = "select * from " + tbl + " where ID= :ID  ";
        Map pmp=new HashMap();
        pmp.put("ID",idx);
        return loadRowByMap(sql, pmp);
    }

    public List loadListByCode(String code, Map param) {
        Map map = SqlParser.getInstance().loadConfByCode(code, param);
        List rlist = this.loadListByMap((String) map.get("sql"), param);
        return rlist;
    }

    public Map loadPage(String code, Map param, Map pg) {
        Map result = new HashMap();
        Map map = SqlParser.getInstance().loadConfByCode(code, param);
        param = convertPageParam(param, pg);
        List rlist = this.loadListByMap((String) map.get("psql"), param);
        result.put("list", rlist);
        if (pg.containsKey("tcount")) {
            Number tcount = 0L;
            if (rlist != null && !rlist.isEmpty()) {
                Map rtmp = loadRowByMap((String) map.get("tsql"), param);
                if (rtmp != null) tcount = (Number) rtmp.get("tcount");
                Map frow = (Map) rlist.get(0);
                frow.put("__tcount", tcount);
            }
            result.put("tcount", tcount);
        }

        return result;
    }

    public Map loadRowByCode(String code, Map param) {
        List list = this.loadListByCode(code, param);
        if (list.isEmpty()) return null;
        return (Map) list.get(0);
    }

    public List loadListByMap(String sql, Map param) {
        if (param == null) param = new HashMap();

        Map uinfo=(Map) ThreadVariable.getInstance().get(CONSTANTS.REQ_UKEY);
        if(uinfo!=null){
            if(!uinfo.get("IS_SUPER_ADMIN").equals("3")){
                param.put(CONSTANTS.TENANT_ID,uinfo.get(CONSTANTS.TENANT_ID));
                if(!param.containsKey("unit_id")){
                    param.put("unit_id",uinfo.get("unit_id"));
                }
            }
        }
        SqlParameterSource ps = new MapSqlParameterSource(param);
        sql = StringUtils.replace(sql, "{2}", "");
        List list = getTemplate().queryForList(sql, ps);
        return list;
    }

    public Map loadRowByMap(String sql, Map param) {
        List list = this.loadListByMap(sql, param);
        if (list.isEmpty()) return null;
        return (Map) list.get(0);
    }

    public Map addEntity(String tbl, Map entity) {
        int indx = 0;
        StringBuffer cbuffer = new StringBuffer(), pbuffer = new StringBuffer();
        MapSqlParameterSource ps = new MapSqlParameterSource();
        entity = DataPreset.getInstance().insertPre(entity);
        String sql = "INSERT INTO %s (%s) VALUES (%s)  ";
        List columns = (List) findTblInfo(tbl).get("cols");
        insertOrderNum(tbl, columns, entity);
        for (Object k : entity.keySet()) {

            // 空值过滤
            Object value = entity.get(k);
            if (value instanceof String && StringUtils.isEmpty((String) value)) {
                entity.put(k, null);
                continue;
            }
            String key = (String) k;
            Map detail = getDetailDefine(columns, key);
            if (detail == null) continue;
            if (indx > 0) {
                cbuffer.append(",");
                pbuffer.append(",");
            }
            cbuffer.append(detail.get("name"));
            pbuffer.append(":" + key);
            indx++;
            String type = (String) detail.get("type");
            ps = putParam(value, key, type, ps);
        }

        sql = String.format(sql, tbl, cbuffer.toString(), pbuffer.toString());
        KeyHolder keyholder = new GeneratedKeyHolder();
        getTemplate().update(sql, ps, keyholder);
        return entity;
    }

    /***
     * 自动插入序列号
     * @param tbl  表名
     * @param columns 表字段
     * @param entity 待插入sql的字段
     */
    private void insertOrderNum(String tbl, List columns, Map entity) {

        Object orderNum = entity.get(CONSTANTS.C_ORDER_NUM);
        if (orderNum != null && !"".equals(orderNum)) {
            return;
        }
        Map column = getDetailDefine(columns, CONSTANTS.C_ORDER_NUM);
        if (column != null) {
            String sql = "select MAX(ORDER_NUM) ORDER_NUM from " + tbl;
            Number result = (Number) this.loadRowByMap(sql, null).get(CONSTANTS.C_ORDER_NUM);
            Double maxNum = result == null ? 1 : result.doubleValue() + 1;
            entity.put(CONSTANTS.C_ORDER_NUM, maxNum);
        }


    }

    public void delEntity(String tbl, Map entity) {
        Map map = new HashMap(4) {{
            put("ID", entity.get("ID"));
            put("DELETED", "1");
        }};
        updateEntity(tbl, map);
    }

    public void removeEntity(String tbl, Map entity) {
        String sql = "DELETE FROM  " + tbl + "  WHERE ID = :ID ";
        excuteSql(sql, entity);
    }

    public void removeEntityByCol(String tbl, Map entity,String key) {
        String sql = "DELETE FROM  " + tbl + "  WHERE " + key +  " = :" + key;
        excuteSql(sql, entity);
    }

    public Map updateEntity(String tbl, Map entity) {
        int index = 0;
        StringBuffer cbuffer = new StringBuffer();
        StringBuffer pbuffer = new StringBuffer();
        String sql = "UPDATE  %s  SET  %s WHERE %s ";
        MapSqlParameterSource ps = new MapSqlParameterSource();
        entity = DataPreset.getInstance().updatePre(entity);
        List columns = (List) findTblInfo(tbl).get("cols");

        for (Object k : entity.keySet()) {
            String key = (String) k;
            Map detail = getDetailDefine(columns, key);
            if (detail == null || StringUtils.equalsIgnoreCase(key, CONSTANTS.C_PK)) continue;
            if (index > 0) {
                cbuffer.append(",");
            }

            cbuffer.append(key).append("=:").append(key);
            index++;
            Object v = entity.get(k);
            String type = (String) detail.get("type");
            ps = putParam(v, key, type, ps);
        }

        String pk = (String) entity.get(CONSTANTS.C_PK);
        if (StringUtils.isBlank(pk)) {
            return null;
        }
        pbuffer.append(" ").append(CONSTANTS.C_PK).append("=:").append(CONSTANTS.C_PK).append(" ");
        ps.addValue(CONSTANTS.C_PK, pk);

        sql = String.format(sql, tbl, cbuffer.toString(), pbuffer.toString());
        getTemplate().update(sql, ps);
        return entity;
    }

    private MapSqlParameterSource putParam(Object v, String key, String type, MapSqlParameterSource ps) {
        ps.addValue(key, v);
        if(StringUtils.equalsIgnoreCase(type, CONSTANTS.TY_BIG_INT)){
            ps.addValue(key, v, Types.BIGINT);
        }else if (StringUtils.containsIgnoreCase(type, CONSTANTS.TY_INT)){
            ps.addValue(key, v, Types.INTEGER);
        }else if (StringUtils.containsIgnoreCase(type, CONSTANTS.TY_DATE)) {
            if (v instanceof java.lang.String)
                ps.addValue(key, DateUtil.parse((String) v));
        }else if (StringUtils.containsIgnoreCase(type, CONSTANTS.TY_CHAR) || StringUtils.containsIgnoreCase(type, CONSTANTS.TY_TEXT)) {
            if (v instanceof Map || v instanceof List)
                ps.addValue(key, JSONParser.obj2Json(v), Types.CHAR);
        }
        return ps;
    }

    public Map findTblInfo(String tbl) {
        Map result = new HashMap();
        String sql = getSql4Tbl();
        Map pmp = new HashMap();
        pmp.put("tname", tbl);
        pmp.put("schema", dbname);
        result.put("cols", loadListByMap(sql, pmp));
        return result;
    }

    private Map getDetailDefine(List columns, String key) {
        for (Object col : columns) {
            Map detail = (Map) col;
            String name = (String) detail.get("name");
            String sname = (String) detail.get("sname");
            if (StringUtils.equalsIgnoreCase(key, name) || StringUtils.equalsIgnoreCase(key, sname))
                return detail;
        }
        return null;
    }

    private NamedParameterJdbcTemplate getTemplate() {
        if (template == null)
            template = new NamedParameterJdbcTemplate(jdbcTemplate);
        return template;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /**
     * 批量新增
     *
     * @return
     */
    public List<Map> addBatch(String tbl, List<Map> entitys) {

        StringBuffer cbuffer = new StringBuffer(), pbuffer = new StringBuffer();
        String sql = "INSERT INTO %s (%s) VALUES  (%s)  ";
        List columns = (List) findTblInfo(tbl).get("cols");
        entitys.forEach(entity -> {
            //插入sql前操作
            entity = DataPreset.getInstance().insertPre(entity);
            insertOrderNum(tbl, columns, entity);
        });
        createColumnName(entitys.get(0), columns, cbuffer, pbuffer);
        sql = String.format(sql, tbl, cbuffer.toString(), pbuffer.toString());
        Map[] entityMap = new Map[entitys.size()];
        entitys.toArray(entityMap);
        getTemplate().batchUpdate(sql, SqlParameterSourceUtils.createBatch(entityMap));
        return entitys;

    }

    /**
     * 创建字段列表
     *
     * @param entity
     * @param columns
     * @return
     */
    private StringBuffer createColumnName(Map entity, List columns, StringBuffer cbuffer, StringBuffer pbuffer) {

        int indx = 0;
        for (Object k : entity.keySet()) {
            String key = (String) k;
            Map detail = getDetailDefine(columns, key);
            if (detail == null) continue;
            if (indx > 0) {
                cbuffer.append(",");
                pbuffer.append(",");
            }
            cbuffer.append(detail.get("name"));
            pbuffer.append(":" + key);
            indx++;

            Object v = entity.get(k);
            String type = (String) detail.get("type");
        }
        return cbuffer;
    }

    /**
     * 根据指定字段获取是否已经存在记录
     *
     * @param tableName 表名
     * @param entity    FIELD 字段名字 VALUE 值
     */
    public Map loadRowByField(String tableName, HashMap entity) {

        String field = (String) entity.get("FIELD");
        List columns = (List) findTblInfo(tableName).get("cols");
        Map detail = getDetailDefine(columns, field);
        if (detail == null) {
            return null;
        }


        MapSqlParameterSource ps = new MapSqlParameterSource();
        String sql = "SELECT * FROM %s where %s = :%s";
        //判断字段  是否删除
        Map deletedField = getDetailDefine(columns, "DELETED");
        if (deletedField != null) {
            sql += " AND DELETED=:DELETED";
            ps.addValue("DELETED", "0", Types.CHAR);
        }
        sql = String.format(sql, tableName, field, field);
        ps = putParam(entity.get("VALUE"), field, (String) detail.get("type"), ps);
        List list = getTemplate().queryForList(sql, ps);
        if (list != null && list.size() != 0) {
            return (Map) list.get(0);
        }
        return null;

    }

    ;
}

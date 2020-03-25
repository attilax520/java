package org.chwin.firefighting.apiserver.core.spring.jdbc.adapter;


import java.util.Map;

import org.chwin.firefighting.apiserver.core.spring.jdbc.JdbcAdapter;
/**
 * Created by liming on 2017/9/18.
 */
public class MysqlAdapter extends JdbcAdapter {

    @Override
    public String getSql4Tbl() {
        String sql="SELECT column_name AS 'name',column_comment AS 'sname',data_type as type FROM information_schema.columns WHERE  table_name = :tname and table_schema = :schema ORDER BY ordinal_position ";
        return sql;
    }

    @Override
    public String getSql4TbPK(){
        return "SELECT column_name as 'name' FROM information_schema.key_column_usage WHERE table_name = :tname and table_schema = :shcema ";
    }

    @Override
    public Map convertPageParam(Map param, Map pg) {
        Number start=(Number)pg.get("start");
        Number limit=(Number)pg.get("limit");
        param.put("start", start.intValue());
        param.put("end", limit.intValue());
        return param;
    }
}


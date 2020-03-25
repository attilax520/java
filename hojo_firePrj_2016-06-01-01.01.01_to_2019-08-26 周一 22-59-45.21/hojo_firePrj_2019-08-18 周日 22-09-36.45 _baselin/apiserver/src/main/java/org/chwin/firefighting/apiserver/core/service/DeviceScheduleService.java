package org.chwin.firefighting.apiserver.core.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceScheduleService {
    @Autowired
    private JdbcDao jdbcDao;

    public void deviceStatistics(){
        String statistics_date = DateTime.now().toString("yyyy-MM-dd");
        //根据租户分组,查询设备总数量,在线数量,离线数量
        String sql = "SELECT d.unit_id,d.zone_code,d.tenant_id,d.type,count(d.id) total,\n" +
                "(select count(d1.id) from DATA_DEVICE d1  WHERE d1.logic_status = 1  AND d1.is_online = 1 and d1.tenant_id=d.tenant_id and d1.type=d.type) online_count,\n" +
                "(select count(d1.id) from DATA_DEVICE d1  WHERE d1.logic_status = 1  AND d1.is_online = 0 and d1.tenant_id=d.tenant_id and d1.type=d.type) offline_count\n" +
                "FROM DATA_DEVICE d WHERE d.logic_status = 1 GROUP BY d.tenant_id,d.type,d.unit_id,d.zone_code ";
        List<Map<String, Object>> list = jdbcDao.loadListByMap(sql,null);
        List<Map<String, Object>> entitys = new ArrayList<>();
        for (Map<String, Object> object : list){
            if (object.get("tenant_id") != null && !"".equals(object.get("tenant_id"))){
                Map param = new HashMap();
                param.put("statistics_date",statistics_date);
                param.put("tenant_id",object.get("tenant_id"));
                param.put("device_type",object.get("type"));
                param.put("total",object.get("total"));
                param.put("online_count",object.get("online_count"));
                param.put("offline_count",object.get("offline_count"));
                entitys.add(param);
            }
        }
        System.out.println("插入设备数量统计表的数据有:"+entitys.size());
        if (entitys != null && entitys.size() > 0){
            jdbcDao.addBatch("DATA_DEVICE_STATISTICS",entitys);
        }
    }
}

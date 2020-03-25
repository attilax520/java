package org.chwin.firefighting.apiserver.fire.service;

import org.chwin.firefighting.apiserver.core.dao.JdbcDao;
import org.chwin.firefighting.apiserver.core.service.ScriptComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataCenterService {
    @Autowired
    private ScriptComponent scriptCmp;
    @Autowired
    private JdbcDao jdbcDao;

    public Object doProcess(Map param){
        Map params = new HashMap();
        String url = (String)param.get("url");
        params.put("db", jdbcDao);
        params.put("prm", param);
        Object rtmap =  scriptCmp.excute(getRuleByUrl(url), params);
        return rtmap;
    }
    private String getRuleByUrl(String url){
        return "pub.cc.dataCenter." + url;
    }


    //单位评分功能
    public void unitScore(){
        //先获得所有联网单位
        List<Map> list = jdbcDao.loadListByCode("score_net_unit",new HashMap());
        for(int i=0;i<list.size();i++){
            String id = list.get(i).get("ID") + "";
            double rate = completionRata(id);//消防设施完好率项目的 得分
            double hidden = hiddenScore(id);//隐患处理得分计算 得分
            double response = alarmResponse(id);//报警响应 得分
            double repair = checkRepairScore(id);//设施检测维保情况 得分
            double emergency = emergencyPlanScore(id);//应急预案 得分
            double task = taskScore(id);//巡查情况 得分
            double train = trainScore(id);//培训情况 得分
            double mini = miniStationScore(id);//微型消防站情况 得分
            double bonusScore = bonusScore(id);//加分项 得分
            Map param = new HashMap();
            param.put("facility_soundness",rate);
            param.put("incipient_fault",hidden);
            param.put("alarm_response",response);
            param.put("facility_maintenance",repair);
            param.put("contingency_plan",emergency);
            param.put("inspection_situation",task);
            param.put("training_situation",train);
            param.put("station",mini);
            param.put("bonus",bonusScore);
            double average = (rate + hidden + response + repair + emergency + task + train + mini + bonusScore)/8;
            param.put("average",average);
            param.put("unit_id",id);
            List<Map> unit = jdbcDao.loadListByCode("score_unit_list",param);
            if(unit.size() > 0){
                String sid  = unit.get(0).get("id") + "";
                param.put("ID",sid);
                jdbcDao.update("UNIT_SCORE",param);
            }else{
                jdbcDao.add("UNIT_SCORE",param);
            }
        }


    }

    //消防设施完好率计算
    public double completionRata(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        // 火灾报警控制器及下属设施故障数量
        int fire = Integer.parseInt(jdbcDao.loadRowByCode("score_fire_machine",map).get("COUNT").toString());
        // 消防水系统故障数量
        Map result = jdbcDao.loadRowByCode("score_fire_ele_gas",map);
        int water = Integer.parseInt(result.get("WATER").toString());
        // 电气火灾报警系统故障数量
        int electric = Integer.parseInt(result.get("ELECTRIC").toString());
        // 气体监测系统故障数量
        int gas = Integer.parseInt(result.get("GAS").toString());
        double bad = fire * 0.1 + water * 0.1 + electric * 0.05 + gas * 0.05;
        return 30 * (1 - bad <= 0 ? 0 : (1 - bad));
    }

    //隐患处理得分计算
    public double hiddenScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        // 隐患处理
        Map<String,BigDecimal> result = jdbcDao.loadRowByCode("score_hidden",map);
        int fault = result.get("FAULT").intValue();//故障
        int general = result.get("GENERAL").intValue();//一般
        int danger = result.get("DANGER").intValue();//重大隐患
        double fault_score = (20 - fault * 5) <= 0 ? 0 : (20 - fault * 5);
        double general_score = (5 - general * 1) <= 0 ? 0 : (5 - general * 1);
        double danger_score = (5 - danger * 1) <= 0 ? 0 : (5 - danger * 1);
        return fault_score + general_score + danger_score;
    }

    //报警响应 得分计算
    public double alarmResponse(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);

        double fire_score = 15;
        double water_score = 5;
        double electric_score = 5;
        double gas_score = 5;

        // 火灾自动报警系统
        List<Map> fire = jdbcDao.loadListByCode("score_response_machine",map);
        for(int i=0;i<fire.size();i++){
            Map m = fire.get(i);
            String BUSINESS_ID = m.get("id") + "";
            long create_time = Long.parseLong(m.get("create_time").toString());
            Map<String,String> param = new HashMap();
            param.put("BUSINESS_ID",BUSINESS_ID);
            Map r = jdbcDao.loadRowByCode("score_response_machine_result",param);
            if( r == null){
                Date n = new Date();
                long handle = n.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    fire_score = fire_score - 0;
                }else if(min>3 && min<60){
                    fire_score = fire_score - 1;
                }else{
                    fire_score = fire_score - 5;
                }
            }else{
                Date d = (Date) r.get("CREATE_TIME");
                long handle = d.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    fire_score = fire_score - 0;
                }else if(min>3 && min<60){
                    fire_score = fire_score - 1;
                }else{
                    fire_score = fire_score - 5;
                }
            }
        }
        // 消防水系统
        map.put("parent_id","76");
        List<Map> water = jdbcDao.loadListByCode("score_response_other",map);
        for(int i=0;i<water.size();i++){
            Map m = water.get(i);
            String BUSINESS_ID = m.get("id") + "";
            long create_time = Long.parseLong(m.get("create_time").toString());
            Map<String,String> param = new HashMap();
            param.put("BUSINESS_ID",BUSINESS_ID);
            Map r = jdbcDao.loadRowByCode("score_response_machine_result",param);
            if( r == null){
                Date n = new Date();
                long handle = n.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    water_score = water_score - 0;
                }else if(min>3 && min<60){
                    water_score = water_score - 0.5;
                }else{
                    water_score = water_score - 1;
                }
            }else{
                Date d = (Date) r.get("CREATE_TIME");
                long handle = d.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    water_score = water_score - 0;
                }else if(min>3 && min<60){
                    water_score = water_score - 0.5;
                }else{
                    water_score = water_score - 1;
                }
            }
        }
        // 电气火灾报警系统
        map.put("parent_id","72");
        List<Map> electric = jdbcDao.loadListByCode("score_response_other",map);
        for(int i=0;i<electric.size();i++){
            Map m = electric.get(i);
            String BUSINESS_ID = m.get("id") + "";
            long create_time = Long.parseLong(m.get("create_time").toString());
            Map<String,String> param = new HashMap();
            param.put("BUSINESS_ID",BUSINESS_ID);
            Map r = jdbcDao.loadRowByCode("score_response_machine_result",param);
            if( r == null){
                Date n = new Date();
                long handle = n.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    electric_score = electric_score - 0;
                }else if(min>3 && min<60){
                    electric_score = electric_score - 0.5;
                }else{
                    electric_score = electric_score - 1;
                }
            }else{
                Date d = (Date) r.get("CREATE_TIME");
                long handle = d.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    electric_score = electric_score - 0;
                }else if(min>3 && min<60){
                    electric_score = electric_score - 0.5;
                }else{
                    electric_score = electric_score - 1;
                }
            }
        }
        // 气体监测系统
        map.put("parent_id","78");
        List<Map> gas = jdbcDao.loadListByCode("score_response_other",map);
        for(int i=0;i<gas.size();i++){
            Map m = gas.get(i);
            String BUSINESS_ID = m.get("id") + "";
            long create_time = Long.parseLong(m.get("create_time").toString());
            Map<String,String> param = new HashMap();
            param.put("BUSINESS_ID",BUSINESS_ID);
            Map r = jdbcDao.loadRowByCode("score_response_machine_result",param);
            if( r == null){
                Date n = new Date();
                long handle = n.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    gas_score = gas_score - 0;
                }else if(min>3 && min<60){
                    gas_score = gas_score - 0.5;
                }else{
                    gas_score = gas_score - 1;
                }
            }else{
                Date d = (Date) r.get("CREATE_TIME");
                long handle = d.getTime();
                int min = (int) ((handle - create_time)/1000/60);
                if(min < 3){
                    gas_score = gas_score - 0;
                }else if(min>3 && min<60){
                    gas_score = gas_score - 0.5;
                }else{
                    gas_score = gas_score - 1;
                }
            }
        }
        return fire_score + water_score + electric_score + gas_score;
    }

    //设施检测维保情况
    public double checkRepairScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        // 维保情况
        map.put("type","R");

        double repair_score = 10;
        double check_score = 10;

        List<Map> repair = jdbcDao.loadListByCode("score_check_repair",map);
        if(repair.size() == 0){
            repair_score = repair_score - 3;
        }else{
            Map m = repair.get(0);
            String ID = m.get("ID") + "";
            Map<String,String> param = new HashMap();
            param.put("ID",ID);
            List file = jdbcDao.loadListByCode("score_check_repair_file",param);
            if(file.size() > 0){
                repair_score = repair_score - 0;
            }else{
                repair_score = repair_score - 2;
            }
        }
        // 检测情况
        map.put("type","C");
        List<Map> check = jdbcDao.loadListByCode("score_check_repair",map);
        if(check.size() == 0){
            check_score = check_score - 3;
        }else{
            Map m = check.get(0);
            String ID = m.get("ID") + "";
            Map<String,String> param = new HashMap();
            param.put("ID",ID);
            List file = jdbcDao.loadListByCode("score_check_repair_file",param);
            if(file.size() > 0){
                check_score = check_score - 0;
            }else{
                check_score = check_score - 2;
            }
        }
        return repair_score + check_score;
    }
    //应急预案
    public double emergencyPlanScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        // 应急预案
        List<Map> emergency = jdbcDao.loadListByCode("score_emergency",map);
        double score = 10;
        if(emergency.size() > 0){
            score = score - 0;
        }else{
            score = score - 1;
        }
        return score;
    }

    //巡查情况
    public double taskScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        // 巡查情况
        List<Map> config = jdbcDao.loadListByCode("score_task_config",map);
        double score = 10;
        if(config.size() > 0){
            List<Map> task = jdbcDao.loadListByCode("score_task",map);
            double num = task.size();
            score = score - (num > 10 ? 10 : num);
        }else{
            score = 0;
        }
        return score;
    }

    //培训情况
    public double trainScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        // 培训情况
        map.put("type","train");
        List<Map> train = jdbcDao.loadListByCode("score_train",map);
        double train_score;
        if(train.size() > 0){
            train_score = 5;
        }else{
            train_score = 4;
        }
        // 演练情况
        map.put("type","drill");
        List<Map> drill = jdbcDao.loadListByCode("score_train",map);
        double drill_score;
        if(drill.size() > 0){
            drill_score = 5;
        }else{
            drill_score = 4;
        }
        return train_score + drill_score;
    }

    //微型消防站情况
    public double miniStationScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        List<Map> station = jdbcDao.loadListByCode("score_mini_station",map);
        double m_score;
        double e_score;
        if(station.size() > 0){
            Map m = station.get(0);
            String fid = m.get("ID") + "";
            map.put("id",fid);
            Map men = jdbcDao.loadRowByCode("score_mini_station_men",map);
            Map equip = jdbcDao.loadRowByCode("score_mini_station_equipment",map);
            // 人员情况
            if(men != null){
                if(Integer.parseInt(men.get("COUNT").toString()) > 6){
                    m_score = 10;
                }else{
                    m_score = 0;
                }
            }else{
                m_score = 0;
            }
            // 演练情况
            if(equip != null){
                if(Integer.parseInt(equip.get("COUNT").toString()) > 1){
                    e_score = 10;
                }else{
                    e_score = 0;
                }
            }else{
                e_score = 0;
            }
        }else{
            m_score = 0;
            e_score = 0;
        }
        return m_score + e_score;
    }

    public double bonusScore(String id){
        Map<String,String> map = new HashMap();
        map.put("unit_id",id);
        Map machine = jdbcDao.loadRowByCode("score_bonus_machine",map);
        Map other = jdbcDao.loadRowByCode("score_bonus_other",map);
        double score;
        double m_s = Double.parseDouble(machine.get("RATE").toString());
        double w_s = Double.parseDouble(other.get("WATER").toString());
        double e_s = Double.parseDouble(other.get("ELECTRIC").toString());
        double g_s = Double.parseDouble(other.get("GAS").toString());
        if(m_s == -1 && w_s == -1 && e_s == -1 && g_s == -1){
            score = 0;
        }else{
            score = ((m_s == -1 ? 0 : m_s) * 0.02 + (w_s == -1 ? 0 : w_s) * 0.02 + (e_s == -1 ? 0 : e_s) * 0.01 + (g_s == -1 ? 0 : g_s) * 0.01) * 5/((m_s == -1 ? 0 : 0.02) + (w_s == -1 ? 0 : 0.02) + (e_s == -1 ? 0 : 0.01)
                    + (g_s == -1 ? 0 : 0.01));
        }
        return score;
    }

}

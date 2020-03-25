package org.chwin.firefighting.apiserver.core;

/**
 * Created by liming on 2017/9/18.
 */
public class CONSTANTS {


    /**
     * 设备种类常量
     */
    public static final String FIRE_AUTO_ALARM_SYSTEM = "machine"; //火灾自动报警系统
    public static final String MACHINC_USER = "machine_user";  //用户信息传输装置
    public static final String MANUAL_FIRE_POINT = "manual_fire_point";  //手动火灾报警按钮
    public static final String SMOKE_DETECTOR_LINE = "smoke_detector_line";  //烟感（有线）
    public static final String AUDIBLE_VISUAL_ALARM = "audible_visual_alarm"; //声/光报警器

    public static final String ELECTRICAL_FIRE_ALARM = "electrical_fire_alarm";  //电气火灾报警系统
    public static final String FAULT_ARC_FIRE = "fault_arc_fire";  //故障电弧
    public static final String RESIDUAL_POWER_MONITOR = "residual_power_monitor";  //剩余电量监测

    public static final String AUTOMATIC_SPRINKLER = "automatic_sprinkler";  //自动喷水灭火系统
    public static final String FIRE_MAIN_NETWORK = "fire_main_network";  //消防主管网水压
    public static final String END_WATER_PRESSURE = "end_water_pressure"; //末端水压
    public static final String ALARM_VALVE_WATER = "alarm_valve_water";  //报警阀水压

    public static final String SMOKE_DETECTOR = "smoke_detector"; //独立式感烟探测器
    public static final String FIRE_HOSE = "fire_hose";  //室外消防栓
    public static final String IN_FIRE_HYDRANT = "in_fire_hydrant";  //消防水箱水位监测
    public static final String HYDRAULIC_PRESSURE_GAUGE = "hydraulic_pressure_gauge"; //室内消防栓
    public static final String MANUAL_FIRE_ALARM = "manual_fire_alarm"; //手动火灾报警按钮（独立）


    /**
     * 报警类型  设备状态： 告警warn 故障 fault 正常 normal  在线 online 离线 offline
     */
    public static final String WARN = "warn";  //告警状态
    public static final String NORMAL = "normal"; //正常运行
    public static final String FAULT = "fault";  //故障
    public static final String ONLINE = "online";  //在线
    public static final String OFFLINE = "offline"; //离线

    public static final String UTF8 = "UTF-8";
    public static final String SECURITY_MD5 = "MD5";
    public static final String SECURITY_SHA1 = "SHA1";
    public static final String SECURITY_DES = "DES";
    public static final String SECURITY_AES = "AES";
    public static final String _DES_PWD = "1qa2ws!@#$%";

    public static final String DF_JSON = "json";
    public static final String DF_XML = "xml";
    public static final String RESULT_NODE = "response";

    public static final String TY_INT_ARR = "ints";
    public static final String TY_CHAR_ARR = "chars";
    public static final String TY_CHAR = "char";
    public static final String TY_TEXT = "text";
    public static final String TY_INT = "int";
    public static final String TY_BIG_INT = "bigint";
    public static final String TY_DATE = "date";

    public static final String C_PK = "ID";
    public static final String C_CRT_TIME = "CREATE_TIME";
    public static final String C_CRT_ID = "CREATE_BY";
    public static final String C_CRT_IP = "CREATE_IP";
    public static final String C_UPT_TIME = "UPDATE_TIME";
    public static final String C_UPT_ID = "UPDATE_BY";
    public static final String C_UPT_IP = "UPDATE_IP";
    public static final String C_ORDER_NUM = "ORDER_NUM";
    public static final String C_SUPER_ADMIN = "IS_SUPER_ADMIN";
    public static final String C_DEPT_ID = "COLLECTOR_DEPT_ID";



    public static final int RE_LOGIN_CODE = -9;

    public static final String COOKIE_ID = "ssid";
    public static final String CURREN_SID = "CURR_SID";

    public static final String REQ_IP = "ip";
    public static final String REQ_ID = "sid";
    public static final String REQ_UKEY = "USER";
    public static final String REQ_DEPT = "LOGIN_USER_DEPT";
    public static final String TENANT_ID="TENANT_ID";

    //POS
    public static final String REQ_CKEY = "COLLECTOR";

    //用户登录
    public static final String DO_LOGIN="dologin";
    public static final String LOGIN = "login";

    //sql操作类型
    public static final String NULL = "NULL";
    public static final String ADD = "ADD";
    public static final String UPDATE = "UPDATE";

    //假删
    public static final String DEL = "DEL";
    //真删
    public static final String REMOVE = "REMOVE";
    //规则
    public static final String RULE = "RULE";
    /**
     * 竖线分隔
     */
    public static final String VERTICAL="|";
    /**
     * 横线
     */
    public static final String HORIZONTAL="-";
    //金额计算常量
    public static final Double FACTOR_DOUBLE_100 = 100.00;
    public static final int FACTOR_INT_100 = 100;
    /**
     * 超时时间，5分钟 过期
     */
    public static final int OVERDUE_TIME=5 * 60 * 1000;

    /**
     * 停车订单业务编码
     */
    public static final String PARKING_ORDER_NO_BS_CODE = "10";
    /**
     * 支付订单业务编码
     */
    public static final String ORDER_NO_BS_CODE = "20";
    /**
     * 退费订单业务编码
     */
    public static final String REFUND_ORDER_NO_BS_CODE = "30";
    /**
     * 优惠券业务编码
     */
    public static final String TICKET_NO_BS_CODE = "40";
    /**
     * 优惠券业务编码
     */
    public static final String ACCOUNT_BS_CODE = "100";
    /**
     * 任务订单业务号
     */
    public static final String TASK_ORDER_NO_BS_CODE = "TASK99";

}

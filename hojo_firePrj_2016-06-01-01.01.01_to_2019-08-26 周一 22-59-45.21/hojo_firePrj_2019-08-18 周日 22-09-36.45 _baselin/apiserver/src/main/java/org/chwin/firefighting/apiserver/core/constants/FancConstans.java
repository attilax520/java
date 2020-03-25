package org.chwin.firefighting.apiserver.core.constants;

/**
 * @Author GJW
 * @since 2017/12/19.
 */
public class FancConstans {


    //代缴卡常量
    public static final String KEY_CARD_IDENTIFY_CODE = "IDENTIFY_CODE";

    //ACCOUNT KEY 常量
    public static  final String KEY_BALANCE = "BALANCE";
    public static  final String KEY_ACCOUNT_ID = "ACCOUNT_ID";

    public static  final String KEY_ORDER_NO = "ORDER_NO";
    public static  final String KEY_SUB_ACCOUNT_ID = "SUB_ACCOUNT_ID";
    public static  final String KEY_PAY_ACCOUNT = "PAY_ACCOUNT";
    public static  final String KEY_CUSTOMER_ID= "CUSTOMER_ID";
    public static  final String KEY_ACTUAL_AMOUNT = "ACTUAL_AMOUNT";
    //order 常量
    public static final String KEY_BUSI_TYPE = "BUSI_TYPE";
    public static final String KEY_PAY_TYPE = "PAY_TYPE";
    public static final String KEY_PAY_CHANNAL = "PAY_CHANNAL";
    public static final String KEY_ORDER_STATE = "ORDER_STATE";
    public static final String KEY_ACCOUNT_BALANCE = "ACCOUNT_BALANCE";
    public static final String KEY_ORDER_SOURCE = "ORDER_SOURCE";
    public static final String KEY_PRICE = "AMOUNT";
    public static final String KEY_ORDER_BY = "ORDER_BY";
    public static final String KEY_SUBJECT = "SUBJECT";
    public static final String KEY_ORDER_TIME = "ORDER_TIME";
    public static final String KEY_SALE_INFO = "SALE_INFO";
    public static final String KEY_SALE_INFO_TYPE = "TYPE";
    public static final String KEY_PRINCIPAL_AMOUNT = "PRINCIPAL_AMOUNT";
    public static final String KEY_EXTRA_PRINCIPAL_AMOUNT = "EXTRA_PRINCIPAL_AMOUNT";
    //order sub key 常量
    public static final String KEY_ORDER_CAN_REFUND_FEE = "CAN_REFUND_FEE";
    //public static final String KEY_SALE_INFO_TICKET_ID = "TICKET_ID";
    public static final String KEY_SALE_INFO_ACTIVE_CODE = "ACTIVITY_CODE";
    public static final String KEY_SALE_INFO_SALE_NUM = "SALE_NUM";
    public static final String KEY_SALE_INFO_DISC_FEE = "DISC_FEE";
    public static final String KEY_SALE_INFO_ACTIVE_MSG = "ACTIVE_MSG";
    public static final String KEY_PARK_REC_INFO = "ORDER_AND_PRICE";
    public static final String KEY_PARK_REC_INFO_PARK_REC_ID = "PARKING_RECORD_ID";
    public static final String KEY_PARK_REC_INFO_PRICE = "PRICE";
    public static final String KEY_PARK_REC_INFO_DEPT_ID = "DEPT_ID";
    public static final String KEY_ORDER_IS_REFUND = "IS_REFUND";

    //ORDER_DISC key 常量
    public static final String KEY_DISC_FEE = "DISC_FEE";
    public static final String KEY_BEFOR_DISC = "BEFOR_DISC";
    public static final String KEY_AFTER_DISC = "AFTER_DISC";
    public static final String KEY_DISC_STRATEGY = "STRATEGY";
    public static final String KEY_DISC_SALE_NUM = "SALE_NUM";
    public static final String KEY_ACTIVITY_CODE = "ACTIVITY_CODE";
    public static final String KEY_ACTIVITY_MSG = "ACTIVITY_MSG";

    //返回值key常量
    public static final String KEY_EXTEND_CHANNAL_ORDER_NO = "EXTEND_CHANNAL_ORDER_NO";
    public static final String KEY_CUSTIMER_CODE = "CUSTOMER_CODE";
    public static final String KEY_CODE_INFO = "CODE_INFO";
    public static final String KEY_SUCCESS_TIME = "SUCCESS_TIME";
    public static final String KEY_PARKING_RECORD_INFO = "PARKING_RECORD_INFO";
    public static final String KEY_ORDER_ID = "ORDER_ID";

    //订单描述 待格式化字符串
    public static final String STR_ORDER_DESC = "支付方式：%s,支付金额：%s,订单号：%s";

    //退款请求常量
    public static final String KEY_REFUND_AMOUNT = "REFUND_AMOUNT";
    public static final String KEY_REFUND_TYPE = "REFUND_TYPE";
    public static final String KEY_REFUND_TIME = "REFUND_TIME";
    public static final String KEY_REFUNDABLE_FEE = "REFUNDABLE_FEE";
    public static final String KEY_REFUND_ORDRE_NO = "REFUND_ORDRE_NO";

    //退款订单关联表 KEY
    public static final String KEY_REFUND_REL_ORDER_ID = "REFUND_ORDER_ID";
    public static final String KEY_REFUND_ORDER_REL_ID = "ORDER_REL_ID";
    public static final String KEY_REFUND_REL_SUB_ID = "ORDER_SUB_ID";
    public static final String KEY_REFUND_EXTERNAL_ORDER_NO = "EXTERNAL_ORDER_NO";
    public static final String KEY_REFUND_OPERATOR_DEPT_ID = "OPERATOR_DEPT_ID";
}

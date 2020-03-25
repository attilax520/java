package org.chwin.firefighting.apiserver.fire.enums;

/**
 * @ClassName EnumErrorMsg
 * @Description TODO
 * @Author aniu
 * @Date 2018/8/14 9:09
 * @Version 1.0
 **/
public enum  EnumErrorMsg {
    OPERATION_SUCCESS(0,"操作成功!"),
    OPERATION_EXCEPTION(-1,"操作异常!"),
    UPLOAD_SUCCESS(1000,"上传成功!"),
    UPLOAD_FAIL(1001,"上传失败!"),
    FILE_TYPE_NOT_SUPPORT(1002,"文件类型不支持!"),
    FILE_SIZE_BEYOND_LIMIT(1003,"文件大小不支持!"),
    RENAME_FAIL(1004,"重命名失败!"),
    RENAME_SUCCESS(1005,"重命名成功!"),
    DELETE_FILE_SUCCESS(1006,"删除成功!"),
    DELETE_FILE_FAIL(1007,"重命名成功!")
    ;

    private Integer code;//状态码
    private String msg;//状态描述

    private EnumErrorMsg(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
    /**
     *@author aniu
     *@Description 根据编码查找枚举
     *@Date 10:39 2018/5/9
     *
     *
     *@Param [code]
     *@return org.chwin.iot.apiserver.iot.enums.EnumMsgCode
     **/
    public static EnumErrorMsg find(int code) {
        for (EnumErrorMsg eec: EnumErrorMsg.values()) {
            if(eec.getCode()==code) {
                return eec;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

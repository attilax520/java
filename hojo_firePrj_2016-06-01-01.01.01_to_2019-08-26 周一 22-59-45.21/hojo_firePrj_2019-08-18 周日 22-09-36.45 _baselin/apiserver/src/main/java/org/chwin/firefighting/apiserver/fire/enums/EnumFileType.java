package org.chwin.firefighting.apiserver.fire.enums;

/**
 * @ClassName EnumFileType
 * @Description 确定上传文件 为视频文件、文本文件、图片、压缩包
 * @Author aniu
 * @Date 2018/8/3 11:26
 * @Version 1.0
 **/
public enum EnumFileType {
    TYPE_01("video/mp4","",3),//0：照片 1：文本 2：压缩包 3：视频 4:音频
    TYPE_02("application/octet-stream","",1),
    TYPE_03("application/x-zip-compressed","",2),
    TYPE_04("application/pdf","",1),
    TYPE_05("text/plain","",1),
    TYPE_06("text/html","",1),
    TYPE_07("application/octet-stream","",1),
    TYPE_08("image/jpeg","",0),
    TYPE_09("application/msword","",1),
    TYPE_10("image/png","",0),
    TYPE_11("image/gif","",0),
//    TYPE_12("text/html","",1),
    TYPE_13("image/x-icon","",0),
    TYPE_14("application/x-jpg","",0),
    TYPE_15("video/mpeg4","",3),
    TYPE_16("application/vnd.ms-powerpoint","",1),
    TYPE_17("application/x-ppt","",1),
    TYPE_18("application/x-xls","",1),
    TYPE_19("text/xml","",1),
    TYPE_20("image/tiff","",0),
    TYPE_21("video/avi","",3),
    TYPE_22("audio/mp3","",4),
    TYPE_23("application/vnd.visio","",1),
    TYPE_24("application/vnd.visio","",1),
    TYPE_25("application/vnd.ms-excel","",1),
    TYPE_26("application/ppt","",1),
    TYPE_27("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","",1),
    TYPE_28("application/docx","",1),
    TYPE_29("application/vnd.ms-excel","",1),
    TYPE_30("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","",1),
    TYPE_31("application/vnd.openxmlformats-officedocument.wordprocessingml.document","",1)
    ;
    private String type;//文件类型
    private String code;//16进制 文件流中文件类型
    private int flag;//文件标记

    public static EnumFileType find(String type) {
        for (EnumFileType des: EnumFileType.values()) {
            if(des.getType().equals(type)) {
                return des;
            }
        }
        return null;
    }

    EnumFileType(String type, String code,int flag) {
        this.type = type;
        this.code = code;
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

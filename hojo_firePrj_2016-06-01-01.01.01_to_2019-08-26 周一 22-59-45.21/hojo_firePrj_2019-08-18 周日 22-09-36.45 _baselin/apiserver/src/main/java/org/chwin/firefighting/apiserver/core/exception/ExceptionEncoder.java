package org.chwin.firefighting.apiserver.core.exception;

import java.sql.SQLException;

import org.chwin.firefighting.apiserver.core.util.BeanUtil;

/**
 * Created by liming on 2017/9/28.
 */
public class ExceptionEncoder {

    public static <T> T findTypeException(Throwable ex,Class<T> clz){
        if(BeanUtil.isInstance(ex, clz)){
            return (T)ex;
        }

        Throwable cause = ex.getCause();
        if(cause!=null)
            return findTypeException(cause,clz);
        return null;
    }

    public static String msg(Throwable ex){
        String exceptmsg = ex.getMessage();

        if(BeanUtil.isInstance(ex, SQLException.class)){
            return parserSqlException((SQLException)ex);
        }

        Throwable cause = ex.getCause();
        if(cause!=null)
            return msg(cause);

        return exceptmsg;
    }

    private static String parserSqlException(SQLException ex){
        String sqlstate=ex.getSQLState();
        Long errorcode=new Long(sqlstate);
        switch (errorcode.intValue()){
            case 42501:
                return "权限不够";
            case 23503:
                return "该信息已经被其他业务引用，不能删除!!!";
            case 23505:
                return "该记录中的某些信息违反了唯一性原则，请检查!!!";
            case 42830:
                return "外键异常，请检查!!!";
            case 22026:
                return "输入字段过长,请检查!!!";
            default:
                return sqlstate+"<br/>ex.getMessage()";
        }
    }
}

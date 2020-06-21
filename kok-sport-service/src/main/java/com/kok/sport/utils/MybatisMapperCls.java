package com.kok.sport.utils;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
 
// com.kok.sport.utils.MybatisMapperCls
@Mapper 
//mybatis346pkg.MybatisMapperCls

public interface MybatisMapperCls {
	
	
	//@Autowired
	//public	SqlSessionTemplate sqlSessionTemplate1;
    
    /*
     * ���ǻ���ע���ӳ�䷽ʽ��ʵ�ֶ����ݵ���ɾ�Ĳ飬��sql���ֱ��д��ע���������
     * ����һ���ӿڣ��䲻��Ҫ��ȥʵ����
     * �±߷ֱ��ǲ��룬ɾ�����޸ģ���ѯһ����¼����ѯ���еļ�¼
     * */
    
 
    
//    @Select("${sql_intag}")
//    public List<Map>   query(@Param("sql_intag") String sql);
//    
//    @Insert("${sql_intag}")
//    public int   insert(@Param("sql_intag") String sql);
    
    @Update("${sql_intag}")
    public int   updateV2(@Param("sql_intag") String sql);
    
    @Select("${sql_intag}")
	public List<LinkedHashMap> querySqlV2(@Param("sql_intag") String sql);
    
    @Select("${sql_intag}")
	 
	public Object executeSqlRetObj(@Param("sql_intag") String sql);
    
}
package com.kok.sport.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//  com.kok.sport.utils.utils.MybatisMapper
@Mapper
public interface MybatisMapper {

	//
	@Select("select ${select} from ${from} ${join}   ${where} ${group} ${HAVIN} ${order}  ${limit}")
	List<LinkedHashMap> query(Map m);

//	 

	@Select("${sql_intag}")
	public List<LinkedHashMap> querySql(@Param("sql_intag") String sql);
	
	//mayhbe multi sql or call sp
	@Select("${sql_intag}" )
	@ResultMap({"/.rm","/.rm2","rm3","rm4","rm5"})
	public List<List<LinkedHashMap>> querySqlMultiRs(@Param("sql_intag") String sql);


	@Insert("${sql_intag}")
	public int insert(@Param("sql_intag") String sql);
	
	@Deprecated
	@Insert(" insert into ${tab}( ${cols})values( ${vals}) ")
	public int insertBymap(Map m);
	
	@Insert(" insert into ${tab} set ${set} ")
	public int insertBymapV2(Map m);
	
	@Insert(" replace into ${tab} set ${set} ")
	public int replaceBymapV2(Map m);
	
	@Update("update ${tab} set ${setList} where ${where} ")
	public int updateByMap(Map m);
	
	@Update("update ${tab} set ${set} where ${where} ")
	public int updateByMapV2(Map m);
	
//	@Update("update ${tab} set ${set} where ${where} ")
//	public int updateByMapV2(Map m);

	@Select("call  ${call}('${jsonparam}') ")
	public List<LinkedHashMap> call(Map m);
	
	
//	@Select(" call  ${call}('${jsonparam}')  " )
//@ResultMap({"rm","rm2","rm3","rm4","rm5"})
//	public List<List<LinkedHashMap>> callV2(Map m);

	
	@Update("${sql_intag}")
	public int update(@Param("sql_intag") String sql);

	@Select("select * from ${tab} where 1=1 and ${condFld} in (${condFldVals}) ${limit}")
	List<Map> queryByFld(Map m);

 

 
}
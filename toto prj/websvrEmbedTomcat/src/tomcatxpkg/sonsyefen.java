package tomcatxpkg;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSON;
import com.attilax.db.dbutilUtil;
import com.attilax.util.OfficePoiExcelUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javassist.expr.NewArray;
import ognl.Ognl;
import ognl.OgnlException;

@SuppressWarnings("all")
public class sonsyefen {
	final static Logger log = Logger.getLogger(sonsyefen.class);

	// 1888888,50,10000
	static	String dbcfg = "pre";
	final static String nameCellIndex = "a";
	final static String telCellIndex = "b";
	final static String scoreIndex = "c";

	private static String nowScoreIndex;
	static String ymlString = "G:\\0ttapi\\tt-api\\com-tt-yxt\\src\\main\\resources\\application.yml";
	public static void main(String[] args) throws Exception {

		log.info("--start");

//		String tel = "18932016672";
//		float nowScore = 4900;
//		float addScore = 20;
		// 也可以将值转换为Map
//        Map map =(Map)yaml.load(new FileInputStream(url.getFile()));
//        System.out.println(map);
		// 通过map我们取值就可以了.

		
		DataSource datasouce = getDatasource(ymlString);
	//	String filePath = "C:\\Users\\zhoufeiyue\\Desktop\\0319猜谜语活动奖励v2.xlsx";

	//	main_xlsx(datasouce, filePath);
		String cpsString="15100043854,15755292910,15080613325,15251049740,13965281709,13386289159,13862621937";
		String[] cps=cpsString.split(",");
		for (String tel : cps) {
		    System.out.println("---------------------------------------------------");
		    log.info("---------------------------------------------------");
			float addScore=1000;
			try {
				processSingle(tel, addScore, datasouce);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}

	}

	private static void main_xlsx(DataSource datasouce, String filePath) throws IOException, FileNotFoundException {
		XSSFWorkbook XSSFWorkbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));

		XSSFSheet sheet = XSSFWorkbook1.getSheetAt(0);

		Integer rowNum;

		List li = Lists.newArrayList();
		//
		for (rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
			try {
				XSSFRow XSSFRow1 = sheet.getRow(rowNum);

				String name = OfficePoiExcelUtil.getCellValueStringModeForce(XSSFRow1, nameCellIndex);
				
				String tel = OfficePoiExcelUtil.getCellValueStringModeForce(XSSFRow1, telCellIndex);
				float addScore;
				Float  nowScore =null;
				// if first line ,skip
				
				try {
					addScore = parseFloat(XSSFRow1);
				} catch (Exception e) {
					continue;
				}
				try {
					
					nowScore = Float
							.parseFloat(OfficePoiExcelUtil.getCellValueStringModeForce(XSSFRow1, nowScoreIndex));

				} catch (Exception e) {

//					Map debugMap = Maps.newConcurrentMap();
//					debugMap.put("name", name);
//					debugMap.put("tel", tel);
//					log.warn(JSON.toJSONString(debugMap), e);
//					continue;

				}

				log.info("  now will to exe process,readfrom excel info is ::: name:" + name + " tel:" + tel
						+ " addscore:" + addScore + " nowscore:" + nowScore);
				if (nowScoreIndex == null)
					processSingle(tel, addScore, datasouce);
				else
					processSingle(tel, nowScore, addScore, datasouce);

				log.info("----------------------------------------------");
			} catch (Exception e) {
				log.error("", e);
			}

		}
	}

	private static float parseFloat(XSSFRow XSSFRow1) {

		return Float.parseFloat(OfficePoiExcelUtil.getCellValueStringModeForce(XSSFRow1, scoreIndex));
	}

	private static void processSingle(String tel, float addScore, DataSource datasouce) throws SQLException {
		Map map_rztMap = Maps.newConcurrentMap();

		// get ucode
		Map<String, Object> map2_getucode = getUcodeMap(tel, datasouce);
		String user_code = map2_getucode.get("code").toString();

		// String judeScoreTotalNow = ;
		// judeScore(nowScore, datasouce, user_code);

		// insert item
		insert_item_data(tel, addScore, user_code, datasouce, map_rztMap);

		syso("select uc.user_code,uc.total_credit,u.user_name,u.cell_phone from i_user_credit  uc left join a_user u on uc.user_code=u.code where    user_code='"
				+ user_code + "'", datasouce);
		upScorex(addScore, user_code, datasouce, map_rztMap);
		System.out.println(JSON.toJSONString(map_rztMap));

		// syso("select * from i_user_credit where
		// user_code='"+user_code+"'",datasouce);
		syso("select uc.user_code,uc.total_credit,u.user_name,u.cell_phone from i_user_credit  uc left join a_user u on uc.user_code=u.code where    user_code='"
				+ user_code + "'", datasouce);
	}

	private static void processSingle(String tel, float nowScore, float addScore, DataSource datasouce)
			throws SQLException {
		Map map_rztMap = Maps.newConcurrentMap();

		// get ucode
		Map<String, Object> map2_getucode = getUcodeMap(tel, datasouce);
		String user_code = map2_getucode.get("code").toString();

		// String judeScoreTotalNow = ;
		judeScore(nowScore, datasouce, user_code);

		// insert item
		insert_item_data(tel, addScore, user_code, datasouce, map_rztMap);

		upScorex(addScore, user_code, datasouce, map_rztMap);
		System.out.println(JSON.toJSONString(map_rztMap));

		// syso("select * from i_user_credit where
		// user_code='"+user_code+"'",datasouce);
		syso("select uc.user_code,uc.total_credit,u.user_name,u.cell_phone from i_user_credit  uc left join a_user u on uc.user_code=u.code where    user_code='"
				+ user_code + "'", datasouce);
	}

	private static void syso(String sql, DataSource datasouce) throws SQLException {
		log.info(sql);
		System.out.println(JSON.toJSONString(dbutilUtil.execSqlQuery(sql, datasouce)));

	}

	public static DataSource getDatasource(String ymlString)
			throws FileNotFoundException, OgnlException, PropertyVetoException {
		Map m = getTestCfg(ymlString);

		return getDatasource(m);
	}

	private static DataSource getDatasource(Map m) throws OgnlException, PropertyVetoException {
		// 非根节点取值需要#开头
		Object expression = Ognl.parseExpression("spring.datasource");

		Object value = Ognl.getValue(expression, m); // Ognl.getValue(expression);
		System.out.println(value);
		String url = (String) Ognl.getValue(Ognl.parseExpression("spring.datasource.url"), m);
		String u = (String) Ognl.getValue(Ognl.parseExpression("spring.datasource.username"), m);
		String p = Ognl.getValue(Ognl.parseExpression("spring.datasource.password"), m).toString();
		DataSource datasouce = DBPoolC3p0Util.getDatasouce("com.mysql.jdbc.Driver", url, u, p);
		return datasouce;
	}

	private static void upScorex(float addScore, String user_code, DataSource datasouce, Map map_rztMap)
			throws SQLException {
		String upScore = "update  i_user_credit set total_credit=total_credit+ $addscore where  user_code='$user_code'";

		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();

		context.put("user_code", user_code);
		context.put("addscore", addScore);
		upScore = getTmpltCalcRzt(upScore, context);
		// upScore = MessageFormat.format(upScore, addScore, user_code);
		System.out.println(upScore);
		QueryRunner qr4 = new QueryRunner(datasouce);
		int upScore_rzt = qr4.update(upScore);
		map_rztMap.put("up user creadit rzt", upScore_rzt);
	}

	private static void insert_item_data(String tel, float addScore, String user_code, DataSource datasouce,
			Map map_rztMap) throws SQLException {
		String addItemSqlString = "insert i_user_credit_item("
				+ "code,user_code,credit_way_code,credit_value,create_on,create_by)"
				+ "values('$code','$user_code','008',$credit_value,'$create_on','ati'" + ")\r\n" + "";

		String content_str = addItemSqlString;
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();

		// 把数据填入上下文
		context.put("name", "javaboy2012");

		context.put("date", (new Date()).toString());
		context.put("create_on", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		context.put("code", tel);
		context.put("user_code", user_code);
		context.put("credit_value", addScore);

		String rzt = getTmpltCalcRzt(content_str, context);
		System.out.println(rzt);
		QueryRunner qr3 = new QueryRunner(datasouce);

		int insert_item_rzt = qr3.update(rzt);

		map_rztMap.put("insert_item_rzt", insert_item_rzt);

	}

	public static String getTmpltCalcRzt(String content, VelocityContext context) {
		// 初始化并取得Velocity引擎
		VelocityEngine ve = new VelocityEngine();
		ve.init();

		// 取得velocity的模版内容, 模板内容来自字符传

		// 输出流
		StringWriter writer = new StringWriter();

		// 转换输出

		ve.evaluate(context, writer, "", content); // 关键方法

		String rzt = writer.toString();
		return rzt;
	}

	private static void judeScore(float nowScore, DataSource datasouce, String ucodecode) throws SQLException {
		QueryRunner qr2 = new QueryRunner(datasouce);
		String getScoreTotal = "select i_user_credit.*,u.cell_phone,u.user_name from i_user_credit  left join a_user u on i_user_credit.user_code=u.code where user_code='"
				+ ucodecode + "'";
		System.out.println(getScoreTotal);
		Map<String, Object> map22 = qr2.query(getScoreTotal, new MapHandler());
		System.out.println(map22);
		if (!((Float) map22.get("total_credit")).equals(nowScore)) {
			Map<String, Object> m2 = new HashMap<>();
			m2.put("username", map22.get("user_name"));
			m2.put("cell_phone", map22.get("cell_phone"));
			m2.put("nowScoreFromInput", nowScore);
			m2.put("nowscoreFrmDb", map22.get("total_credit"));
			throw new RuntimeException(JSON.toJSONString(m2));
		}
	}

	public static Map<String, Object> getUcodeMap(String tel, DataSource datasouce) throws SQLException {
		Map<String, Object> map2;
		// ,13984141202,18931195812
		Map<String, String> map = new HashMap<>();

		map.put("expressDeliveryType", "sf");
		map.put("tel", "18932016672");
		map.put("addScore", "20");
		map.put("nowScore", "10000");
		String getUCodeSqlString = "select  *,code from a_user where cell_phone in(  {0}  ) order by cell_phone";

		getUCodeSqlString = MessageFormat.format(getUCodeSqlString, tel);

		log.info(getUCodeSqlString);
		System.out.println(getUCodeSqlString);

		QueryRunner qr = new QueryRunner(datasouce);
//		List<Map<String, Object>> list = qr.query(getUCodeSqlString, new MapListHandler());
//		System.out.println(JSON.toJSONString(list, true));
		map2 = qr.query(getUCodeSqlString, new MapHandler());
		System.out.println(map2);
		log.info(map2);
		return map2;
	}

	public static Map getTestCfg(String ymlString) throws FileNotFoundException {
		// ymlSingledoc(ymlString);

//		loadall spring.profiles.active
//		在一个yaml文件中可以存入多组配置并使用loadAll进行读取，多组之间使用三个横杠分开
//
//		    @Test
//		    public void loadall() throws FileNotFoundException {

		Map m = YmlUtil.getDoc(ymlString, new Predicate<Map>() {

			@Override
			public boolean test(Map m) {
				// can use ognl improve
//				Map spring = (Map) t.get("spring");
//				if (spring.get("profiles").equals("test"))
//					return true;
				try {
					Object expression = Ognl.parseExpression("spring.profiles");
					Object value = Ognl.getValue(expression, m);
					if (value == null)
						return false;
					
					if (value.equals(dbcfg))
						return true;
				} catch (OgnlException e) {
					e.printStackTrace();
					// if cont contain this key ,then continue
					// throw new RuntimeException(e);
				}
				return false;
			}
		});
		if(m.keySet().size()==0)
			throw new RuntimeException("YmlUtil.getDoc getTestCfg ex, actProfile:"+dbcfg);
		return m;
	}

	private static void ymlSingledoc(String ymlString) throws FileNotFoundException {
		// ymlString="H:\\gitWorkSpace\\tomcatx\\t.yml";
		org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
		// Object mObject=yaml.load(sonsyefen.class.getResourceAsStream("/test.yml"));
		Object mObject = yaml.load(new FileInputStream(ymlString));
		System.out.println(JSON.toJSONString(mObject, true));
		// TestEntity testEntity =
		// yaml.loadAs(sonsyefen.class.getResourceAsStream("/test.yml"),
		// TestEntity.class);//如果读入Map,这里可以是Mapj接口,默认实现为LinkedHashMap
	}

}
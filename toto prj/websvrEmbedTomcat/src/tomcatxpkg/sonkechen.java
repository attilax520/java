package tomcatxpkg;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;

import com.attilax.util.IGenerateIdService;
import com.attilax.util.velocityUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import ognl.OgnlException;

public class sonkechen {

	//gift kechen
	public static void main(String[] args) throws OgnlException, PropertyVetoException, SQLException, IOException {

		sonsyefen.dbcfg = "prod";
		  t2();

	//	shequGiftKechen();
	}

	private static void shequGiftKechen()
			throws FileNotFoundException, OgnlException, PropertyVetoException, SQLException, IOException {
		String lookKechenString = "";

	//	lookKechenString = "SELECT course_package_code from a_user_course_package where user_code = '298939024310145024' and is_delete = 0";
		lookKechenString= "SELECT course_package_code from a_user_course_package where user_code = '316321395355291648' and is_delete = 0";
		DataSource datasouce = sonsyefen.getDatasource(sonsyefen.ymlString);
		List<Map<String, Object>> kechens = getKechen(lookKechenString, datasouce);

		String telsString ="";
				//"13477056466,15972930008,13477056466,15377659536,18971681282,15272266966,15107102259,15107153281,15172105146,15527049531,15107153281";
	
		telsString="15802706992,15377659536,13607176558,15802706992";
		String[] ta = telsString.split(",");
		for (String tel : ta) {
			List li = sonkechen_singleUserBytel(datasouce, kechens, tel); // tel
			String string = Joiner.on("\r\n").join(li);
			FileUtils.write(new File("g:\\course_package_423_aa2.sql.txt"), string + "\r\n", true);
		}
	}

	private static void t2()
			throws FileNotFoundException, OgnlException, PropertyVetoException, SQLException, IOException {
		String lookKechenString = "SELECT course_package_code, opening_days FROM a_user_course_package WHERE is_delete = 0 AND user_code = '298939024310145024' AND is_partner = 1";

		// lookKechenString=" SELECT code from a_user_course_package where user_code =
		// '298939024310145024' and is_delete = 0 ";

		lookKechenString = "SELECT course_package_code from a_user_course_package where user_code = '298939024310145024' and is_delete = 0";

		DataSource datasouce = sonsyefen.getDatasource(sonsyefen.ymlString);
		List<Map<String, Object>> kechens = getKechen(lookKechenString, datasouce);

		String telsString = "18971675928";
		String[] ta = telsString.split(",");
		for (String tel : ta) {
			List li = sonkechen_singleUserBytel(datasouce, kechens, tel); // tel
			String string = Joiner.on("\r\n").join(li);
			FileUtils.write(new File("g:\\course_package_423.sql.txt"), string + "\r\n", true);
		}

		// songkech(datasouce, kechens);
		// String tel = "305836518860263424";

//		String sql="select * from a_user where school like '%光谷%十%' or school like '%光谷分校%'";
//		List<Map<String, Object>> users=query(sql, datasouce);
//		
//		for (Map<String, Object> u : users) {
//			List li=sonkechen_singleUserByucode(kechens,u.get("code").toString());
//			String string= Joiner.on("\r\n").join(li);
//			FileUtils.write(new File("g:\\course_package.sql.txt"), string+"\r\n", true);
//		}
		// sonkechen_singleUserBytel(datasouce, kechens, "13120597435");
		// sonkechen_singleUser(kechens, "305836518860263424");
	}

	private static List sonkechen_singleUserBytel(DataSource datasouce, List<Map<String, Object>> kechens, String tel)
			throws SQLException {
		Map uMap = sonsyefen.getUcodeMap(tel, datasouce);
		String ucode = (String) uMap.get("code");// "305837127227281408";

		return sonkechen_singleUser(kechens, ucode);
	}

	private static List sonkechen_singleUserByucode(List<Map<String, Object>> kechens, String ucode) {
		List list = Lists.newLinkedList();
		for (Map<String, Object> kc : kechens) {
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			context.put("kechenbaoNo", kc.get("course_package_code"));

			context.put("tiyeTyeshu", 100);

			context.put("usercode", ucode);
			context.put("cyvdaosheo", "298939024310145024");
			context.put("uuid", UUID());
			String iString = "INSERT INTO a_user_course_package VALUES"
					+ " (NULL,$uuid , '$usercode', '${kechenbaoNo}', $tiyeTyeshu,"
					+ "	NOW(), DATE_ADD(NOW(),INTERVAL $tiyeTyeshu day), 0, 0, 0,"
					+ "NOW(), 'adminx', NOW(), 'adminx', 0,0) ;";

//			INSERT into a_user_course_package VALUES
//			(null,${code},${userCode},${coursePackageCode},100
//					,NOW(),	date_add(NOW(), interval 100 day),0,0,0,
//					NOW(),'adminx',NOW(),'adminx',0,0),

			String sqlString = velocityUtil.getTmpltCalcRzt(iString, context);
			System.out.println(sqlString);
			list.add(sqlString);

			// INTERVAL 10 day
		}
		return list;
	}

	private static List sonkechen_singleUser(List<Map<String, Object>> kechens, String ucode) {
		List list = Lists.newArrayList();
		for (Map<String, Object> kc : kechens) {
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			context.put("kechenbaoNo", kc.get("course_package_code"));

			context.put("tiyeTyeshu", 100);
			// context.put("create_on", new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss").format(new Date()));

			// context.put("用户编号", mapU.get("code")); //cnese zichi bhao

			context.put("usercode", ucode);
			context.put("cyvdaosheo", "298939024310145024");
			context.put("uuid", UUID());
			String iString = "INSERT INTO a_user_course_package VALUES (NULL,$uuid , '$usercode', '${kechenbaoNo}', $tiyeTyeshu,"
					+ "	NOW(), DATE_ADD(NOW(),INTERVAL $tiyeTyeshu day), 0, 0, 0, '$cyvdaosheo', NOW(), '$cyvdaosheo', NOW(), 0,0) ;";

//			INSERT into a_user_course_package VALUES
//			(null,${code},${userCode},${coursePackageCode},100,NOW(),
//					date_add(NOW(), interval 100 day),0,0,0,NOW(),'adminx',NOW(),'adminx',0,0),

			String sqlString = velocityUtil.getTmpltCalcRzt(iString, context);
			System.out.println(sqlString);
			// INTERVAL 10 day
			list.add(sqlString);

		}
		return list;
	}

	private static void songkech(DataSource datasouce, List<Map<String, Object>> kechens) throws SQLException {
		String userSqlString = "SELECT * FROM a_user where create_on>'2019-03-20 20:22:58' limit 30";
		QueryRunner qr2 = new QueryRunner(datasouce);
		List<Map<String, Object>> li2 = qr2.query(userSqlString, new MapListHandler());
		for (Map<String, Object> mapU : li2) {
			for (Map<String, Object> kc : kechens) {
				VelocityContext context = new VelocityContext();
				// 把数据填入上下文
				context.put("kechenbaoNo", kc.get("course_package_code"));

				context.put("tiyeTyeshu", 10);
				// context.put("create_on", new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss").format(new Date()));

				context.put("用户编号", mapU.get("code")); // cnese zichi bhao
				context.put("usercode", mapU.get("code"));
				context.put("cyvdaosheo", "298939024310145024");
				context.put("uuid", UUID());
				String iString = "INSERT INTO a_user_course_package VALUES (NULL,$uuid , '$usercode', '${kechenbaoNo}', $tiyeTyeshu,"
						+ "	NOW(), DATE_ADD(NOW(),INTERVAL $tiyeTyeshu day), 0, 0, 0, '$cyvdaosheo', NOW(), '$cyvdaosheo', NOW(), 0,0) ;";
				String sqlString = velocityUtil.getTmpltCalcRzt(iString, context);
				System.out.println(sqlString);
				// INTERVAL 10 day
			}
		}
	}

	private static Object UUID() {
		IGenerateIdService service = new IGenerateIdService();
		String code = service.generateKey();
		return code;
	}

	private static List<Map<String, Object>> query(String sql, DataSource datasouce) throws SQLException {
		QueryRunner qr = new QueryRunner(datasouce);
//		List<Map<String, Object>> list = qr.query(getUCodeSqlString, new MapListHandler());
//		System.out.println(JSON.toJSONString(list, true));
		List<Map<String, Object>> li = qr.query(sql, new MapListHandler());
		System.out.println(li);
		return li;
	}

	private static List<Map<String, Object>> getKechen(String lookKechenString, DataSource datasouce)
			throws SQLException {
		QueryRunner qr = new QueryRunner(datasouce);
//		List<Map<String, Object>> list = qr.query(getUCodeSqlString, new MapListHandler());
//		System.out.println(JSON.toJSONString(list, true));
		List<Map<String, Object>> li = qr.query(lookKechenString, new MapListHandler());
		System.out.println(li);
		return li;
	}

}

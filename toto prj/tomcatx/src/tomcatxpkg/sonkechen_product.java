package tomcatxpkg;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.velocity.VelocityContext;

import com.attilax.util.IGenerateIdService;
import com.attilax.util.velocityUtil;

import ognl.OgnlException;

public class sonkechen_product {

	public static void main(String[] args)
			throws FileNotFoundException, OgnlException, PropertyVetoException, SQLException {

		sonsyefen.dbcfg = "prod";
		DataSource datasouce = sonsyefen.getDatasource(sonsyefen.ymlString);
		// t();

		String tel = "13965281709";
		String kechenNoProdNo = "300986995755323392";
		songkechen_usertel_prodno(tel, kechenNoProdNo, datasouce);
	}

	private static void songkechen_usertel_prodno(String tel, String kechenNoProdNo, DataSource datasouce)
			throws SQLException {
		Map uMap = sonsyefen.getUcodeMap(tel, datasouce);
		String ucode = (String) uMap.get("code");// "305837127227281408"

		// String ucode="311482585811390464";
		songkechen_usercode_prodno(ucode, kechenNoProdNo);
	}

	private static void t() throws FileNotFoundException, OgnlException, PropertyVetoException, SQLException {
		String lookKechenString = "SELECT course_package_code, opening_days FROM a_user_course_package WHERE is_delete = 0 AND user_code = '298939024310145024' AND is_partner = 1";

		lookKechenString = "	SELECT code from a_user_course_package where user_code = '298939024310145024' and is_delete = 0 ";
		DataSource datasouce = sonsyefen.getDatasource(sonsyefen.ymlString);
		List<Map<String, Object>> kechens = getKechen(lookKechenString, datasouce);

		// songkech(datasouce, kechens);
		String tel = "305836518860263424";
		sonkechen_singleUserBytel(datasouce, kechens, "13120597435");
		// sonkechen_singleUser(kechens, "305836518860263424");
	}

	private static void sonkechen_singleUserBytel(DataSource datasouce, List<Map<String, Object>> kechens, String tel)
			throws SQLException {
		Map uMap = sonsyefen.getUcodeMap(tel, datasouce);
		String ucode = (String) uMap.get("code");// "305837127227281408";

		sonkechen_singleUser(kechens, ucode);
	}

	private static void sonkechen_singleUser(List<Map<String, Object>> kechens, String ucode) {
		for (Map<String, Object> kc : kechens) {

			String kechenNoProdNo = "123445";

			songkechen_usercode_prodno(ucode, kechenNoProdNo);
			// INTERVAL 10 day
		}
	}

	private static void songkechen_usercode_prodno(String ucode, String kechenNoProdNo) {
		VelocityContext context = new VelocityContext();

		context.put("usercode", ucode);

		Object orderid_code = UUID();
		context.put("uuid", orderid_code);
		String iString = "	INSERT INTO `o_order` ( `code`, `user_code`, `amount`, `pay_type`, `pay_status`, `wx_order_id`,"
				+ " `create_on`, `create_by`, `last_update_on`, `last_update_by`, `version`, `is_delete`, `coupon_code`) "
				+ "VALUES ('$uuid','$usercode',0,1,1,'000'," + "NOW(),'jerr.wang',NOW(),'jerry.wang',0,0,NULL);";
		String sqlString = velocityUtil.getTmpltCalcRzt(iString, context);

		System.out.println(sqlString);
		VelocityContext context_item = new VelocityContext();
		context_item.put("order_item_code", UUID());
		context_item.put("uuid", orderid_code);
		context_item.put("kechenNoProdNo", kechenNoProdNo);
		String itemString = "	INSERT INTO `o_order_item` ( `code`, `order_code`, `product_code`, `is_delete`, `tmp_grade`) "
				+ "VALUES ('$order_item_code','$uuid','$kechenNoProdNo',0,6);";
		itemString = velocityUtil.getTmpltCalcRzt(itemString, context_item);
		System.out.println(itemString);
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

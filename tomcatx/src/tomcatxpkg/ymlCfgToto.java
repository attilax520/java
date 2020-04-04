package tomcatxpkg;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.function.Predicate;

import javax.sql.DataSource;

import org.yaml.snakeyaml.Yaml;

import ognl.Ognl;
import ognl.OgnlException;

public class ymlCfgToto {
	static String ymlString = "H:\\gitCode\\tt-api\\com-tt-yxt\\src\\main\\resources\\application.yml";
	public static void main(String[] args) throws FileNotFoundException, OgnlException, PropertyVetoException {
		
	//	String ymlString;
		String getActiveProfileString=getActiveProfile(ymlString);
        Map actMap=getActCfg(ymlString,  getActiveProfileString);
        System.out.println(getDatasource(actMap));
		
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

	
	private static String getActiveProfile(String ymlString) throws OgnlException, FileNotFoundException {
		Yaml yaml = new Yaml();
	 
		Iterable<Object> result = yaml.loadAll(new FileInputStream((String) ymlString));
		for (Object obj : result) {
			Map fstMap=(Map) obj;
			Object expression = Ognl.parseExpression("spring.profiles.active");
			String value = (String) Ognl.getValue(expression, fstMap);
			return value;
		}
		throw new RuntimeException("getActiveProfileEx");
	}


	public static Map getActCfg(String ymlString,String actProfile ) throws FileNotFoundException {
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
					
					if (value.equals(actProfile))
						return true;
				} catch (OgnlException e) {
					e.printStackTrace();
					// if cont contain this key ,then continue
					// throw new RuntimeException(e);
				}
				return false;
			}
		});
		return m;
	}
}

package com.kok.sport.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.google.common.base.Joiner;
import com.kok.sport.utils.db.MybatisUtil;

//import groovy.lang.Closure;
//import groovy.transform.stc.ClosureParams;
//import groovy.transform.stc.FromString;
//import groovy.transform.stc.PickFirstResolver;
@SuppressWarnings("all")
public class XmlUtilAti {

	public static void main(String[] args) throws  Exception {
		// org.testng.xml.XmlUtils
		// ch.qos.logback.core.joran.spi.XMLUtil XMLUtil.
		// import com.aliyuncs.utils.XmlUtils; XmlUtils.
		// import org.testng.xml.XmlUtils; XmlUtils. //
		// com.sun.org.apache.xml.internal.security.utils.XMLUtils
		// com.sun.xml.internal.ws.util.xml.XmlUtil.
		// only blow can ok has escape method
		// org.testng.reporters.XMLUtils.escape(input)
		System.out.println(cn.hutool.core.util.XmlUtil.escape("aaa=1&bbb=2"));
		;
		// url = XmlUtil.escapeXml(url.toString() + "&allowMultiQueries=true");
		// cant com.sun.xml.internal.ws.util.xml.XmlUtil
		// cant .com.aliyuncs.utils.XmlUtils
		// XMLUtils. com.sun.javaws.jnl.XMLUtils
		// com.sun.org.apache.xml.internal.security.utils.XMLUti
		Configuration Configuration1=	MybatisUtil.getConn().getConfiguration();
		
		String xmlf = "D:\\prj\\sport-service\\kok-sport-service\\src\\main\\resources\\mapper\\FootballLiveMatchdetailliveDao.xml";
		String xpath = "/update";
		// jdom
		SAXBuilder sb = new SAXBuilder();
		org.jdom.Document doc = sb.build(xmlf);
	 
		Element root = (Element) doc.getRootElement();
		
		
		String sttID="insert_into_football_incident_t";
		System.out.println( parseStt(sttID,root));
		

		// selectSingleNode /text()
		// doc./text() [@id=insert_into_football_incident_t]

		// dom4j 声明SAXReader
//    SAXReader saxReader = new SAXReader();  
//  	Document doc = saxReader.read(xmlf);  
//    // 获得所有grade=1的Element的text的值  

//    List list = doc.selectNodes(xpath);
		System.out.println(root);

	}

	private static String parseStt(String id, Element root) throws Exception {
		String pathFltById = "/mapper/*[@id='{0}']";
	//	pathFltById = MessageFormat.format(pathFltById, id );
		pathFltById=pathFltById.replaceAll("\\{0\\}", id);
		Object sql = XPath.selectNodes(root, pathFltById);
		Element stt = (Element) XPath.selectSingleNode(root, pathFltById);
		List li = stt.getContent();
	//	System.out.println();
		return parseSttContestList(li, root);
	}

	private static String parseSttContestList(List li, Element root) throws Exception {

		List li_rzt = (List) li.stream().map(o -> {
			if (o instanceof Text) {
				return ((Text) o).getValue();
			}
			if (o instanceof Element) {
				Element o2 = (Element) o;
				if (o2.getName() == "include") {
					String refid = o2.getAttribute("refid").getValue();
					return ParseInclude(refid, root);
				}
			}
//			  return 0;
			return "";

		}).collect(Collectors.toList());
		return Joiner.on(" ").join(li_rzt);
	}

	private static Object ParseInclude(String refid, Element root) {
		try {
			// MessageFormat.format("我是{0}，我来自{1}", "中国人", "中国");
			String pathFltById = "/mapper/sql[@id='{0}']/text()";
			pathFltById=pathFltById.replaceAll("\\{0\\}", refid);
		//	pathFltById = MessageFormat.format(pathFltById, refid, "中国");
			//msg fomt cant use in xpath...bcz single quore is missed..
			Text sql;

			sql = (Text) XPath.selectSingleNode(root, pathFltById);
			return sql.getValue();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Escape the following characters {@code " ' & < >} with their XML entities,
	 * e.g. {@code "bread" & "butter"} becomes
	 * {@code &quot;bread&quot; &amp; &quot;butter&quot}. Notes:
	 * <ul>
	 * <li>Supports only the five basic XML entities (gt, lt, quot, amp, apos)</li>
	 * <li>Does not escape control characters</li>
	 * <li>Does not support DTDs or external entities</li>
	 * <li>Does not treat surrogate pairs specially</li>
	 * <li>Does not perform Unicode validation on its input</li>
	 * </ul>
	 *
	 * @param orig the original String
	 * @return A new string in which all characters that require escaping have been
	 *         replaced with the corresponding XML entities.
	 * @see #escapeControlCharacters(String)
	 */
//    public static String escapeXml(String orig) {
//        return collectReplacements(orig, new Closure<String>(null) {
//            public String doCall(Character arg) {
//                switch (arg) {
//                    case '&':
//                        return "&amp;";
//                    case '<':
//                        return "&lt;";
//                    case '>':
//                        return "&gt;";
//                    case '"':
//                        return "&quot;";
//                    case '\'':
//                        return "&apos;";
//                }
//                return null;
//            }
//        });
//    }

}

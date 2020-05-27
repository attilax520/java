package com.kok.sport.utils.mockdata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.xml.sax.InputSource;

import com.google.common.collect.Lists;

public class mybatisXmlParser {
	
	/**
	 * 
	 * <mappers> 
    <!-- 使用相对于类路径的资源引用 -->
  <mapper resource="org/mybatis/example/BlogMapper.xml"/> 
    <!-- 使用完全限定资源定位符（URL） -->
     <mapper url="file:///var/mappers/BlogMapper.xml"/>
    <!-- 使用映射器接口实现类的完全限定类名 -->
    <mapper class="org.mybatis.builder.BlogMapper"/>
    <!-- 将包内的映射器接口实现全部注册为映射器 -->
    <package name="org.mybatis.builder"/>
  </mappers> 
	 * @param args
	 * @throws Exception
	 */
	
	public static void main(String[] args) throws  Exception {
		String xmlf="D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\mybatis.xml";;
		String mpf="D:\\prj\\sport-service\\kok-sport-service\\src\\main\\resources\\mapper\\FootballMatchDao.xml";
		List<String> li=Lists.newArrayList();
		li.add(mpf);
		String str = xmlAddMapperFile(xmlf, li);
				 		System.out.println(str);

				 
			//	System.out.println( parseStt(sttID,root));
	}

	public static String xmlAddMapperFile(String xmlf, List<String> li)
			throws Exception {
		
		String t=FileUtils.readFileToString(new File(xmlf));
		
		
		return addmapper2xml(li, t);
	}

	public static String addmapper2xml(List<String> li, String xmlOri)
			throws JDOMException, IOException, UnsupportedEncodingException {
		// jdom
				SAXBuilder sb = new SAXBuilder();
				//sb.build yenen drktl file path
				org.jdom.Document doc = sb.build(IOUtils.toInputStream(xmlOri));
			 
				Element root = (Element) doc.getRootElement();
				
				
				String sttID="insert_into_football_incident_t";
				
				Element mappers = (Element) XPath.selectSingleNode(root, "//mappers");
				
				for (String mpf2 : li) {
					 // 创建元素person2
					
			          Element person2 = new Element("mapper");
			         person2.setAttribute("url", "file:///"+mpf2);
			         mappers.addContent(person2);
				}
				
				
				       String str = xml2str(doc);
		return str;
	}

	private static String xml2str(org.jdom.Document doc) throws IOException, UnsupportedEncodingException {
		//格式化输出xml文件字符串
			Format format = Format.getCompactFormat();
			format.setEncoding("utf-8");
			//这行保证输出后的xml的格式
			format.setIndent(" ");
			XMLOutputter xmlout = new XMLOutputter(format);
			ByteArrayOutputStream byteRsp = new ByteArrayOutputStream();
			xmlout.output(doc, byteRsp);
			String str = byteRsp.toString("utf-8");
		return str;
	}

}

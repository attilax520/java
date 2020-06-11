package com.kok.sport.utils.mockdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.kok.sport.utils.MybatisMapper;
import com.kok.sport.utils.Timeutil;
import com.kok.sport.utils.constant.Httpcliet;
import com.kok.sport.utils.db.MybatisUtil;

import ch.qos.logback.core.util.TimeUtil;

public class HtmlUnitTest {

	public static void main(String[] args) throws Exception {
		String url = "https://live.leisu.com/";
//		String t = Httpcliet.testGet(url);
//		FileUtils.write(new File("d:\\cache\\leisu.txt"), t);

		String phantomjs = "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
		String js = "D:\\prj\\sport-service\\kok-sport-service\\js\\leisu.js";
		// https://live.leisu.com/
		// String t = getAjaxCotnent(phantomjs,js,url);
		String leisuHtml = "d:\\cache\\leisu.htm";
//		File file = new File(leisuHtml);
//		System.out.println(Timeutil.toStr(file.lastModified()));
//		System.out.println(file.lastModified());
//		String pageXml = FileUtils.readFileToString(file);
	//	processTime(pageXml);
		// FileUtils.write(new File(leisuHtml), t);
		  htmlunitFail(url);
		System.out.println("f");
	}

	public static void processTime(String pageXml) {
		Document document = Jsoup.parse(pageXml);// 获取html文档
		Elements trs = document.getElementsByTag("tr");
		MybatisMapper MybatisMapper1 = MybatisUtil.getMybatisMapper();
		for (Element tr : trs) {
			if (tr.text().trim().contains("未开始的比赛"))
				break;
			if (tr.hasAttr("data-id")) {
				try {
					String matid = tr.attr("data-id").trim();
					String match = tr.getElementsByTag("td").get(1).text();
					match = "'" + match + "'";
					System.out.println(matid);
					Element timeTd = tr.getElementsByClass("lab-status").first();
					String timestr_min = timeTd.text().trim();

					Integer time_min_int = getMinInt(timestr_min);

					timestr_min = timestr_min.replaceAll("'", "''");
					String sql = "replace mat_runtime set  match_id= " + matid + ",min=" + time_min_int.toString()
							+ ",minstr='" + timestr_min + "',`match`=" + match;
					System.out.println(sql);
					System.out.println("==>" + MybatisMapper1.update(sql));
				} catch (Exception e) {
					e.printStackTrace();
				}

				// System.out.println(timestr_min);
				// System.out.println(element);
			}

		}
	}

	private static int getMinInt(String matid) {

		if (matid .equals("90+")  )
			return 91;
		if (matid .equals("45+"))
			return 46;
		if (matid .equals( "中"))
			return 46;
		try {
			return Integer.parseInt(matid.replaceAll("'", ""));
		} catch (Exception e) {
			return -1;
		}

	}

	// phantomjs cant find websocket page
	public static String getAjaxCotnent(String phantomjs, String js, String url) throws IOException {
		String cmd = phantomjs + " " + js + " " + url;
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(cmd);// 这里我的codes.js是保存在c盘下面的phantomjs目录
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer sbf = new StringBuffer();
		String tmp = "";
		while ((tmp = br.readLine()) != null) {
			sbf.append(tmp);
		}
		// System.out.println(sbf.toString());
		return sbf.toString();
	}

//	    public static void main(String[] args) throws IOException {   
//	       
//	    }   

	// always mem over gc
	private static void htmlunitFail(String url) throws IOException {
		
		//html unit 2.8
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);// 新建一个模拟谷歌Chrome浏览器的浏览器客户端对象//
		webClient.setJavaScriptEnabled(true);
		
		/**html unit 2.4
		 * webClient.getOptions().setThrowExceptionOnScriptError(false);// 当JS执行出错的时候是否抛出异常, 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);// 当HTTP的状态非200时是否抛出异常, 这里选择不需要
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(true);// 是否启用CSS, 因为不需要展现页面, 所以不需要启用
		webClient.getOptions().setJavaScriptEnabled(true); // 很重要，启用JS
		 */
		
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX

		HtmlPage page = null;
		try {
			page = webClient.getPage(url);// 尝试加载上面图片例子给出的网页
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webClient.closeAllWindows();
		}

		webClient.waitForBackgroundJavaScript(15000);// 异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

		String pageXml = page.asXml();// 直接将加载完成的页面转换成xml格式的字符串
// page.asText()
		FileUtils.writeStringToFile( new File("d:\\cache\\leisu.htm"), pageXml);
//
////TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库
//
		Document document = Jsoup.parse(pageXml);// 获取html文档
		document.getElementsByTag("tr");
//List<Element> infoListEle = document.getElementById("feedCardContent").getElementsByAttributeValue("class", "feed-card-item");//获取元素节点等
//infoListEle.forEach(element -> {
//    System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").text());
//    System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
//});
//}
//
	}

}

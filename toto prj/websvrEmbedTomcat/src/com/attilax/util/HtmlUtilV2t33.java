package com.attilax.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class HtmlUtilV2t33 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String html2txt(String htmlBody) {
		Document document = Jsoup.parse(htmlBody);
//		NodeTraversor nTraversor=new NodeTraversor(new NodeVisitor() {
//			
//			@Override
//			public void tail(Node arg0, int arg1) {
//				    System.out.println("tail..."+arg0);
//				
//			}
//			
//			@Override
//			public void head(Node arg0, int arg1) {
//				System.out.println("--head");
//			    System.out.println(arg0);
//				
//			}
//		});
//		nTraversor.traverse(document);

		 
	 
	 
		String txtFinal =ele2txt(document);
		txtFinal=txtFinal.replaceAll("\r\n\r\n", "\r\n");
		txtFinal=txtFinal.replaceAll("\r\n\r\n", "\r\n");
		txtFinal=txtFinal.replaceAll("\r\n\r\n", "\r\n");
	return txtFinal;
	}

	public static String ele2txt(Element e) {
		if (e.children().size() > 0) {
			List txtList = Lists.newLinkedList();
			for (Element e_sub : e.children()) {
				String childTxt = ele2txt(e_sub);
				txtList.add(childTxt);
			}
			if (isBlogckElement(e))
				return "\r\n" + Joiner.on("").join(txtList) + "\r\n";
			else {
				return Joiner.on("").join(txtList);
			}
			 

		} else { // no children
			if (isBlogckElement(e))
				return "\r\n" + e.text() + "\r\n";
			else {
				return e.text();
			}
		}
		// return null;
	}

	public static boolean isBlogckElement(Element e) {
	//	System.out.println(e.tagName());
		if (e.tagName().toString().equals("br"))

			return true;
		if (e.tagName().toString().equals("table"))

			return true;
		if (e.tagName().toString().equals("tr"))

			return true;
		if (e.tagName().toString().equals("div"))

			return true;
		else {

			return false;
		}
	}
}

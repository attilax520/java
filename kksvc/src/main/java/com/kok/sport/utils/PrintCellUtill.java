package com.kok.sport.utils;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PrintCellUtill {
	// ��ʽ��չʾ ���ʽcellչʾ����
	// static int widthChar = 100;
	public	static int cellwidth = 20;
	public	static int cellwidth2 = 30;
	public static void printCell(Object lable, String Align) {
		Map cell1 = new HashMap();
		cell1.put("lable", lable);
		cell1.put("Align����ģʽ", Align);
		cell1.put("width", cellwidth);
		printCell(cell1);
	}

	public static String printCell(Object lable, String Align, int cellwidth22) {
		Map cell1 = new HashMap();
		cell1.put("lable", lable);
		cell1.put("Align����ģʽ", Align);
		cell1.put("width", cellwidth22);
	return	printCell(cell1);
	}

	public static void printPriceByMidtitle(Object prc, int paddAdj, int mdishowTitle) {
		int priceLastEndIdx = cellwidth / 2 + mdishowTitle / 2 + paddAdj;
		String lefted = StringUtils.leftPad(prc.toString(), priceLastEndIdx);
		System.out.print(StringUtils.rightPad(lefted, cellwidth));
	}

	public static String printCell(Map cell1) {
		int cellwidth = (int) cell1.get("width");
		if (cell1.get("Align����ģʽ").toString() == "left") {
			String line = StrUtil.rightPad(cell1.get("lable").toString(), cellwidth);
			System.out.print(line);
			return line;
		} else if (cell1.get("Align����ģʽ").toString() == "mid") {
			String lefted = StringUtils.leftPad(cell1.get("lable").toString(),
					cellwidth / 2 + cell1.get("lable").toString().length() / 2);
			System.out.print(StringUtils.rightPad(lefted, cellwidth));
		} else {// right
			System.out.print(StringUtils.leftPad(cell1.get("lable").toString(), cellwidth));
		}
		return "";

	}


}

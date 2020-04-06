package vcfvcardprj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.attilax.util.OfficePoiExcelUtil;
import com.csvreader.CsvReader;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Nickname;
import ezvcard.property.Organization;

public class vcfVcardExcelDemo2 {

	public static void main(String[] args) throws IOException {

		String filePath = "C:\\\\Users\\\\zhoufeiyue\\\\Desktop\\\\公司通讯录2019年3月份.xlsx";
		filePath = "C:\\Users\\zhoufeiyue\\Documents\\toto doc t3\\user——toto.xlsx";
		// 创建CSV读对象
		// new CsvReader

		XSSFWorkbook XSSFWorkbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));

		XSSFSheet sheet = XSSFWorkbook1.getSheetAt(0);

		Integer rowNum;

		List li = Lists.newArrayList();
		//
		for (rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
			XSSFRow XSSFRow1 = sheet.getRow(rowNum);
			String nameString = getCellValue(XSSFRow1, "c");

//			if(nameString.trim().length()==0)
//				continue;
			if (nameString.contains("艾提拉"))
				System.out.println("");
			XSSFCell cell = getCell(XSSFRow1, "h");
			String telStr = getTel(cell);
			if (telStr.trim().length() == 0)
				continue;
			
			

			// String jobee= XSSFRow1.getCell(2).getStringCellValue();
			VCard vcard = new VCard();
			vcard.setFormattedName(nameString);
			vcard.addTelephoneNumber(telStr, TelephoneType.CELL);
			vcard.setOrganization("sh-totoKustm");

			String str = Ezvcard.write(vcard).version(VCardVersion.V3_0).go();

			System.out.println(str);
			li.add(str);
		}

		String s = Joiner.on("\r\n").join(li);
		FileUtils.write(new File("g:\\addbook_sh_toto_kstm.vcf"), s, "utf8");
		System.out.println(s);

	}

	private static XSSFCell getCell(XSSFRow xSSFRow1, String cellIndexAlphbat) {
		String alpString = "abcdefghijklmn";
		// String numstrString="0123456789";
		char[] alp_a = alpString.toCharArray();
		// char[] num_a=numstrString.toCharArray();
		int alp_index = get_alp_index(cellIndexAlphbat);
		int alp_int_idx = alp_index - 1;
		return xSSFRow1.getCell(alp_int_idx);
	}

	private static String getCellValue(XSSFRow xSSFRow1, String cellIndexAlphbat) {
		String alpString = "abcdefghijklmn";
		// String numstrString="0123456789";
		char[] alp_a = alpString.toCharArray();
		// char[] num_a=numstrString.toCharArray();
		int alp_index = get_alp_index(cellIndexAlphbat);
		int alp_int_idx = alp_index - 1;
		return   OfficePoiExcelUtil.getCellValueStringModeForce( xSSFRow1.getCell(alp_int_idx));
	}

	private static int get_alp_index(String cellIndexAlphbat) {
		String alpString = "abcdefghijklmn";
		String numstrString = "0123456789";
		char[] alp_a = alpString.toCharArray();

		char cia = cellIndexAlphbat.toCharArray()[0];
		int n = 1;
		for (char c : alp_a) {
			if (c == cia)
				return n;
			n++;

		}
		return n;
	}

	private static String getTel(XSSFCell cell) {
		try {
			return cell.getStringCellValue();
		} catch (Exception e) {

			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			// double tel= cell.getNumericCellValue();

			// return String.valueOf(tel);

			return cell.getStringCellValue();
		}

	}
}

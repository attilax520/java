package vcfvcardprj;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.fsm.TokenEndEx;
import com.attilax.parser.Token;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.TelephoneType;

public class mblFetch {

	public static void main(String[] args) throws IOException, CantFindEx {
   mainx();
 	//  	tets();
 
 System.out.println("--f");

	}

	private static void mainx() throws IOException {
	     List li_vcardStr=Lists.newArrayList();
		String fpath="D:\\000atibek\\l2 nlaile_fem v3 s11\\nlaiye_fem_v3_s11.txt";
		List<String> li=FileUtils.readLines(new File(fpath));
		for (String line : li) {
			if( line.contains("18911889447"))
				System.out.println(line);
					
			String fname=	FilenameUtils.getBaseName(line);
			List<Token> process = new mblFetch().getTokens(fname);
			try {
				List<String> cp=getNameAndMblCp(process);
				String name=cp.get(0);
				String mbl=cp.get(1);
				  VCard vcard = new VCard(); 
		            vcard.setFormattedName(name);
		            vcard.addTelephoneNumber(mbl, TelephoneType.CELL);
		            String str = Ezvcard.write(vcard).version(VCardVersion.V3_0).go();
		            li_vcardStr.add(str);
			} catch (CantFindEx e) {
				  System.out.println("CantFindEx"+line);
			}
		}
		
		  String s=Joiner.on("\r\n").join(li_vcardStr);
	        FileUtils.write(new File("d:\\ccs_nlaiye_fem_saa.vcf"),s,"utf8");
	        
	}

	private static void tets() throws CantFindEx {
		String s = "周何琪__学校郑州大学__联系方式15538130516__身高体重162cm,47k";
	 //	s="天津广播影视职业学院 韩震宇 15641656234  161cm，44kg";
		s="王喆婷__学校首都师范大学_身高体重168cm，47kg   18911889447.jpeg";
		s="a18911889447.jpeg";
		List<Token> process = new mblFetch().getTokens(s);
		System.out.println( JSON.toJSONString(process,true) );
	List cp=getNameAndMblCp(process);
		System.out.println(cp);
	}

	private static String getfname(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private static List<String> getNameAndMblCp(List<Token> process) throws CantFindEx {
		 List<String> nameTokens=Lists.newArrayList();
		 String cp="";
		for (Token token : process) {
			if(new mblFetch().isnum(token.value) &&  token.value.length()==11 && token.value.startsWith("1"))
			{
				 
				String name=Joiner.on("").join(nameTokens);
				 List<String> li=Lists.newArrayList();
				 li.add(name);
				 li.add(token.value);
			   return li;
			}else
				nameTokens.add(token.value);
			 
				 
		}
		
	//	 List<String>
		
		//String errstr=Joiner.on("").join(process);
		throw new CantFindEx( "");
		
	}

	private static String getMblCp(List<Token> process) {
		for (Token token : process) {
			if(new mblFetch().isnum(token.value) &&  token.value.length()==11 && token.value.startsWith("1"))
				return token.value;
		}
	   return "";
	}

	private char[] process(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	int charIndex=-1;
	char cur_char;
	char[] code_char_arr;
	private String curStat ="start";
	private List<Token> tokens_tmp;
	private String curTokenTxt="";
	private String codeStr;

	@SuppressWarnings("unchecked")
	public List<Token> getTokens(String codeStr) {
		this.codeStr=codeStr;
		List<Token> li = Lists.newArrayList();
		code_char_arr = codeStr.toCharArray();

		while (true) {
			Object tk;
			try {
				tk = nextTokens();
			} catch (TokenEndEx e) {
				break;
			}
			if (tk instanceof Token)
				li.add((Token) tk);
			else if (tk instanceof List)
				li.addAll((Collection<? extends Token>) tk);
			else
				throw new RuntimeException("token type err,curchar:" + cur_char + ",colidx:" + charIndex);

		}

		return li;

	}

	/**
	 * 
	 * @return token or list<token>
	 * @throws TokenEndEx
	 */
	public Object nextTokens() throws TokenEndEx {
		// code_char_arr = code.toCharArray();
		charIndex++;
		if( this.codeStr.contains("18911889447") && charIndex>=30)
			System.out.println("");
		
		if (charIndex == code_char_arr.length - 1)  //last char
		{
		     
		}
		if (charIndex > code_char_arr.length - 1)
		{
			throw new TokenEndEx(new String(code_char_arr));
		}
		cur_char = code_char_arr[charIndex];
		// cur_char=cur_char;
		// if (this.curTokenTxt.equals("1598"))
		// System.out.println("dbg");
		// if(this.gColumn==30)
		// System.out.println("dbg");

		// get next char,,then change stat
		// jude cur char and cur stat...then if or not chage stat
		if (ishanzi(cur_char))
			return hanziEvt();
		else if (isnum(cur_char))
			return numEvt();
		else
			return splitorCharEvt();
		// break;
	}

	private Object numEvt() throws TokenEndEx {
	
		if (this.curStat.equals("start")) {
			this.curStat="numStat";
			return gaziStat();
		}
		if (this.curStat.equals("numStat")) {
			return gaziStat();
		}
		if (this.curStat.equals("hanziStat")) {		 
			this.curStat="numStat";
			 return retNumtoken();
		}
		
		if (this.curStat.equals("splitorStat")) {
			this.curStat="numStat";
			return retSplitorToken();
		}
		return null;
	}

	private Object hanziEvt() throws TokenEndEx {
		
		if (this.curStat.equals("start")) {
			this.curStat="hanziStat";
			return gaziStat();
		}
		if (this.curStat.equals("hanziStat")) {
			return gaziStat();
		}
		// if is hanzi && cur is numstat
		if (this.curStat.equals("numStat")) {
			this.curStat="hanziStat";
			return retNumtoken();
		} 
		if (this.curStat.equals("splitorStat")) {
			this.curStat="hanziStat";
			return retSplitorToken();
		}
		
		this.curStat="hanziStat";
		return null;
		
		
	}


	private Object splitorCharEvt() throws TokenEndEx {

		if (this.curStat.equals("start")) {
			this.curStat="splitorStat";
			return gaziStat();
		}
		if (this.curStat.equals("hanziStat")) {
			this.curStat="splitorStat";
			return retHeziToken();
		}
		if (this.curStat.equals("numStat")) {
			this.curStat="splitorStat";
			return retNumtoken();
		}
		
		//gazi 
		this.curStat="splitorStat";
			return gaziStat();
	 
	 
	}

	private Object retHeziToken() {
	 
		Token tk = new Token();
		tk.Text = curTokenTxt.toString();
		tk.Type = "hezi";
		tk.value = curTokenTxt.toString();
		curTokenTxt=String.valueOf(cur_char);
		return tk;
	}

	

	private Object retNumtoken() {
	 
		Token tk = new Token();
		tk.Text = curTokenTxt.toString();
		tk.Type = "num";
		tk.value = curTokenTxt.toString();
		curTokenTxt="";
		curTokenTxt=String.valueOf(cur_char);
		return tk;
	}

	private Object retSplitorToken() {
		 
		Token tk = new Token();
		tk.Text = curTokenTxt.toString();
		tk.Type = "splitor";
		tk.value = curTokenTxt.toString();
		curTokenTxt="";	curTokenTxt=String.valueOf(cur_char);
		return tk;
	}
	private Object gaziStat() throws TokenEndEx {
		curTokenTxt = curTokenTxt + String.valueOf(cur_char);
		return nextTokens();
	}

	private boolean ishanzi(char cur_char2) {

		return isChinese(String.valueOf(cur_char2));
	}

	private boolean isnum(char cur_char2) {
		String str = String.valueOf(cur_char2);
		return isnum(str);
	}

	private boolean isnum(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;

		return flg;
	}

}

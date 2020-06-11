package com.attilax.ast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.attilax.collection.listUtil;
import com.attilax.fsm.JavaExpFsm;
import com.attilax.fsm.JavaTokener;
import com.attilax.fsm.Token;
import com.attilax.json.AtiJson;
import com.attilax.lang.javaUtil;
import com.attilax.net.UrlEncode_del;
import com.attilax.ref.refx;
import com.google.common.collect.Lists;

public class AstBuilder {
	public Object obj;
	public Object rzt;

	public static void main(String[] args) throws UnsupportedEncodingException {

		System.out
				.println(URLEncoder
						.encode("new(com.attilax.util.connReduceDync).set_resfile(userPhone4jobusImp/uc_js.txt).joinNoutV2()",
								"utf8"));

		String s = "new(com.attilax.orm.AtiOrmV2).queryAsRzt(\"select sum(rmb) sumx from recharge where accountId in (  select id as uid from account where promoter=888 ) \")";

		s = "HttpUtil.down(\"http://www.winrar.com.cn/download/wrar540scp.exe\", \"c:\\0down.exe\")";

		Expression buildAst = new AstBuilder().buildAst(s);
		System.out.println(AtiJson.toJson(buildAst));
		Object rzt = new AstParser().parse(buildAst);
		System.out.println(rzt);
		// "select sum(rmb) sumx from recharge where accountId in (  select id as uid from account where promoter=888 )"
		System.out.println("..");
		// System.setProperty("prj","jobus");
		// String
		// code="new(com.attilax.util.connReduceDync).set_resfile(userPhone4jobusImp/uc_js.txt).joinNoutV2() ".trim();
		// Ast astParser = new Ast();
		// List ast=astParser.getExprsLiAst(code);
		// System.out.println(AtiJson.toJson( astParser.parse(ast)));
		// s="login(admin,admin)";

	}

	// public Object parse;
	// public Object parse(List ast) {
	// for (Object object : ast) {
	// // String exp=(String) object;
	// parseSingle(object);
	// }
	// return this.rzt;
	// }

	// private void parseSingle(Object exp) {
	// Exprs e = (Exprs) (exp);
	// e.obj = this.obj;
	//
	// this.rzt = e.calc();
	// this.obj = e.obj;
	//
	// }

	@SuppressWarnings("all") 	@Deprecated
	public Expression buildAst(String code) {

		// List li = new ArrayList();

		List<Token> tokens = new JavaTokener(code).getTokens();
		return buildAst(tokens);
	}

	@Deprecated
	public Expression buildAst(List<Token> tokens) {
		// System.out.println(AtiJson.toJson(tokens));
		List<String> tokens_slice_li = Lists.newLinkedList();
		String stat = "ini";
		MethodInvocation mi = new MethodInvocation();
		Expression exp;
		boolean isConstrutsced = false; // qaa is first
		boolean isNewDysnInvoke = false;
		boolean isStaticInvoke = false;

		for (int i = 0; i < tokens.size(); i++) {

			Token cur = tokens.get(i);
			// last
			// dot is expres splitor..if cur pos of tokens is dot

			if (cur.getType().equals("op") && cur.getText().equals("("))
			// if (cur.getType().equals("var") )
			{
				stat = "brkStart";

				String text = tokens.get(i - 1).getText();

				// if(isNewOpV2(tokens)
				if (isNewOpV2(tokens) && isConstrutsMeth(tokens, i)
						&& isConstrutsced == false)// newxxx
				{
					mi.Exp = getClassInstance(text);
					mi.Name = "__con";
					isConstrutsced = true;
					tokens_slice_li = Lists.newLinkedList();
					continue;

				}

				if (isConstrutsced == false) { // static //first

					mi.Exp = new SimpleName(refx.getClassName(text));
					mi.Name = refx.getMethodName(text);
					isConstrutsced = true;

					tokens_slice_li = Lists.newLinkedList();

					continue;

				}

				// new xxx() meth.chain

				if (mi.Name.equals("__con"))
					mi.Name = text;
				else // static ,meth chain
				{
					MethodInvocation tmp = (MethodInvocation) mi.clone();
					MethodInvocation mi_Outer_new = new MethodInvocation();
					mi_Outer_new.Exp = tmp;
					mi_Outer_new.Name = text;
					mi = mi_Outer_new;
				}
				tokens_slice_li = Lists.newLinkedList();
				continue;

				// tokens_slice_li = Lists.newLinkedList();

				// continue;
			}
			if (cur.getText().equals(")") && cur.Type.equals("op")) {
				stat = "brkEnd";
				continue;
			}
			// param push
			if (stat.equals("brkStart"))
				mi.arguments.add(cur.Text);

		} // end for

		// Collections.addAll(li, a);
		return mi;
	}

	List<Token> tokens;
	int m_index = 0;
	List<Object> params = Lists.newArrayList();
	String invokeClsDyncOrStatic = "dyn";
	MethodInvocation mi = new MethodInvocation();
	boolean isConstrutsced = false; // qaa is first
	public Expression buildAstV2(List<Token> tokens) {
		this.tokens = tokens;
		// System.out.println(AtiJson.toJson(tokens));
		// List<String> tokens_slice_li = Lists.newLinkedList();
		// String stat = "ini";
	
		// Expression exp;
	
		boolean isNewDysnInvoke = false;
		boolean isStaticInvoke = false;

		if(m_index>tokens.size()-1)
			return mi;
		Token cur = tokens.get(m_index);

		if (cur.getText().equals("(") && cur.getType().equals("op"))

		{
			// stat = "brkStart";

			String textBefBrkStart = tokens.get(m_index - 1).getText();

			// newxx first

			if (isNewOpV2(tokens) && isConstrutsced == false)// newxxx
			{
				return build4NewOp(tokens, textBefBrkStart);

			}
			if(!isNewOpV2(tokens)  && isConstrutsced == false)
				return buildAst4staticInvoke(tokens, textBefBrkStart);
			
			if(!isNewOpV2(tokens)  && isConstrutsced ) //static meth chain
				return buildAst4staticInvokeMethChain(tokens, textBefBrkStart);
		 
		}

		m_index++;
		return buildAstV2(tokens);
	}

	/**
	attilax    2016年10月24日  下午3:06:24
	 * @param tokens2
	 * @param textBefBrkStart
	 * @return
	 */
	private Expression buildAst4staticInvokeMethChain(List<Token> tokens2,
			String textBefBrkStart) {
	//	if (isConstrutsced == true) { // static //first
		//	{
				params = Lists.newArrayList();
				MethodInvocation mi_Outer_new = new MethodInvocation();
				mi_Outer_new.Exp = cloneCurMI(mi);
				mi_Outer_new.Name = textBefBrkStart;
				mi = mi_Outer_new;
				
				// param push
				List<Expression> params = buildAstV2_params(tokens);
				mi.arguments = params;
		//	}

			m_index++;
			return buildAstV2(tokens);
			// return mi;
	}

	private Expression build4NewOp(List<Token> tokens, String textAftBrkStart) {
		Token cur;
		String className = javaUtil.clr(textAftBrkStart);
		mi.Exp = getClassInstance(className);
		while (true) {
			m_index++;
			cur = tokens.get(m_index);
			if (cur.getText().equals("(") && cur.Type.equals("op")) {

				mi.Name = tokens.get(m_index - 1).getText();
				// return mi;
				break;
			}
		}

		// param push
		while (true) {
			m_index++;
			cur = tokens.get(m_index);
			if (cur.getText().equals(")") && cur.Type.equals("op")) {
				break;
			}
			mi.arguments.add(cur.Text);
		}
		return buildAstV2(tokens);
	}

	private Expression buildAst4staticInvoke(List<Token> tokens,
			String textAftBrkStart) {
		// new method invoke exp
		String className = refx.getClassName(textAftBrkStart);
		className = javaUtil.clr(className);
		mi.Exp = new SimpleName(className); // cls name
		mi.Name = refx.getMethodName(textAftBrkStart);
	

		// param push
		List<Expression> params = buildAstV2_params(tokens);
		mi.arguments = params;

	
		isConstrutsced = true;
	  return buildAstV2(tokens);
	}

	/**
	attilax    2016年10月24日  下午2:25:11
	 * @param mi
	 * @return
	 */
	private Expression cloneCurMI(MethodInvocation mi) {
		MethodInvocation tmp = (MethodInvocation) mi.clone();
		return tmp;
	}

	private List<Expression> buildAstV2_params(List<Token> tokens2) {

		// List<Expression> li=Lists.newArrayList();
		m_index++;
		Token cur = tokens.get(m_index);

		// build array param
		if (cur.getText().equals("[") && cur.getType().equals("spltr")) {
			// params.add(e);
			ArrayCreation ac = getArrayCreation();
			params.add(ac);
			return buildAstV2_params(tokens2);
		}

		// close param
		if (cur.getText().equals(")") && cur.Type.equals("op")) {
			// PARAMS close
			return listUtil.clone(params);
		}

		// add param dep ..skip comma
		if (cur.getText().equals(","))
			return buildAstV2_params(tokens2);
		//cast  .skip  queuo..
		if(cur.getText().startsWith(":") && cur.getType().equals("op"))
			return buildAstV2_params(tokens2);
		if( (cur.getText().equals("s")||cur.getText().equals("i"))  && cur.getType().equals("op"))
			return buildAstV2_params(tokens2);
		
		//meth chain
		if(cur.getType().equals("var") && cur.getText().contains(".")) //convert var aram  ...is ref id...ref  id char..
		{
			String cls=cur.getText().trim();
			int d=cls.lastIndexOf(".");
			cls=cls.substring(0, d);
			TypeLiteral tl=new TypeLiteral(cls);
			params.add(tl);
			return buildAstV2_params(tokens2);
		}

		// add param
		
	 	
		Token nextToken = getNext2JoinToken();	
		
		if(nextToken.Text.trim().equals(":s")  )
			params.add(cur.Text);
		else if(nextToken.Text.trim().equals(":i")  )
			params.add( Integer.parseInt( cur.Text));
		else
			params.add(cur.Text);
		
			
		return buildAstV2_params(tokens2);
	}

	/**
	attilax    2016年10月24日  下午5:46:17
	 * @return
	 */
	private Token getNext2JoinToken() {
	Token next=	tokens.get(m_index+1);
		String curTypeAct="str";
		Token next2Token= tokens.get(m_index+2);
		
		return new Token(next.Text+""+next2Token.Text);
	}

	/**
	 * attilax 2016年10月20日 下午6:25:13
	 * 
	 * @return
	 */
	private ArrayCreation getArrayCreation() {
		// m_index++;
		ArrayCreation ac = new ArrayCreation();
		ArrayInitializer ai = new ArrayInitializer();
		List<Object> params = Lists.newArrayList();
		// param push
		while (true) {
			m_index++;
			Token cur = tokens.get(m_index);
			if (cur.getText().equals("]")) {
				// System.out.println("dbg");
			}

			if (cur.getText().equals("]") && cur.Type.equals("spltr")) {
				ai.expressions = params;
				ac.optionalInitializer = ai;
				return ac;
			}
			if (cur.getText().equals(","))
				continue;
			params.add(cur.Text);
			if (cur.getType().equals("str"))
				ac.arrayType = "string";
		}
		// return null;
	}

	private boolean isNewOpV2(List<Token> tokens) {
		if (tokens.get(0).getText().toString().trim().equals("new"))
			return true;
		return false;
	}

	// private Exprs getExprsFrmTokenslice(List<String> tokens_slice_li) {
	// Exprs e = new Exprs();
	//
	// // if (tokens_slice_li.size() == 5) {
	// //
	// // }
	// // if (tokens_slice_li.size() == 4) {
	// // e.method = (String) tokens_slice_li.get(0);
	// // String params = tokens_slice_li.get(2);
	// // // e.params = params; //e.parseParams(params.toString());
	// // e.params = Lists.newLinkedList();
	// // e.params.add(params);
	// // //q716 must as a params..
	// //
	// //'new(com.attilax.agent.AgentRechargeService).getSubMemTotalsRecycleByAgentId("promoter:$pid$,fld2:v2")';
	// //
	// // }
	// // last log() mode ,empty param mode
	// if (tokens_slice_li.size() == 3) {
	// e.method = (String) tokens_slice_li.get(0);
	// e.params = Lists.newLinkedList();
	// } else {
	// e.method = (String) tokens_slice_li.get(0);
	// e.params = parseParams(tokens_slice_li);
	// }
	//
	// return e;
	// }

	private boolean isConstrutsMeth(List<Token> tokens, int i) {

		return isNewOp(tokens, i);
	}

	private Expression getClassInstance(String text) {
		ClassInstanceCreation cic = new ClassInstanceCreation();
		cic.name = text;
		return cic;
	}

	private boolean isNewOp(List<Token> tokens, int i) {
		Token name = tokens.get(i - 1);
		Token newx = null;
		try {
			newx = tokens.get(i - 2);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		if (newx.getText().equals("new"))
			return true;
		return false;
	}

	@Deprecated
	public List parseParams(String pa_str) {
		String[] a = pa_str.split(",");
		List li = new ArrayList();
		Collections.addAll(li, a);
		return li;
	}

	/**
	 * attilax 2016骞�9鏈�2鏃� 涓嬪崍12:12:01
	 * 
	 * @param tokens_slice_li
	 * @return
	 */
	@Deprecated
	public List parseParams(List<String> tokens_slice_li) {
		List li = new ArrayList();
		for (int i = 2; i < tokens_slice_li.size() - 1; i++) {
			li.add(tokens_slice_li.get(i));
		}
		return li;
	}

}

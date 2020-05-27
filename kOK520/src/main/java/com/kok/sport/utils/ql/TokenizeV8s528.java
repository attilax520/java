//package com.attilax.core;
//
//import java.util.List;
//
//import com.alibaba.fastjson.JSON;
//import com.attilax.ast.ClassInstanceCreation;
//import com.attilax.ast.Expression;
//import com.attilax.ast.MethodInvocation;
//import com.attilax.ast.SimpleName;
//import com.attilax.collection.listBuilder;
//import com.attilax.parser.Token;
//import com.attilax.str.strService;
//import com.google.common.collect.Lists;
//
//public class TokenizeV8s528 {
//
//	public static void main(String[] args) {
//
//		// new
//		// com.attilax.core.methodRunner(str:contstuParamVal).methodync(str:haha);
//		String s = "new com.attilax.core.methodRunner  \"string\" contstuParamVal .  methDync \"string\"   haha";
//		String[] a = s.split(" ");
//		List li = listBuilder.$(a).trimElement().delEmptyElement().build();
//		List<Token> li_tokens = (List<Token>) new TokenizeV8s528().TokenizeProcess(li);
//
//		System.out.println(JSON.toJSONString(li_tokens, true));
//	}
//
//	List<Token> token_obj_list = Lists.newArrayList();
//	List<String> tokens;
//	int token_index = 0;
//	int paramIdx = 0;
//	String cur_stat = "ini";
//	private Expression Expression;
//	String cur_token;
//
//	public List<Token> TokenizeProcess(List<String> tokens) {
//		this.tokens = tokens;
//		System.out.println("tokenindex" + token_index);
//		if (token_index == 4)
//			System.out.println("dbg");
//		if (token_index >= tokens.size())
//			return this.token_obj_list;
//		cur_token = tokens.get(token_index);
//		if (cur_token.equals("."))
//			System.out.println("d");
//		
//		
//		switch (cur_token.trim()) {
//		case "new":
//			TokenNew_cash();
//			break;
//
//		case ".":
//			  TokenPeriod_case();
//				break;
//		default:
//			  tokenNormalchar_case();
//				break;
//		 
//		}
//
//		// } else if (cur_token.length() > 0 &&
//		// cur_stat.equals("getidnameEndState")) {
//
//		// this.cur_stat = " ParamStart";
//
//		token_index++;
//		return TokenizeProcess(tokens);
//	}
//
//	private void TokenPeriod_case() {
//		switch (cur_stat) {
//		case "ParamStart":
//			Token tk = new Token(cur_token);
//			tk.Type = "op";
//			tk.Text = cur_token;
//
//			token_obj_list.add(tk);
//			this.cur_stat = "ParamEnd";
//			break;
//
//		}
//	 
//
//	}
//
//	private List<Token> tokenNormalchar_case() {
//		switch (cur_stat) {
//		case "newopStat":
//
//			TokenNormal_sattNew_startgetIdname_ClassOrFunname_State(cur_token);
//			break;
//		case "ParamStart":
//			TokenNormal_statParamstart_ParamStartStat(cur_token);
//			break;
//
//		case "ParamEnd":
//
//			TokenNormal_sattNew_startgetIdname_ClassOrFunname_State(cur_token);
//			break;
//
//		}
//
//		return null;
//	}
//
//	private void TokenNew_cash() {
//		switch (cur_stat) {
//		case "ini":
//			// if(this.token_index==0)
//			  tokenNew_statIni(cur_token);
//		}
//
//	 
//	 
//	}
//
////	private void ParamEndStat(String cur_token) {
////		MethodInvocation mi = new MethodInvocation();
////		mi.jsonname = "MethodInvocation";
////		mi.Exp = this.Expression;
////		mi.Name = cur_token;
////		this.Expression = mi;
////		this.cur_stat = "MethodInvocationType";
////	}
//
//	private void TokenNormal_statParamstart_ParamStartStat(String cur_token) {
//		Token tk = new Token(cur_token);
//		tk.Type = "val";
//
//		token_obj_list.add(tk);
//
//	}
//
//	 
//
//	private void TokenNormal_sattNew_startgetIdname_ClassOrFunname_State(String cur_token) {
//
//		Token tk = new Token(cur_token);
//		tk.Type = "id";
//
//		token_obj_list.add(tk);
//		// this.stat = "newopStat";
//
//		this.cur_stat = "ParamStart";
//	}
//
////	private List<Token> tokenNew_statIni(String cur_token) {
////		if (cur_token.equals("invokeMethod")) {
////			ClassInstanceCreation cic = new ClassInstanceCreation();
////
////			// cic.arguments = getArgs(tokens, m_index);
////			this.Expression = cic;
////			this.Expression.jsonname = "ClassInstanceCreation";
////			this.cur_stat = "startgetClassname";
////			//
////
////		}
////
////		if (cur_token.equals("new")) {
////			Token tk = new Token(cur_token);
////			tk.Type = "kw";
////
////			token_obj_list.add(tk);
////			this.cur_stat = "newopStat";
////			//
////
////		}
////		if (cur_token.equals("invokeStaticMethod")) {
////			this.Expression = new SimpleName(cur_token);
////			this.Expression.jsonname = "ClassInstanceCreation";
////			this.cur_stat = "startgetClassname";
////		}
////		return TokenizeProcess(this.tokens);
////	}
//
//	private Object getArg(String cur_token, int paramIdx2, String[] paramtypes) {
//		String ptype = paramtypes[paramIdx2];
//		if (ptype.equals("int"))
//			return Integer.parseInt(cur_token.toString());
//
//		return cur_token;
//	}
//
//}

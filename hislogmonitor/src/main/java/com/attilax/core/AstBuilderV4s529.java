package com.attilax.core;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.attilax.ast.ClassInstanceCreation;
import com.attilax.ast.Expression;
import com.attilax.ast.MethodInvocation;
import com.attilax.ast.SimpleName;
import com.attilax.collection.listBuilder;
import com.attilax.parser.Token;
import com.attilax.str.strService;
import com.google.common.collect.Lists;

public class AstBuilderV4s529 {

	public static void main(String[] args) {
		String s = "new  com.attilax.core.methodRunner  \"string\" contstuParamVal .  methDync \"string\"   haha";
		String[] a = s.split(" ");
		List li = listBuilder.$(a).trimElement().delEmptyElement().build();
		List<Token> li_tokens = (List<Token>) new TokenizeV8s528().TokenizeProcess(li);
		com.attilax.ast.Expression ast = new AstBuilderV4s529().buildAstV9s528(li_tokens);

		System.out.println(JSON.toJSONString(ast, true));
	}

	Token cur_token;
	private Expression Expression;
	int paramIdx = 0;
	String stat = "ini";
	int token_index = 0;
	List<Token> tokens;

	public Expression buildAstV9s528(List<Token> tokens) {
		this.tokens = tokens;
		System.out.println("tokenindex" + token_index);
		if (token_index == 4)
			System.out.println("dbg");
		if (token_index >= tokens.size())
			return this.Expression;
		cur_token = tokens.get(token_index);

		switch (cur_token.Type.trim()) {
		case "kw":
			if ((cur_token.Text.equals("new")))
				TokenTypeKw_case();
			break;

		case "id":

			tokenTypeId_case();
			break;
		case "op":

			TokenTypeOp_case();
			break;
		case "val":

			TokenTypeVal_case();
			break;

		default:
			// tokenNormalchar_case();
			break;

		}

	 

		token_index++;
		return buildAstV9s528(tokens);
	}

	private Object getArg(Token cur_token, int paramIdx2, String[] paramtypes) {
		String ptype = paramtypes[paramIdx2];
		if (ptype.equals("int"))
			return Integer.parseInt(cur_token.Text.toString());

		return cur_token.Text;
	}

	private boolean isClassName(Token cur_token) {
		// TODO Auto-generated method stub
		return (token_index == 0 || token_index == 1);
	}

	private boolean isMethodName(Token cur_token) {

		return !isClassName(cur_token);
	}

	private void tokenTypeId_case() {

		switch (this.stat.trim()) {
		case "new":
			tokenTypeId_StateNew_classini(cur_token);
			break;
		case "ini":
			tokenTypeId_case_StateIni_staticclassini(cur_token);
			break;
		default:
			tokenTypeId_StateNOiniNNew_methodini(cur_token);
			break;

		}

	}

	private void tokenTypeId_case_StateIni_staticclassini(Token cur_token) {
		this.Expression = new SimpleName(cur_token.Text);
		this.Expression.jsonname = "ClassInstanceCreation";
		this.stat = "ParamTypeStart";
	}

	private void tokenTypeId_StateNew_classini(Token cur_token) {
		ClassInstanceCreation cic = new ClassInstanceCreation();
		cic.name = cur_token.Text;
		this.Expression = cic;
		this.Expression.jsonname = "ClassInstanceCreation";
		this.stat = "ParamTypeStart";
	}

	private void tokenTypeId_StateNOiniNNew_methodini(Token cur_token) {
		MethodInvocation mi = new MethodInvocation();
		mi.jsonname = "MethodInvocation";
		mi.Exp = this.Expression;
		mi.Name = cur_token.Text;
		this.Expression = mi;
		this.stat = "ParamTypeStart";
	}

	private void TokenTypeKw_case() {

		switch (this.stat.trim()) {
		case "ini":
			this.stat = "new";
			break;

		}

	}

	private void TokenTypeOp_case() {
		if (stat.trim().equals("ParamStart")) {
			this.stat = "ParamEnd";
			paramIdx = 0;
		}

	}

	private void TokenTypeVal_case() {
		switch (stat.trim()) {
		case "ParamTypeStart":
			TokenTypeVal_StateParamTypeStart(cur_token);
			break;

		case "ParamStart":
			TokenTypeVal_StateParamStartStat(cur_token);
			break;
		case "val":
			if ((cur_token.Text.equals("new")))
				TokenTypeVal_case();
			break;
		default:
			// tokenNormalchar_case();
			break;

		}

	}

	private void TokenTypeVal_StateParamStartStat(Token cur_token) {
		// ClassInstanceCreation cic = (ClassInstanceCreation) this.Expression;
		Object params = getArg(cur_token, paramIdx, this.Expression.paramtypes);
		this.Expression.arguments.add(params);
		// paramIdx++;
	}

	private void TokenTypeVal_StateParamTypeStart(Token cur_token) {
		this.stat = "ParamStart";
		this.Expression.paramtypes = cur_token.Text.split(",");

	}

}

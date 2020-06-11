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

public class  AstBuilderV3s528 {

	public static void main(String[] args) {
		String s = "invokeMethod  com.attilax.core.methodRunner  \"string\" contstuParamVal .  methDync \"string\"   haha";
		String[] a = s.split(" ");
		List li = listBuilder.$(a).trimElement().delEmptyElement().build();
		com.attilax.ast.Expression buildAstV3s528 = new AstBuilderV3s528().buildAstV3s528(li);
		
		System.out.println(JSON.toJSONString(buildAstV3s528,true) );
	}

	List<String> tokens;
	int token_index = 0;
	int paramIdx = 0;
	String stat = "ini";
	private Expression Expression;

	public Expression buildAstV3s528(List<String> tokens) {
		this.tokens = tokens;
		System.out.println("tokenindex" + token_index);
		if (token_index == 4)
			System.out.println("dbg");
		if (token_index >= tokens.size())
			return this.Expression;
		String cur_token = tokens.get(token_index);

		if (token_index == 0 && stat.equals("ini")) {
			tokenIndexZeroStat(cur_token);

		} else if (stat.equals("startgetClassname")) {

			startgetClassnameState(cur_token);
		} else if (stat.equals("ClassInstanceCreationParamType")) {
			ClassInstanceCreationParamTypeState(cur_token);

		}

		else if (!cur_token.equals(".")  && stat.equals("ClassInstanceCreationParamStart")) {
			ClassInstanceCreationParamStartStat(cur_token);
		}

		else if (cur_token.equals(".") && stat.equals("ClassInstanceCreationParamStart")) {
			this.stat = "ClassInstanceCreationParamEnd";
		} else if (stat.equals("ClassInstanceCreationParamEnd")) {
			ClassInstanceCreationParamEndStat(cur_token);
		} else if (stat.equals("MethodInvocationType")) {
			this.stat = "MethodInvocationParamStart";
			this.Expression.paramtypes = cur_token.split(",");

		} else if (stat.equals("MethodInvocationParamStart")) {
			MethodInvocation cic = (MethodInvocation) this.Expression;
			cic.arguments.add(cur_token);
		}

		token_index++;
		return buildAstV3s528(tokens);
	}

	private void ClassInstanceCreationParamEndStat(String cur_token) {
		MethodInvocation mi = new MethodInvocation();
		mi.jsonname="MethodInvocation";
		mi.Exp = this.Expression;
		mi.Name = cur_token;
		this.Expression = mi;
		this.stat = "MethodInvocationType";
	}

	private void ClassInstanceCreationParamStartStat(String cur_token) {
		ClassInstanceCreation cic = (ClassInstanceCreation) this.Expression;
		Object params = getArg(cur_token, paramIdx, this.Expression.paramtypes);
		cic.arguments.add(cur_token);
		paramIdx++;
	}

	private void ClassInstanceCreationParamTypeState(String cur_token) {
		this.stat = "ClassInstanceCreationParamStart";
		this.Expression.paramtypes = cur_token.split(",");
	}

	private void startgetClassnameState(String cur_token) {
		if (this.Expression instanceof ClassInstanceCreation) {
			ClassInstanceCreation cic = (ClassInstanceCreation) this.Expression;
			cic.name = cur_token;
		} else // static class simplename
		{
			SimpleName sn = (SimpleName) this.Expression;
			sn.IDENTIFIER = cur_token;
		}

		this.stat = "ClassInstanceCreationParamType";
	}

	private void tokenIndexZeroStat(String cur_token) {
		if (cur_token.equals("invokeMethod")) {
			ClassInstanceCreation cic = new ClassInstanceCreation();

			// cic.arguments = getArgs(tokens, m_index);
			this.Expression = cic;
			this.Expression.jsonname = "ClassInstanceCreation";
			this.stat = "startgetClassname";
			//

		}
		if (cur_token.equals("invokeStaticMethod")) {
			this.Expression = new SimpleName(cur_token);
			this.Expression.jsonname = "ClassInstanceCreation";
			this.stat = "startgetClassname";
		}
	}

	private Object getArg(String cur_token, int paramIdx2, String[] paramtypes) {
		String ptype = paramtypes[paramIdx2];
		if (ptype.equals("int"))
			return Integer.parseInt(cur_token.toString());

		return cur_token;
	}

}

package com.attilax.ast;

import java.util.List;

import com.attilax.fsm.JavaTokener;
import com.attilax.fsm.Token;

public class AstBuilderTest {

	
	public static void main(String[] argsx) {
		String args=" aaaPKg.  DslParser.m3(123)  ";	
		args="  new aaaPKg .DslParser().m2(123) ";
		//	args=" aaaPKg.DslParser.m3(123).m4()";
			List<Token> tokens = new JavaTokener(args).getTokens();// er(args).getTokens();
			Expression buildAst = new AstBuilder().buildAstV2(tokens);
			Object rzt = new AstParser().parse(buildAst);
		//	System.out.println(AtiJson.toJson(tokens));		
			System.out.println("test   ok"+ rzt);
	}
}

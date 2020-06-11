/**
 * 
 */
package com.attilax.ast;

import java.util.List;

import com.attilax.fsm.JavaTokener;
import com.attilax.fsm.Token;

/**com.attilax.ast.MethodInvoker
 * @author attilax
 *2016骞�9鏈�19鏃� 涓嬪崍4:28:30
 */
public class MethodInvoker {

	/**
	attilax    2016骞�9鏈�19鏃�  涓嬪崍4:28:30
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("..start");
		@SuppressWarnings("unchecked")
		List<Token> tokens = new JavaTokener(args[0]).getTokens();
		Expression buildAst = new AstBuilder().buildAst(tokens);
		Object rzt = new AstParser().parse(buildAst);
		System.out.println(rzt);
		System.out.println("...finish");
		
	}
	
	public  void parse(String args) {
		// TODO Auto-generated method stub
		System.out.println("..start");
		@SuppressWarnings("unchecked")
		List<Token> tokens = new JavaTokener(args).getTokens();
		Expression buildAst = new AstBuilder().buildAst(tokens);
		Object rzt = new AstParser().parse(buildAst);
		System.out.println(rzt);
		System.out.println("...finish");
		
	}

}

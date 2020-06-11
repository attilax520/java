/**
 * 
 */
package com.attilax.ast;

import java.util.ArrayList;
import java.util.List;

import antlr.Token;

/**
 * @author attilax 2016年9月5日 下午2:28:08
 */
public class AST {

	Token token;
	List<AST> children;

	public AST(Token token) {
		this.token = token;
	}

	public void addChild(AST t) {
		if (children == null)
			children = new ArrayList<AST>();
		children.add(t);
	}

}

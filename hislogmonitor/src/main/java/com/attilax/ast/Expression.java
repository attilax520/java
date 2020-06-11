package com.attilax.ast;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * java.lang.Object
org.eclipse.jdt.core.dom.ASTNode
org.eclipse.jdt.core.dom.Expression
 * Abstract base class of AST nodes that represent expressions. There are several kinds of expressions.
 * @author Administrator
 *
 */
public abstract  class Expression  extends ASTNode{
	
	public List getNodeList() {
		return nodeList;
	}
	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}
	public String[] getParamtypes() {
		return paramtypes;
	}
	public void setParamtypes(String[] paramtypes) {
		this.paramtypes = paramtypes;
	}
	public String getJsonname() {
		return jsonname;
	}
	public void setJsonname(String jsonname) {
		this.jsonname = jsonname;
	}
	List nodeList;
//	public MethodInvocation Exp;
	public String[] paramtypes;
	public String jsonname;
	public List arguments=Lists.newArrayList();

}

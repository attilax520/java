package com.attilax.ast;

import java.util.List;

import com.attilax.util.ExUtil;
import com.google.common.collect.Lists;

public class MethodInvocation  extends Expression  implements Cloneable {
	
//	  Expression EXPRESSIONn;  //ClassInstanceCreation [316+14]  new,,,SimpleName [230+8] static method
//String	NAME;
//	List ARGUMENTS;
	
 
	public void setExp(Expression exp) {
		Exp = exp;
	}

 

	public void setName(String name) {
		Name = name;
	}

	public List getArguments() {
		return arguments;
	}

	public void setArguments(List arguments) {
		this.arguments = arguments;
	}

	public static String getArgumentsProperty() {
		return ARGUMENTS_PROPERTY;
	}

	private static final String ARGUMENTS_PROPERTY = null;
	public Expression Exp = null;
	public Expression getExp() {
		return Exp;
	}

	public String Name = null;
	
 
	
	 public Object clone() {   
	        try {   
	            return super.clone();   
	        } catch (CloneNotSupportedException e) {   
	           ExUtil.throwExV2(e);
	        }
			return null;   
	    }   

}

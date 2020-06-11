/**
 * 
 */
package com.attilax.ast;

import java.util.List;

//import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
//import org.eclipse.jdt.core.dom.Expression;
//import org.eclipse.jdt.core.dom.Type;
//import org.eclipse.jdt.core.dom.TypeLiteral;

/**
 * @author attilax
 *2016年10月24日 下午1:17:34
 */
public class TypeLiteral extends Expression {
	 
/**
	 * @param text
	 */
	public TypeLiteral(String text) {
		this.type=text;
	}

	//	private static final List PROPERTY_DESCRIPTORS;
	 String type = null;
}

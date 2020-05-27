package com.kok.sport.utils.ql;

import java.util.Map;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.google.common.collect.Maps;

public class QlSpelUtil {
	
	public static void main(String[] args) {
//		String expressionString = "#root['data'][0]['id']";
//		
//		  expressionString = "#root['data'].?[['id']<10003]";
//		//"#myMap.?[key.length()<5&&value>90]"
//		Object result1 = QlSpelUtil.query(m, expressionString); 
//		System.out.println(result1);
		
		Map m=Maps.newConcurrentMap();
		m.put("name",666);
		String str = "我的名字是#{[name]}"
				+ ",体重是#{[weight]}";
		
		String value = parse(str, m);
				System.out.println(value);
				// 将使用p1对象name、height填充上面表达式模板中的#{}

		 
 	System.out.println("ff");
	}

	/**
	 * str  var insert
	 * @param strTmp
	 * @param m
	 * @return
	 */
	public static String parse(String strTmp, Map m) {
		// 创建一个ExpressionParser对象，用于解析表达式
				ExpressionParser parser = new SpelExpressionParser();
				//------------在SpEL中使用表达式模板-----------
			
				
				Expression expr3 = parser.parseExpression(strTmp, new TemplateParserContext());
				// 将使用p1对象name、height填充上面表达式模板中的#{}
				String value = (String) expr3.getValue(m);
		return value;
	}
/**
 *   ExpressionParser parser = new SpelExpressionParser();

	        Expression exp = parser.parseExpression(e);
	       
	        System.out.println(exp.getValue());
 * @param m
 * @param expressionString
 * @return
 */
	public static Object query(Map m, String expressionString) {
		ExpressionParser parser = new SpelExpressionParser();  	//1.访问root对象属性  
		 
//	  m=Maps.newConcurrentMap();
//	m.put("ret",220);


		StandardEvaluationContext context = new StandardEvaluationContext(m);  
//	context.setRootObject(null); 
	//	System.out.println( parser.parseExpression("#root['ret']").getValue(context));
		Object result1 = parser.parseExpression(expressionString).getValue(context);
		return result1;
	}

}
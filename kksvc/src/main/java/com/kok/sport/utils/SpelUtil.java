package com.kok.sport.utils;

import org.apache.logging.log4j.LogManager;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.kok.sport.utils.ql.MethodRunner;

public class SpelUtil {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(MethodRunner.class);
	public static void main(String[] args) {
		String e="T(com.kok.sport.utils.constant.OddsLive).oddsLive()";
		   ExpressionParser parser = new SpelExpressionParser();

	        Expression exp = parser.parseExpression(e);
	       
	        Object value = exp.getValue();
			//System.out.println(value);
	        logger.info(">>MethodRunner.exe:");
	        logger.info(value);

	}

}

package com.attilax.ast;

public class SimpleName  extends Expression{
public SimpleName(String text) {
	this.IDENTIFIER=text;
	}

public String	IDENTIFIER;


public String toString()
{
	return this.IDENTIFIER;
}
}

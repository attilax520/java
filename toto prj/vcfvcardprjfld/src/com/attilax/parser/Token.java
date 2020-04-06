package com.attilax.parser;

//import com.attilax.ast.Expression;

public class Token  {

	public   String value="";
	
	  public  String Type;
	  public  String Text;

	public Token(String curTokenTxt) {
		this.Text=curTokenTxt;
	}

	public Token() {
		// TODO Auto-generated constructor stub
	}

 

	public Token setType(String type) {
		Type = type;
		return this;
	}

 

	public Token setText(String text) {
		Text = text;return this;
	}

 
	public void setValue(String value) {
		this.value = value;
	}

}

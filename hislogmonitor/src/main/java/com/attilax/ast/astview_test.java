package com.attilax.ast;

import java.io.IOException;

import com.attilax.fsm.Token;
import com.attilax.net.HttpUtil;

public class astview_test {
	
	public static void main(String[] args) throws IOException {
		System.out.println("..start down");
//		new Token().setType("tp").setText("txt").setText("txt22");
		HttpUtil.down("http://www.winrar.com.cn/download/wrar540scp.exe", "c:\\0down.exe");
//	new HttpUtil().down("http://www.winrar.com.cn/download/wrar540scp.exe", "c:\\0down.exe");
		System.out.println("--ok");
	}

}

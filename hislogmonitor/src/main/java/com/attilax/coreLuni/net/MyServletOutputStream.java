package com.attilax.coreLuni.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
/**
 * 包装ServletOutputStream ，改写write 方法。
 * @author 贺佐安
 *
 */
public class MyServletOutputStream extends ServletOutputStream {
    private ByteArrayOutputStream bos = null ; 
    public MyServletOutputStream (ByteArrayOutputStream bos) {
        this.bos = bos ;
    }
    public void write(int b) throws IOException {
        bos.write(b) ;
    }
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setWriteListener(WriteListener writeListener) {
		// TODO Auto-generated method stub
		
	}
}
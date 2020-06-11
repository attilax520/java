package com.attilax.ref;

import static org.junit.Assert.*;

import org.junit.Test;

public class methodRunnerByclassfileTest {

	@Test
	public void testMain() {
		
		String cmd="  "
		   // 模拟命令行参数
	      String[] args = { "-dir", ".", "-t" };
		methodRunnerByclassfile.main( );
	}

}

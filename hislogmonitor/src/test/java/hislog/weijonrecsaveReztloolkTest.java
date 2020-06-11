package hislog;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class weijonrecsaveReztloolkTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLookrzt() {
		//fail("Not yet implemented");
		new weijonrecsaveReztloolk().lookrzt( new testdbService().getConnection() );
	}
	
	
	@Test
	public   void main( ) {
		for(int i=71;i<100;i++)
		{
			String tmp="VCF@n@=\"${VCF@n@}\"";
			tmp=tmp.replaceAll("@n@", String.valueOf(i));
			System.out.print(" "+tmp);
		}
	}
	

}

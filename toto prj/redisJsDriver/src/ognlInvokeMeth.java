import com.google.common.collect.Maps;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.SetPropertyAccessor;


//ognlInvokeMeth
public class ognlInvokeMeth {
	
	public static void methStatic() {
		System.out.println("--meth stt");
	}
	
	public   void methDync() {
		System.out.println("--meth dync");
	}
	
	public static void main(String[] args) throws OgnlException {
		
	//	态方法的调用. <br>  注意静态方法的调用，格式是@包名.类名@方法名
	//	动态方法的调用.<br/>  注意动态方法的调用，格式是new 包名.类名().方法名
		 OgnlContext context=new OgnlContext();
      //   Study study=new Study();
          context.setRoot(Maps.newConcurrentMap());
	//	  Object expression = Ognl.parseExpression("@java.lang.Math@random()");
    //      Object expression = Ognl.parseExpression("@ognlInvokeMeth@methStatic()");  //ok  if staic meth
       //   Object expression = Ognl.parseExpression("new ognlInvokeMeth().methDync()");  //ok  if dyn meth
          Object expression = Ognl.parseExpression(" @java.lang.Runtime@getRuntime().exec('calc')"); 
         
   //       老牌的Ognl(老到网站都找不到了) 新来的MVEL 国产的Aviator 目前最快的JSEL:JSEL测试表达式
          
          Object value1 = Ognl.getValue(expression, context, context.getRoot());
          System.out.println(value1);
          
          
 
	}

}

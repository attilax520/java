/**
 * @author attilax 老哇的爪子
	@since  2014-6-17 下午03:31:22$
 */
package com.focustar.util;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.attilax.core;
import com.attilax.dotnet.System.Web.HttpContext;
import com.attilax.io.filex;
import com.attilax.net.requestImp;
import com.attilax.sso.loginUtil;
import com.attilax.util.tryX;

import cn.freeteam.dao.OperlogsMapper;
import cn.freeteam.model.Operlogs;
import cn.freeteam.util.MybatisSessionFactory;
import cn.freeteam.util.OperLogUtil;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-17 下午03:31:22$
 */
public class OperLogUtil4vod {
	
	public static void main(String[] args) {
		HttpContext.Current.set(new HttpContext());
		 requestImp req = new requestImp();
		 req.setRemoteAddr("125.235.236.237");
	//	 req.getRemoteAddr()
		HttpContext.Current.get().Request.set(req);
		log("tttt");
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-17 下午03:31:35$
	
	 * @param name
	 * @param string
	 * @param request
	 */	@SuppressWarnings("static-access")
	public static void log(final  String string) {
		// attilax 老哇的爪子  下午03:31:35   2014-6-17 
		
		try {
		
			final HttpServletRequest request = (HttpServletRequest) HttpContext.Current.get().Request.get();
			core.execMeth_Ays(new Runnable() {
				
				@Override
				public void run() {
					try {
					
						// attilax 老哇的爪子  下午03:37:57   2014-6-17 
						String curName=loginUtil.getuname(request) ;
						OperLogUtil.log(curName ,string, request);	
					//	return null; 
					} catch (Exception e) {
						 
					}
					
				}
			}, "oplogerThread"+filex.getUUidName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		new tryX<Object>(){
//
//			@Override
//			public Object item(Object t) throws Exception {
//				final
//				
//			}
//	
//		}.$("");
//	
		
	}
	//  attilax 老哇的爪子 下午03:31:22   2014-6-17   
}

//  attilax 老哇的爪子
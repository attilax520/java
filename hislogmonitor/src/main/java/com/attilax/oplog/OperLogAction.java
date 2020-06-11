package com.attilax.oplog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.attilax.util.Global;
import com.attilax.web.requestImp;

import chrriis.dj.nativeswing.swtimpl.demo.examples.webbrowser.Ati4vod_jujobak;

//import com.attilax.core;

import cn.freeteam.dao.OperlogsMapper;
import cn.freeteam.model.Operlogs;
import cn.freeteam.model.OperlogsExample;
import cn.freeteam.model.RoleFunc;
import cn.freeteam.model.OperlogsExample.Criteria;
import cn.freeteam.util.MybatisSessionFactory;
import cn.freeteam.util.Pager;

/**
 * 
 * <p>
 * Title: OperLogAction.java
 * </p>
 * 
 * <p>
 * Description: 操作日志
 * </p>
 * 
 * <p>
 * Date: Dec 14, 2011
 * </p>
 * 
 * <p>
 * Time: 10:45:59 PM
 * </p>
 * 
 * <p>
 * Copyright: 2011
 * </p>
 * 
 * <p>
 * Company: freeteam
 * </p>
 * 
 * @author freeteam
 * @version 1.0
 * 
 *          <p>
 *          ============================================
 *          </p>
 *          <p>
 *          Modification History
 *          <p>
 *          Mender:
 *          </p>
 *          <p>
 *          Date:
 *          </p>
 *          <p>
 *          Reason:
 *          </p>
 *          <p>
 *          ============================================
 *          </p>
 */
public class OperLogAction extends BaseAction {

	/**
	 * 
	 * @author attilax 老哇的爪子
	 * @since o78 h3748$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MybatisSessionFactory.CONFIG_FILE_LOCATION = "/mybatis_postgresql.xml";
		OperLogAction.request4test = new requestImp();
		String s = t2_json();
		System.out.println("---f" + s);
	}

	private static String t2_json() {
		return "";
		  
	}

	private static String t1() {
		OperLogAction c = new OperLogAction();
	//	c.request4test = new requestImp();
		String s = c.list();		
		System.out.println(" list size:"+c.operlogList.size());
		return s;
	}

	private Operlogs operlog;
	private OperlogsMapper operlogsMapper; // dao imp o6c
	private List<Operlogs> operlogList;
	private String order = "opertime desc";

	public OperLogAction() {
		initMapper("operlogsMapper");
	}
	
	public String list2(String where)
	{
		HttpServletRequest	req = null;
		 
		try {
			req=(HttpServletRequest) Global.curReq.get();
			if(operlog==null)
				operlog=new Operlogs();
			operlog.setLoginname(req.getParameter("operlog.loginname"));  //operlog.loginname
			operlog.setContent(req.getParameter("operlog.content"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			currPage=Integer.parseInt(req.getParameter("pageTextbox"));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			 
			pageSize=Integer.parseInt(req.getParameter("pageSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 list();	
		Map<String ,Object> m=new HashMap<>();
		m.put("operlogList",  operlogList);
		m.put("totalCount",  totalCount);
		
		/*
		 com.alibaba.fastjson.JSON.toJSONString(ExecutorService1_theardpool, new PropertyPreFilter() {

			 

			 @Override

			 public boolean apply(JSONSerializer arg0, Object arg1, String prop) {

			 //// rt false //not show

			 if (prop.equals("queue"))

			 return false;

			 else

			 return true;

			 }

			 }, new SerializerFeature[] { SerializerFeature.PrettyFormat }));
			 */
		return com.alibaba.fastjson.JSON.toJSONString(m,true);
		
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	public String list() {
		// core.log("--o6clog enter");
		if (order.trim().length() == 0) {
			order = " opertime desc ";
		}
		OperlogsExample example = new OperlogsExample();
		Criteria criteria = example.createCriteria();
		if (operlog != null && operlog.getLoginname() != null && operlog.getLoginname().trim().length() > 0) {
			criteria.andLoginnameLike("%" + operlog.getLoginname().trim() + "%");
		}
		if (operlog != null && operlog.getContent() != null && operlog.getContent().trim().length() > 0) {
			criteria.andContentLike("%" + operlog.getContent().trim() + "%");
		}
		if (operlog != null && operlog.getIp() != null && operlog.getIp().trim().length() > 0) {
			criteria.andIpLike("%" + operlog.getIp().trim() + "%");
		}
		if (order != null && order.trim().length() > 0) {
			example.setOrderByClause(order);
		}
		// 非admin用户只能查看自己的操作日志o6c del
		// if (!isAdminLogin()) {
		// criteria.andLoginnameEqualTo(getLoginName());
		// }
		// criteria.
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);

		// ob0 ret rzt operlogsMapper is proxy
		operlogList = operlogsMapper.selectPageByExample(example);
		totalCount = operlogsMapper.countByExample(example);

		// core.log("--load log count:"+String.valueOf(totalCount));
		Pager pager = new Pager(getHttpRequest());
		pager.appendParam("operlog.loginname");
		pager.appendParam("operlog.content");
		pager.appendParam("operlog.ip");
		pager.appendParam("pageSize");
		pager.appendParam("pageFuncId");
		pager.setCurrPage(currPage);
		pager.appendParam("order");
		pager.setPageSize(pageSize);
		pager.setTotalCount(totalCount); //all rows count：
		pager.setOutStr("operLog_list");// todox page_goto_url for gene goto url
										// contolrs
		pageStr = pager.getOutStr();
		return "list";
	}

	public Operlogs getOperlog() {
		return operlog;
	}

	public void setOperlog(Operlogs operlog) {
		this.operlog = operlog;
	}

	public OperlogsMapper getOperlogsMapper() {
		return operlogsMapper;
	}

	public void setOperlogsMapper(OperlogsMapper operlogsMapper) {
		this.operlogsMapper = operlogsMapper;
	}

	public List<Operlogs> getOperlogList() {
		return operlogList;
	}

	public void setOperlogList(List<Operlogs> operlogList) {
		this.operlogList = operlogList;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}

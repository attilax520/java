package com.cnhis.cloudhealth.common.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.cnhis.cloudhealth.common.utils.Global;

/**
 * 分页类
 * 
 * @author Liujian
 * @version 2013-7-2
 * @param <T>
 */
public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int     pageNo    = 1;                                                  // 当前页码
//    private int pageSize = 50;
    private int pageSize = Integer.parseInt(Global.getConfig("page.pageSize"));
    
    private long    count;                                                          // 总记录数，设置为“-1”表示不查询总数

    private int     first;                                                          // 首页索引
    private int     last;                                                           // 尾页索引
    
    private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc

    private List<T> list      = new ArrayList<T>();

    public Page() {
        this.pageSize = -1;
    }

    /**
     * 构造方法
     * 
     * @param request
     *            传递 repage 参数，来记住页码
     */
    public Page(HttpServletRequest request) {
        this(request, -2);
    }

    /**
     * 构造方法
     * 
     * @param request
     *            传递 repage 参数，来记住页码
     * @param response
     *            用于设置 Cookie，记住页码
     * @param defaultPageSize
     *            默认分页大小，如果传递 -1 则为不分页，返回所有数据
     */
    public Page(HttpServletRequest request, int defaultPageSize) {
        // 设置页码参数（传递repage参数，来记住页码）
        String no = request.getParameter("pageNo");
        if (StringUtils.isNumeric(no)) {
            this.setPageNo(Integer.parseInt(no));
        }
        
        // 设置页面大小参数（传递repage参数，来记住页码大小）
        String size = request.getParameter("pageSize");
        if (StringUtils.isNumeric(size)) {
            this.setPageSize(Integer.parseInt(size));
        } else if (defaultPageSize != -2) {
            this.pageSize = defaultPageSize;
        }
        
        // 设置排序参数
		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isNotBlank(orderBy)){
			this.setOrderBy(orderBy);
		}
    }

    /**
     * 构造方法
     * 
     * @param pageNo
     *            当前页码
     * @param pageSize
     *            分页大小
     */
    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, 0);
    }

    /**
     * 构造方法
     * 
     * @param pageNo
     *            当前页码
     * @param pageSize
     *            分页大小
     * @param count
     *            数据条数
     */
    public Page(int pageNo, int pageSize, long count) {
        this(pageNo, pageSize, count, new ArrayList<T>());
    }

    /**
     * 构造方法
     * 
     * @param pageNo
     *            当前页码
     * @param pageSize
     *            分页大小
     * @param count
     *            数据条数
     * @param list
     *            本页数据对象列表
     */
    public Page(int pageNo, int pageSize, long count, List<T> list) {
        this.setCount(count);
        this.setPageNo(pageNo);
        this.pageSize = pageSize;
        this.list = list;
    }

    /**
     * 初始化参数
     */
    public void initialize() {

        // 1
        this.first = 1;

        this.last = (int) (count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);

        if (this.count % this.pageSize != 0 || this.last == 0) {
            this.last++;
        }

        if (this.last < this.first) {
            this.last = this.first;
        }

        if (this.pageNo <= 1) {
            this.pageNo = this.first;
        }

        if (this.pageNo >= this.last) {
            this.pageNo = this.last;
        }

        // 2
        if (this.pageNo < this.first) {// 如果当前页小于首页
            this.pageNo = this.first;
        }

        if (this.pageNo > this.last) {// 如果当前页大于尾页
            this.pageNo = this.last;
        }
    }

    /**
     * 获取设置总数
     * 
     * @return
     */
    public long getCount() {
        return count;
    }

    /**
     * 设置数据总数
     * 
     * @param count
     */
    public void setCount(long count) {
        this.count = count;
        if (pageSize >= count) {
            pageNo = 1;
        }
    }

    /**
     * 获取当前页码
     * 
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页码
     * 
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取页面大小
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小（最大500）
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;// > 500 ? 500 : pageSize;
    }

    /**
     * 获取本页数据对象列表
     * 
     * @return List<T>
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置本页数据对象列表
     * 
     * @param list
     */
    public Page<T> setList(List<T> list) {
        this.list = list;
        initialize();
        return this;
    }

    /**
     * 获取 Hibernate FirstResult
     */
    @JsonIgnore
    public int getFirstResult() {
        int firstResult = (getPageNo() - 1) * getPageSize();
        if (firstResult >= getCount()) {
            firstResult = 0;
        }
        return firstResult;
    }

    /**
     * 获取 Hibernate MaxResults
     */
    @JsonIgnore
    public int getMaxResults() {
        return getPageSize();
    }
    
    /**
	 * 获取查询排序字符串
	 * @return
	 */
	@JsonIgnore
	public String getOrderBy() {
		// SQL过滤，防止注入 
		String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
					+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		if (sqlPattern.matcher(orderBy).find()) {
			return "";
		}
		return orderBy;
	}

	/**
	 * 设置查询排序，标准查询有效， 实例： updatedate desc, name asc
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}
	
}

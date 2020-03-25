package org.chwin.firefighting.apiserver.data;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import ognl.OgnlException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.chwin.firefighting.apiserver.dsl.velocityUtil;
import org.chwin.firefighting.apiserver.net.RequestImpAdv;
import org.chwin.firefighting.apiserver.net.RequestUtil;
import org.chwin.firefighting.apiserver.net.ResponseUtil;
import org.chwin.firefighting.apiserver.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api("业务流程操作api")
@Controller
@Component
public class Processor {
    @Autowired
    public HttpServletRequest req;
    @Autowired
    public HttpServletResponse res;

    public static void main(String[] args) throws Exception {
        System.out.println("--");
        //   org.apache.ibatis.reflection.MetaObject.forObject
        //  pagetest();
        RequestImpAdv ri = new RequestImpAdv();

        ri.setParam("type", "故障");

        Processor dataExecuter1 = new Processor();
        dataExecuter1.req = ri;
//        dataContrler.res = resI;
        System.out.println(dataExecuter1.selectList("warning_query", 1, 2));

        System.out.println(new Processor().addUinfoParam("sp1(123)"));
    }

    public static String HostAppPrefix;

    //@Autowired
//    SqlSession SqlSession1;
    //  http://localhost:8088/startProcess?process_name=warning_query
    //  &pagenum=1&pagesize=2
    @ApiOperation(value = "执行业务流程操作 通过接口", notes = "说明note")
    @RequestMapping(value = "/startProcess")
    @ResponseBody
    @ApiParam(name = "tkid", value = "用户标识id", required = true)

    @ApiImplicitParams({
            @ApiImplicitParam(name = "tkid", value = "用户标识的id", dataType = "string", paramType = "query", required=true),
            @ApiImplicitParam(name = "uname", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Descript ", value = "描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type ", value = "类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ProblemReporter ", value = "问题上报人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "TaskName ", value = "名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pagenum ", value = "pagenum", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pagesize ", value = "pagesize", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Status  ", value = "状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ActualTimeStart ", value = "实际处理时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ActualTimeEnd ", value = "实际处理时间截止", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Handler  ", value = "处理人", dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "Property  ", value = "物业", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Property_id ", value = "物业id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Maintenance  ", value = "维保公司", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Maintenance_id ", value = "维保公司id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "Maintenance_personnel_id  ", value = "维保人员id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ProcessTime ", value = "应处理时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "warn_id ", value = "关联的隐患信息id", dataType = "string", paramType = "query")





    })
   public String startProcess(@ApiParam(name = "process_name", value = "流程名称", required = true) String process_name) {
        ResponseUtil.setAccessControlAllowOrigin(res);//cookie cross domain
        try {
            Map paramMap = RequestUtil.getMap(req);
            //add login user info
            injectAuthinfo(paramMap);
            if (paramMap.get("pagenum") != null) {
                int pagenum = Integer.parseInt(paramMap.get("pagenum").toString());

                int pagesize = Integer.parseInt(paramMap.get("pagesize").toString());
                PageInfo pageInfo = MybatisUtil.selectList(process_name, paramMap, pagenum, pagesize);
                return (JSON.toJSONString(pageInfo, true));

            }
            //else mode ..not page mode,list mode
            Object pageInfo = MybatisUtil.selectList(process_name, paramMap);
            return (JSON.toJSONString(pageInfo, true));

        } catch (Exception e) {
            logger.error(e);
            return ExceptionUtil.getExceptionJson(e);
        }

    }

    private void injectAuthinfo(Map paramMap) throws IOException, OgnlException {
        List<Map> umapList = MybatisUtil.selectList("listRecentlyLoginUsrMood", paramMap);
        if (umapList.size() == 0)
            throw new RuntimeException("notlogin");
        paramMap.put("loginUserInfo", umapList.get(0));
    }

    //  http://localhost:8088/selectList?select_id=warning_query&pagenum=1&pagesize=2
    @ApiOperation(value = "执行查询操作 通过接口", notes = "请求数据说明note")

    @ResponseBody
    public String selectList(String select_id, int pagenum, int pagesize) throws Exception {
        ResponseUtil.setAccessControlAllowOrigin(res);//cookie cross domain
        try {
            Map paramMap = RequestUtil.getMap(req);
            PageInfo pageInfo = MybatisUtil.selectList(select_id, paramMap, pagenum, pagesize);
            return (JSON.toJSONString(pageInfo, true));

        } catch (Exception e) {
            return ExceptionUtil.getExceptionJson(e);
        }

    }


    private static void pagetest() throws IOException, OgnlException {
        String sql_id = "warning_query";
        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
        //	api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

        //设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
        PageHelper.startPage(1, 3, true);
        // PageHelper.startPage(1,10);
//此时已经分页了
        List<Map> rzt_list = session.selectList(sql_id, null);

        //  可以使用PageInfo 查看分页信息
        PageInfo pageInfo = new PageInfo<>(rzt_list);
        System.out.println(JSON.toJSONString(pageInfo));
        System.out.println(JSON.toJSONString(rzt_list, true));
    }

    @ApiOperation(value = "执行查询视图", notes = "说明note")

    @ResponseBody
    public String executeQueryView(String view) throws Exception {
        ;
        String rzt = JSON.toJSONString(MybatisUtil.executeQueryView(view), true);
        return rzt;
    }

    //with auth
    @ApiOperation(value = "执行sp", notes = "说明note")

    @ResponseBody
    private String executeSp(String sp) throws Exception {

        //repalce cur usre info with cookie userinfo
        sp = addUinfoParam(sp);
        sp = "call " + sp;
        String rzt = JSON.toJSONString(MybatisUtil.executeSql(sp), true);
        return rzt;
    }

    // @ApiOperation(value = "执行sp", notes = "说明note")
    private static Logger logger = Logger.getLogger(Processor.class);

    @ResponseBody
    private String executeSpSecury(String sp) throws Exception {

        //repalce cur usre info with cookie userinfo
        //  sp = addUinfoParam(sp);
        sp = "call " + sp;
        String rzt = JSON.toJSONString(MybatisUtil.executeSql(sp), true);
        return rzt;
    }

    private String addUinfoParam(String sp) {
        String u_col = " '$uname','$usertype' ";
        //创建context
        VelocityContext context = new VelocityContext();
//添加数据
        String uname = CookieUtil.getValue(req, "curUserInfo.uname");
        context.put("uname", uname);
        context.put("usertype", CookieUtil.getValue(req, "curUserInfo.usertype"));

        u_col = velocityUtil.getTmpltCalcRzt(u_col, context);
        sp = sp.substring(0, sp.length() - 1) + "," + u_col + ")";
        return sp;
    }


}

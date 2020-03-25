package org.chwin.firefighting.apiserver.data;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import groovy.xml.XmlUtil;
import io.swagger.annotations.ApiOperation;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.velocity.VelocityContext;
import org.chwin.firefighting.apiserver.dsl.velocityUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;



public class MybatisUtil {

    public static void main(String[] args) throws Exception {

        //System.out.println( new MybatisUtil());
//       Map dbcfg=SpringUtil.getDbcfg();
//         SqlSessionFactory sqlSessionFactory =MybatisUtil. getSqlSessionFactory();
//        System.out.println(dbcfg);
      // System.out.println( MybatisUtil.selectList("warning_query",null));
        MybatisUtil.selectList("variTest",null);
        System.out.println((float)(314*9+381*3-3500)/3500f);
        System.out.println((float)(4336-3600)/3600);
        Map m=Maps.newLinkedHashMap();
        m.put("TaskName","最近几天通道情况隐患");
        m.put("HiddenDescript","通道堵塞");
        m.put("place","余杭区文一西路");
        m.put("ProblemReporter","刘媛");

      //  System.out.println( MybatisUtil.selectList("上报隐患流程",m));

        m.put("Property","xxx物业公司");
        m.put("id",1);
        System.out.println( MybatisUtil.selectList("管委会派单给物业流程",m));


    }

    public static SqlSessionFactory getSqlSessionFactory() throws IOException, OgnlException {
        String mybatisCfg_result = getCfgTxt();


      //  System.out.println(mybatisCfg_result);
        InputStream is2=new ByteArrayInputStream(mybatisCfg_result.getBytes());
        // ����sqlSession �Ĺ���
        return new SqlSessionFactoryBuilder().build(is2);
    }

    public static String getCfgTxt() throws IOException, OgnlException {
        String resource = "/mybatis.xml";
        // ����mybatis �������ļ�����Ҳ���ع�����ӳ���ļ���
        //ClassLoader classLoader = .getClassLoader();
        InputStream is = mybatisdemo.class.getResourceAsStream(resource);
        String mybatisCfg_result = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));

        String getCfgFile=SpringUtil.getCfgFile();
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        Object mObject=yaml.load(mybatisdemo.class.getResourceAsStream(getCfgFile));
        Object expression = Ognl.parseExpression("spring.datasource.url");
        Object url = Ognl.getValue(expression, mObject);
        Object usr = Ognl.getValue(Ognl.parseExpression("spring.datasource.username"), mObject);
        Object pwd = Ognl.getValue(Ognl.parseExpression("spring.datasource.password"), mObject);
        if(pwd==null)pwd="";
        url= XmlUtil.escapeXml(url.toString()+"&allowMultiQueries=true");

        mybatisCfg_result=mybatisCfg_result.replaceAll("\\$\\{mysql.url}",url.toString());
        mybatisCfg_result=mybatisCfg_result.replaceAll("\\$\\{mysql.username}",usr.toString());

        mybatisCfg_result=mybatisCfg_result.replaceAll("\\$\\{mysql.password}",pwd.toString());
        return mybatisCfg_result;
    }



    public static List executeQueryView(String view) throws  Exception {

        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
//"call sp1(2)"
        MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

        String sql="select "+view;
        List<Map> li = mapper.query(sql);

        return li;
    }

    public static Object executeSp(String sp) throws Exception {
        //, HttpServletResponse res
        //repalce cur usre info with cookie userinfo
//        if (sp.contains("@当前用户信息@")) {
//
//            String u_col = " '$uname','$usertype' ";
//            //创建context
//            VelocityContext context = new VelocityContext();
////添加数据
//            String uname = CookieUtil.getValue(req, "curUserInfo.uname");
//            context.put("uname", uname);
//            context.put("usertype", CookieUtil.getValue(req, "curUserInfo.usertype"));
//
//            u_col = velocityUtil.getTmpltCalcRzt(u_col, context);
//            sp = sp.replaceAll("@当前用户信息@", u_col);
//
//        }
        sp = "call " + sp;
        return executeSql(sp );

    }


    public static List executeSql(String sql) throws IOException, OgnlException {
        SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
//"call sp1(2)"
        MybatisMapperCls mapper = session.getMapper(MybatisMapperCls.class);

        List<Map> li = mapper.query(sql);

        session.close();
        return li;
//        res.getWriter().write(rzt);
//        res.getWriter().flush();
//        res.flushBuffer();
    }

    public static PageInfo executeQuery(String select_id) throws IOException, OgnlException {
        SqlSessionFactory sqlSessionFactory = MybatisUtil. getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
        //	api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

        //设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
        PageHelper.startPage(1, 10, true);
        // PageHelper.startPage(1,10);
//此时已经分页了
        List<Map> rzt_list = session.selectList(select_id,null);

        //  可以使用PageInfo 查看分页信息
        PageInfo pageInfo = new PageInfo<>(rzt_list);
return pageInfo;
    }

    public static PageInfo executeQuery(String select_id, Map paramMap) throws IOException, OgnlException {
        SqlSessionFactory sqlSessionFactory = MybatisUtil. getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
        //	api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

        //设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
        PageHelper.startPage(1, 10, true);
        // PageHelper.startPage(1,10);
//此时已经分页了
        List<Map> rzt_list = session.selectList(select_id,paramMap);

        //  可以使用PageInfo 查看分页信息
        PageInfo pageInfo = new PageInfo<>(rzt_list);
        return pageInfo;

    }


    public static PageInfo selectList(String select_id, Map paramMap,int pageNum,int pagesize) throws IOException, OgnlException {
        SqlSessionFactory sqlSessionFactory = MybatisUtil. getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
        //	api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

        //设置分页条件，Parameters:pageNum 页码pageSize 每页显示数量count 是否进行count查询
        PageHelper.startPage(pageNum, pagesize, true);
        // PageHelper.startPage(1,10);
//此时已经分页了
        List<Map> rzt_list = session.selectList(select_id,paramMap);

        //  可以使用PageInfo 查看分页信息
        PageInfo pageInfo = new PageInfo<>(rzt_list);
        return pageInfo;

    }


    public static List selectList(String select_id, Map paramMap) throws IOException, OgnlException {
        SqlSessionFactory sqlSessionFactory = MybatisUtil. getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession(true);
        //	api ��Ϊ[ openSession(boolean autoCommit) ]���ò���ֵ���������Ƹ� sqlSession �Ƿ��Զ��ύ��true��ʾ�Զ��ύ��false��ʾ���Զ��ύ[���޲εķ�������һ�£������Զ��ύ]

      //  session.select();
        List<Map> rzt_list = session.selectList(select_id,paramMap);


        return rzt_list;

    }
}

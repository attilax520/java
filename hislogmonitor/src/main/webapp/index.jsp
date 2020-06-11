<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>系统管理首页</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />  <!--以IE8模式解析页面-->
    
</head>
<body>
<!-----------------头部开始--------------------->
<jsp:include page="/include/head.jsp"></jsp:include>
<!-----------------头部结束--------------------->
<!--------------------左侧菜单开始-menu-------------------------->
<jsp:include page="/include/leftmenu.jsp"></jsp:include>
<!--------------------左侧菜单结束-menu-------------------------->

<!--main-container-part-->
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="index.html" title="Go to Home" class="tip-bottom"><i class="icon-home"></i>首页</a></div>
  </div>
  <!-- 数据表格一 -->
  <!-- <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>企业授权到期提醒 （6个）</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table table-responsive text-center">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>医院编码</th>
                  <th>医院名称</th>
                  <th>简称</th>
                  <th>授权日期</th>
                  <th>开始日期</th>
                  <th>结束日期</th>
                  <th>联系人</th>
                  <th>联系电话</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeX">
                  <td>1</td>
                  <td>10001</td>
                  <td>西安高新医院</td>
                  <td>高新医院</td>
                  <td>2018-02-03</td>
                  <td>2018-02-03</td>
                  <td>2018-12-03</td>
                  <td>小王</td>
                  <td>18798748747</td>
                  <td>重新授权</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div> -->


</div>
<script type="text/javascript">

</script>
</body>
</html>

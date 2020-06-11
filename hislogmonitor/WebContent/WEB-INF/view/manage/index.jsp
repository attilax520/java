<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<!-- web inf/view/maanger/index.jsp  626 -->
<html>
<head>
    <title>系统管理首页</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />  <!--以IE8模式解析页面-->
    <link rel="shortcut icon" href="${ctxStatic}/img/favicon.ico" />
    <script src="${ctxStatic}/js/jquery.min.js"></script>
   
</head>
<style>
.pager{height:30px;line-height:30px;font-size: 12px;margin: 0px 0px;text-align: center;color: #2E6AB1;overflow: hidden;}
.pager a{border:1px solid #5bb75b;color:#5bb75b;margin:0px 5px;padding:5px 8px;text-decoration: none;}
.pager a.hover,.pager a.active{background-color:#5bb75b;border-color:#5bb75b;color:#FFF;}
.pager a.disabled{color:#C8CDD2;cursor:auto;}
</style>
<body>

	


<!-----------------头部开始--------------------->
<jsp:include page="/include/head.jsp"></jsp:include>
<!-----------------头部结束--------------------->
<!--------------------左侧菜单开始-menu-------------------------->
<%-- <jsp:include page="/include/leftmenu.jsp"></jsp:include> --%>
<!--------------------左侧菜单开始-menu-------------------------->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 默认首页</a>




  <ul style="display: none00">
			<li><a href="javascript:void(0)"><i class="icon icon-home"></i> <span>首页</span></a> </li> 
    <li class="active"><a href=javascript:void(0)"><i class="icon icon-home"></i> <span>				服务器地址连接信息:</span></a> </li>
   
     <li class="">  	<iframe src="../left.htm" width="100%" height='100px' id="ifrmx" border="0" frameborder="0"  style="overflow-x:hidden;overflow:hidden" ></iframe> </li>    
       
        
  </ul>
</div>
<!--------------------左侧菜单结束-menu-------------------------->




<!--main-container-part 626a123-->
<div id="content">
	<div id="content-header" style="margin-top:0px">
		<div id="breadcrumb" style="display: none">
			<a href="${ctxWeb}/license/licenseList" class="tip-bottom">
				<i class="icon-home"></i>
			</a>
		</div>
	</div>
	<!-- 数据表格一 -->
	<!--  main start -->
	 
	<div class="container-fluid000" id="divs626"> 
		<iframe src="../moniplat/main.htm" width="100%" height='1000px'></iframe>

	</div>

	<!--  main end s626-->
</div>



<!--  tmplt626-->
<div id="tmplt626">


		<div class="container-fluid000" id="divs626a"> 
				<iframe src="../../autoup/main.htm" width="100%" height='1000px'></iframe>
		
			</div>

</div>

<script>//alert()</script>




<script >
window.setTimeout(function(){
//	alert()

//$("#content").append(	$("#tmplt626").html());

},3000);


</script>



</body>

 
 
<script>
	var count = ${page.count};//总数
	var first = ${page.first};//首页索引
	var last = ${page.last};//尾页索引
	var pageNo = ${page.pageNo};//当前页
	var pageSize = ${page.pageSize};//每页条数
	
	var bloccount = ${blocList.count};//总数
	var blocfirst = ${blocList.first};//首页索引
	var bloclast = ${blocList.last};//尾页索引
	var blocpageNo = ${blocList.pageNo};//当前页
	var blocpageSize = ${blocList.pageSize};//每页条数
	
 
	//展开
	function changeTypeOpen(id){
		$(".em_"+id).removeClass("hide");
	    $("#btnOpen_"+id).addClass("hide");
	    $("#btnClose_"+id).removeClass("hide");
	}
	//收起
	function changeTypeClose(id){
		$(".em_"+id).addClass("hide");
		$("#btnOpen_"+id).removeClass("hide");
		$("#btnClose_"+id).addClass("hide");
	}

 
</script>
</html>

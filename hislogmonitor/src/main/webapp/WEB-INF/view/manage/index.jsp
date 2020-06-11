<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
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
  <ul>
    <li class="active"><a href="${ctxWeb}/license/licenseList"><i class="icon icon-home"></i> <span>首页</span></a> </li>
    <li> <a href="${ctxWeb}/license/enterpriseLicenseList"><i class="icon-angle-right"></i> <span>企业授权</span></a> </li>
    <li><a href="${ctxWeb}/license/blocLicenseList"><i class="icon-angle-right"></i> <span>集团授权</span></a></li>
  </ul>
</div>
<!--------------------左侧菜单结束-menu-------------------------->

<!--main-container-part-->
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="${ctxWeb}/license/licenseList"  class="tip-bottom"><i class="icon-home"></i>首页</a></div>
  </div>
  <!-- 数据表格一 -->
  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>企业授权到期提醒 （${remindNum}个）</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table table-responsive">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>医院编码</th>
                  <th>医院名称</th>
                  <th>授权起始</th>
                  <th>授权终止</th>
                  <th>到期提示天数</th>
                  <th>服务起始</th>
                  <th>服务终止</th>
                  <th>服务类型</th>
                  <th>加密狗ID</th>
                  <th>备注</th>
                </tr>
              </thead>
              <tbody id="qytbody">
              	<c:forEach items="${page.list}" var="list" varStatus="sta">
	           		<tr>
	                  <td>${sta.index+1 }</td>
	                  <td>${list.code}</td>
	                  <td>${list.name}</td>
	                  <td><fmt:formatDate value="${list.accsdate}" pattern="yyyy-MM-dd" /></td>
	                  <td><fmt:formatDate value="${list.accedate}" pattern="yyyy-MM-dd" /></td>
	                  <td>${list.expireDay}天</td>
	                  <td><fmt:formatDate value="${list.svrsdate}" pattern="yyyy-MM-dd" /></td>
	                  <td><fmt:formatDate value="${list.svredate}" pattern="yyyy-MM-dd" /></td>
	                  <td>${list.svrtype == 0 ? "免费":"收费"}</td>
	                  <td>${list.dogid}</td>
	                  <td style="width:250px;word-break:break-all;word-wrap:break-word;">${list.remarks}</td>
	                </tr>
              	</c:forEach>
	              	<tr>
	              		<td colspan="13"><div class="jqueryPage" id="jqueryPage"></div></td>
	              	</tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>集团授权到期提醒 （${remindNum}个）</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table table-responsive table-hover table-expandable">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>医院编码</th>
                  <th>医院名称</th>
                  <th>授权起始</th>
                  <th>授权终止</th>
                  <th>到期提示天数</th>
                  <th>服务起始</th>
                  <th>服务终止</th>
                  <th>服务类型</th>
                  <th>加密狗ID</th>
                  <th>备注</th>
                </tr>
              </thead>
	              <tbody id="blocTbody">
              			<c:forEach items="${blocList.list}" var="bloc" varStatus="em">
		              		<tr class="${bloc.id}">
								<td colspan="13">
								<a onclick="changeTypeOpen(${em.index})"  id="btnOpen_${em.index}"  class="hide" style="cursor:pointer;">
									<i class="icon-plus"></i>
								</a>
								<a onclick="changeTypeClose(${em.index})" id="btnClose_${em.index}" style="cursor:pointer;">
									<i class="icon-minus"></i>
								</a>
								  <font style="font-weight:bold">${bloc.blocName}</font> 
								</td>
							</tr>
							<c:forEach items="${bloc.corporationList }" var="corporationList" varStatus="sta">
								<c:if test="${bloc.id == corporationList.parentid }">
					           		<tr class="em_${em.index}">
					                  <td>${sta.index+1 }</td>
					                  <td>${corporationList.code}</td>
					                  <td>${corporationList.name}</td>
					                  <td><fmt:formatDate value="${corporationList.accsdate}" pattern="yyyy-MM-dd" /></td>
	                  				  <td><fmt:formatDate value="${corporationList.accedate}" pattern="yyyy-MM-dd" /></td>
					                  <td>${corporationList.expireDay}天</td>
					                  <td><fmt:formatDate value="${corporationList.svrsdate}" pattern="yyyy-MM-dd" /></td>
					                  <td><fmt:formatDate value="${corporationList.svredate}" pattern="yyyy-MM-dd" /></td>
					                  <td>${corporationList.svrtype == 0 ? "免费":"收费"}</td>
					                  <td>${corporationList.dogid}</td>
					                  <td style="width:250px;word-break:break-all;word-wrap:break-word;">${corporationList.remarks}</td>
					                </tr>
					             </c:if>
				            </c:forEach>
             			</c:forEach>
             			<tr>
	              			<td colspan="13"><div class="jqueryPage" id="jqueryblocPage"></div></td>
	              		</tr>
	              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>

<script src="${ctxStatic}/js/jqueryPage.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/license.js"></script>
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
	
	$("#jqueryPage").pagination({
		count: count, //总数
		size: pageSize, //每页数量
		index: pageNo,//当前页
		lrCount: 3,//当前页左右最多显示的数量
		lCount: first,//最开始预留的数量
		rCount: last,//最后预留的数量
		callback: function (options) {
			licensePage(options.index,pageSize);
		}
	});
	
	$("#jqueryblocPage").pagination({
		count: bloccount, //总数
		size: blocpageSize, //每页数量
		index: blocpageNo,//当前页
		lrCount: 3,//当前页左右最多显示的数量
		lCount: blocfirst,//最开始预留的数量
		rCount: bloclast,//最后预留的数量
		callback: function (options) {
			blocPage(options.index,pageSize);
		}
	});
	 
	function licensePage(pageNo,pageSize){
		$.ajax({
	    	url:"${ctxWeb}/license/licensePage",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:{"pageNo":pageNo,"pageSize":pageSize},
	    	success:function(res){
	    		if(res.code==0){
	    			var data = res.data;
	    			var list = data.list;
	    			$("#qytbody").html("");
	    			var str ="";
	    			for (var i= 0; i < list.length; i++) {
	    				var inde = 1*1+i;
		           		str+="<tr>";
		           		str+="<td>"+inde+"</td>"
		                str+="<td>"+list[i].code+"</td>";
		           		str+="<td>"+list[i].name+"</td>";
		           		if(list[i].type == 0){
		           			str+="<td>集团医院</td>";
		           		}
		           		if(list[i].type == 1){
		           			str+="<td>企业医院</td>";
		           		}
		           		str+="<td>"+formatDateTime(list[i].accsdate)+"</td>";
		           		str+="<td>"+formatDateTime(list[i].accedate)+"</td>";
		           		str+="<td>"+list[i].expireDay+"天</td>";
		           		str+="<td>"+formatDateTime(list[i].svrsdate)+"</td>";
		           		str+="<td>"+formatDateTime(list[i].svredate)+"</td>";
		           		if(list[i].svrtype == 0){
		           			str+="<td>免费</td>";
		           		}
		           		if(list[i].svrtype == 1){
		           			str+="<td>收费</td>";
		           		}
		           		str+="<td>"+list[i].dogid+"</td>";
		           		str+="<td style='width:250px;word-break:break-all;word-wrap:break-word;'>"+list[i].remarks+"</td></tr>";
					}
	    			str+="<tr><td colspan='13'><div class='jqueryPage' id='jqueryPage'></div></td></tr>"
	    			$("#qytbody").html(str);
	    			$("#jqueryPage").pagination({
	    				count: data.count, //总数
	    				size: data.pageSize, //每页数量
	    				index: data.pageNo,//当前页
	    				lrCount: 3,//当前页左右最多显示的数量
	    				lCount: data.first,//最开始预留的数量
	    				rCount: data.last,//最后预留的数量
	    				callback: function (options) {
	    					licensePage(options.index,pageSize);
	    				}
	    			});
	    		}else{
	    			layer.msg(res.message);
	    		}
	    	}
	    })
    }
	
	function blocPage(pageNo,pageSize){
		$.ajax({
	    	url:"${ctxWeb}/license/blocPage",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:{"pageNo":pageNo,"pageSize":pageSize},
	    	success:function(res){
	    		if(res.code==0){
	    			var data = res.data;
	    			var bloc = data.list;
	    			$("#blocTbody").html("");
	    			var str ="";
					for (var i= 0; i < bloc.length; i++) {
						var corporationList = bloc[i].corporationList
						var em = 1*1+i;
						str+="<tr class='"+bloc[i].id+"'><td colspan='13'>";
						str+="<a onclick='changeTypeOpen("+em+")'  id='btnOpen_"+em+"'  class='hide' style='cursor:pointer;'>";
						str+="<i class='icon-plus'></i></a>";
						str+="<a onclick='changeTypeClose("+em+")' id='btnClose_"+em+"' style='cursor:pointer;'>";
						str+="<i class='icon-minus'></i></a>";
						str+=" <font style='font-weight:bold'>"+bloc[i].blocName+"</font></td></tr>";
						for (var j= 0; j < corporationList.length; j++) {
							var inde = 1*1+j;
							if(bloc[i].id == corporationList[j].parentid){
								str+="<tr class='em_"+em+"'>";
								str+="<td>"+inde+"</td>"
								str+="<td>"+corporationList[j].code+"</td>";
								str+="<td>"+corporationList[j].name+"</td>";
								if(corporationList[j].type == 0){
				           			str+="<td>集团医院</td>";
				           		}
				           		if(corporationList[j].type == 1){
				           			str+="<td>企业医院</td>";
				           		}
				           		str+="<td>"+formatDateTime(corporationList[j].accsdate)+"</td>";
				           		str+="<td>"+formatDateTime(corporationList[j].accedate)+"</td>";
				           		str+="<td>"+corporationList[j].expireDay+"天</td>";
								str+="<td>"+formatDateTime(corporationList[j].svrsdate)+"</td>";
								str+="<td>"+formatDateTime(corporationList[j].svredate)+"</td>";
								if(corporationList[j].svrtype == 0){
				           			str+="<td>免费</td>";
				           		}
				           		if(corporationList[j].svrtype == 1){
				           			str+="<td>收费</td>";
				           		}
								str+="<td>"+corporationList[j].dogid+"</td>";
								str+="<td style='width:250px;word-break:break-all;word-wrap:break-word;'>"+corporationList[j].remarks+"</td></tr>";
							}
						}
					}
	    			str+="<tr><td colspan='13'><div class='jqueryPage' id='jqueryblocPage'></div></td></tr>"
	    			$("#blocTbody").html(str);
	    			$("#jqueryblocPage").pagination({
	    				count: data.count, //总数
	    				size: data.pageSize, //每页数量
	    				index: data.pageNo,//当前页
	    				lrCount: 3,//当前页左右最多显示的数量
	    				lCount: data.first,//最开始预留的数量
	    				rCount: data.last,//最后预留的数量
	    				callback: function (options) {
	    					blocPage(options.index,pageSize);
	    				}
	    			});
	    		}else{
	    			layer.msg(res.message);
	    		}
	    	}
	    })
    }
	
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

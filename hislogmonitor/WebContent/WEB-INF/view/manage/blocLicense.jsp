<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
    <title>系统管理首页</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />  <!--以IE8模式解析页面-->
    <link rel="shortcut icon" href="${ctxStatic}/img/favicon.ico" />
    <link rel="stylesheet" href="${ctxStatic}/css/demo.css" type="text/css">
    <link href="${ctxStatic}/css/metroStyle.css" rel="stylesheet" />
    <link href="${ctxStatic}/css/jquery.fs.stepper.css" rel="stylesheet" type="text/css" media="all" />
</head>
<style>
.pager{height:30px;line-height:30px;font-size: 12px;margin: 25px 0px;text-align: center;color: #2E6AB1;overflow: hidden;}
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
    <li><a href="${ctxWeb}/license/licenseList"><i class="icon icon-home"></i> <span>首页</span></a> </li>
    <li><a href="${ctxWeb}/license/enterpriseLicenseList"><i class="icon-angle-right"></i> <span>企业授权</span></a> </li>
    <li class="active"><a href="${ctxWeb}/license/blocLicenseList"><i class="icon-angle-right"></i> <span>集团授权</span></a></li>
     <li class="active"><a href="${ctxWeb}/index1.htm"><i class="icon-angle-right"></i> <span>监控平台</span></a></li>
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
            <table id="list_style"class="table table-bordered data-table table-responsive table-hover table-expandable" style="margin-top: 50px;display:none;">
              <thead>
              	<tr>
              		<th colspan="13" style="text-align: right;">
              			<span class="icon-reorder" onclick="reorder()" style="cursor:pointer;"></span>
	             			&nbsp;&nbsp;
	          			<span class="icon-columns" onclick="columns()" style="cursor:pointer;"></span>
              		</th>
              	</tr>
              	<tr>
                  <th colspan="13" style="text-align: left;">
			              <div class="controls">
			                	<input type="text" placeholder="请输入医院名称，编码" id="serchName" name="serchName"/>
			                	&nbsp;&nbsp;&nbsp;&nbsp;授权起始：<input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="授权起始日期" id="serchSdate" name="serchSdate"/>
			                	&nbsp;&nbsp;&nbsp;&nbsp;授权终止：<input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="请选择授权终止日期" id="serchEdate" name="serchEdate"/>
			                	<span class="pull-right">
			                		<a type="button" href="#" class="btn btn-success" onclick="blocPage()"/>查询</a>
									<a type="button" href="#" class="btn btn-success" onclick="saveBlocModal()"/>新增集团</a>
								</span>
			                	<br/>
			                	<button type="button" class="btn btn-default btn-xs" value="3" onclick="blocPage('','',this.value)">3天内到期</button>
			                	<button type="button" class="btn btn-default btn-xs" value="5" onclick="blocPage('','',this.value)">5天内到期</button>
			                	<button type="button" class="btn btn-default btn-xs" value="7" onclick="blocPage('','',this.value)">7天内到期</button>
			              </div>
                  </th>
                </tr>
              	<tr>
                  <th colspan="13" style=" text-align: left;">
                  	<span class="icon"></span>
                  	企业授权到期提醒 （${remindNum}个）
                  </th>
                </tr>
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
                  <th>备注</th>
                  <th>操作</th>
                </tr>
              </thead>
	              <tbody id="blocTbody">
	              		<c:if test="${fn:length(blocList.list) > 0}">
	              			<c:forEach items="${blocList.list}" var="bloc" varStatus="em">
			              		<tr class="${bloc.id}" id="btr1${bloc.id}">
									<td colspan="13">
									<a onclick="changeTypeOpen(${em.index})"  id="btnOpen_${em.index}"  class="hide" style="cursor:pointer;">
										<i class="icon-plus"></i>
									</a>
									<a onclick="changeTypeClose(${em.index})" id="btnClose_${em.index}" style="cursor:pointer;">
										<i class="icon-minus"></i>
									</a>
									  <font style="font-weight:bold">${bloc.blocName}</font> 
									  <span class="pull-right">
									  	<a type="button" href="#" class="btn btn-success" onclick="updateBloc('${bloc.id}')"/>修改</a>
	              						<a type="button" href="#" class="btn btn-success" onclick="delBloc('${bloc.id}')"/>删除</a>
										<a type="button" href="#" class="btn btn-success" onclick="saveLicenseModal('${bloc.id}')"/>新增授权</a>
									  </span>
									</td>
								</tr>
								<c:forEach items="${bloc.corporationList }" var="corporationList" varStatus="sta">
									<c:if test="${bloc.id == corporationList.parentid }">
						           		<tr class="em_${em.index} btr1_${bloc.id}" id="tr1${corporationList.id}">
						                  <td>${sta.index+1 }</td>
						                  <td>${corporationList.code}</td>
						                  <td>${corporationList.name}</td>
						                  <td><fmt:formatDate value="${corporationList.accsdate}" pattern="yyyy-MM-dd" /></td>
		                  				  <td><fmt:formatDate value="${corporationList.accedate}" pattern="yyyy-MM-dd" /></td>
						                  <td>${corporationList.expireDay}天</td>
						                  <td><fmt:formatDate value="${corporationList.svrsdate}" pattern="yyyy-MM-dd" /></td>
						                  <td><fmt:formatDate value="${corporationList.svredate}" pattern="yyyy-MM-dd" /></td>
						                  <td>${corporationList.svrtype == 0 ? "免费":"收费"}</td>
						                  <td style="width:250px;word-break:break-all;word-wrap:break-word;">${corporationList.remarks}</td>
						                  <td>
							                  <a href="javascript:goAnewLicense('${corporationList.id}',0)"><font style='color:#5bb75b;'>重新授权</font></a>
							                  &nbsp;<a href="${ctxWeb}/licenseFileXML/dowloadLicenseFile?id=${corporationList.id}&t=bloc0"><font style='color:#5bb75b;'>下载授权证书</font></a>
							                  &nbsp;<a href="javascript:delHospital('${corporationList.id}')"><font style='color:#5bb75b;'>删除</font></a>
						                  </td>
						                </tr>
						             </c:if>
					            </c:forEach>
	             			</c:forEach>
             			</c:if>
             			<tr>
	              			<td colspan="13"><div class="jqueryPage" id="jqueryPage"></div></td>
	              		</tr>
	              </tbody>
            </table>
            
            <!-- 左集团右集团数据表格数据 -->
            <table id ="about_style" class="table table-bordered data-table table-responsive table-hover table-expandable" style="margin-top: 50px;">
              <thead>
              	<tr>
              		<th colspan="13" style="text-align: right;">
	             		<span class="icon-reorder" onclick="reorder()" style="cursor:pointer;"></span>
	             			&nbsp;&nbsp;
	          			<span class="icon-columns" onclick="columns()" style="cursor:pointer;"></span>
              		</th>
              	</tr>
              	<tr>
                  <th colspan="13" style="text-align: left;">
			              <div class="controls">
			                	<input type="text" placeholder="请输入医院名称，编码" id="serchName1" name="serchName"/>
			                	&nbsp;&nbsp;&nbsp;&nbsp;授权起始：<input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="授权起始日期" id="serchSdate1" name="serchSdate"/>
			                	&nbsp;&nbsp;&nbsp;&nbsp;授权终止：<input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="请选择授权终止日期" id="serchEdate1" name="serchEdate"/>
			                	<span class="pull-right">
			                		<a type="button" href="#" class="btn btn-success" onclick="blocStylePage('','','','')"/>查询</a>
								</span>
			                	<br/>
			                	<button type="button" class="btn btn-default btn-xs" value="3" onclick="blocStylePage('','','',this.value)">3天内到期</button>
			                	<button type="button" class="btn btn-default btn-xs" value="5" onclick="blocStylePage('','','',this.value)">5天内到期</button>
			                	<button type="button" class="btn btn-default btn-xs" value="7" onclick="blocStylePage('','','',this.value)">7天内到期</button>
			              </div>
                  </th>
                </tr>
              	<tr>
                  <th colspan="13" style=" text-align: left;">
                  	<span class="icon"></span>
                  	企业授权到期提醒 （${remindNum}个）
                  </th>
                </tr>
                <tr>
                  <th>集团名称</th>
                  <th>企业信息</th>
                </tr>
              </thead>
	              <tbody id="blocTbody">
	              		<tr>
	              			<td rowspan="${fn:length(blocListName)}" style="width:20%;">
	              				<ul class="list-group">
	              					<a type="button" href="#" class="btn btn-success" onclick="saveBlocModal()"/>新增</a>
	              					<a type="button" href="#" class="btn btn-success" onclick="updateBloc('')"/>修改</a>
	              					<a type="button" href="#" class="btn btn-success" onclick="delBloc('')"/>删除</a>
								</ul>
	              				<c:forEach items="${blocListName}" var="blocs">
	              					<ul class="list-group" id ="ul${blocs.id}">
										<li class="list-group-item">
											<a href="javascript:void(0)" onclick="blocStylePage('','',${blocs.id},'')">${blocs.blocName}</a>
										</li>
									</ul>
	              				</c:forEach>
              				</td>
	             			<td rowspan="${fn:length(blocListName)}" style="width:80%;">
	             				<table class="table data-table table-responsive table-hover table-expandable">
             						<thead>
             							<tr>
						                  <th colspan="13" style="text-align: right;">
						                  	<a type="button" href="#" class="btn btn-success" onclick="saveLicenseModal('newSave')"/>新增授权</a>
						                  	<input type="hidden"  id="style_pid"/>
						                  </th>
						                </tr>
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
						                  <th>备注</th>
						                  <th>操作</th>
						                </tr>
				                  	</thead>
				                  	<tbody id="about_style_Tbody">
				                  		
				                  	</tbody>
				                </table>
	             			</td>
             			</tr>
	              </tbody>
            </table>
  		</div>
</div>

<div style="clear: both;"></div>
<!-- 新增授权信息模态窗口 -->
<div class="modal fade"  data-toggle="modal" data-backdrop="static" id="saveLicenseModal" style="width:60%;left:40%;position:absolute;z-index:1">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
    	<div class="modal-header" style="background: #01aeef;color:#fff">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin-top: 11px;"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="gridSystemModalLabel">企业授权添加</h4>
      	</div>
      	<!-- FORM表单 -->
            <div class="container-fluid">
				<div class="row-fluid">
					<!-- 授权基本信息 -->
          			<form action="#" method="get" class="form-horizontal" id="saveForm">
					<div class="span6" style="width: 70%;">
						<!-- 医院信息 -->
						<div class="widget-title"> <span class="icon"> <i class="icon-hospital"></i> </span>
				          <h5>医院信息</h5>
				        </div>
						<div class="control-group">
			              <label class="control-label">医院名称:</label>
			              <div class="controls controls-row">
			                <input type="text" class="span8" placeholder="医院名称" id="name" name="name" required />
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">医院编码:</label>
			              <div class="controls">
			              	<input type="hidden" class="span8" name="save_type" id="save_type"/>
			              	<input type="hidden" class="span8" name="id" id="cid" />
			              	<input type="hidden" class="span8" name="parentid" id="parentid" />
			                <input type="text" class="span8" readonly placeholder="医院编码" id="code" name="code" required/>
			                <input type="hidden" class="span8" placeholder="医院类型(0:集团医院,1:企业医院)"  value ="0" name="type"/>
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">地区:</label>
			              <div class="controls">
			              	<select name="provincial" id="provincial" class="span8" required>
		              			<option selected="selected" value="0">请选择</option>
			              		<c:forEach items="${provincial}" var="provincial">
			              			<option value="${provincial.id}">${provincial.provincial}</option>
			              		</c:forEach>
			                </select>
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">备注:</label>
			              <div class="controls">
			              	 <textarea class="span8" id="remarks" name="remarks"></textarea>
			              </div>
			            </div>
			             <!-- 产品授权 -->
			            <div class="widget-title"> <span class="icon"> <i class="icon-hospital"></i> </span>
				          <h5>产品授权</h5>
				        </div>
			            <div class="control-group">
			              <label class="control-label">授权起止日期:</label>
			              <div class="controls">
			              	<div data-date="" class="input-append date datepicker">
			                  <input type="text" readonly="readonly" value="${ininDate}" data-date-format="yyyy-mm-dd" class="span4" name="accsdate" id="accsdate"  placeholder="授权起始日期" required>
			                  <span class="add-on">--至--</span>
			                  <input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" class="span4" name="accedate" id="accedate" placeholder="授权终止日期" required>
			                  <button type="button" class="btn btn-info btn-xs" onclick="infinite()">无限制</button>
			                </div>
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">到期提示天数:</label>
			              <div class="controls">
			              	<input type="number"  class="span8" placeholder="只能是正整数" name="expireDay" id="expireDay" pattern="[0-9]*" min="0" pattern="[1-9]*" min="1"  oninput="if(value.length>3)value=value.slice(0,3)" required />
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">加密狗ID:</label>
			              <div class="controls">
			               <input type="text" class="span8" placeholder="加密狗ID" name="dogid" id="dogid" readonly="readonly"/>
			               <button type="button" class="btn btn-info btn-xs" onclick="getLocaDog('t')">刷新</button>
			              </div>
			            </div>
			            <!-- 产品授权 -->
			            <!-- 服务授权 -->
			            <div class="widget-title"> <span class="icon"> <i class="icon-hospital"></i> </span>
				          <h5>服务授权</h5>
				        </div>
				        <div class="control-group">
			              <label class="control-label">服务起始:</label>
			              <div class="controls">
			              	<div data-date="" class="input-append date datepicker">
			                  <input type="text" readonly="readonly" value="${ininDate}" data-date-format="yyyy-mm-dd" class="span6" name="svrsdate" id="svrsdate"  placeholder="服务起始日期" required>
							  <button type="button" class="btn btn-info btn-xs" onclick="svrsdateBut()">今月初</button>
			                </div>
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">服务终止:</label>
			              <div class="controls">
			              	<div data-date="" class="input-append date datepicker">
			                  <input type="text" readonly="readonly" value="${ininDate}" data-date-format="yyyy-mm-dd" class="span6" name="svredate" id="svredate"  placeholder="服务终止日期" required>
			                  <button type="button" class="btn btn-info btn-xs" onclick="svredateBut1()">半年</button>
			                  <button type="button" class="btn btn-info btn-xs" onclick="svredateBut2()">一年</button>
			                  <button type="button" class="btn btn-info btn-xs" onclick="svredateBut3()">三年</button>
			                </div>
			              </div>
			            </div>
			            
			            
			            <div class="control-group">
			              <label class="control-label">服务类型:</label>
			              <div class="controls">
			              	<select name="svrtype" id="svrtype" class="span8" required>
			                  <option selected="selected" value="1">收费</option>
			                  <option value="0">免费</option>
			                </select>
			              </div>
			            </div>
			            <!-- 服务授权 -->
					</div>
					<!-- 授权基本信息 -->
					
					<div class="span6" style="width: 26%;">
						<div class="widget-title"> <span class="icon"> <i class="icon-cog"></i> </span>
				          <h5>授权使用模块</h5>
				        </div>
						<ul id="treeDemo" class="ztree" style="margin-left: 13%;"></ul>
					</div>
					</form>
				</div>
			</div>
      	<!-- FORM表单 -->
      	<div class="modal-footer">
	        <button type="button" class="btn btn-default" onclick="edit()" id="editBut" >保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
    </div>
  </div>
</div>
<!-- 新增授权信息模态窗口 -->

<!-- 新增企业信息模态窗口 -->
<div class="modal fade" data-toggle="modal" data-backdrop="static" id="saveBlocModal" style="width:60%;left:40%;">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
    	<div class="modal-header" style="background: #01aeef;color:#fff">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin-top: 11px;"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="gridSystemModalLabel">集团添加</h4>
      	</div>
      	<!-- FORM表单 -->
            <div class="container-fluid">
				<div class="row-fluid">
					<!-- 授权基本信息 -->
          			<form action="#" method="get" class="form-horizontal" id="bloc_saveForm">
					<div class="span12">
						<div class="widget-title"> <span class="icon"> <i class="icon-hospital"></i> </span>
				          <h5>新增集团信息</h5>
				        </div>
						<div class="control-group">
			              <label class="control-label">集团名称:</label>
			              <div class="controls">
			                <input type="text" class="span8" placeholder="集团名称" id="blocName" name="blocName" required />
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">医院数量</label>
			              <div class="controls">
			              	 <input type="number" class="span8" placeholder="医院数量" id="hospitalNum" name="hospitalNum" pattern="[0-9]*" min="0" required/>
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">集团简介:</label>
			              <div class="controls">
			              	<input type="hidden" class="span8" name="id" id="bloc_cid" />
			              	<input type="hidden" class="span8" name="parentid" value="0"/>
			                <input type="hidden" class="span8" placeholder="医院类型(0:集团医院,1:企业医院)"  value ="0" name="type" required />
			                <textarea class="span8" id="blocBrief" name="blocBrief" placeholder="集团简介"></textarea>
			              </div>
			            </div>
					</div>
		          	</form>
					<!-- 授权基本信息 -->
				</div>
			</div>
      	<!-- FORM表单 -->
      	<div class="modal-footer">
	        <button type="button" class="btn btn-default" onclick="saveBloc()">保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
    </div>
  </div>
</div>
<!-- 新增企业信息模态窗口 -->


<script type="text/javascript" src="${ctxStatic}/js/ChinesePY.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/license.js"></script>
<script src="${ctxStatic}/js/jquery.ztree.all.js"></script>
<script src="${ctxStatic}/js/jqueryPage.js"></script>
<script src="${ctxStatic}/js/jquery.fs.stepper.js"></script>
<script>

	var zNodes = ${productList};
	var count = ${blocList.count};//总数
	var first = ${blocList.first};//首页索引
	var last = ${blocList.last};//尾页索引
	var pageNo = ${blocList.pageNo};//当前页
	var pageSize = ${blocList.pageSize};//每页条数
	var error ='${error}';
	var pid ='${pid}';
	
	var productArr = "";
	
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback:{
            onCheck:onCheck
        }
	};
	
	$(document).ready(function(){
		
		if(error == 'bloc0'){
			$("#about_style").hide();
			$("#list_style").show();
			blocPage('','',"");
			layer.msg("授权证书不存在,请重新授权！",{icon: 2});
		}
		if(error == 'bloc1'){
			$("#about_style").show();
			$("#list_style").hide();
			blocStylePage('','',pid,'');
			layer.msg("授权证书不存在,请重新授权！",{icon: 2});
		}
		
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
	
	function onCheck(e,treeId,treeNode){
		productArr = "";
         var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
         nodes=treeObj.getCheckedNodes(true);
         for(var i=0;i<nodes.length;i++){
        	 if(!nodes[i].isParent){
        		 productArr+=nodes[i].id + ",";
        	 }
        }
    }
	
	$("#jqueryPage").pagination({
		count: count, //总数
		size: pageSize, //每页数量
		index: pageNo,//当前页
		lrCount: 3,//当前页左右最多显示的数量
		lCount: first,//最开始预留的数量
		rCount: last,//最后预留的数量
		callback: function (options) {
			blocPage(options.index,pageSize,"");
		}
	});
	
	function blocPage(pageNo,pageSize,day){
		var accsdate = $("#serchSdate").val();
		var accedate = $("#serchEdate").val();
		var blocName = $("#serchName").val();
		var parma={"pageNo":pageNo,"pageSize":pageSize,"accsdate":accsdate,"accedate":accedate,"day":day,"blocName":blocName};
		$.ajax({
	    	url:"${ctxWeb}/license/blocPage",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:parma,
	    	success:function(res){
	    		if(res.code==0){
	    			var data = res.data;
	    			var bloc = data.list;
	    			$("#blocTbody").html("");
	    			var str ="";
					for (var i= 0; i < bloc.length; i++) {
						var corporationList = bloc[i].corporationList
						if(!jQuery.isEmptyObject(corporationList)){
							var em = 1*1+i;
							str+="<tr class='"+bloc[i].id+"' id='btr1"+bloc[i].id+"'><td colspan='13'>";
							str+="<a onclick='changeTypeOpen("+em+")'  id='btnOpen_"+em+"'  class='hide' style='cursor:pointer;'>";
							str+="<i class='icon-plus'></i></a>";
							str+="<a onclick='changeTypeClose("+em+")' id='btnClose_"+em+"' style='cursor:pointer;'>";
							str+="<i class='icon-minus'></i></a>";
							str+="<font style='font-weight:bold'>"+bloc[i].blocName+"</font>";
							str+="<span class='pull-right'>";
      						str+="<a type='button' href='javascript:void(0)' class='btn btn-success' onclick='updateBloc("+bloc[i].id+")'>修改</a>";
      						str+="<a type='button' href='javascript:void(0)' class='btn btn-success' onclick='delBloc("+bloc[i].id+")'>删除</a>";
							str+="<a type='button' href='javascript:void(0)' class='btn btn-success' onclick='saveLicenseModal("+bloc[i].id+")'>新增授权</a>";
							str+="</span></td></tr>";
							for (var j= 0; j < corporationList.length; j++) {
								var inde = 1*1+j;
								if(bloc[i].id == corporationList[j].parentid){
									str+="<tr class='em_"+em+" btr1_"+bloc[i].id+"' id='tr1"+corporationList[j].id+"'>";
									str+="<td>"+inde+"</td>"
									str+="<td>"+corporationList[j].code+"</td>";
									str+="<td>"+corporationList[j].name+"</td>";
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
									str+="<td style='width:250px;word-break:break-all;word-wrap:break-word;'>"+corporationList[j].remarks+"</td>";
									str+="<td><a href='javascript:goAnewLicense("+corporationList[j].id+",0)'><font style='color:#5bb75b;'>重新授权</font></a>";
									str+="&nbsp;<a href='${ctxWeb}/licenseFileXML/dowloadLicenseFile?id="+corporationList[j].id+"&t=bloc0'><font style='color:#5bb75b;'>下载授权证书</font></a>";
									str+="&nbsp;<a href='javascript:delHospital("+corporationList[j].id+")'><font style='color:#5bb75b;'>删除</font></a></td></tr>";
								}
							}
						}
					}
	    			str+="<tr><td colspan='13'><div class='jqueryPage' id='jqueryPage'></div></td></tr>"
	    			$("#blocTbody").html(str);
	    			$("#jqueryPage").pagination({
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
	
	
	//左右样式的时候调用
	function blocStylePage(pageNo,pageSize,parentid,day){
		var accsdate = $("#serchSdate1").val();
		var accedate = $("#serchEdate1").val();
		var blocName = $("#serchName1").val();
		var parma={"pageNo":pageNo,"pageSize":pageSize,"accsdate":accsdate,"accedate":accedate,"day":day,"name":blocName,"parentid":parentid};
		$("#style_pid").val(parentid);
		$.ajax({
	    	url:"${ctxWeb}/license/findByBlocCorPage",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:parma,
	    	success:function(res){
	    		if(res.code==0){
	    			var data = res.data;
	    			var bloc = data.list;
	    			$("#about_style_Tbody").html("");
	    			var str ="";
					for (var j= 0; j < bloc.length; j++) {
						var inde = 1*1+j;
							str+="<tr id='tr"+bloc[j].id+"'>";
							str+="<td>"+inde+"</td>"
							str+="<td>"+bloc[j].code+"</td>";
							str+="<td>"+bloc[j].name+"</td>";
			           		str+="<td>"+formatDateTime(bloc[j].accsdate)+"</td>";
			           		str+="<td>"+formatDateTime(bloc[j].accedate)+"</td>";
			           		str+="<td>"+bloc[j].expireDay+"天</td>";
							str+="<td>"+formatDateTime(bloc[j].svrsdate)+"</td>";
							str+="<td>"+formatDateTime(bloc[j].svredate)+"</td>";
							if(bloc[j].svrtype == 0){
			           			str+="<td>免费</td>";
			           		}
			           		if(bloc[j].svrtype == 1){
			           			str+="<td>收费</td>";
			           		}
							str+="<td style='width:250px;word-break:break-all;word-wrap:break-word;'>"+bloc[j].remarks+"</td>";
							str+="<td><a href='javascript:goAnewLicense("+bloc[j].id+",\"newSave\")'><font style='color:#5bb75b;'>重新授权</font></a>";
							str+="&nbsp;<a href='${ctxWeb}/licenseFileXML/dowloadLicenseFile?id="+bloc[j].id+"&t=bloc1'><font style='color:#5bb75b;'>下载授权证书</font></a>";
							str+="&nbsp;<a href='javascript:delHospital("+bloc[j].id+")'><font style='color:#5bb75b;'>删除</font></a></td></tr>";
					}
	    			str+="<tr><td colspan='13'><div class='jqueryPage' id='jqueryPage2'></div></td></tr>"
	    			$("#about_style_Tbody").html(str);
	    			$("#jqueryPage2").pagination({
	    				count: data.count, //总数
	    				size: data.pageSize, //每页数量
	    				index: data.pageNo,//当前页
	    				lrCount: 3,//当前页左右最多显示的数量
	    				lCount: data.first,//最开始预留的数量
	    				rCount: data.last,//最后预留的数量
	    				callback: function (options) {
	    					blocStylePage(options.index,pageSize,parentid,'');
	    				}
	    			});
	    		}else{
	    			layer.msg(res.message);
	    		}
	    	}
	    })
    }
	
	
	
	function corCode(){
		$.ajax({
	    	url:"${ctxWeb}/license/findMaxCode",
	    	type:"POST",
	    	dataType:"JSON",
	    	success:function(res){
	    		if(res.code == 0){
	    			$("#code").val(res.message);
	    		}
	    	}
		});
	}
	
	function edit(){
		//集团为 2企业为1
		if(saveCheck(2)){
		$("#editBut").attr("disabled", true);
			layer.load();
			var cid = $("#cid").val();
			if(cid == null || cid == ""){
				save();
			}else{
				updateLicense();
			}
		}
	}

	function save(){
		var save_type = $("#save_type").val();
		var params = $("#saveForm").serializeArray();
	    var values = {};  
	    for( x in params ){  
	       values[params[x].name] = params[x].value;  
	    } 
	    values["productArr"] = productArr;
	    var idata = JSON.stringify(values);
	    $.ajax({
	    	url:"${ctxWeb}/license/saveLicense",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:values,
	    	success:function(res){
    			layer.closeAll('loading');
	    		if(res.code==0){
	    			$.ajax({
	    				url:"${ctxWeb}/licenseFileXML/createBlocLicenseFile",
	    				type:"POST",
	    		    	dataType:"JSON",
	    		    	data:{id:res.data},
	    		    	success:function(res){
	    		    		if(save_type == "newSave"){
	    	    				var parentid = $("#style_pid").val();
	    	    				blocStylePage('','',parentid,'');
	    	    				$("#saveLicenseModal").modal('hide');
	    	    			}else{
	    	    				blocPage('','',"");
	    	    				$("#saveLicenseModal").modal('hide');
	    	    			}
	    	    			$("#editBut").attr("disabled", false);
	    	    			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	    	    			treeObj.checkAllNodes(false);
	    	    			productArr = "";
	    		    	}
	    			});
	    		}else{
	    			layer.msg(res.message);
	    		}
	    	}
	    })
	}
	
	//打开重新授权模态窗口
	function goAnewLicense(id,type){
		$("#saveLicenseModal").modal('show');
		 $.ajax({
		    	url:"${ctxWeb}/license/goAnewLicense",
		    	type:"POST",
		    	dataType:"JSON",
		    	data:{"id":id},
		    	success:function(res){
		    		if(res.code==0){
		    			var data=res.data;
		    			$("#cid").val(data.id);
		    			$("#parentid").val(data.parentid);
		    			$("#name").val(data.name);
		    			$("#provincial").val(data.provincial);
		    			$("#code").val(data.code);
		    			$("#accsdate").val(formatDateTime(data.accsdate));
		    			$("#accedate").val(formatDateTime(data.accedate));
		    			$("#svrsdate").val(formatDateTime(data.svrsdate));
		    			$("#svredate").val(formatDateTime(data.svredate));
		    			$("#svrtype").val(formatDateTime(data.svrtype));
		    			$("#dogid").val(data.dogid);
		    			$("#expireDay").val(data.expireDay);
		    			$("#remarks").val(data.remarks);
		    			$("#save_type").val(type);
		    			getLicenseProduct(data.id);
		    		}else{
		    			$("#saveLicenseModal").modal('hide');
		    			layer.msg(res.message);
		    		}
		    	}
		    })
	}
	
	//重新加载产品树
	function getLicenseProduct(id){
		 $.ajax({
		    	url:"${ctxWeb}/license/getLicenseProduct",
		    	type:"POST",
		    	dataType:"JSON",
		    	data:{"id":id},
		    	success:function(res){
		    		if(res.code==0){
		    			zNodes = res.message;
		    			zNodes = eval("(" + zNodes + ")");
		    			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		    			onCheck();
		    		}else{
		    			$("#saveLicenseModal").modal('hide');
		    			layer.msg(res.message);
		    		}
		    	}
		    })
	}
	
	function saveLicenseModal(id){
		getLocaDog("");
		var save_type = id;
		if(id == 'newSave'){
			id = $("#style_pid").val();
		}
		if(id == ""){
			layer.msg("请选择集团！");
			return false;
		}
		$("#cid").val("");
		$("#name").val("");
		$("#provincial").val("0");
		$("#accedate").val("");
		$("#svredate").val("");
		$("#svrtype").val("");
		$("#expireDay").val("");
		$("#remarks").val("");
		$("#parentid").val(id);
		$("#save_type").val(save_type);
		corCode();
		$("#saveLicenseModal").modal('show');
	}
	
	function delBloc(list_id){
		var id="";
		if(list_id == ""){
			id = $("#style_pid").val();
		}else{
			id = list_id;
		}
		if(id == ""){
			layer.msg("请选择集团！");
			return false;
		}
		layer.confirm('删除集团将会删除集团下的所有企业，您确定删除吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  $.ajax({
		    	url:"${ctxWeb}/license/delBloc",
		    	type:"POST",
		    	dataType:"JSON",
		    	data:{id:id},
		    	success:function(res){
		    		layer.closeAll('loading');
		    		if(res.code==0){
		    			if(list_id == ''){
		    				$("#ul"+id).remove();
			    			$("#about_style_Tbody").html("");
		    			}else{
		    				$("#btr1"+list_id).remove();
		    				$(".btr1_"+list_id).remove();
		    			}
		    			layer.msg("删除成功",{icon: 1});
		    		}else{
		    			layer.msg(res.message,{icon: 2});
		    		}
		    	}
		  })
		});
	}
	
	
	//修改集团信息打开模态窗口
	function updateBloc(list_id){
		var id="";
		if(list_id == ""){
			id = $("#style_pid").val();
		}else{
			id = list_id;
		}
		if(id == ""){
			layer.msg("请选择集团！");
			return false;
		}
		$("#saveBlocModal").modal('show');
		 $.ajax({
		    	url:"${ctxWeb}/license/goAnewLicense",
		    	type:"POST",
		    	dataType:"JSON",
		    	data:{"id":id},
		    	success:function(res){
		    		if(res.code==0){
		    			var data=res.data;
		    			$("#blocName").val(data.blocName);
		    			$("#hospitalNum").val(data.hospitalNum);
		    			$("#bloc_cid").val(data.id);
		    			$("#blocBrief").val(data.blocBrief);
		    		}else{
		    			$("#saveBlocModal").modal('hide');
		    			layer.msg(res.message,{icon: 2});
		    		}
		    	}
		    })
	}
	
	
	function delHospital(id){
		layer.confirm('您确定删除该医院吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
			  $.ajax({
			    	url:"${ctxWeb}/license/delHospital",
			    	type:"POST",
			    	dataType:"JSON",
			    	data:{id:id},
			    	success:function(res){
			    		layer.closeAll('loading');
			    		if(res.code==0){
			    			$("#tr"+id).remove();
			    			$("#tr1"+id).remove();
			    			layer.msg("删除成功",{icon: 1});
			    		}else{
			    			layer.msg(res.message,{icon: 2});
			    		}
			    	}
			  })
			});
	}
	
	function updateLicense(){
		var save_type = $("#save_type").val();
		var params = $("#saveForm").serializeArray();
	    var values = {};  
	    for( x in params ){  
	       values[params[x].name] = params[x].value;  
	    } 
	    values["productArr"] = productArr;
	    var idata = JSON.stringify(values);
	    $.ajax({
	    	url:"${ctxWeb}/license/anewLicense",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:values,
	    	success:function(res){
	    		layer.closeAll('loading');
	    		if(res.code==0){
	    			$.ajax({
	    				url:"${ctxWeb}/licenseFileXML/createBlocLicenseFile",
	    				type:"POST",
	    		    	dataType:"JSON",
	    		    	data:{id:res.data},
	    		    	success:function(res){
	    		    		if(save_type == "newSave"){
	    	    				var parentid = $("#style_pid").val();
	    	    				blocStylePage('','',parentid,'');
	    	    				$("#saveLicenseModal").modal('hide');
	    	    			}else{
	    	    				blocPage('','',"");
	    	    				$("#saveLicenseModal").modal('hide');
	    	    			}
	    	    			$("#editBut").attr("disabled", false);
	    	    			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	    	    			treeObj.checkAllNodes(false);
	    		    	}
	    			})
	    			//window.location.href="${ctxWeb}/licenseFileXML/createLicenseFile?id="+res.data;
	    		}else if(res.code== -1){
	    			window.location.href="${ctxWeb}/logout";
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
	
	function saveBlocModal(){
		$("#bloc_cid").val("");
		$("#blocName").val("");
		$("#remarks").val("");
		$("#hospitalNum").val("");
		$("#saveBlocModal").modal('show');
	}
	
	function saveBloc(){
		
		var blocid = $("#bloc_cid").val();
		if(saveBlocCheck()){
			layer.load();
			var params = $("#bloc_saveForm").serializeArray();
		    var values = {};  
		    for( x in params ){  
		       values[params[x].name] = params[x].value;  
		    } 
		    var idata = JSON.stringify(values);
		    $.ajax({
		    	url:"${ctxWeb}/license/saveBloc",
		    	type:"POST",
		    	dataType:"JSON",
		    	data:values,
		    	success:function(res){
		    		layer.closeAll('loading');
		    		if(res.code==0){
		    			$("#saveBlocModal").modal('hide');
		    			if(blocid == ""){
		    				window.location.reload();
		    			}
		    		}else if(res.message == -1){
		    			window.location.href="${ctxWeb}/logout";
		    		}else{
		    			layer.msg(res.message);
		    		}
		    	}
		    })
		}
	}
	
	function infinite(){
		$("#accedate").val("9999-12-31");
	}
	
	function svrsdateBut(){
		$("#svrsdate").val(getMonthStartDate());
	}
	
	function svredateBut1(){
		$("#svredate").val(DateAdd(new Date(),"m ", 6));
	}
	function svredateBut2(){
		$("#svredate").val(DateAdd(new Date(),"y ", 1));
	}
	function svredateBut3(){
		$("#svredate").val(DateAdd(new Date(),"y ", 3));
	}
	
		
	function reorder(){
		$("#about_style").hide();
		$("#list_style").show();
	}
	function columns(){
		$("#about_style").show();
		$("#list_style").hide();
	}
	
	
	function getLocaDog(type){
		if(type == 't'){
			$.ajax({
		    	url:"http://127.0.0.1:8087/license",
		    	type:"POST",
		    	dataType:"JSON",
		    	success:function(res){
		    		if(res != null && res != ""){
		    			$("#dogid").val(res);
		    		}else{
	    				layer.msg("请启动加密狗应用程序！",{icon: 2});
		    		}
		    	},
		    	error: function (XMLHttpRequest, textStatus, errorThrown) {
		    		layer.msg("请启动加密狗应用程序！",{icon: 2});
	            }
			});
		}else{
			$.ajax({
		    	url:"http://127.0.0.1:8087/license",
		    	type:"POST",
		    	dataType:"JSON",
		    	success:function(res){
		    		if(res != null && res != ""){
		    			$("#dogid").val(res);
		    		}else{
	    				layer.msg("请启动加密狗应用程序！",{icon: 2});
		    		}
		    	}
			});
		}
		
	} 
</script>
</body>
</html>

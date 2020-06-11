<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>


<head>
    <title>系统管理首页</title>
    <meta name="viewport" content="width=240, height=320, user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimun-scale=1.0" />
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
    <li class="active"> <a href="${ctxWeb}/license/enterpriseLicenseList"><i class="icon-angle-right"></i> <span>企业授权</span></a> </li>
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
            <table class="table table-bordered data-table table-responsive">
              <thead>
              	<tr>
                  <th colspan="13" style="text-align: left;">
			              <div class="controls">
			                	<input type="text" placeholder="请输入医院名称，编码" id="serchName" name="serchName"/>
			                	&nbsp;&nbsp;&nbsp;&nbsp;授权起始：<input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="授权起始日期" id="serchSdate" name="serchSdate"/>
			                	&nbsp;&nbsp;&nbsp;&nbsp;授权终止：<input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="请选择授权终止日期" id="serchEdate" name="serchEdate"/>
			                	<span class="pull-right">
			                		<a type="button" href="#" class="btn btn-success" onclick="licensePage()"/>查询</a>
									<a type="button" href="#" class="btn btn-success" onclick="saveLicenseModal()"/>新增授权</a>
								</span>
			                	<br/>
			                	<button type="button" class="btn btn-default btn-xs" value="3" onclick="licensePage('','',this.value)">3天内到期</button>
			                	<button type="button" class="btn btn-default btn-xs" value="5" onclick="licensePage('','',this.value)">5天内到期</button>
			                	<button type="button" class="btn btn-default btn-xs" value="7" onclick="licensePage('','',this.value)">7天内到期</button>
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
              <tbody id="qytbody">
              	<c:forEach items="${page.list}" var="list" varStatus="sta">
	           		<tr id="tr${list.id}">
	                  <td>${sta.index+1 }</td>
	                  <td>${list.code}</td>
	                  <td>${list.name}</td>
	                  <td><fmt:formatDate value="${list.accsdate}" pattern="yyyy-MM-dd" /></td>
	                  <td><fmt:formatDate value="${list.accedate}" pattern="yyyy-MM-dd" /></td>
	                  <td>${list.expireDay}天</td>
	                  <td><fmt:formatDate value="${list.svrsdate}" pattern="yyyy-MM-dd" /></td>
	                  <td><fmt:formatDate value="${list.svredate}" pattern="yyyy-MM-dd" /></td>
	                  <td>${list.svrtype == 0 ? "免费":"收费"}</td>
	                  <td style="width:250px;word-break:break-all;word-wrap:break-word;">${list.remarks}</td>
	                  <td>
	                  <a href="javascript:goAnewLicense('${list.id}')"><font style='color:#5bb75b;'>重新授权</font></a>
	                  &nbsp;<a href="${ctxWeb}/licenseFileXML/dowloadLicenseFile?id=${list.id}"><font style='color:#5bb75b;'>下载授权证书</font></a>
	                  &nbsp;<a href="javascript:delHospital('${list.id}')"><font style='color:#5bb75b;'>删除</font></a>
	                  </td>
	                </tr>
              	</c:forEach>
              		<tr>
              			<td colspan="13"><div class="jqueryPage" id="jqueryPage"></div></td>
              		</tr>
              </tbody>
            </table>
  		</div>
</div>

<!-- 新增授权信息模态窗口 -->
<div class="modal fade bs-example-modal-sm" role="dialog" data-toggle="modal" data-backdrop="static" id="saveLicenseModal" style="width:70%;left:30%;">
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
			                <input type="text" class="span8" placeholder="医院名称"  id="name" name="name" required />
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">医院编码:</label>
			              <div class="controls">
			              	<input type="hidden" class="span8" name="id" id="cid" />
			              	<input type="hidden" class="span8" name="parentid" id="parentid" value="0"/>
			                <input type="text" class="span8" readonly placeholder="医院编码" id="code" name="code" required/>
			                <input type="hidden" class="span8" placeholder="医院类型(0:集团医院,1:企业医院)"  value ="1" name="type"/>
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
			                  <input type="text" readonly="readonly" value="${ininDate}" data-date-format="yyyy-mm-dd" class="span4" name="accsdate" id="accsdate"  required placeholder="授权起始日期">
			                  <span class="add-on">--至--</span>
			                  <input type="text" readonly="readonly" data-date-format="yyyy-mm-dd" class="span4" name="accedate" id="accedate" required placeholder="授权终止日期">
			                  <button type="button" class="btn btn-info btn-xs" onclick="infinite()">无限制</button>
			                </div>
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">到期提示天数:</label>
			              <div class="controls">
			              	<input type="number"  placeholder="只能是正整数" name="expireDay" id="expireDay" pattern="[1-9]*" min="1"  oninput="if(value.length>3)value=value.slice(0,3)" required />
			              </div>
			            </div>
			            <div class="control-group">
			              <label class="control-label">加密狗ID:</label>
			              <div class="controls">
			               <input type="text" class="span8" placeholder="加密狗ID" name="dogid" id="dogid" required readonly="readonly"/>
			               <button type="button" class="btn btn-info btn-xs" onclick="getLocaDog()">刷新</button>
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
			              	<select name="svrtype" id="svrtype" class="span8">
			                  <option selected="selected" value="0">收费</option>
			                  <option value="1">免费</option>
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
          </form>
      	<!-- FORM表单 -->
      	<div class="modal-footer">
	        <button type="button" class="btn btn-default" onclick="edit()" id="editBut">保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
    </div>
  </div>
</div>
<!-- 新增授权信息模态窗口 -->


<script type="text/javascript" src="${ctxStatic}/js/ChinesePY.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/license.js"></script>
<script src="${ctxStatic}/js/jquery.ztree.all.js"></script>
<script src="${ctxStatic}/js/jqueryPage.js"></script>
<script src="${ctxStatic}/js/jqueryPage.js"></script>
<script src="${ctxStatic}/js/jquery.fs.stepper.js"></script>
<script>
	var zNodes = ${productList};
	var count = ${page.count};//总数
	var first = ${page.first};//首页索引
	var last = ${page.last};//尾页索引
	var pageNo = ${page.pageNo};//当前页
	var pageSize = ${page.pageSize};//每页条数
	var productArr = "";
	var error ='${error}';
	
	$("input[type='number']").stepper();
	
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
		if(error == 'error'){
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
			licensePage(options.index,pageSize,"");
		}
	});
	
	function licensePage(pageNo,pageSize,day){
		var accsdate = $("#serchSdate").val();
		var accedate = $("#serchEdate").val();
		var name = $("#serchName").val();
		var parma={"pageNo":pageNo,"pageSize":pageSize,"accsdate":accsdate,"accedate":accedate,"day":day,"name":name};
		
		$.ajax({
	    	url:"${ctxWeb}/license/licensePage",
	    	type:"POST",
	    	dataType:"JSON",
	    	data:parma,
	    	success:function(res){
	    		if(res.code==0){
	    			var data = res.data;
	    			var list = data.list;
	    			$("#qytbody").html("");
	    			var str ="";
	    			for (var i= 0; i < list.length; i++) {
	    				var inde = 1*1+i;
		           		str+="<tr id='tr"+list[i].id+"'>";
		           		str+="<td>"+inde+"</td>"
		                str+="<td>"+list[i].code+"</td>";
		           		str+="<td>"+list[i].name+"</td>";
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
		           		if(list[i].remarks == null || list[i].remarks == ""){
		           			str+="<td style='width:250px;word-break:break-all;word-wrap:break-word;'></td>";
		           		}else{
		           			str+="<td style='width:250px;word-break:break-all;word-wrap:break-word;'>"+list[i].remarks+"</td>";
		           		}
		           		str+="<td><a href='javascript:goAnewLicense("+list[i].id+")'><font style='color:#5bb75b;'>重新授权</font></a>";
		           		str+="&nbsp;<a href='${ctxWeb}/licenseFileXML/dowloadLicenseFile?id="+list[i].id+"'><font style='color:#5bb75b;'>下载授权证书</font></a>";
		           		str+="&nbsp;<a href='javascript:delHospital("+list[i].id+")'><font style='color:#5bb75b;'>删除</font></a></td></tr>";
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
	
	function edit(){
		var cid = $("#cid").val();
		//集团为 2企业为1
		if(saveCheck(1)){
			layer.load();
			$("#editBut").attr("disabled", true);
			if(cid == null || cid == ""){
				save();
			}else{
				updateLicense();
			}
		}
	}

	function save(){
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
	    			window.location.href="${ctxWeb}/licenseFileXML/createLicenseFile?id="+res.data;
	    		}else{
	    			layer.msg(res.message);
	    		}
	    	}
	    })
	}

	
	//打开重新授权模态窗口
	function goAnewLicense(id){
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
		    			$("#name").val(data.name);
		    			$("#code").val(data.code);
		    			$("#svrsdate").val(formatDateTime(data.svrsdate));
		    			$("#svredate").val(formatDateTime(data.svredate));
		    			$("#svrtype").val(formatDateTime(data.svrtype));
		    			$("#accsdate").val(formatDateTime(data.accsdate));
		    			$("#accedate").val(formatDateTime(data.accedate));
		    			$("#expireDay").val(data.expireDay);
		    			$("#dogid").val(data.dogid);
		    			$("#provincial").val(data.provincial);
		    			$("#remarks").val(data.remarks);
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
	
	function saveLicenseModal(){
		getLocaDog();
		$("#cid").val("");
		$("#name").val("");
		$("#svredate").val("");
		$("#svrtype").val("");
		$("#remarks").val("");
		corCode();
		$("#saveLicenseModal").modal('show');
	}
	
	function updateLicense(){
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
	    			window.location.href="${ctxWeb}/licenseFileXML/createLicenseFile?id="+res.data;
	    		}else if(res.code== -1){
	    			window.location.href="${ctxWeb}/logout";
	    		}else{
	    			layer.msg(res.message);
	    		}
	    	}
	    })
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
	
	function getLocaDog(){
		$.ajax({
	    	url:"http://127.0.0.1:8087/license",
	    	type:"POST",
	    	dataType:"JSON",
	    	success:function(res){
	    		if(res != null && res != ""){
	    			$("#dogid").val(res);
	    		}else{
	    			layer.msg("请启动加密狗应用程序",{icon: 2});
	    		}
	    	},
	    	error: function (XMLHttpRequest, textStatus, errorThrown) {
	    		layer.msg("请启动加密狗应用程序！",{icon: 2});
            }
		});
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
	
	</script>
</body>
</html>



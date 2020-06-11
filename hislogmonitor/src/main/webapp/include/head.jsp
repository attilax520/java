<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.cnhis.cloudhealth.module.license.entity.SysUser" %>
<%@ include file="/include/taglib.jsp"%>
<%  
    SysUser user = (SysUser)request.getSession().getAttribute("user");  
    Integer userId = user.getId();
	String sysAccount = user.getSysAccount();
%> 

	<link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/fullcalendar.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/matrix-style.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/matrix-media.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/datepicker.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/layer.css"/>
	
	<link href="${ctxStatic}/font-awesome/css/font-awesome.css" rel="stylesheet" />
	<script src="${ctxStatic}/js/excanvas.min.js"></script>
	<script src="${ctxStatic}/js/jquery.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.ui.custom.js"></script> 
	<script src="${ctxStatic}/js/bootstrap.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.flot.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.flot.resize.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.peity.min.js"></script> 
	<script src="${ctxStatic}/js/fullcalendar.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.gritter.min.js"></script>
	<script src="${ctxStatic}/js/jquery.validate.js"></script> 
	<script src="${ctxStatic}/js/jquery.wizard.js"></script> 
	<script src="${ctxStatic}/js/select2.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.peity.min.js"></script> 
	<script src="${ctxStatic}/js/jquery.dataTables.min.js"></script>
	<script src="${ctxStatic}/js/bootstrap-paginator.js"></script>
	<script src="${ctxStatic}/js/bootstrap-colorpicker.js"></script> 
	<script src="${ctxStatic}/js/jquery.uniform.js"></script>
	<script src="${ctxStatic}/js/lab2.js"></script> 
	<script src="${ctxStatic}/js/select2.min.js"></script> 
	<script src="${ctxStatic}/js/matrix.form_common.js"></script>
	<script src="${ctxStatic}/js/bootstrap-datepicker.js"></script>
	<script src="${ctxStatic}/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${ctxStatic}/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${ctxStatic}/js/matrix.js"></script>
	<script src="${ctxStatic}/js/dateUtils.js"></script>
	<script src="${ctxStatic}/js/layer.js"></script>
	
	
<div id="header">
  <h1></h1>
</div>
<!---------------头部开始------------------->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">个人信息</span><b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a href="#" data-toggle="modal" data-target="#editPwd" ><i class="icon-user"></i>修改密码</a></li>
        <li class="divider"></li>
        <li><a href="${ctxWeb}/logout"><i class="icon-key"></i>注销</a></li>
      </ul>
    </li>
    <li class=""><a title="" href="${ctxWeb}/logout"><i class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
  </ul>
</div>
<!---------------头部开始------------------->
<!-- 密码修改modal -->
<div class="modal fade" data-backdrop="static" tabindex="-1" role="dialog" id="editPwd">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
    	<div class="modal-header" style="background: #01aeef;color:#fff">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="gridSystemModalLabel">密码修改</h4>
      	</div>
      	<!-- FORM表单 -->
          <form action="${ctxWeb}/updatePwd" method="post" class="form-horizontal">
          	<input type="hidden" id="userId" value="<%=userId %>"/>
          	<input type="hidden" id="sysAccount" value="<%=sysAccount %>"/>
            <div class="control-group">
              <label class="control-label">原&nbsp;密&nbsp;码 :</label>
              <div class="controls">
                <input type="password" class="span3" id="oldPassword" min="6" maxlength="12" required="required" placeholder="原始密码"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">新&nbsp;密&nbsp;码 :</label>
              <div class="controls">
                <input type="password" class="span3" id="sysPassword" placeholder="新密码" min="6" maxlength="12" required="required" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">确认密码 :</label>
              <div class="controls">
                <input type="password" class="span3" id="affirmPwd" placeholder="确认密码" min="6" maxlength="12" required="required" />
              </div>
            </div>
          </form>
      	<!-- FORM表单 -->
      	<div class="modal-footer">
	        <button type="button" class="btn btn-default" id="updatePwd" value="Validate">保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
    </div>
  </div>
</div>
<!-- 密码修改modal -->

<script>
	$(function(){
		$("#updatePwd").click(function(){
			var userId = $("#userId").val();
			var sysAccount = $("#sysAccount").val();
			var oldPassword = $("#oldPassword").val();
			var sysPassword = $("#sysPassword").val();
			var affirmPwd = $("#affirmPwd").val();
			
			if(oldPassword == ""){
				layer.msg("原密码不能为空");
				return false;
			}
			if(sysPassword == ""){
				layer.msg("新密码不能为空");
				return false;
			}
			
			if(sysPassword.length < 6){
				layer.msg("密码不能少于6位");
				return false;
			}
			
			if(affirmPwd == ""){
				layer.msg("确认密码不能为空");
				return false;
			}
			if(sysPassword != affirmPwd){
				layer.msg("两次输入的密码不相同！");
				return false;
			}
			$.ajax({
				url:"${ctxWeb}/updatePwd",
				type:"POST",
				data:{"id":userId,"sysAccount":sysAccount,"sysPassword":sysPassword,"oldPassword":oldPassword},
				dataType:"JSON",
				success:function(res){
					if(res.code == 0){
						window.location.href="${ctxWeb}";
					}else{
						layer.msg(res.message);
					}
				}
			})
		});
	})
	
</script>

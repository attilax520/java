<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>授权管理中心登录</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />  <!--以IE8模式解析页面-->
    <link rel="shortcut icon" href="${ctxStatic}/img/favicon.ico" />
	<link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/matrix-login.css" />
    <link href="${ctxStatic}/font-awesome/css/font-awesome.css" rel="stylesheet" />
	<script src="${ctxStatic}/js/jquery.min.js"></script>
    <script src="${ctxStatic}/js/bootstrap.min.js"></script>
    <script src="${ctxStatic}/js/layer.js"></script>
</head>
<body>
        <div id="loginbox">
            <form id="loginform" class="form-vertical" action="#" data-toggle="validator" role="form">
				<div class="control-group normal_text"> <h3><img src="${ctxStatic}/img/logo.png" alt="Logo" /></h3></div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lg"><i class="icon-user"></i></span><input type="text" placeholder="用户名" id="sysAccount" autocomplete="off" />
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i class="icon-lock"></i></span><input type="password" placeholder="密码" id="sysPassword" autocomplete="off" />
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-right"><a type="submit" href="javascript:void(0)" class="btn btn-success" id="login" />登录</a></span>
                </div>
            </form>
        </div>
</body>
</html>
<script>
	$(function(){
		keyupLogin();
		
		$("#login").click(function(){
			var sysAccount = $("#sysAccount").val();
			var sysPassword = $("#sysPassword").val();
			$.ajax({
				url:"${ctxWeb}/login",
				type:"POST",
				data:{"sysAccount":sysAccount,"sysPassword":sysPassword},
				dataType:"JSON",
				success:function(res){
					if(res.code == 0){
						window.location.href="${ctxWeb}/license/licenseList";
					}else{
						layer.msg(res.message);
					}
				}
			})
			
			
		})
	})
	
	//回车登录事件
function keyupLogin() {
    document.onkeydown = function (e) {
        if (!e) {
            e = window.event;
        }
        // window.event
        if ((e.keyCode || e.which) == 13) {
        	var pwd = $("#sysPassword").val();
        	
        	if(pwd == null || pwd == ""){
        		$("#sysPassword").focus();
        	}else{
	            $('#login').click();
        	}
        }
    };
}
	
</script>

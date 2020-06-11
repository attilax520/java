$(function(){
		$('#svrsdate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#svredate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$("#bloc_sdate").datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#bloc_edate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#bloc_svrsdate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#bloc_svredate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#accsdate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#accedate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$("#serchSdate").datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#serchEdate').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$("#serchSdate1").datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		$('#serchEdate1').datetimepicker({
	        language: 'zh-CN',  //日期
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
		
		$("#name").blur(function(){
			$("#name").css("border","1px solid #ccc");
		});
		$("#provincial").blur(function(){
			$("#provincial").css("border","1px solid #ccc");
		});
		$("#expireDay").blur(function(){
			$("#expireDay").css("border","1px solid #ccc");
		});
		$("#blocName").blur(function(){
			$("#blocName").css("border","1px solid #ccc");
		});
		$("#blocBrief").blur(function(){
			$("#blocBrief").css("border","1px solid #ccc");
		});
		
	});
	
	
	
	
	function saveCheck(parme){
		var name = $("#name").val();
		if(name == null || name == ""){
			$("#name").css("border","1px solid red");
			layer.msg("医院名称不能为空");
			return false;
		}
		
		var code = $("#code").val();
		if(code == null || code == ""){
			$("#code").css("border","1px solid red");
			layer.msg("CODE不能为空");
			return false;
		}else{
			$("#code").css("border","1px solid #ccc");
		}
		
		var provincial = $("#provincial").val();
		if(provincial == "0"){
			$("#provincial").css("border","1px solid red");
			layer.msg("请选择医院所在地区");
			return false;
		}
		
		if(parme == 1){
			var dog = $("#dogid").val();
			if(dog == null || dog == ""){
				$("#dogid").css("border","1px solid red");
				layer.msg("加密狗ID不能为空");
				return false;
			}else{
				$("#dogid").css("border","1px solid #ccc");
			}
		}
		
		var accsdate = $("#accsdate").val();
		if(accsdate == null || accsdate == ""){
			$("#accsdate").css("border","1px solid red");
			layer.msg("产品授权起始日期不能为空");
			return false;
		}else{
			$("#accsdate").css("border","1px solid #ccc");
		}
		var accedate = $("#accedate").val();
		if(accedate == null || accedate == ""){
			$("#accedate").css("border","1px solid red");
			layer.msg("产品授权终止日期不能为空");
			return false;
		}else{
			$("#accedate").css("border","1px solid #ccc");
		}
		
		var expireDay = $("#expireDay").val();
		if(expireDay == null || expireDay == ""){
			$("#expireDay").css("border","1px solid red");
			layer.msg("到期提示天数不能为空");
			return false;
		}else{
			$("#expireDay").css("border","1px solid #ccc");
		}
		
		var svrsdate = $("#svrsdate").val();
		if(svrsdate == null || svrsdate == ""){
			$("#svrsdate").css("border","1px solid red");
			layer.msg("服务起始日期不能为空");
			return false;
		}else{
			$("#svrsdate").css("border","1px solid #ccc");
		}
		var svredate = $("#svredate").val();
		if(svredate == null || svredate == ""){
			$("#svredate").css("border","1px solid red");
			layer.msg("服务终止日期不能为空");
			return false;
		}else{
			$("#svredate").css("border","1px solid #ccc");
		}
		
		if(productArr == "" || productArr.length ==0){
			layer.msg("授权模块至少选择一个");
			return false;
		}
		return true;
	}
	
	function saveBlocCheck(){
		var blocName = $("#blocName").val();
		if(blocName == null || blocName == ""){
			$("#blocName").css("border","1px solid red");
			layer.msg("集团名称不能为空");
			return false;
		}
		
		var provincial = $("#blocBrief").val();
		if(provincial == null || provincial == ""){
			$("#blocBrief").css("border","1px solid red");
			layer.msg("集团简介不能为空");
			return false;
		}
		return true;
	}
	

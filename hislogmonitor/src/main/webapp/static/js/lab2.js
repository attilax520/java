$(function(){
	//单选
	$('#b1').click(function(){
		$obj = $('.selAll option:selected').clone(true);
		if($obj.size() >0){
			$label = $('.selAll option:selected').parent('.pgroup').attr("label");
			$id = $('.selAll option:selected').parent('.pgroup').attr("id");
			if($("#s"+$id).length == 0){
				var group="<optgroup class='pgroup' label='"+$label+"' id='s"+$id+"'></optgroup>";
				$('#productArr').append(group);
			}
			$('#s'+$id).append($obj);
			$('.selAll option:selected').remove();
		}
	});
	
	//全选
	$('#b2').click(function(){
		$('#productArr').append($('#s1 .pgroup'));
		$('#s1 .pgroup').append($('#s1 .opts'));
	});
	
	//删除某一条
	$('#b3').click(function(){
		$sid = $('#s1 option:selected').parent('.pgroup').attr("id");
		console.log($sid);
		if($sid == "undefined"){
			alert($sid);
		}
		$id = $('#productArr option:selected').parent('.pgroup').attr("id");
		$obj = $('.selAll option:selected').clone(true);
		if($obj.size() >0){
			var sidArr= $id.split('sgroup');
			if(sidArr.length <= 1){
				sidArr = $id.split('group');
			}
			var sid = sidArr[1];
			
			var optlength = $('#productArr #'+$id).children('option').length;
			var grouplength = $('#s1 #'+$id).children('option').length;
			if(grouplength==0){
				$label = $('.selAll option:selected').parent('.pgroup').attr("label");
				var group="<optgroup class='pgroup' label='"+$label+"' id='"+$id+"'></optgroup>";
				$('#s1').append(group);
			}
			
			$('#s1 #'+$id).append($obj);
			$('#productArr option:selected').remove();
			if(optlength <= 1){
				$('#productArr #'+$id).remove();
			}
		}
	});
	
	//全删
	$('#b4').click(function(){
		$('#s1').append($('#productArr .pgroup'));
		$('#s1 .pgroup').append($('#productArr .opts'));
	});
	
//	$('.opts').dblclick(function(){
//		var flag = $(this).parent().attr('id');
//		if(flag == "s1"){
//			var $obj = $(this).clone(true);
//			console.log($obj);
//			$('#productArr').append($obj);
//			$(this).remove();
//		} else {
//			var $obj = $(this).clone(true);
//			console.log($obj);
//			$('#s1').append($obj);
//			$(this).remove();
//		}
//	});
	
});
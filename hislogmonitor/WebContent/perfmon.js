cputopdata = [ 10, 12, 21, 54, 60, 30, 0 ];
memtopdata = [ 10, 12, 21, 54, 60, 30, 0 ];
iotopdata = [ 10, 12, 21, 54, 60, 30, 0 ];

function inihost() {

	$.ajax({

		url : "./webdavurl/host.txt",
		dataType : "json",

		data : "method=cpuservice.sysinfo&" + Math.random(),
		type : "get",

		success : function(d) {
			console.log(d);
			console.log("----fini");

			$.each(d, function(n, item) {
				$('#encode_list').append(
						'<option label="' + item + '" value="' + item
								+ '"></option>');
			});

		}
	});

}
inihost()
function btn_click() {

	diskspace_ajax_echart();
}

function getPerfinfo_cpuMem_getAjaxDAta() {
	var svrinfo = $("#svrinfo").val();
	if (svrinfo == "") {
		var ajax_data_invoke_method = "new=xx&class=com.attilax.device.perfMonitorServ&method=sysinfo_curpc ";
		svrinfo = "127.0.0.1,u,p,auto";
	} else {

		var a = svrinfo.split(",");
		var svrinfo_obj = {};
		svrinfo_obj.host = a[0];
		svrinfo_obj.user = a[1];
		svrinfo_obj.pwd = a[2];
		svrinfo_obj.os = a[3];

		var toStr_svrinfo_obj = JSON.stringify(svrinfo_obj);

		var paramEncoded = encodeURIComponent(toStr_svrinfo_obj);
		var ajax_data_invoke_method = "new=xx&class=com.attilax.device.perfMonitorServ&method=sysinfo&argstypes=map&args="
				+ encodeURIComponent(paramEncoded);
	}

	return ajax_data_invoke_method;
}

function getPerfinfo_cpuMem() {

	$.ajax({

		url : "./commServletV3",
		dataType : "json",

		data : getPerfinfo_cpuMem_getAjaxDAta(),
		type : "get",

		success : function(d) {
			console.log(d);
			console.log("----fini");
			// {cpu=77, mem={canuse=1045, all=8100}}
			var memusePercent = 50;
			try {

				memAll = d.mem.all;
				memCanuse = d.mem.canuse;
				memuse = memAll - memCanuse;
				f = memuse / memAll * 100;
				memusePercent = Math.ceil(f);
			} catch (e) {
				console.log(e);
			}
			chartini(d.cpu, memusePercent, d);
		}
	});

}
function main() {

	var iniobj = {};
	iniobj.io = {
		"DiskReadBytesPersec" : "100",
		"DiskWriteBytesPersec" : "10",
		"DiskReadsPersec" : "10",
		"DiskWritesPersec" : "20",
		"DiskBytesPersec" : "110",
		"DiskTransfersPersec" : "50",
		"PercentDiskTime" : 10
	};
	chartini(50, 30, iniobj);
	//	window.clipboardData.setData('Text', 'aaaaaaaaajs clip');

	window.setInterval(function() {

		//getPerfinfo_cpuMem
		getPerfinfo_cpuMem()

	}, 5000);

	diskspace_ajax_echart();

}

function diskspace_ajax_echart() {
	//192.168.1.18,root,cloudhealth

	var svrinfo = $("#svrinfo").val();
	if (svrinfo == "")
		svrinfo = "127.0.0.1,u,p";
	$
			.ajax({

				url : "./commServletV2",
				dataType : "json",
				data : "new=x&class=com.attilax.io.SpaceCheck&method=spaceinfo&paramtypes=str,str,str,str&args="
						+ svrinfo,
				type : "get",

				success : function(d) {
					console.log(d);
					console.log("----fini");
					// {cpu=77, mem={canuse=1045, all=8100}}

					var fst = d[0];

					diskspace_ini(fst.file_getUsableSpace.GB,
							fst.file_getTotalSpace.GB, fst.used_space.GB);
				}
			});
}

function diskspace_ini(useable, total, used) {
	var col1_txt = '使用' + used + "GB"
	var col2_txt = '剩余' + useable + "GB"
	var myChart = echarts.init(document.getElementById('diskspace'));
	option = {
		title : {
			text : '磁盘占用',
			subtext : '',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : [ col1_txt, col2_txt ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'pie', 'funnel' ],
					option : {
						funnel : {
							x : '25%',
							width : '50%',
							funnelAlign : 'left',
							max : 1548
						}
					}
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : [ {
			name : '访问来源',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [

			{
				value : used,
				name : col1_txt
			}, {
				value : useable,
				name : col2_txt
			},

			]
		} ]
	};

	myChart.setOption(option);
}

function chartini(cpu, mem, o) {
	try {
		iniCpuMemIoOnTopBar(cpu, mem, o);

	} catch (e) {
		console.log(e);
	}

	try {
		iniiobyte(o);
		iniiops(o);

	} catch (e) {
		console.log(e);
	}

}

// --------------end chartini
function iniCpuMemIoOnTopBar(cpu, mem, o) {
	// move a space to left
	for ( var idx in cputopdata) {
		try {
			var lastval = cputopdata[1 + parseInt(idx)];
			cputopdata[idx] = lastval;
		} catch (e) {
		}
	}

	cputopdata[6] = cpu;
	var PercentDiskTime = 0;
	try {
		PercentDiskTime = o.io.PercentDiskTime;
	} catch (e) {
		console.log(e);
	}
	// var define = require('echarts/define');
	// var ecConfig = echarts.config;
	// e_macarons ,'dark'
	var myChart = echarts.init(document.getElementById('main'));
	option = {
		theme : "dark",
		title : {
			text : '服务器资源占用情况',
			subtext : 'cpu mem io'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ 'cpu', '内存', 'io' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			boundaryGap : false,
			data : [ 't1', 't2', 't3', 't4', 't5', 't6', 't7' ]
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : 'cpu',
			type : 'line',
			smooth : true,
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : cputopdata
		}, {
			name : '内存',
			type : 'line',
			smooth : true,
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : [ 30, 82, 34, 91, 90, 30, mem ]
		}, {
			name : 'io',
			type : 'line',
			smooth : true,
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : [ 20, 32, 10, 32, 20, 5, PercentDiskTime ]
		} ]
	};

	myChart.setOption(option);
}

function iniiobyte(o)

{

	// DiskReadBytesPersec,DiskWriteBytesPersec,,DiskReadsPersec,DiskWritesPersec

	DiskReadBytesPersec_span = o.io.DiskBytesPersec - o.io.DiskReadBytesPersec;
	DiskWriteBytesPersec_span = o.io.DiskBytesPersec
			- o.io.DiskWriteBytesPersec;
	//o.io.DiskTransfersPersec

	var myChart = echarts.init(document.getElementById('iobytes'));

	var dataStyle = {
		normal : {
			label : {
				show : true
			},
			labelLine : {
				show : false
			}
		}
	};
	var placeHolderStyle = {
		normal : {
			color : 'rgba(0,0,0,0)',
			label : {
				show : false
			},
			labelLine : {
				show : false
			}
		},
		emphasis : {
			color : 'rgba(0,0,0,0)'
		}
	};
	option = {
		title : {
			text : 'io bytes',
			subtext : '..',
			sublink : 'http://e.weibo.com/1341556070/AhQXtjbqh',
			x : 'center',
			y : 'center',
			itemGap : 20,
			textStyle : {
				color : 'rgba(30,144,255,0.9)',
				fontFamily : '微软雅黑',
				fontSize : 35,
				fontWeight : 'bolder'
			}
		},
		tooltip : {
			show : true,
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : document.getElementById('main').offsetWidth / 2,
			y : 45,
			itemGap : 12,
			data : [ '读写总字节', '读取字节' + o.io.DiskReadBytesPersec,
					'写入字节' + o.io.DiskWriteBytesPersec ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		series : [ {

			name : '1',
			type : 'pie',
			clockWise : false,
			radius : [ 125, 150 ],
			itemStyle : dataStyle,
			data : [ {
				value : o.io.DiskBytesPersec,
				name : '读写总字节'
			}, {
				value : 1,
				name : 'invisible',
				itemStyle : placeHolderStyle
			} ]
		}, {
			name : '2',
			type : 'pie',
			clockWise : false,
			radius : [ 100, 125 ],
			itemStyle : dataStyle,
			data : [ {
				value : o.io.DiskReadBytesPersec,
				name : '读取字节' + o.io.DiskReadBytesPersec
			}, {
				value : DiskReadBytesPersec_span,
				name : 'invisible',
				itemStyle : placeHolderStyle
			} ]
		}, {
			name : '3',
			type : 'pie',
			clockWise : false,
			radius : [ 75, 100 ],
			itemStyle : dataStyle,
			data : [ {
				value : o.io.DiskWriteBytesPersec,
				name : '写入字节' + o.io.DiskWriteBytesPersec
			}, {
				value : DiskWriteBytesPersec_span,
				name : 'invisible',
				itemStyle : placeHolderStyle
			} ]
		} ]

		,
		color : [ 'rgba(200,200,200,1)', 'rgba(195,230,253,1)',
				'rgba(250,150,110,1)' ]
	};

	myChart.setOption(option);

}

function iniiops(o) {

	//DiskReadBytesPersec,DiskWriteBytesPersec,,DiskReadsPersec,DiskWritesPersec

	DiskReadsPersec_span = o.io.DiskTransfersPersec - o.io.DiskReadsPersec;
	DiskWritesPersec_span = o.io.DiskTransfersPersec - o.io.DiskWritesPersec;
	//o.io.DiskTransfersPersec

	var myChart = echarts.init(document.getElementById('iops'));

	var dataStyle = {
		normal : {
			label : {
				show : true
			},
			labelLine : {
				show : false
			}
		}
	};
	var placeHolderStyle = {
		normal : {
			color : 'rgba(0,0,0,0)',
			label : {
				show : false
			},
			labelLine : {
				show : false
			}
		},
		emphasis : {
			color : 'rgba(0,0,0,0)'
		}
	};
	option = {
		title : {
			text : 'iops',
			subtext : '..',
			sublink : 'http://e.weibo.com/1341556070/AhQXtjbqh',
			x : 'center',
			y : 'center',
			itemGap : 20,
			textStyle : {
				color : 'rgba(30,144,255,0.8)',
				fontFamily : '微软雅黑',
				fontSize : 35,
				fontWeight : 'bolder'
			}
		},
		tooltip : {
			show : true,
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : document.getElementById('main').offsetWidth / 2,
			y : 45,
			itemGap : 12,
			data : [ '总iops', '读取iops' + o.io.DiskReadsPersec,
					'写入iops' + o.io.DiskWritesPersec ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		series : [ {
			name : '1',
			type : 'pie',
			clockWise : false,
			radius : [ 125, 150 ],
			itemStyle : dataStyle,
			data : [ {
				value : o.io.DiskTransfersPersec,
				name : '总iops'
			}, {
				value : 1,
				name : 'invisible',
				itemStyle : placeHolderStyle
			} ]
		}, {
			name : '2',
			type : 'pie',
			clockWise : false,
			radius : [ 100, 125 ],
			itemStyle : dataStyle,
			data : [ {
				value : o.io.DiskReadsPersec,
				name : '读取iops' + o.io.DiskReadsPersec
			}, {
				value : DiskReadsPersec_span,
				name : 'invisible',
				itemStyle : placeHolderStyle
			} ]
		}, {
			name : '3',
			type : 'pie',
			clockWise : false,
			radius : [ 75, 100 ],
			itemStyle : dataStyle,
			data : [ {
				value : o.io.DiskWritesPersec,
				name : '写入iops' + o.io.DiskWritesPersec
			}, {
				value : DiskWritesPersec_span,
				name : 'invisible',
				itemStyle : placeHolderStyle
			} ]
		} ]

		,
		color : [ 'rgba(200,200,200,1)', 'rgba(195,230,253,1)',
				'rgba(250,150,110,1)' ]
	};

	myChart.setOption(option);

}

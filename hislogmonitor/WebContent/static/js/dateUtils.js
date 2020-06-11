var now = new Date(); //当前日期 
var nowDayOfWeek = now.getDay(); //今天本周的第几天 
var nowDay = now.getDate(); //当前日 
var nowMonth = now.getMonth(); //当前月 
var nowYear = now.getYear(); //当前年 

nowYear += (nowYear < 2000) ? 1900 : 0; // 

var lastMonthDate = new Date(); //上月日期 
lastMonthDate.setDate(1); 
lastMonthDate.setMonth(lastMonthDate.getMonth()-1); 
var lastYear = lastMonthDate.getYear(); 
var lastMonth = lastMonthDate.getMonth(); 


//格局化日期：yyyy-MM-dd 
function formatDate(date) { 
	var myyear = date.getFullYear(); 
	var mymonth = date.getMonth()+1; 
	var myweekday = date.getDate(); 
	if(mymonth < 10){ 
		mymonth = "0" + mymonth; 
	} 
	if(myweekday < 10){ 
		myweekday = "0" + myweekday; 
	} 
	return (myyear+"-"+mymonth + "-" + myweekday); 
}

//获得某月的天数 
function getMonthDays(myMonth){ 
	var monthStartDate = new Date(nowYear, myMonth, 1); 
	var monthEndDate = new Date(nowYear, myMonth + 1, 1); 
	var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24); 
	return days;
}

//时间戳转年月日
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d;    
}

//获得本季度的开端月份 
function getQuarterStartMonth(){ 
	var quarterStartMonth = 0; 
	if(nowMonth<3){ 
		quarterStartMonth = 0; 
	} 
	if(2<nowMonth && nowMonth<6){ 
		quarterStartMonth = 3; 
	} 
	if(5<nowMonth && nowMonth<9){ 
		quarterStartMonth = 6; 
	} 
	if(nowMonth>8){ 
		quarterStartMonth = 9; 
	} 
	return quarterStartMonth; 
}

//获得本周的开端日期 
function getWeekStartDate() { 
	var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek); 
	return formatDate(weekStartDate);
}

//获得本周的停止日期 
function getWeekEndDate() { 
	var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)); 
	return formatDate(weekEndDate); 
}

//获得本月的开端日期 
function getMonthStartDate(){ 
	var monthStartDate = new Date(nowYear, nowMonth, 1); 
	return formatDate(monthStartDate);
}

//获得本月的停止日期 
function getMonthEndDate(){ 
	var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth)); 
	return formatDate(monthEndDate); 
}

//获得上月开端时候 
function getLastMonthStartDate(){ 
	var lastMonthStartDate = new Date(nowYear, lastMonth, 1); 
	return formatDate(lastMonthStartDate);
}

//获得上月停止时候 
function getLastMonthEndDate(){ 
	var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth)); 
	return formatDate(lastMonthEndDate); 
}

//获得本季度的开端日期 
function getQuarterStartDate(){ 
	var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1); 
	return formatDate(quarterStartDate); 
} 

//或的本季度的停止日期 
function getQuarterEndDate(){ 
	var quarterEndMonth = getQuarterStartMonth() + 2; 
	var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth)); 
	return formatDate(quarterStartDate); 
}


/*
 *   功能:实现时间的加减功能.
 *   参数:interval,字符串表达式，表示要添加的时间间隔 注意参数后加空格.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   返回:新的时间字符串.
 *   
 *	var now = new Date();
 *	// 加五天.
 *	var newDate = DateAdd("d ", 5);
 *	alert(newDate.toLocaleDateString())
 *	// 加两个月.
 *	newDate = DateAdd("m ", 2);
 *	alert(newDate.toLocaleDateString())
 *	// 加一年
 *	newDate = DateAdd("y ", 1);
 *	alert(newDate.toLocaleDateString())
 *   
 *---------------   DateAdd(interval,number,date)   -----------------
 */
function DateAdd(data,interval, number) {
    switch (interval) {
    case "y ": {
        data.setFullYear(data.getFullYear() + number);
        return formatDate(data);
        break;
    }
    case "q ": {
        data.setMonth(data.getMonth() + number * 3);
        return formatDate(data);
        break;
    }
    case "m ": {
        data.setMonth(data.getMonth() + number);
        return formatDate(data);
        break;
    }
    case "w ": {
        data.setnow(data.getnow() + number * 7);
        return formatDate(data);
        break;
    }
    case "d ": {
    	data.setnow(data.getnow() + number);
        return formatDate(data);
        break;
    }
    case "h ": {
        data.setHours(data.getHours() + number);
        return formatDate(data);
        break;
    }
    case "m ": {
        data.setMinutes(data.getMinutes() + number);
        return formatDate(data);
        break;
    }
    case "s ": {
        data.setSeconds(data.getSeconds() + number);
        return formatDate(data);
        break;
    }
    default: {
        data.setnow(d.getnow() + number);
        return formatDate(data);
        break;
    }
    }
}

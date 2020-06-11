var serverSavePath = "/home/cnhis/cnhisShell";
serverSavePath = "/0installx";



function testx(){

    //------------for dbg
    var testdats = { "dbginfo": ["upzip file准备解压文件:update.bat", "upzip file to dst解压文件到当前目录:c:\\d\\dd2/update.bat", "upzip file准备解压文件:war/", "upzip file to dst解压文件到当前目录:c:\\d\\dd2/war/"] };
    var dbg = testdats.dbginfo;
    //下面使用each进行遍历
    $.each(dbg, function (n, value) {
        console.log(value);
    });
    console.log(dbg.join("\r\n"));
    //------------for dbg  end

}
	



//------------------for upload

var upPrcsPctTime;
function upload() {


   

    $("#savepath").val("上传中");
    $("#loadimg").show();
    var self = this;

    var fd = new FormData();
    fd.append("svrinfo", $("#svrinfo").val());
    fd.append("serverSavePath", serverSavePath);
    fd.append("upfile", $("#filex").get(0).files[0]);
    $.ajax({
        url: "../UploadServlet1",
        type: "POST",
        processData: false,
        contentType: false,
        data: fd,
        success: function (d) {
            console.log(d);
            console.log("----fini");
            $("#savepath").val(d);
            $("#loadimg").hide();
            $("#upaftinfo").show();
            window.setTimeout(function () {

                window.clearInterval(upPrcsPctTime);

            }, 5000);

        }
    });

    window.clearInterval(upPrcsPctTime);
    upPrcsPctTime = window.setInterval(function () {
        var url = "../commServletV3?class=%20com.attilax.web.UploadServlet1%20&method=upProcessInfo%20&argstypes=&args=";
        $.get(url, function (result, status, xhr) {
            var upPct = result[0]; var putpct = result[1];
            $("#uppct").text(upPct.rate + "%");
            $("#putpct").text(putpct.rate + "%");

        }, "json");


    }, 3000);


}




//----------------------------for exec     
var console_ret_timer;

function btn_click() {

    var rztContainId = Math.random();
    $("#loadimg_exe").show();
    $("#editctrl").val("");
    //alert("");
    url = "../autoupSrvV3?cmdtextbox=@savepath@&mode=&cwd=" + $("#cwd").val() + "&cfgtextbox=@cfgtextbox@&cmdFrom=file&encode=@encode@&jarupzippath=@shellExePath@&rztContainId=" + rztContainId;

    url = url.replace("@savepath@", encodeURIComponent($("#savepath").val()));
    url = url.replace("@cfgtextbox@", encodeURIComponent($("#cfgtextbox").val()));
    url = url.replace("@encode@", $("#encodetxt").val());
    url = url.replace("@shellExePath@", encodeURIComponent($("#shellExePath").val()));
    url = url + "&svrinfo=" + encodeURIComponent($("#svrinfo").val());

    $.get(url, function (result, status, xhr) {
        if (result['@type']) {
            //  @type":"com.attilax.util.RuntimeExceptionAtiVer   
            $("#editctrl").val(JSON.stringify(result));
            $("#loadimg_exe").hide();
            return;
        }

        //   console.log( result.join("\r\n"));





        $("#loadimg_exe").hide();

        clearTimeout(console_ret_timer);

        //finish last a cat cmd ret
        showConsoleRet_last(rztContainId, function () {
            clearTimeout(console_ret_timer);
            $("#editctrl").val($("#editctrl").val() + "\r\n" + JSON.stringify(result));
            var scrollTop = $("#editctrl")[0].scrollHeight;
            $("#editctrl").scrollTop(scrollTop);

        });
    }, "json");


    //reset timer if re click btn
    if (console_ret_timer) {
        clearTimeout(console_ret_timer);
        //clearInterval(console_ret_timer);  
    }
    console_ret_timer = window.setTimeout(
        function () {
            console.log("showConsoleRet...");
            showConsoleRet(rztContainId);
        }
        , 2000);

}


function showConsoleRet_last(rztContainId, aftfun) {
    //  http://localhost:1314/hislog/commServletV2?new=xx&class=com.attilax.autoup.autoupdate&method=getRztByContainid&paramtypes=str&args=1213
    // 
    var url = "../commServletV2?new=xx&class=com.attilax.autoup.autoupdate&method=getRztByContainid&paramtypes=str&args=" + rztContainId;

    $.get(url, function (result, status, xhr) {

        $("#loadimg_exe").hide();
        if (result['@type']) {
            //  @type":"com.attilax.util.RuntimeExceptionAtiVer   
            $("#editctrl").val(JSON.stringify(result));

            return;
        }


        if (result.length > 0) {
            $("#editctrl").val($("#editctrl").val() + "\r\n" + result.join("\r\n"));
            var scrollTop = $("#editctrl")[0].scrollHeight;
            $("#editctrl").scrollTop(scrollTop);

        }

        aftfun();
        return;




    }, "json");


}


function showConsoleRet(rztContainId) {
    //  http://localhost:1314/hislog/commServletV2?new=xx&class=com.attilax.autoup.autoupdate&method=getRztByContainid&paramtypes=str&args=1213
    // 
    var url = "../commServletV2?new=xx&class=com.attilax.autoup.autoupdate&method=getRztByContainid&paramtypes=str&args=" + rztContainId;

    $.get(url, function (result, status, xhr) {

        $("#loadimg_exe").hide();
        if (result['@type']) {
            //  @type":"com.attilax.util.RuntimeExceptionAtiVer   
            $("#editctrl").val(JSON.stringify(result));

            return;
        }

        //   console.log( result.join("\r\n"));
        if (result.length == 0) { //finish
            return;

        }
        if (result.length > 0) {
            $("#editctrl").val($("#editctrl").val() + "\r\n" + result.join("\r\n"));
            var scrollTop = $("#editctrl")[0].scrollHeight;
            $("#editctrl").scrollTop(scrollTop);
        }


        console_ret_timer = window.setTimeout(
            function () {
                showConsoleRet(rztContainId);
            }
            , 2000);

    }, "json");


}

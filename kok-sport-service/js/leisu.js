system = require('system')   
address = system.args[1];//获得命令行第二个参数 接下来会用到   
//console.log('Loading a web page');   
var page = require('webpage').create();   
var url = address;   
//console.log(url);   
page.open(url, function (status) {   


    //Page is loaded!  
/*
    if (status !== 'success') {   
        console.log('Unable to post!');   
    } else {    
    //此处的打印，是将结果一流的形式output到java中，java通过InputStream可以获取该输出内容
        console.log(page.content);   
    }    
   // phantom.exit();  	
*/		
	
	 window.setTimeout(function () {
		     page.render( "webscreenshot.png"); //网页截图
            console.log(page.content); //网页html文本
		 //   console.log(page.content);   
       //     page.render(output);
            phantom.exit();
        }, 30000); // Change timeout as required to allow sufficient time 
 
});    

// D:\phantomjs-2.1.1-windows\bin\phantomjs.exe  D:\prj\sport-service\kok-sport-service\js\leisu.js https://live.leisu.com/
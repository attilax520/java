function inihostV2(urlx,finishFun)
{


        $.ajax({

                    url: urlx,
                    dataType: "json",

                    data: "method=cpuservice.sysinfo&"+ Math.random(),
                    type: "get",

                    success: function (d) {
                        console.log(d);
                        console.log("----fini");
                        
                        $.each(d, function (n, item) {
                                var fstcomma=item.indexOf(",");
                                var val=item.substr(fstcomma+1);


                                //------get lab
                                var a=item.split(",");
                             //   var val_arr=a.slice(1,4);  //remove first hostcfg name
                                a.splice(3,1); //remve pwd field
                                var lab=a.join(",");


                                var opt='<option   value="' + val + '">@lab@</option>';
                                opt=opt.replace("@lab@",lab);
                                $('#svrinfo').append(opt);  
                                finishFun();
                                // <option value ="saab">Saab</option>
                            //    $('#svrinfo').append('<option label="' + item + '" value="' + val + '"></option>');  
                           });
                        
                    }
                });
                
                

}


function inihost(urlx)
{


        $.ajax({

                    url: urlx,
                    dataType: "json",

                    data: "method=cpuservice.sysinfo&"+ Math.random(),
                    type: "get",

                    success: function (d) {
                        console.log(d);
                        console.log("----fini");
                        
                        $.each(d, function (n, item) {
                                var fstcomma=item.indexOf(",");
                                var val=item.substr(fstcomma+1);


                                //------get lab
                                var a=item.split(",");
                             //   var val_arr=a.slice(1,4);  //remove first hostcfg name
                                a.splice(3,1); //remve pwd field
                                var lab=a.join(",");


                                var opt='<option   value="' + val + '">@lab@</option>';
                                opt=opt.replace("@lab@",lab);
                                $('#svrinfo').append(opt);  
                                // <option value ="saab">Saab</option>
                            //    $('#svrinfo').append('<option label="' + item + '" value="' + val + '"></option>');  
                           });
                        
                    }
                });
                
                

}
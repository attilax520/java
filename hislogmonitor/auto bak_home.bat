set pathx="%~dp0"
set nameMain=hislogprj
set uuid=%~dp0%nameMain%_%date:/=-% %time::=-% 

 set  time=2016-11-01-01:01:01
set zipname="%uuid%_fix_from_%time::=-%.zip"
set zipname_baseline="%uuid%_baselin.zip"
rem coresrc
set zipname_baseline_src="%uuid%_baselin_src.zip"

 --echo %date:/=-%   

  
 set java_home="C:\Program Files\WinRAR\WinRAR.exe"
if not exist %java_home% ( set java_home=c:\WinRAR\WinRAR.exe)
if not exist %java_home% ( set java_home=d:\WinRAR\WinRAR.exe )
if not exist %java_home% (
 set java_home="C:\Program Files (x86)\WinRAR\WinRAR.exe"
 echo aaa
 )
%java_home% a   -x*\build\ -x*\.svn\ -x*\dist\ -x*\nbproject\ -x*\.git\ -x*.jar   -ep1 -m1 -r -n*.bsh  -n*.properties -n*.csv -n*.classpath  -n*.project -n*.ini -n*.ahk  -n*.bat  -n*.dmp   -n*.jsp -n*.vm -n*.css -n*.java  -n*.xml -n*.dwt -n*.php -n*.txt -n*.doc -n*.cs -n*.aspx -n*.ascx -n*.htm -n*.html -n*.js -n*.csproj -n*.sln -n*.resx -n*.sql -n*.config -n*.xsd -n*.settings  -n*.bat -n*.txt -n*.tld  -n*.tag -n*.properties   -n*.buildpath  -n*.classpath  -n*.myhibernatedata  -n*.mymetadata  -n*.project  -n*.jsdtscope  -n*.prefs  -n*.component  -n*.xml  -n*.container  -n*.name  -n*.sql  -n*.bat  -n*.hql  -n*.  -n*.log  -n*.txt  -n*.java  -n*.html  -n*.groovy  -n*.p12  -n*.properties  -n*.jsp  -n*.js  -n*.htaccess  -n*.pack  -n*.css  -n*.htm  -n*.xsd  -n*.htc  -n*.php  -n*.json  -n*.example-php  -n*.as  -n*.svg  -n*.sh  -n*.template  -n*.smd  -n*.fla  -n*.tld  -n*.asp  -n*.aspx  -n*.ashx  -n*.MF  -n*.scc  -n*.eot  -n*.ttf  -n*.woff  -n*.application  -n*.page  -n*.smap -ta%time%  %zipname%   %pathx%

 %java_home% a   -x*\build\ -x*\.svn\ -x*\dist\ -x*\nbproject\ -x*\.git\ -x*.jar  -ep1 -m1 -r -n*.bsh  -n*.properties -n*.csv -n*.classpath  -n*.project -n*.ini -n*.ahk  -n*.bat  -n*.dmp     -n*.vm  -n*.java  -n*.xml -n*.dwt -n*.php   -n*.doc -n*.cs -n*.aspx -n*.ascx -n*.csproj -n*.sln -n*.resx -n*.sql -n*.config -n*.xsd -n*.settings  -n*.bat -n*.txt -n*.tld  -n*.tag -n*.properties   -n*.buildpath  -n*.classpath  -n*.myhibernatedata  -n*.mymetadata  -n*.project  -n*.jsdtscope  -n*.prefs  -n*.component  -n*.xml  -n*.container  -n*.name  -n*.sql  -n*.bat  -n*.hql  -n*.  -n*.log  -n*.txt  -n*.java    -n*.groovy  -n*.p12  -n*.properties    -n*.htaccess  -n*.pack     -n*.xsd  -n*.htc  -n*.php  -n*.json  -n*.example-php     -n*.sh  -n*.template  -n*.smd  -n*.fla  -n*.tld  -n*.asp  -n*.aspx  -n*.ashx  -n*.MF  -n*.scc  -n*.eot    -n*.application  -n*.page  -n*.smap   %zipname_baseline_src%   %pathx%
 
%java_home% a   -x*\build\ -x*\.svn\ -x*\dist\ -x*\nbproject\ -x*\.git\ -x*.jar  -ep1 -m1 -r -n*.bsh  -n*.properties -n*.csv -n*.classpath  -n*.project -n*.ini -n*.ahk  -n*.bat  -n*.dmp   -n*.jsp -n*.vm -n*.css -n*.java  -n*.xml -n*.dwt -n*.php -n*.txt -n*.doc -n*.cs -n*.aspx -n*.ascx -n*.htm -n*.html -n*.js -n*.csproj -n*.sln -n*.resx -n*.sql -n*.config -n*.xsd -n*.settings  -n*.bat -n*.txt -n*.tld  -n*.tag -n*.properties   -n*.buildpath  -n*.classpath  -n*.myhibernatedata  -n*.mymetadata  -n*.project  -n*.jsdtscope  -n*.prefs  -n*.component  -n*.xml  -n*.container  -n*.name  -n*.sql  -n*.bat  -n*.hql  -n*.  -n*.log  -n*.txt  -n*.java  -n*.html  -n*.groovy  -n*.p12  -n*.properties  -n*.jsp  -n*.js  -n*.htaccess  -n*.pack  -n*.css  -n*.htm  -n*.xsd  -n*.htc  -n*.php  -n*.json  -n*.example-php  -n*.as  -n*.svg  -n*.sh  -n*.template  -n*.smd  -n*.fla  -n*.tld  -n*.asp  -n*.aspx  -n*.ashx  -n*.MF  -n*.scc  -n*.eot  -n*.ttf  -n*.woff  -n*.application  -n*.page  -n*.smap   %zipname_baseline%   %pathx%



set sql_bakName="%uuid%.sql"
set sql_zip="%uuid%.sql.zip"
C:\wamp\mysql\bin\mysqldump.exe -uroot -proot --set-charset=utf8 wxb_srv_mir --result-file=%sql_bakName%
D:\wamp\bin\mysql\mysql5.5.20\bin\mysqldump.exe -uroot -proot --set-charset=utf8 wxb_site_new --result-file=%sql_bakName%
%java_home% a -m1  %sql_zip% %sql_bakName%
pause


说明：

pathx为工程目录，目录如有空格中文要用双引号括起来……目录结尾不要有反斜杠……
zipname 为要打包的文件名
time  这个指明什么时间以后修改的文件。根据文件修改时间来打包呵。

 -n*.jsp  这个参数表明，打包JSP文件……如果打包文本文件，可增加 -n*.txt..其它类似可。
 
 
 
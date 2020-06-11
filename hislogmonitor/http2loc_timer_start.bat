java  -classpath "%~dp0bin;WebContent\WEB-INF\classes"   -Djava.ext.dirs="%~dp0libtomcart;WebContent\WEB-INF\lib"  com.attilax.net.http.httpfile2local   http://192.168.1.18:1314/webdavapp/webdavurl/home/cnhis/cloudhealth/logs/log.log  c:/tmp/cloudhealthtmp.log

rem  setx path  "%path%;C:\Program Files\Java\jdk1.8.0_131\jre\bin"
= atitit url and  query parse 解析
:doctype: book

 
--
This is the introduction to the first part of our mud-encrusted journey.
--
doctype=book
<doctype>book</doctype>
:doctype:book
TIP:  some nice text to show inside admonition...
  
:toc:
[chapter]
= able of Contents  toc

	1. 半结构化数据
	2. url的使用场景
	3. url前边部分解析都有
	4. query部分的解析 
	4.1. js的解析
	4.2. js bom 没有提供api解析
	4.3. node.js 貌似可以解析
	4.4. php和python
	4.5. Java梅雨体提供api
	4.6. Python阿皮
	 
	5. mysql url的解析

  


:sectnums:
== 半结构化数据
url ,json xml yml

 
== url的使用场景
参数传递，，参数配置等（jdbc url。。

== url前边部分解析都有

== query部分的解析

=== js的解析
=== js bom 没有提供api解析
=== node.js 貌似可以解析

=== php和python

Atitit 解析url querystr到对象

Php
parse_str( $_SERVER[ 'QUERY_STRING' ],$parr);print_r($parr);

  $json_str=json_encode($parr);
file_put_contents("C:\\data\\tisyi\\".time(), $json_str);


=== Java梅雨体提供api
=== Python阿皮


== mysql url的解析

		ConnectionUrlParser connStringParser = ConnectionUrlParser.parseConnectionString(mysqlConnUrl);
		System.out.println(connStringParser);


com.mysql.cj.core.conf.url.ConnectionUrlParser

This class parses a connection string using the general URI structure defined in RFC 3986. Instead of using a URI instance to ensure the correct syntax of the connection string, this implementation uses regular expressions which is faster but also less strict in terms of validations. This actually works better because database URLs don't exactly stick to the RFC 3986 rules. 
scheme://authority/path?query#fragment 
This results in splitting the connection string URL and processing its internal parts: 
scheme
The protocol and subprotocol identification. Usually "jdbc:mysql:" or "mysqlx:".
authority
Contains information about the user credentials and/or the host and port information. Unlike its definition in the RFC 3986 specification, there can be multiple authority sections separated by a single comma (,) in a connection string. It is also possible to use an alternative syntax for the user and/or host identification, that also allows setting per host connection properties, in the form of "[user[:password]@]address=(key1=value)[(key2=value)]...[,address=(key3=value)[(key4=value)]...]...".
path
Corresponds to the database identification.
query
The connection properties, written as "propertyName1[=[propertyValue1]][&propertyName2[=[propertyValue2]]]..."
fragment
The fragment section is ignored in Connector/J connection strings.

[appendix]
== Copyright and License
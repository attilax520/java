CREATE VIEW  foot_qinbao_v AS


select * from football_match_t
select '有利情报' as type ,'巴塞罗那上半场出色' as title ,'巴塞罗那上半场出色副标题' as subtitle, '有利情报内容内容内容' as  context
union
select '不利情报' as type ,'巴塞罗那上半场不出色' as title ,'不利轻薄副标题' as subtitle, ' 内容内容内容' as  context
  
  union
select '中立情报' as type ,'中立情报标题' as title ,'中立情报副标题' as subtitle, '中立情报内容内容内容' as  context




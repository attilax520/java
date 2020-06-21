update football_match_t m set odd_yapan_home=oddsOf_home_odds(`m`.`id`,0,1) 
where
(
   m.`match_time` between unix_timestamp
   
      (
         now()- interval 200 hour
      )
  
   and
      unix_timestamp (now())
      
      
)



select
  * 
from
   `football_match_t` m
where
(
   m.`match_time` between unix_timestamp
   
      (
         now () interval 200 hour
      )
  
   and
      unix_timestamp (now ())
      
      
)

and 
isnull ( `oddsOf_home_odds`(`m`.`id`,`m`.`home_id`,1) )
order by
   m.`match_time` desc 
   
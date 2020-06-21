select * from football_match_t where   
 odd_yapan_away is null and
 `match_time` between unix_timestamp
   
      (
         now()- interval 200 hour
      )
  
   and
      unix_timestamp (now())

order by match_time desc limit 3000
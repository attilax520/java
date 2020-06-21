select
   'vv' AS `t1`,

   (
      select concat
         (
            '[',
            group_concat
            (
               concat
               (
                  '{"name":"',
                  'aiai',
                  '",'
               ),
               concat
               (
                  '"id":"',
                  55,
                  '"}'
               )
               separator ','
            ),
            ']'
         )
   ) AS `j2`
	AND (
			from_unixtime(`m`.`match_time`) BETWEEN concat(
				cast(now() AS date),
				' 12:00:00'
			)
			AND concat(
				cast((now() + INTERVAL 1 DAY) AS date),
				' 12:00:00'
			)
		)
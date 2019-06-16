SELECT date_part('year', t.creation_date) AS "Year",
	   date_part('month', t.creation_date) AS "Month",
	   date_part('day', t.creation_date) AS "Day",
	   COUNT(t.*) AS "Volume"
FROM perm_tweet t 
JOIN perm_extraction e ON e.identifier=t.extraction_identifier 
JOIN perm_user u ON e.user_identifier=u.identifier 
WHERE u.identifier=:user
GROUP BY date_part('day', t.creation_date),
		 date_part('month', t.creation_date),
		 date_part('year', t.creation_date) 
ORDER BY "Year","Month","Day";
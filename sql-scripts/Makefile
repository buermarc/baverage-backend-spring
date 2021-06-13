all:

dump:
	mysqldump -h localhost -u root -p --no-data app > dump.sql
	sed -i '1i use app;' dump.sql

import: 
	mysql -u iot -p < dump.sql
	mysql -u iot -p < data.sql

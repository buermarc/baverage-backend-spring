all:

dump:
	mysqldump -h 127.0.0.1 -u root -p --no-data app > dump.sql
	sed -i '1i use app;' dump.sql

import: 
	mysql -h 127.0.0.1 -u iot -p < dump.sql
	mysql -h 127.0.0.1 -u iot -p < data.sql

user:
	mysql -h 127.0.0.1 -u root -p < user-and-permissions.sql

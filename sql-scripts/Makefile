all:

dump:
	mysqldump -h 127.0.0.1 -u root -p --no-data app > dump.sql
	sed -i '1i create database if not exists app;' dump.sql
	sed -i '2i use app;' dump.sql
	sed -i 's/AUTO_INCREMENT=.* /AUTO_INCREMENT=1 /g' dump.sql

import: 
	mysql -h 127.0.0.1 -u iot -p < dump.sql
	mysql -h 127.0.0.1 -u iot -p < data.sql

user:
	mysql -h 127.0.0.1 -u root -p < user-and-permissions.sql

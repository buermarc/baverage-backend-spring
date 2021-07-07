create user 'MYSQL_U'@'10.0.2.100' identified by 'MYSQL_P';
grant all privileges on MYSQL_DB.* to 'MYSQL_P'@'10.0.2.100';
flush privileges;

create user 'iot'@'10.0.2.100' identified by '1234';
grant all privileges on app.* to 'iot'@'10.0.2.100';
flush privileges;

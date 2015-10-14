USE `mysql`;
create user 'twitterBackup'@'localhost' identified by 'twitterBackup';
grant all privileges on TwitterBackup.* to 'twitterBackup'@'localhost';
flush privileges;

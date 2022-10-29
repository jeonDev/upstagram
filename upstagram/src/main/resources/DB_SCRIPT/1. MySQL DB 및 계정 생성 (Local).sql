/* 여기부터 계정 생성 */
create database upstagram default character set UTF8;

CREATE USER upstagram@'%' IDENTIFIED BY 'up7sta7gram' ;

grant all privileges on upstagram.* to 'upstagram'@'%';

FLUSH PRIVILEGES;
/* 여기까지 */
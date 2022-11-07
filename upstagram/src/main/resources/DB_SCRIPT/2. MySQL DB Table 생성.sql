/* ALT + X : 드래그 후, 전체 실행 */
DROP TABLE IF EXISTS `DM_CHAT`;

CREATE TABLE `DM_CHAT` (
	`DM_CHAT_NO` BIGINT NOT NULL auto_increment primary key COMMENT '채팅번호',
	`DM_CHAT_ROOM_NO`	BIGINT	NOT NULL	COMMENT '채팅방 번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`MESSAGE`	VARCHAR(1024)	NOT NULL	COMMENT '메시지',
	`VIEW_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '읽음여부',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정시간'
);

DROP TABLE IF EXISTS `DM_CHAT_ROOM`;

CREATE TABLE `DM_CHAT_ROOM` (
	`DM_CHAT_ROOM_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '채팅방 번호',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `DM_CHAT_ROOM_USER`;


CREATE TABLE `DM_CHAT_ROOM_USER` (
	`DM_CHAT_ROOM_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '채팅방 번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);


DROP TABLE IF EXISTS `LOGIN_HISTORY`;

CREATE TABLE `LOGIN_HISTORY` (
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
    `LOGIN_DTTM`	DATETIME	NULL	COMMENT '로그인접속시간',
	`LOGIN_URI`	VARCHAR(100)	NULL	COMMENT '접속경로',
	`LOGIN_IP`	VARCHAR(15)	NULL	COMMENT '접속 IP'
);


DROP TABLE IF EXISTS `MEMBER_INFO`;

CREATE TABLE `MEMBER_INFO` (
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`OAUTH_NO`	VARCHAR(100)	NOT NULL	COMMENT 'OAuth 계정정보',
	`PASSWORD`	VARCHAR(100)	NOT NULL	COMMENT '패스워드',
	`NAME`	VARCHAR(100)	NOT NULL	COMMENT '이름',
	`NICKNAME` VARCHAR(30) NOT NULL	COMMENT '닉네임', 
	`SEX`	CHAR(1)	NULL	COMMENT '성별 (M - 남자 / W - 여자)',
	`TEL`	VARCHAR(11)	NULL	COMMENT '전화번호(- 없이)',
	`ROLE`	VARCHAR(20)	NULL	DEFAULT 'ROLE_USER'	COMMENT '사용자 권한',
	`PUSH_VIEW_YN`	CHAR(1)	NULL	DEFAULT 'N'	COMMENT '푸시알림여부',
	`TAG_ALLOW_YN`	CHAR(1)	NULL	DEFAULT 'N'	COMMENT '태그허용여부',
	`JOIN_DTTM`	DATETIME	NULL	COMMENT '가입일자',
	`LAST_LOGIN_DTTM`	DATETIME	NULL	COMMENT '최근 로그인일시',
	`WRONG_PASSWORD_NUMBER`	INTEGER	NULL	COMMENT '패스워드틀린횟수',
	`PASSWORD_CHG_DTTM`	DATETIME	NULL	COMMENT '패스워드변경일',
	`USE_YN` CHAR(1) null COMMENT '사용여부',
	`REG_DTTM` DATETIME null COMMENT '등록시간',
	`LAST_DTTM` DATETIME null COMMENT '수정시간'
);

DROP TABLE IF EXISTS `OAUTH_MEMBER_INFO`;

CREATE TABLE `OAUTH_MEMBER_INFO` (
	`ID`	VARCHAR(100)	NOT NULL	COMMENT 'OAuth 계정정보',
	`PICTURE`	VARCHAR(2)	NULL	COMMENT '소셜로그인 구분',
	`NAME`	VARCHAR(100)	NULL	COMMENT '소셜로그인 이름',
	`EMAIL`	VARCHAR(200)	NULL	COMMENT '소셜로그인 이메일',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `FOLLOW_USER`;

CREATE TABLE `FOLLOW_USER` (
	`FOLLOW_NO`	BIGINT	NOT NULL auto_increment primary key	 COMMENT '팔로우 번호'  ,
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`FOLLOW_ID`	VARCHAR(30)	NOT NULL	COMMENT '팔로우 아이디',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '등록일자'
);

DROP TABLE IF EXISTS `STORY_REACTION`;

CREATE TABLE `STORY_REACTION` (
	`REACTION_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '스토리반응번호',
	`STORY_NO`	BIGINT	NOT NULL	COMMENT '스토리번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`STORY_LOVE_YN`	VARCHAR(1)	NULL	DEFAULT 'N'	COMMENT '스토리 좋아요(Y/N)',
	`STORY_VIEW_DATE`	DATETIME	NULL	COMMENT '조회시간',
	`REG_DTTM`	DATETIME	NULL	COMMENT '마지막 본 시간',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `STORY`;

CREATE TABLE `STORY` (
	`STORY_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '스토리번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`STORY_FILE_NAME`	VARCHAR(100)	NULL	COMMENT '스토리 파일명',
	`STORY_TIME`	VARCHAR(20)	NULL	COMMENT '영상재생시간',
	`SHOW_YN`	CHAR(1)	NULL	DEFAULT 'Y'	COMMENT '표시여부(Y/N)',
	`KEEP_YN`	CHAR(1)	NULL	COMMENT '보관여부(Y/N)',
	`REG_DTTM`	DATETIME	NULL	COMMENT '스토리 등록시간',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `STORY_WATCHING`;

CREATE TABLE `STORY_WATCHING` (
	`STORY_WATCHING_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '시청기록 번호',
	`STORY_NO`	BIGINT	NOT NULL	COMMENT '스토리번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록시간',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `FEED_TAG`;

CREATE TABLE `FEED_TAG` (
	`FEED_TAG_NO` BIGINT NOT NULL auto_increment primary key COMMENT '태그 번호',
	`FEED_NO`	BIGINT	NOT NULL	COMMENT '피드 번호',
	`TAG_ID`	VARCHAR(30)	NOT NULL	COMMENT '태그 아이디',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `FEED_FILE`;

CREATE TABLE `FEED_FILE` (
	`FEED_FILE_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '피드 파일 번호',
	`FEED_NO`	BIGINT	NOT NULL	COMMENT '피드 번호',
	`FILE_NAME` VARCHAR(100) NULL   COMMENT '피드파일명',
	`FILE_DIV`	VARCHAR(10)	NULL	COMMENT '파일구분',
	`FILE_EXT`	VARCHAR(10)	NULL	COMMENT '파일확장자',
	`SORT_ORDER`	INTEGER	NULL	COMMENT '정렬순서',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`REG_ID`	VARCHAR(20)	NULL	COMMENT '등록자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자',
	`LAST_ID`	VARCHAR(20)	NULL	COMMENT '수정자'
);

DROP TABLE IF EXISTS `FEED`;

CREATE TABLE `FEED` (
	`FEED_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '피드 번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`TITLE`	BLOB	NOT NULL	COMMENT '게시글 제목',
	`HASHTAG`	VARCHAR(100)	NULL	COMMENT '해시태그',
	`USE_YN`	CHAR(1)	NULL	DEFAULT 'Y'	COMMENT '사용여부(Y/N)',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`REG_ID`	VARCHAR(20)	NULL	COMMENT '등록자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자',
	`LAST_ID`	VARCHAR(20)	NULL	COMMENT '수정자'
);

DROP TABLE IF EXISTS `FEED_COMMENT`;

CREATE TABLE `FEED_COMMENT` (
	`FEED_COMMENT_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '피드 댓글 번호',
	`FEED_NO`	BIGINT	NOT NULL	COMMENT '피드 번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`CONTENT`	VARCHAR(1024)	NULL	COMMENT '댓글',
	`USE_YN`	CHAR(1)	NULL	COMMENT '사용여부',
	`TOP_FIX`	CHAR(1)	NULL	COMMENT '상단고정여부',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`REG_ID`	VARCHAR(20)	NULL	COMMENT '등록자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자',
	`LAST_ID`	VARCHAR(20)	NULL	COMMENT '수정자'
);


DROP TABLE IF EXISTS `FEED_HEART`;

CREATE TABLE `FEED_HEART` (
	`FEED_HEART_NO` BIGINT NOT NULL auto_increment primary key COMMENT '좋아요번호',
	`FEED_NO`	BIGINT	NOT NULL	COMMENT '피드 번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `FEED_COMMENT_HEART`;

CREATE TABLE `FEED_COMMENT_HEART` (
	`FEED_COMMENT_HEART_NO` BIGINT NOT NULL auto_increment primary key COMMENT '댓글좋아요번호',
	`FEED_COMMENT_NO`	BIGINT	NOT NULL	COMMENT '피드 댓글 번호',
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `AD_VIEW_HISTORY`;

CREATE TABLE `AD_VIEW_HISTORY` (
	`AD_VIEW_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '광고번호',
	`AD_NO` BIGINT 		NOT NULL 	COMMENT '광고번호' ,
	`ID`	VARCHAR(30)	NOT NULL	COMMENT '아이디',
	`LINK_COUNT_YN`	CHAR(1)	NULL	COMMENT '링크연결여부',
	`LINK`	VARCHAR(1024)	NULL	COMMENT '링크',
	`VIEW_DTTM`	DATETIME	NULL	COMMENT '조회시간',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);

DROP TABLE IF EXISTS `AD`;

CREATE TABLE `AD` (
	`AD_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '광고번호',
	`AD_COMPANY_NO`	BIGINT	NOT NULL	COMMENT '광고업체번호',
	`AD_NAME`	VARCHAR(128)	NULL	COMMENT '광고명',
	`AD_FILE_NAME`	VARCHAR(100)	NULL	COMMENT '광고파일명',
	`USE_YN`	CHAR(1)	NULL	COMMENT '사용여부',
	`FILE_EXT`	VARCHAR(10)	NULL	COMMENT '파일확장자',
	`COST_DIV`	VARCHAR(10)	NULL	COMMENT '청구구분',
	`TIME_COUNT_COST`	DECIMAL	NULL	COMMENT '시간당금액',
	`VIEW_COUNT_COST`	DECIMAL	NULL	COMMENT '조회수당 광고금액',
	`LINK_COUNT_COST`	DECIMAL	NULL	COMMENT '링크연동 광고금액',
	`LINK_USE_YN`	CHAR(1)	NULL	COMMENT '페이지연동여부',
	`LINK`	VARCHAR(1024)	NULL	COMMENT '링크',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`REG_ID`	VARCHAR(20)	NULL	COMMENT '등록자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자',
	`LAST_ID`	VARCHAR(20)	NULL	COMMENT '수정자'
);


DROP TABLE IF EXISTS `AD_COMPANY`;

CREATE TABLE `AD_COMPANY` (
	`AD_COMPANY_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '광고업체번호',
	`AD_COMPANY_NAME`	VARCHAR(200)	NULL	COMMENT '광고업체명',
	`MANAGER_NAME`	VARCHAR(100)	NULL	COMMENT '관리자명',
	`MANAGER_TEL`	VARCHAR(11)	NULL	COMMENT '관리자전화번호',
	`ADDR`	VARCHAR(200)	NULL	COMMENT '주소',
	`ADDR_DETAIL`	VARCHAR(200)	NULL	COMMENT '상세주소',
	`ZIP_CD`	VARCHAR(5)	NULL	COMMENT '우편번호',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`REG_ID`	VARCHAR(20)	NULL	COMMENT '등록자',
	`LAST_DTTM`	DATETIME	NULL,
	`LAST_ID`	VARCHAR(20)	NULL	COMMENT '수정자'
);

DROP TABLE IF EXISTS `AD_MANAGE`;

CREATE TABLE `AD_MANAGE` (
	`AD_MANAGE_NO`	BIGINT	NOT NULL	auto_increment primary key COMMENT '광고노출번호',
	`AD_NO`	BIGINT	NOT NULL	COMMENT '광고번호',
	`START_DTTM`	VARCHAR(8)	NULL	COMMENT '시작일자',
	`END_DTTM`	VARCHAR(8)	NULL	COMMENT '종료일자',
	`START_TIME`	VARCHAR(6)	NULL	COMMENT '시작시간',
	`END_TIME`	VARCHAR(6)	NULL	COMMENT '종료시간',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`REG_ID`	VARCHAR(20)	NULL	COMMENT '등록자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자',
	`LAST_ID`	VARCHAR(20)	NULL	COMMENT '수정자'
);

ALTER TABLE `AD_MANAGE` ADD CONSTRAINT `FK_AD_TO_AD_MANAGE_1` FOREIGN KEY (
	`AD_NO`
)
REFERENCES `AD` (
	`AD_NO`
);



DROP TABLE IF EXISTS `COMMON_CODE`;

CREATE TABLE `COMMON_CODE` (
	`COMMON_CODE`	VARCHAR(30)	NULL	COMMENT '공통코드',
	`COMMON_CODE_NAME`	VARCHAR(50)	NULL	COMMENT '공통코드명',
	`COMMON_TYPE`	VARCHAR(30)	NULL	COMMENT '타입',
	`USE_YN`	CHAR(1)	NULL	COMMENT '사용여부',
	`SRT_ODR`	INTEGER	NULL	COMMENT '정렬순서',
	`CODE_DETAIL`	VARCHAR(100)	NULL	COMMENT '설명'
);

DROP TABLE IF EXISTS `CERT_NUM`;

CREATE TABLE `CERT_NUM` (
	`CERT_NO`	VARCHAR(20)	NULL	COMMENT '인증고유번호',
	`CERT_DIV`	VARCHAR(20)	NULL	COMMENT '구분',
	`CERT_NUMBER`	VARCHAR(10)	NULL	COMMENT '인증번호',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록시간'
);



ALTER TABLE `LOGIN_HISTORY` ADD CONSTRAINT `PK_LOGIN_HISTORY` PRIMARY KEY (
	`ID`, 
	`LOGIN_DTTM`
);

ALTER TABLE `MEMBER_INFO` ADD CONSTRAINT `PK_MEMBER_INFO` PRIMARY KEY (
	`ID`
);

ALTER TABLE MEMBER_INFO ADD CONSTRAINT UNIQUE_MEMBER_INFO UNIQUE (NICKNAME);

ALTER TABLE `OAUTH_MEMBER_INFO` ADD CONSTRAINT `PK_OAUTH_MEMBER_INFO` PRIMARY KEY (
	`ID`
);


ALTER TABLE `FOLLOW_USER` ADD CONSTRAINT `FK_MEMBER_INFO_TO_FOLLOW_USER_1` FOREIGN KEY (
	`ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);

ALTER TABLE `FOLLOW_USER` ADD CONSTRAINT `FK_MEMBER_INFO_TO_FOLLOW_USER_2` FOREIGN KEY (
	`FOLLOW_ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);

ALTER TABLE FOLLOW_USER ADD CONSTRAINT UNIQUE_FOLLOW_USER_ID UNIQUE (ID, FOLLOW_ID);
   
ALTER TABLE `FEED_TAG` ADD CONSTRAINT `FK_FEED_TO_FEED_TAG_1` FOREIGN KEY (
	`FEED_NO`
)
REFERENCES `FEED` (
	`FEED_NO`
);

ALTER TABLE `STORY_REACTION` ADD CONSTRAINT `FK_STORY_TO_STORY_REACTION_1` FOREIGN KEY (
	`STORY_NO`
)
REFERENCES `STORY` (
	`STORY_NO`
);

ALTER TABLE `STORY_REACTION` ADD CONSTRAINT `FK_MEMBER_INFO_TO_STORY_REACTION_1` FOREIGN KEY (
	`ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);

ALTER TABLE `STORY` ADD CONSTRAINT `FK_MEMBER_INFO_TO_STORY_1` FOREIGN KEY (
	`ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);

ALTER TABLE `STORY_WATCHING` ADD CONSTRAINT `FK_STORY_TO_STORY_WATCHING_1` FOREIGN KEY (
	`STORY_NO`
)
REFERENCES `STORY` (
	`STORY_NO`
);

ALTER TABLE `STORY_WATCHING` ADD CONSTRAINT `FK_MEMBER_INFO_TO_STORY_WATCHING_1` FOREIGN KEY (
	`ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);



ALTER TABLE `FEED_FILE` ADD CONSTRAINT `FK_FEED_TO_FEED_FILE_1` FOREIGN KEY (
	`FEED_NO`
)
REFERENCES `FEED` (
	`FEED_NO`
);

ALTER TABLE `FEED` ADD CONSTRAINT `FK_MEMBER_INFO_TO_FEED_1` FOREIGN KEY (
	`ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);

ALTER TABLE `FEED_COMMENT` ADD CONSTRAINT `FK_FEED_TO_FEED_COMMENT_1` FOREIGN KEY (
	`FEED_NO`
)
REFERENCES `FEED` (
	`FEED_NO`
);

ALTER TABLE `FEED_HEART` ADD CONSTRAINT `FK_FEED_TO_FEED_HEART_1` FOREIGN KEY (
	`FEED_NO`
)
REFERENCES `FEED` (
	`FEED_NO`
);

ALTER TABLE `AD_VIEW_HISTORY` ADD CONSTRAINT `FK_AD_TO_AD_VIEW_HISTORY_1` FOREIGN KEY (
	`AD_NO`
)
REFERENCES `AD` (
	`AD_NO`
);

ALTER TABLE `AD_VIEW_HISTORY` ADD CONSTRAINT `FK_MEMBER_TO_AD_VIEW_HISTORY_1` FOREIGN KEY (
	`ID`
)
REFERENCES `MEMBER_INFO` (
	`ID`
);

ALTER TABLE `AD` ADD CONSTRAINT `FK_AD_COMPANY_TO_AD_1` FOREIGN KEY (
	`AD_COMPANY_NO`
)
REFERENCES `AD_COMPANY` (
	`AD_COMPANY_NO`
);




/* History 테이블 */
DROP TABLE IF EXISTS `MEMBER_INFO_HISTORY`;

CREATE TABLE `MEMBER_INFO_HISTORY` (
	`HISTORY_NO`	BIGINT	NOT NULL	auto_increment primary key	 COMMENT '이력번호',
	`ID`	VARCHAR(30)	NULL	COMMENT '아이디',
	`OAUTH_NO`	VARCHAR(100)	NULL	COMMENT '계정번호',
	`PASSWORD`	VARCHAR(100)	NOT NULL	COMMENT '패스워드',
	`NAME`	VARCHAR(100)	NOT NULL	COMMENT '이름',
	`NICKNAME`	VARCHAR(30)	NOT NULL	COMMENT '닉네임',
	`SEX`	CHAR(1)	NULL	COMMENT '성별 (M - 남자 / W - 여자)',
	`TEL`	VARCHAR(11)	NULL	COMMENT '전화번호(- 없이)',
	`ROLE`	VARCHAR(20)	NULL	COMMENT '사용자 권한',
	`PUSH_VIEW_YN`	CHAR(1)	NULL	COMMENT '푸시알림여부',
	`TAG_ALLOW_YN`	CHAR(1)	NULL	COMMENT '태그허용여부',
	`JOIN_DTTM`	DATETIME	NULL	COMMENT '가입일자',
	`LAST_LOGIN_DTTM`	DATETIME	NULL	COMMENT '최근 로그인일시',
	`WRONG_PASSWORD_NUMBER`	INTEGER	NULL	COMMENT '패스워드틀린횟수',
	`PASSWORD_CHG_DTTM`	DATETIME	NULL	COMMENT '패스워드변경일',
	`USE_YN`	CHAR(1)	NULL	COMMENT '사용여부',
	`REG_DTTM`	DATETIME	NULL	COMMENT '등록일자',
	`LAST_DTTM`	DATETIME	NULL	COMMENT '수정일자'
);




CREATE TABLE BOARD(
SEQ NUMBER PRIMARY KEY,
NAME VARCHAR2(50) NOT NULL,
TITLE VARCHAR2(100) NOT NULL,
CONTENT CLOB NOT NULL,
PASS VARCHAR2(10) NOT NULL,
HIT NUMBER(5) NOT NULL,
REGDATE DATE NOT NULL
);
 
CREATE SEQUENCE BOARD_SEQ;

create table bridge(
    boardno NUMBER,
    filename varchar2(100) not null,
    constraint fk_boardno  foreign key(boardno) references board(seq) 
);

create table reply(
    originno number,
    replyno number,
    replyseq number,
    boardno number,
    constraint fk_replyno  foreign key(originno) references board(seq),
    constraint fk_boardno_reply  foreign key(boardno) references board(seq) 
);
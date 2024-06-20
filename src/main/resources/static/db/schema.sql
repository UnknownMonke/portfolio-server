drop table T_GEO_EXPOSURE if exists;
drop table T_SEC_EXPOSURE if exists;
drop table T_EQUITY if exists;
drop table T_GEOGRAPHY_NODE if exists;
drop table T_SECTOR_NODE if exists;
drop table T_USER if exists;
drop table T_THEME if exists;

create table T_EQUITY (
    ID integer primary key,
    BROKER_ID varchar(48),
    NAME varchar(48),
    TICKER varchar(4) not null,
    TYPE varchar(5),
    ACTIVE boolean,
    CURRENCY varchar(3),
    QUANTITY integer,
    PRICE decimal(8,2),
    SOURCE varchar(48),
    unique(TICKER));

create table T_GEOGRAPHY_NODE (
    ID integer primary key,
    NAME varchar(48),
    unique(NAME));

create table T_SECTOR_NODE (
    ID integer primary key,
    NAME varchar(48),
    LEVEL integer,
    PARENT_ID integer,
    unique(NAME));

create table T_GEO_EXPOSURE (
    ID integer primary key,
    EQUITY_ID integer,
    NODE_ID integer,
    EXPOSURE decimal(3,2));

create table T_SEC_EXPOSURE (
    ID integer primary key,
    EQUITY_ID integer,
    NODE_ID integer,
    EXPOSURE decimal(3,2),
    unique(EQUITY_ID, NODE_ID));

create table T_USER (
    ID integer primary key,
    USERNAME varchar(48) not null,
    PASSWORD varchar(48) not null,
    EMAIL varchar(48),
    THEME_ID integer,
    unique(EMAIL, THEME_ID));

create table T_THEME (
    ID integer primary key,
    NAME varchar(48),
    unique(NAME));

alter table T_USER add constraint FK_THEME foreign key (THEME_ID) references T_THEME(ID) on delete cascade;
alter table T_GEO_EXPOSURE add constraint FK_GEO_NODE foreign key (NODE_ID) references T_GEOGRAPHY_NODE(ID) on delete cascade;
alter table T_GEO_EXPOSURE add constraint FK_GEO_EQUITY_NODE foreign key (EQUITY_ID, NODE_ID) references T_EQUITY(ID) on delete cascade;
alter table T_SEC_EXPOSURE add constraint FK_SEC_NODE foreign key (NODE_ID) references T_SECTOR_NODE(ID) on delete cascade;
alter table T_SEC_EXPOSURE add constraint FK_SEC_EQUITY foreign key (EQUITY_ID) references T_EQUITY(ID) on delete cascade;
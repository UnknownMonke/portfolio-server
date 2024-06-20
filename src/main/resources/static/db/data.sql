insert into T_GEOGRAPHY_NODE (name) values ('USA');
insert into T_GEOGRAPHY_NODE (name) values ('China');
insert into T_GEOGRAPHY_NODE (name) values ('France');
insert into T_GEOGRAPHY_NODE (name) values ('Germany');
insert into T_GEOGRAPHY_NODE (name) values ('Nordics');

insert into T_SECTOR_NODE (name, level, parent_id) values ('Technology', 0, null);
insert into T_SECTOR_NODE (name, level, parent_id) values ('Industrials', 0, null);
insert into T_SECTOR_NODE (name, level, parent_id) values ('Video Games', 1, 1);
insert into T_SECTOR_NODE (name, level, parent_id) values ('Hardware & Peripherals', 1, 1);
insert into T_SECTOR_NODE (name, level, parent_id) values ('IT & Services', 1, 1);
insert into T_SECTOR_NODE (name, level, parent_id) values ('Automotive', 1, 2);
insert into T_SECTOR_NODE (name, level, parent_id) values ('Aeronautics', 1, 2);

insert into T_EQUITY (broker_id, name, ticker, type, active, currency, quantity, price, source)
values ('DeGiro-1147582', 'NVIDIA Corp', 'NVDA', 'STOCK', true, 'USD', 5, 264.45, 'Degiro');
insert into T_EQUITY (broker_id, name, ticker, type, active, currency, quantity, price, source)
values ('DeGiro-1147583', 'MICROSOFT', 'MSFT', 'STOCK', true, 'USD', 8, 461.56, 'Degiro');
insert into T_EQUITY (broker_id, name, ticker, type, active, currency, quantity, price, source)
values ('DeGiro-1147586', 'Airbus', 'AIR', 'STOCK', true, 'USD', 3, 171.24, 'Degiro');
insert into T_EQUITY (broker_id, name, ticker, type, active, currency, quantity, price, source)
values ('DeGiro-1147584', 'Take-Two Interactive Software', 'TTWO', 'STOCK', true, 'USD', 10, 143.03, 'Degiro');
insert into T_EQUITY (broker_id, name, ticker, type, active, currency, quantity, price, source)
values ('DeGiro-1147585', 'Tesla', 'TSLA', 'STOCK', true, 'USD', 42, 245.56, 'Degiro');

insert into T_THEME (name) values ('light');
insert into T_THEME (name) values ('dark');

insert into T_USER (username, password, email, theme_id) values ('test', 'test', 'test@test.com', 1);


insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (1, 1, 0.45);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (1, 2, 0.25);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (1, 3, 0.10);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (1, 4, 0.10);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (1, 5, 0.10);

insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (2, 1, 0.55);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (2, 3, 0.25);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (2, 4, 0.10);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (2, 5, 0.10);

insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (3, 1, 0.30);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (3, 3, 0.60);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (3, 4, 0.10);

insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (4, 1, 0.75);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (4, 2, 0.05);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (4, 3, 0.15);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (4, 4, 0.10);

insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (5, 1, 0.80);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (5, 2, 0.05);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (5, 3, 0.10);
insert into T_GEO_EXPOSURE (equity_id, node_id, exposure) values (5, 4, 0.05);


insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (1, 3, 0.40);
insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (1, 4, 0.60);

insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (2, 3, 0.30);
insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (2, 5, 0.70);

insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (3, 7, 1.00);

insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (4, 3, 1.00);

insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (5, 4, 0.50);
insert into T_SEC_EXPOSURE (equity_id, node_id, exposure) values (5, 6, 0.50);
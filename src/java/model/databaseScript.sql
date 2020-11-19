# CREATE SCHEMA sql_smart_care_surgery_database;
# USE sql_smart_care_surgery_database;

DROP TABLE admins;

create table admins (
	username VARCHAR(50),
	password VARCHAR(50),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
        is_full_time boolean,
	admin_id INT
);
insert into admins (username, password, first_name, sur_name, admin_id, is_full_time) values ('kmurrigans0', 'mivCdxMTCb', 'Kiele', 'Murrigans', 1, true);


DROP TABLE doctors;
create table doctors (
	username VARCHAR(50),
	password VARCHAR(50),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
        is_full_time boolean,
	doctor_id INT
);
insert into doctors (username, password, first_name, sur_name, doctor_id, is_full_time) values ('gbuckoke0', 'qiqNfXuJ', 'Gustaf', 'Buckoke', 1, true);
insert into doctors (username, password, first_name, sur_name, doctor_id, is_full_time) values ('dpilkinton1', 'SOkdV4f8', 'Dal', 'Pilkinton', 2, false);


DROP TABLE nurses;
create table nurses (
	username VARCHAR(50),
	password VARCHAR(50),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
        is_full_time boolean,
	nurse_id INT
);
insert into nurses (username, password, first_name, sur_name, nurse_id, is_full_time) values ('mbleasdale0', 'T2PkF0', 'Mollie', 'Bleasdale', 1, false);

DROP TABLE patients;
create table patients (
	username VARCHAR(50),
	password VARCHAR(50),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
	patient_id INT,
	date_of_birth DATE,
	address VARCHAR(100)
);
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('sblakely0', 'xDKTJasG7wMT', 'Sanders', 'Blakely', 1, '1931-12-21', '9129 Sommers Junction--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('elittlefair1', 'qk3P6h9', 'Ezechiel', 'Littlefair', 2, '1935-08-21', '0207 Sage Place--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('mlauthian2', 'mytJJ9YN', 'Melissa', 'Lauthian', 3, '1933-07-13', '4 Dovetail Way--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('mflacknoe3', 'bxPjQKQkP02', 'Maxie', 'Flacknoe', 4, '1937-07-22', '530 Anhalt Circle--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('xcawley4', '2atGj8z', 'Xena', 'Cawley', 5, '1981-04-18', '333 Mccormick Hill--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('lstopforth5', 'UlbVEoXy', 'Lorelei', 'Stopforth', 6, '1973-09-15', '7 Manley Terrace--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('jgiorgioni6', 'J4QCZl', 'Jeno', 'Giorgioni', 7, '1954-12-02', '8 Springview Road--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('gmcgrouther7', 'fNhaqBZnw', 'Goddart', 'McGrouther', 8, '1981-02-11', '8 Carpenter Hill--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('jletchmore8', 'ppJ2wJ75', 'Jess', 'Letchmore', 9, '1932-10-23', '2577 Myrtle Center--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('dkeasy9', 'iMxaY9Uf56DJ', 'Dodie', 'Keasy', 10, '1947-01-08', '52313 Pond Hill--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('wkenealya', '2H2UpUD', 'Warde', 'Kenealy', 11, '1941-10-09', '867 Columbus Road--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('glittlecoteb', 'BUsUfLKfmYM', 'Guendolen', 'Littlecote', 12, '1962-03-15', '3 Knutson Point--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('aharwickc', 'aQikDuCF', 'Amble', 'Harwick', 13, '1967-07-10', '65148 Pankratz Center--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('bsnufflebottomd', '3GizKe7DdP7', 'Bryana', 'Snufflebottom', 14, '1958-03-19', '222 Luster Place--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('bbooye', 'Y1GDK8', 'Bailey', 'Booy', 15, '1944-05-10', '85598 Sloan Circle--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('awhitelawf', 'sc6T7PUGg', 'Adelbert', 'Whitelaw', 16, '1955-03-06', '316 Aberg Avenue--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('dshiltong', '1G4DCBaR', 'Dolf', 'Shilton', 17, '1971-08-25', '7704 Bartillon Center--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('jbjorkanh', '63jnsk5guqy8', 'Jeddy', 'Bjorkan', 18, '1977-09-15', '5 Sommers Junction--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('emillikeni', 'eHU0yC9QK', 'Evey', 'Milliken', 19, '1951-01-29', '4 Loftsgordon Avenue--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('icallissj', 'FyuVwO', 'Ida', 'Calliss', 20, '1946-04-10', '06617 Hovde Center--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('tpawlickik', 'V21KR7', 'Tobye', 'Pawlicki', 21, '1955-09-09', '427 Shelley Trail--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('rproudl', 'zWwpTcuxfS', 'Rea', 'Proud', 22, '1954-04-21', '11 Dottie Hill--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('sdakem', 'Rt2F3lF68', 'Shaun', 'Dake', 23, '1963-06-27', '6 Westend Terrace--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('jripleyn', 'Gnee7t', 'Jerrilee', 'Ripley', 24, '1939-08-01', '0013 Village Green Parkway--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('askippono', 'oYrhI7IBsE2', 'Alejandra', 'Skippon', 25, '1976-03-04', '8316 Knutson Avenue--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('grichesp', 'vUICLzkO', 'Gus', 'Riches', 26, '1946-04-25', '00 Stuart Alley--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('cbodicumq', 'YT19ZiZXsdSf', 'Clerc', 'Bodicum', 27, '1946-11-14', '09337 Ridgeway Pass--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('adibollr', 'lmIQGaI', 'Avivah', 'Diboll', 28, '1955-05-25', '9003 Eastwood Point--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('wabelsons', 'YCDA34Ye9', 'Wyn', 'Abelson', 29, '1964-02-08', '31845 Mifflin Lane--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('lrobkert', 'EI48mSra3u', 'Lynnea', 'Robker', 30, '1962-06-24', '2 Veith Road--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('rreicheltu', 'Bne0KnjwnWJz', 'Rees', 'Reichelt', 31, '1971-01-16', '173 Oak Valley Place--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('gkroinv', 'nuPfASZN', 'Griff', 'Kroin', 32, '1988-02-21', '2 Red Cloud Avenue--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('frowetw', 'GxajXzW', 'Felipa', 'Rowet', 33, '1984-10-29', '4904 Union Circle--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('bsemonx', 'SNgio1VG1GB', 'Beulah', 'Semon', 34, '1987-08-01', '060 Gerald Place--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('kjanickiy', 'Gw4JqfRg', 'Ki', 'Janicki', 35, '1950-07-18', '221 Forest Dale Plaza--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('rtrumpz', 'z05P6HdzH', 'Rand', 'Trump', 36, '1935-05-17', '97 Ohio Point--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('rpierri10', 't7v01ctYECb3', 'Roz', 'Pierri', 37, '1993-04-17', '35343 Killdeer Circle--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('kgloucester11', 'wEv769Hh2mBu', 'Kit', 'Gloucester', 38, '1938-04-19', '49850 International Avenue--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('jswinn12', '3lpTslqVz', 'Jerrylee', 'Swinn', 39, '1967-08-05', '0269 Washington Circle--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('credborn13', 'lo2w1s9uiUJR', 'Clerc', 'Redborn', 40, '1986-06-19', '44010 Graceland Point--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('hpechacek14', '05Ad7T2ya6e', 'Harli', 'Pechacek', 41, '1991-05-30', '6495 Kennedy Road--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('kmorilla15', '11gK3EFnEpc', 'Ki', 'Morilla', 42, '1984-06-17', '0611 Veith Lane--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('npietruszka16', 'OXyDA04T0t', 'Nicol', 'Pietruszka', 43, '1962-11-13', '23 Scofield Parkway--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('jcastanie17', 'Klx1UEvPGQBj', 'Jarvis', 'Castanie', 44, '1985-05-12', '7891 Weeping Birch Center--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('ibromell18', 'hCxi1jnBTI', 'Iona', 'Bromell', 45, '1944-11-09', '6510 Forest Run Plaza--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('eostler19', 'oJ7pRfs', 'Elisabetta', 'Ostler', 46, '1987-07-11', '6 Elmside Pass--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('rbordis1a', 'E1QdEAfkI', 'Rozamond', 'Bordis', 47, '1946-02-25', '318 Myrtle Alley--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('zdyer1b', 'sTEIny', 'Zak', 'Dyer', 48, '1984-12-07', '6 Waxwing Alley--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('cmallya1c', 'e0mizaas', 'Cesya', 'Mallya', 49, '1970-03-11', '8187 Crownhardt Point--BE16 3LO-county-Bristol-958297970322');
insert into patients (username, password, first_name, sur_name, patient_id, date_of_birth, address) values ('cstranks1d', 'YByo3U5Ji', 'Celka', 'Stranks', 50, '1989-06-17', '0 Mendota Road--BE16 3LO-county-Bristol-958297970322');
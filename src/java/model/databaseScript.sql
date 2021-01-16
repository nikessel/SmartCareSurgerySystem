/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */

DROP TABLE perscriptions;
DROP TABLE prices;
DROP TABLE surgeries;
DROP TABLE invoices;
DROP TABLE consultations;
DROP TABLE admins;
DROP TABLE doctors;
DROP TABLE nurses;
DROP TABLE patients;


create table admins (
	admin_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 10000, INCREMENT BY 1) primary key,
	username VARCHAR(50),
	password_hash VARCHAR(128),
	salt VARCHAR(64),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
	date_of_birth DATE,
	address VARCHAR(100),
        is_full_time boolean
);
insert into admins (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time) values ('admin', 'faf7cf536b8a1ab503a61371a565d96bf5c365d7662dc5804a3225a5222dc2093dadecd7ec040d9ceb2c5cf0913b29c93ec842e9191d6d404d8e6efa67745f95', '9cd160915b63412ad6a09d8adf0ccc3e4b05401bacc8c2c64189a5573001cf0e', 'Admin', 'First', '1960-10-10', '6 Holy Cross Parkway--BY52 1KU-testCounty-testTown-482929999332', true);
insert into admins (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time) values ('kmurrigans0', '9d40b3fda46e036504262a13f49e99a2606a3abcba868e34c523edfd2a05b2ec864b30528e29ca462df0ec04dffcef832f8364b99777a72d1365c0f59cc3f292', 'cc20e3b462e5a10db9bdcc6f1417da951a748eae5352efaf7f1a2155242b63c0', 'Kiele', 'Murrigans', '1980-04-07', '22967 Arrowood Trail--KW32 1OW-testCounty5-testTown5-381247472228', true);



create table doctors (
	doctor_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 20000, INCREMENT BY 1) primary key,
	username VARCHAR(50),
	password_hash VARCHAR(128),
	salt VARCHAR(64),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
	date_of_birth DATE,
	address VARCHAR(100),
        is_full_time boolean,
        pending boolean
);

insert into doctors (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time, pending) values ('none', '0000', '0000', 'none', 'none','1970-01-01', '1 Test Avenue--TE00 0ST-testCounty-TestTown-999999999999', false, false);
insert into doctors (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time, pending) values ('doctor', '3f0b2e0146d963bb497234ee5cd9dbd27e9c5833ceb9e907740223ac4e900620be03918d2bf4cac30fb6e3cb32e8d47a1557491fc6616cc54cd6269f703bbfb5', '6c841a9111f318ae744388a15de475df47c04b9981cc341434fbeda8b985d8a6', 'Dr', 'First', '1970-02-02', '83 Weeping Birch Alley--KU154 1YR-testCounty2-testTown2-948320200333', false, false);
insert into doctors (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time, pending) values ('gbuckoke0', '5f8ce858e7556d9d83bc959cd9adcf312f04077319d4442dcdff5f7f46d2d1c3e7b9354b16b83b7a2ea3404d03859f6a76e4bd11447c82c5c4a2016b95c23e4c', '6e84653e4d316083180d95b617af8f07eaadc87bfb160d209ccdc15024633640', 'Bob', 'Buckoke', '1977-11-03', '22967 Arrowood Trail--OI153 1ZO-testCounty4-testTown4-48120484442234',  true, false);


create table nurses (
	nurse_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 30000, INCREMENT BY 1) primary key,
	username VARCHAR(50),
	password_hash VARCHAR(128),
	salt VARCHAR(64),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
	date_of_birth DATE,
	address VARCHAR(100),
        is_full_time boolean,
        pending boolean
);

insert into nurses (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time, pending) values ('none', '0000', '0000', 'none', 'none', '1970-01-01', '1 Test Avenue--TE00 0ST-testCounty-TestTown-999999999999', false, false);
insert into nurses (username, password_hash, salt, first_name, sur_name, date_of_birth, address, is_full_time, pending) values ('nurse', '77027f84aa6f001db0b0d44b253f23e906615843bc06cff3acedfe939cb2556a1ad4d144d95276b23228df0decfc3478c1eb5ab4792b5cad23de90448756c9af', 'a94ed051440a587c411ca18c6b74315878e31733c93900e9b511013aa0844dc6', 'Nurse', 'First', '1980-05-02', '1177 Maple Drive--IW213 9LO-testCounty3-testTown3-5235443444322', false, false);


create table patients (
	patient_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 40000, INCREMENT BY 1) primary key,
	username VARCHAR(50),
	password_hash VARCHAR(128),
	salt VARCHAR(64),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
	date_of_birth DATE,
	address VARCHAR(100),
        insured boolean
);

insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('patient', '15c127f33ee049cdbae94a0e2f5d4f8538b2d51e360f08b4bfc9122dadffcc536c919ed226cf5c1395ca01d204f324b0784da81ffc16bb15e9b95a3ced6a9d34', '96281e7c67a008d2a809186e64c74a027916b07436cb9cd4cce915bc8035867d', 'TestPatientFirstName', 'TestPatientSurName', '1992-03-14', '1 Test Avenue--TE00 0ST-testCounty-TestTown-999999999999', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('rglendinning0', 'f32c2199f1cd692585200f12be9a3a4c071ed15aa995da91da11582ceee2983683a8f59301c4e96e9bee20c58926d742073edef560c62d0b8002dacd6b5e2c96', '3580fbfbf8e10cd224f1ae15d71ee098a963808c3e86a926d9efd5ec407a8f6c', 'Rob', 'Smith', '1992-03-14', '1 Melby Parkway--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('aconstantinou1', '34ec15c07558111e9905ab9a9c86facf92a353b744ddb0502a5cf58034a9aab993d4e8f5203fb29e85a39894ef8f09bc9a3f5971a61ff21dd7ce5372fd9ede79', '2d50fda7c9df60eac536b6619ceb3f2524d13ddb7153098dd8eb351a8433161f', 'Lis', 'Brown', '1934-01-17', '773 Bowman Lane--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('adeathridge2', '7d2b7e7e75e3e250db2dca4f81cd3a25e27e04263147ccd1c9371a3a36d41aea4b90cb4fb32bc57dcbda4ee735f190ecef349720cb553794aa835452dc7d79a5', '2f61a044d9a13413e8ad4b8b131b22b6251ae38554110d85d2db4fbb5d04033f', 'Albina', 'Deathridge', '1931-02-09', '54 Prairie Rose Terrace--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('aquinet3', 'a36ae23b635257c75635a88c9fe82eed24e9c6d1f612b8a3494ce33a4ca205de6b81ae1300b8fcffded045db48a64c511af7930f4199cd9068d77d7f3c4f7ef2', '320a8455bbbef529f8bfe5a1d087d848cd2c64db3ddb41d40632322aced92615', 'Roger', 'Quinet', '1937-07-12', '95846 Melrose Center--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('jparres4', 'e71e3bcbb8894e86a144f6b5e925af69d777c0d95cc181bcb550e16b67cdfb0b8114c3ed5f280c047e092937097ba5b3fce8ab5f58f71809999803c81c79497d', 'ce37289865389dc6964641d281b7d4a589eee711df2e911b2759da71a6212a21', 'Frank', 'Parres', '1949-09-05', '0292 Lyons Trail--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('amchirrie5', 'dd60db633d7c16ed33505f52ef80b0502c25c4be5a7cc034bf5878a3e8a2f58ff25d87639667aca958885f1f54f75a4dfc47a77dac54e230a38f60f81eefc9a8', 'be4d4be00a3fd72f2ce4dbbb999051de7ba7cff3197bad729ac3d87e33fd8c03', 'Alecia', 'M''Chirrie', '1978-07-10', '53781 Darwin Point--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('mfomichkin6', '1cf965302b9f67bc350d58de68c4b1a1ae0d66d28df594745e4976637566202a560222fcd3488a69f19bac8af72b5733897cf208673e34143153404cb9cd4c9b', 'f9fe7782e93c018a5e55652d2985186374e037ecff4517c450d12065fc17f7a8', 'Maribelle', 'Fomichkin', '1945-02-02', '91 Anzinger Place--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('gwhitcher7', '838937bfcde6260b7150e046e80efd4a173e0bbc088f0acb9e2d33c6b14e3e4b5bbd9dab7d38840720fa3d1f799d583f20a5dadd0bb4365aec6879157db8dc15', '5c39ec2cab1b7d313c0306dcbb601e4e453a1e35aadc819484d22a97bedc7435', 'Giorgio', 'Whitcher', '1992-11-12', '1627 Forest Run Road--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('ccorpe8', '3c4b905b6dfcec8e235acf4e5bed36767be8769994990dfb80ceb9d205d42931d3de46e96eb492c3cb157e254623d71953942a473cc6dfe7516aac30bf3d340c', '4897b43efcf526b11b750e9ad95ce6d8cf06039a6afbcdee4bb560e93b750e12', 'Claudianus', 'Corpe', '2000-07-14', '55603 Melby Hill--BE16 3LO-county-Bristol-958297970322', false);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('ljellings9', 'bd647f45ddd683e2163112067416539cc80814f3b682a49c60f3d04d68958cdd61ad4556cf8e84828e2cb397d2848e1bebd1a1fbb9159f83ff76782a8314d4a6', '952cc10116763694a3c083f30836e17a7ff18f162bab56a278c2554b3f32e5df', 'Louis', 'Jellings', '1974-10-27', '8595 American Ash Way--BE16 3LO-county-Bristol-958297970322', false);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('espoorsa', '6a60ae93916a47155fb423b0115b0c238446b0d944bc651b30de005bda7867cd0d7d2b57cbbdd3832672fbbba8b778d76a6dccbb8f4ff047b9b66662f7589808', '00eb5c49fdd2bedc798bc22f8bfb4efdbfb58917ec1dad05cf15546b3bd5aebe', 'Elizabeth', 'Spoors', '1951-02-07', '511 Porter Alley--BE16 3LO-county-Bristol-958297970322', false);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('wwestgateb', '0d33097955ea2aca1f0884bbc3c954f8cdf693e43eff07f47a9c3954c2d4f3216b74199af8485fffb3f6c662b7b4c888b870f7092cd92de95af5eedc3aa89ea8', '39e05d2dbc207ce9864c804a0ad2b4387243912a6cdd76fefe307ed56ee7fc9b', 'Wilmette', 'Westgate', '1950-11-11', '6688 Cordelia Crossing--BE16 3LO-county-Bristol-958297970322', false);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('msandcraftc', '5bc07443ff3938bb4f5b1db919dfcb737faed5f8a00d89b3509415d885ebcd337900fb757eb82edac8a4e3a6e3457a76ac9aed9eec4f61a1495f73cdd1391a37', 'a8aee58ebb9ebbf4175a19ca12b7cd5744826a57c41c3445e1ba75984b390c5a', 'Marwin', 'Sandcraft', '1993-08-17', '630 Hazelcrest Trail--BE16 3LO-county-Bristol-958297970322', false);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('cocooneyd', '44b38bec943a7d3d23d1b2811db66067d696fd36cf0da0a3a4a033fe5aeed04dfc947c2dba04a0ed24ffc4550cc031cde45edf49d70c34be4e25d8a912dddb16', 'f56cdc10a660a52af59e64f5a39c97199d8bbc697bf5ac40bed464721e1a0957', 'Crissie', 'O''Cooney', '1955-12-07', '3127 Holmberg Court--BE16 3LO-county-Bristol-958297970322', false);


create table consultations (
	consultation_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 50000, INCREMENT BY 1) primary key,
	patient_id INT,
	doctor_id INT,
        nurse_id INT,
	consultation_time TIMESTAMP,
        pending boolean,
        note VARCHAR(128),
        duration INT,
        CONSTRAINT fk_doctor_consultation
        FOREIGN KEY (doctor_id) 
        REFERENCES doctors(doctor_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE,
        CONSTRAINT fk_nurse_consultation
        FOREIGN KEY (nurse_id) 
        REFERENCES nurses(nurse_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE,
        CONSTRAINT fk_patient_consultation
        FOREIGN KEY (patient_id) 
        REFERENCES patients(patient_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE
);

/* Past */
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20001, 30000, '2020-12-15 08:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20002, 30000, '2020-12-15 09:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40004, 20002, 30000, '2020-12-15 09:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40008, 20001, 30000, '2020-12-15 15:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20001, 30000, '2020-12-16 12:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20001, 30000, '2020-12-16 08:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20002, 30000, '2020-12-16 08:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20001, 30000, '2020-12-16 11:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2020-12-17 08:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40005, 20001, 30000, '2020-12-17 09:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40008, 20001, 30000, '2020-12-17 10:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20001, 30000, '2020-12-17 11:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40012, 20002, 30000, '2020-12-18 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40003, 20002, 30000, '2020-12-18 13:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40000, 20002, 30000, '2020-12-18 14:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40012, 20002, 30000, '2020-12-18 15:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20001, 30000, '2020-12-19 16:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20001, 30000, '2020-12-19 17:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20002, 30000, '2020-12-19 18:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40002, 20001, 30000, '2020-12-19 10:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2020-12-20 11:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20002, 30000, '2020-12-20 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20002, 30000, '2020-12-20 13:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20002, 30000, '2020-12-20 09:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20002, 30000, '2020-12-21 08:50:00', false, '', 10);



/* Doctor only consultations */
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20001, 30000, '2021-01-15 08:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20002, 30000, '2021-01-15 09:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40004, 20002, 30000, '2021-01-15 09:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40008, 20001, 30000, '2021-01-15 15:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20001, 30000, '2021-01-16 12:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20001, 30000, '2021-01-16 08:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20002, 30000, '2021-01-16 08:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20001, 30000, '2021-01-16 11:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2021-01-17 08:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40005, 20001, 30000, '2021-01-17 09:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40008, 20001, 30000, '2021-01-17 10:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20001, 30000, '2021-01-17 11:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40012, 20002, 30000, '2021-01-18 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40003, 20002, 30000, '2021-01-18 13:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40000, 20002, 30000, '2021-01-18 14:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40012, 20002, 30000, '2021-01-18 15:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20001, 30000, '2021-01-19 16:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20001, 30000, '2021-01-19 17:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20002, 30000, '2021-01-19 18:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40002, 20001, 30000, '2021-01-19 10:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2021-01-20 11:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20002, 30000, '2021-01-20 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20002, 30000, '2021-01-20 13:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20002, 30000, '2021-01-20 09:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20002, 30000, '2021-01-21 08:50:00', false, '', 10);

insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40008, 20002, 30000, '2021-01-21 13:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20002, 30000, '2021-01-21 15:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20001, 30000, '2021-01-21 11:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20001, 30000, '2021-01-22 16:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20001, 30000, '2021-01-22 12:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20002, 30000, '2021-01-22 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2021-01-22 12:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20002, 30000, '2021-01-23 12:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40014, 20001, 30000, '2021-01-23 12:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20002, 30000, '2021-01-23 17:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20001, 30000, '2021-01-23 11:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2021-01-24 10:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40002, 20002, 30000, '2021-01-24 09:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40002, 20001, 30000, '2021-01-24 12:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40001, 20002, 30000, '2021-01-24 11:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40014, 20002, 30000, '2021-01-25 12:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20001, 30000, '2021-01-25 16:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40003, 20002, 30000, '2021-01-25 18:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20001, 30000, '2021-01-25 17:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20002, 30000, '2021-01-26 12:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20001, 30000, '2021-01-26 11:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40000, 20001, 30000, '2021-01-26 16:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40014, 20001, 30000, '2021-01-26 12:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20002, 30000, '2021-01-27 12:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20002, 30000, '2021-01-27 16:20:00', false, '', 10);

/* Nurse only consultations */
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20000, 30001, '2021-01-27 13:00:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20000, 30001, '2021-01-27 11:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20000, 30001, '2021-01-28 10:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40011, 20000, 30001, '2021-01-28 11:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40014, 20000, 30001, '2021-01-28 13:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40012, 20000, 30001, '2021-01-28 15:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20000, 30001, '2021-01-29 13:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20000, 30001, '2021-01-29 16:50:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40004, 20000, 30001, '2021-01-29 11:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40004, 20000, 30001, '2021-01-29 10:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20000, 30001, '2021-02-02 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20000, 30001, '2021-02-02 17:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40007, 20000, 30001, '2021-02-02 18:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40000, 20000, 30001, '2021-02-02 13:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20000, 30001, '2021-02-03 12:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20000, 30001, '2021-02-03 16:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40013, 20000, 30001, '2021-02-03 11:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20000, 30001, '2021-02-03 17:30:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40005, 20000, 30001, '2021-02-03 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40014, 20000, 30001, '2021-02-03 11:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40012, 20000, 30001, '2021-02-04 10:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40008, 20000, 30001, '2021-02-04 08:40:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40006, 20000, 30001, '2021-02-04 13:10:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40010, 20000, 30001, '2021-02-04 12:20:00', false, '', 10);
insert into consultations (patient_id, doctor_id, nurse_id, consultation_time, pending, note, duration) values (40009, 20000, 30001, '2021-02-04 11:50:00', false, '', 10);


create table invoices (
        invoice_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 60000, INCREMENT BY 1) primary key,
	price DECIMAL(7,2),
	date_of_invoice DATE,
	consultation_id INT,
        paid boolean,
        insured boolean,
        CONSTRAINT fk_consultation
        FOREIGN KEY (consultation_id) 
        REFERENCES consultations(consultation_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE
);



insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1567.27, '2020-12-30', 50001, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1159.23, '2020-11-05', 50002, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1078.77, '2020-12-11', 50003, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1261.12, '2020-12-19', 50004, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1409.24, '2020-12-08', 50005, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1393.7, '2020-12-27', 50006, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1118.37, '2020-11-11', 50007, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (483.98, '2021-01-10', 50008, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (730.84, '2020-12-05', 50009, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (778.18, '2020-11-25', 50010, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (497.92, '2021-01-05', 50011, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (481.15, '2021-01-14', 50012, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (952.41, '2020-11-30', 50013, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (602.82, '2020-12-12', 50014, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1265.91, '2020-11-12', 50015, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (623.03, '2021-01-09', 50016, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1207.69, '2020-11-22', 50017, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (403.91, '2020-11-30', 50018, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (888.26, '2020-12-24', 50019, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1244.27, '2020-12-17', 50020, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (877.46, '2020-12-27', 50021, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (423.01, '2020-12-09', 50022, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1216.56, '2020-11-11', 50023, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (284.78, '2020-12-01', 50024, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (364.15, '2020-12-03', 50025, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (560.29, '2021-01-11', 50026, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (232.21, '2020-12-10', 50027, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (633.71, '2020-12-14', 50028, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (735.0, '2020-11-02', 50029, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1556.04, '2020-11-05', 50030, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (924.55, '2020-11-26', 50031, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1113.72, '2020-12-12', 50032, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1513.46, '2020-11-24', 50033, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (315.87, '2020-11-05', 50034, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1413.73, '2020-12-18', 50035, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (367.52, '2020-11-17', 50036, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1171.83, '2020-12-09', 50037, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (694.32, '2020-12-12', 50038, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (826.14, '2020-11-11', 50039, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1218.49, '2020-12-18', 50040, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1414.17, '2020-11-04', 50041, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1375.62, '2020-12-22', 50042, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1053.04, '2020-11-11', 50043, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1072.71, '2021-01-10', 50044, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (901.31, '2020-11-20', 50045, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (954.65, '2020-12-03', 50046, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (719.07, '2020-12-24', 50047, false, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (717.96, '2020-11-12', 50048, true, true);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1050.94, '2020-12-03', 50049, false, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (1521.5, '2020-11-18', 50050, true, true);


create table surgeries (
	surgery_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 70000, INCREMENT BY 1) primary key,
	patient_id INT,
	doctor_id INT,
	surgery_time TIMESTAMP,
        CONSTRAINT fk_doctor_surgery
        FOREIGN KEY (doctor_id) 
        REFERENCES doctors(doctor_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE,
        CONSTRAINT fk_patient_surgery
        FOREIGN KEY (patient_id) 
        REFERENCES patients(patient_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE
);

/* Past */
insert into surgeries (patient_id, doctor_id, surgery_time) values (40013, 20001, '2020-12-16 08:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20002, '2020-12-16 09:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40004, 20002, '2020-12-16 10:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40008, 20001, '2020-12-16 11:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40013, 20001, '2020-12-17 12:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20001, '2020-12-17 13:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20002, '2020-12-17 14:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20001, '2020-12-18 15:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2020-12-19 16:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40005, 20001, '2020-12-20 17:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40008, 20001, '2020-12-20 18:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20001, '2020-12-20 19:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40012, 20002, '2020-12-21 20:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40003, 20002, '2020-12-21 08:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40000, 20002, '2020-12-21 09:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40012, 20002, '2020-12-22 10:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20001, '2020-12-22 11:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20001, '2020-12-22 12:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20002, '2020-12-24 13:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40002, 20001, '2020-12-24 14:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2020-12-26 15:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20002, '2020-12-26 16:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20002, '2020-12-26 17:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20002, '2020-12-28 18:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20002, '2020-12-29 19:00:00');


/* Present */
insert into surgeries (patient_id, doctor_id, surgery_time) values (40013, 20001, '2021-01-16 08:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20002, '2021-01-16 09:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40004, 20002, '2021-01-16 10:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40008, 20001, '2021-01-16 11:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40013, 20001, '2021-01-17 12:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20001, '2021-01-17 13:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20002, '2021-01-17 14:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20001, '2021-01-18 15:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2021-01-19 16:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40005, 20001, '2021-01-20 17:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40008, 20001, '2021-01-20 18:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20001, '2021-01-20 19:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40012, 20002, '2021-01-21 20:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40003, 20002, '2021-01-21 08:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40000, 20002, '2021-01-21 09:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40012, 20002, '2021-01-22 10:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20001, '2021-01-22 11:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20001, '2021-01-22 12:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20002, '2021-01-24 13:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40002, 20001, '2021-01-24 14:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2021-01-26 15:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20002, '2021-01-26 16:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20002, '2021-01-26 17:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20002, '2021-01-28 18:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20002, '2021-01-29 19:00:00');

insert into surgeries (patient_id, doctor_id, surgery_time) values (40008, 20002, '2021-02-01 08:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20002, '2021-02-02 09:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20001, '2021-02-02 10:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20001, '2021-02-02 11:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20001, '2021-02-02 12:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20002, '2021-02-05 13:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2021-02-05 14:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20002, '2021-02-05 15:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40014, 20001, '2021-02-05 16:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20002, '2021-02-06 17:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20001, '2021-02-06 18:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2021-02-06 19:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40002, 20002, '2021-02-08 20:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40002, 20001, '2021-02-10 08:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40001, 20002, '2021-02-11 09:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40014, 20002, '2021-02-12 10:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20001, '2021-02-12 11:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40003, 20002, '2021-02-12 12:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40009, 20001, '2021-02-12 13:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40010, 20002, '2021-02-14 14:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40006, 20001, '2021-02-14 15:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40000, 20001, '2021-02-14 16:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40014, 20001, '2021-02-16 17:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40007, 20002, '2021-02-16 18:00:00');
insert into surgeries (patient_id, doctor_id, surgery_time) values (40011, 20002, '2021-02-16 19:00:00');


create table prices (
        price_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 80000, INCREMENT BY 1) primary key,
    	surgery_hourly DECIMAL(7,2),
        consultation_hourly DECIMAL(7,2),
        consultation_nurse_hourly DECIMAL(7,2)
);

insert into prices (surgery_hourly, consultation_hourly, consultation_nurse_hourly) values (1000.00, 100.00, 50.00);

create table perscriptions (
        perscription_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 90000, INCREMENT BY 1) primary key,
        patient_id INT,
	doctor_id INT,
        medication VARCHAR(100),
        pending boolean,
        CONSTRAINT fk_doctor_perscription
        FOREIGN KEY (doctor_id) 
        REFERENCES doctors(doctor_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE,
        CONSTRAINT fk_patient_perscription
        FOREIGN KEY (patient_id) 
        REFERENCES patients(patient_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE

);

insert into perscriptions (patient_id, doctor_id, medication, pending) values (40001, 20001, 'Fluoxetine', false);
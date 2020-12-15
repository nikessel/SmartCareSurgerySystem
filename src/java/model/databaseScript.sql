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
        is_full_time boolean
);
insert into admins (username, password_hash, salt, first_name, sur_name, is_full_time) values ('admin', 'faf7cf536b8a1ab503a61371a565d96bf5c365d7662dc5804a3225a5222dc2093dadecd7ec040d9ceb2c5cf0913b29c93ec842e9191d6d404d8e6efa67745f95', '9cd160915b63412ad6a09d8adf0ccc3e4b05401bacc8c2c64189a5573001cf0e', 'TestAdminFirstName', 'TestAdminSurName', true);
/* insert into admins (username, password_hash, salt, first_name, sur_name, is_full_time) values ('kmurrigans0', '9d40b3fda46e036504262a13f49e99a2606a3abcba868e34c523edfd2a05b2ec864b30528e29ca462df0ec04dffcef832f8364b99777a72d1365c0f59cc3f292', 'cc20e3b462e5a10db9bdcc6f1417da951a748eae5352efaf7f1a2155242b63c0', 'Kiele', 'Murrigans', true);
*/


create table doctors (
	doctor_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 20000, INCREMENT BY 1) primary key,
	username VARCHAR(50),
	password_hash VARCHAR(128),
	salt VARCHAR(64),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
        is_full_time boolean
);

insert into doctors (username, password_hash, salt, first_name, sur_name, is_full_time) values ('none', '0000', '0000', 'none', 'none', false);
insert into doctors (username, password_hash, salt, first_name, sur_name, is_full_time) values ('doctor', '3f0b2e0146d963bb497234ee5cd9dbd27e9c5833ceb9e907740223ac4e900620be03918d2bf4cac30fb6e3cb32e8d47a1557491fc6616cc54cd6269f703bbfb5', '6c841a9111f318ae744388a15de475df47c04b9981cc341434fbeda8b985d8a6', 'TestDoctorFirstName', 'TestDoctorSurName', false);
insert into doctors (username, password_hash, salt, first_name, sur_name, is_full_time) values ('gbuckoke0', '5f8ce858e7556d9d83bc959cd9adcf312f04077319d4442dcdff5f7f46d2d1c3e7b9354b16b83b7a2ea3404d03859f6a76e4bd11447c82c5c4a2016b95c23e4c', '6e84653e4d316083180d95b617af8f07eaadc87bfb160d209ccdc15024633640', 'Bob', 'Buckoke', true);
/* insert into doctors (username, password_hash, salt, first_name, sur_name, is_full_time) values ('dpilkinton1', '1b7444301edf5dfafdb7466bcba2526503f345ddb523bb07357fef3ff8678c9d01bc4efb606d69feb4802ed7dbc57f71cf0f99d4c88b471b2d324886d36f1277', 'e2b7ccdd1dc428a982b32c9b130dbf18126b36f0c178bd061e4eef118547426a', 'James', 'Pilkinton', false);
*/

create table nurses (
	nurse_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 30000, INCREMENT BY 1) primary key,
	username VARCHAR(50),
	password_hash VARCHAR(128),
	salt VARCHAR(64),
	first_name VARCHAR(50),
	sur_name VARCHAR(50),
        is_full_time boolean
);

insert into nurses (username, password_hash, salt, first_name, sur_name, is_full_time) values ('none', '0000', '0000', 'none', 'none', false);
insert into nurses (username, password_hash, salt, first_name, sur_name, is_full_time) values ('nurse', '77027f84aa6f001db0b0d44b253f23e906615843bc06cff3acedfe939cb2556a1ad4d144d95276b23228df0decfc3478c1eb5ab4792b5cad23de90448756c9af', 'a94ed051440a587c411ca18c6b74315878e31733c93900e9b511013aa0844dc6', 'TestNurseFirstName', 'TestNurseSurName', false);
/* insert into nurses (username, password_hash, salt, first_name, sur_name, is_full_time) values ('mbleasdale0', '55b50e12ba227aa387396e50814aebce445ce0125b54f4994bbb84737b625147e708cec9217724346a33a37cf1fd365975a69e5de55ab57a027f8588afafe738', '3afdff3726e0b9e8b56940c23c9190bca924db3474ae4abaeba317da51f0ffdd', 'Mollie', 'Bleasdale', false);
*/

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
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('rglendinning0', 'f32c2199f1cd692585200f12be9a3a4c071ed15aa995da91da11582ceee2983683a8f59301c4e96e9bee20c58926d742073edef560c62d0b8002dacd6b5e2c96', '3580fbfbf8e10cd224f1ae15d71ee098a963808c3e86a926d9efd5ec407a8f6c', 'Ray', 'Glendinning', '1992-03-14', '1 Melby Parkway--BE16 3LO-county-Bristol-958297970322', true);
insert into patients (username, password_hash, salt, first_name, sur_name, date_of_birth, address, insured) values ('aconstantinou1', '34ec15c07558111e9905ab9a9c86facf92a353b744ddb0502a5cf58034a9aab993d4e8f5203fb29e85a39894ef8f09bc9a3f5971a61ff21dd7ce5372fd9ede79', '2d50fda7c9df60eac536b6619ceb3f2524d13ddb7153098dd8eb351a8433161f', 'Amby', 'Constantinou', '1934-01-17', '773 Bowman Lane--BE16 3LO-county-Bristol-958297970322', true);
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
	consultation_date DATE,
        CONSTRAINT fk_doctor
        FOREIGN KEY (doctor_id) 
        REFERENCES doctors(doctor_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE,
        CONSTRAINT fk_nurse
        FOREIGN KEY (nurse_id) 
        REFERENCES nurses(nurse_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE,
        CONSTRAINT fk_patient
        FOREIGN KEY (patient_id) 
        REFERENCES patients(patient_id)
            ON UPDATE RESTRICT
            ON DELETE CASCADE
);

/* Doctor only consultations */
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40013, 20001, 30000, '2020-06-30');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20002, 30000, '2020-07-02');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40004, 20002, 30000, '2020-06-09');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40008, 20001, 30000, '2021-02-20');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40013, 20001, 30000, '2020-12-04');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20001, 30000, '2020-07-11');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40011, 20002, 30000, '2021-02-14');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40007, 20001, 30000, '2021-01-26');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20001, 30000, '2020-08-22');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40005, 20001, 30000, '2020-11-12');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40008, 20001, 30000, '2020-02-16');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40007, 20001, 30000, '2020-07-18');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40012, 20002, 30000, '2020-01-06');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40003, 20002, 30000, '2020-04-22');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40000, 20002, 30000, '2021-03-05');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40012, 20002, 30000, '2020-12-04');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20001, 30000, '2020-07-17');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40011, 20001, 30000, '2020-11-25');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40011, 20002, 30000, '2020-04-24');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40002, 20001, 30000, '2020-05-26');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20001, 30000, '2020-06-23');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40001, 20002, 30000, '2020-06-14');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40007, 20002, 30000, '2020-03-18');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20002, 30000, '2021-02-06');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20002, 30000, '2020-02-29');

insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40008, 20002, 30000, '2020-01-15');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40001, 20002, 30000, '2020-11-01');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20001, 30000, '2021-01-19');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40011, 20001, 30000, '2020-06-11');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20001, 30000, '2020-10-26');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20002, 30000, '2020-04-27');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20001, 30000, '2021-01-30');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40001, 20002, 30000, '2020-09-27');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40014, 20001, 30000, '2021-02-26');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40001, 20002, 30000, '2020-06-05');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40001, 20001, 30000, '2020-01-26');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20001, 30000, '2020-02-29');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40002, 20002, 30000, '2020-08-31');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40002, 20001, 30000, '2021-02-04');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40001, 20002, 30000, '2020-10-11');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40014, 20002, 30000, '2019-12-18');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20001, 30000, '2020-03-10');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40003, 20002, 30000, '2020-12-23');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20001, 30000, '2020-05-23');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20002, 30000, '2020-03-21');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20001, 30000, '2019-12-28');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40000, 20001, 30000, '2020-02-29');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40014, 20001, 30000, '2020-09-15');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40007, 20002, 30000, '2021-01-26');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40011, 20002, 30000, '2021-02-02');

/* Nurse only consultations */
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40007, 20000, 30001, '2020-02-02');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40013, 20000, 30001, '2020-07-22');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40013, 20000, 30001, '2021-01-21');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40011, 20000, 30001, '2021-01-05');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40014, 20000, 30001, '2020-02-29');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40012, 20000, 30001, '2020-07-07');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20000, 30001, '2020-06-22');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20000, 30001, '2020-02-12');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40004, 20000, 30001, '2020-06-14');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40004, 20000, 30001, '2021-02-09');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20000, 30001, '2020-10-03');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20000, 30001, '2020-10-21');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40007, 20000, 30001, '2021-03-15');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40000, 20000, 30001, '2021-03-13');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20000, 30001, '2020-11-29');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20000, 30001, '2020-10-05');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40013, 20000, 30001, '2020-02-11');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20000, 30001, '2020-06-05');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40005, 20000, 30001, '2019-12-29');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40014, 20000, 30001, '2020-09-15');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40012, 20000, 30001, '2021-01-29');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40008, 20000, 30001, '2020-01-04');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40006, 20000, 30001, '2020-07-02');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40010, 20000, 30001, '2020-01-08');
insert into consultations (patient_id, doctor_id, nurse_id, consultation_date) values (40009, 20000, 30001, '2020-11-29');

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


insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (5291.92, '2020-5-18', 50001, true, false);
insert into invoices (price, date_of_invoice, consultation_id, paid, insured) values (7747.29, '2020-2-7', 50003, false, true);

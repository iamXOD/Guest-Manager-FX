BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS "date" (
	"date_id"	TEXT NOT NULL,
	"year"	INTEGER NOT NULL,
	"month"	INTEGER NOT NULL,
	"day"	INTEGER NOT NULL,
	PRIMARY KEY("date_id")
) WITHOUT ROWID;

CREATE TABLE IF NOT EXISTS "country" (
	"country_id"	TEXT NOT NULL UNIQUE,
	"name"	TEXT NOT NULL,
	PRIMARY KEY("country_id")
) WITHOUT ROWID;

CREATE TABLE IF NOT EXISTS "guest" (
	"guest_id"	TEXT NOT NULL,
	"name"	TEXT NOT NULL,
	"surname"	TEXT NOT NULL,
	"ci"	TEXT NOT NULL,
	"age"	INTEGER NOT NULL,
	"country_id"	TEXT NOT NULL,
	PRIMARY KEY("guest_id"),
	FOREIGN KEY("country_id") REFERENCES "country"("country_id") ON UPDATE CASCADE ON DELETE CASCADE
) WITHOUT ROWID;

CREATE TABLE IF NOT EXISTS "stay" (
	"stay_id"	TEXT NOT NULL,
	"guest_id"	TEXT NOT NULL,
	"indate"	TEXT NOT NULL,
	"outdate"	TEXT NOT NULL,
	"room_id"	TEXT NOT NULL,
	PRIMARY KEY("stay_id"),
	FOREIGN KEY("guest_id") REFERENCES "guest"("guest_id") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("indate") REFERENCES "date"("date_id") ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY("outdate") REFERENCES "date"("date_id") ON UPDATE CASCADE ON DELETE CASCADE
) WITHOUT ROWID;

 INSERT OR REPLACE INTO "date" ("date_id","year","month","day") VALUES ('2003-3-16',2003,3,16),
 ('2003-3-20',2003,3,20),
 ('2012-8-17',2012,8,17),
 ('2012-8-25',2012,8,25),
 ('2013-4-12',2013,4,12),
 ('2017-2-22',2017,2,22),
 ('2017-2-25',2017,2,25),
 ('2017-2-27',2017,2,27),
 ('2018-12-1',2018,12,1),
 ('2018-12-10',2018,12,10),
 ('2018-12-17',2018,12,17),
 ('2019-1-12',2019,1,12),
 ('2019-1-17',2019,1,17),
 ('2019-1-23',2019,1,23),
 ('2019-2-2',2019,2,2),
 ('2019-2-17',2019,2,17),
 ('2019-2-20',2019,2,20),
 ('2019-12-2',2019,12,2),
 ('2019-12-11',2019,12,11),
 ('2019-12-12',2019,12,12),
 ('2019-12-13',2019,12,13),
 ('2019-12-17',2019,12,17),
 ('2019-12-20',2019,12,20);

INSERT OR REPLACE INTO "country" ("country_id","name") VALUES ('.ad','Andorra'),
 ('.ae','Emiratos arabes Unidos'),
 ('.af','Afganistan'),
 ('.ag','Antigua y Barbuda'),
 ('.al','Albania'),
 ('.am','Armenia'),
 ('.ao','Angola'),
 ('.ar','Argentina'),
 ('.at','Austria'),
 ('.au','Australia'),
 ('.az','Azerbaiyan'),
 ('.ba','Bosnia-Herzegovina'),
 ('.bb','Barbados'),
 ('.bd','Banglades'),
 ('.be','Belgica'),
 ('.bf','Burkina Faso'),
 ('.bg','Bulgaria'),
 ('.bh','Barein'),
 ('.bi','Burundi'),
 ('.bj','Benin'),
 ('.bm','Bermudas'),
 ('.bn','Brunei'),
 ('.bo','Bolivia'),
 ('.br','Brasil'),
 ('.bs','Bahamas'),
 ('.bt','Butan'),
 ('.bw','Botsuana'),
 ('.by','Bielorrusia'),
 ('.bz','Belice'),
 ('.ca','Canada'),
 ('.cd','Republica Democratica del Congo'),
 ('.cf','Republica Centroafricana'),
 ('.cg','Republica del Congo'),
 ('.ch','Suiza'),
 ('.ci','Costa de Marfil'),
 ('.cl','Chile'),
 ('.cm','Camerun'),
 ('.cn','Republica Popular China'),
 ('.co','Colombia'),
 ('.cr','Costa Rica'),
 ('.cu','Cuba'),
 ('.cv','Cabo Verde'),
 ('.cy','Chipre'),
 ('.cz','Chequia'),
 ('.de','Alemania'),
 ('.dj','Yibuti'),
 ('.dk','Dinamarca'),
 ('.dm','Dominica'),
 ('.do','Republica Dominicana'),
 ('.dz','Argelia'),
 ('.ec','Ecuador'),
 ('.ee','Estonia'),
 ('.eg','Egipto'),
 ('.er','Eritrea'),
 ('.es','España'),
 ('.et','Etiopia'),
 ('.fi','Finlandia'),
 ('.fj','Fiyi'),
 ('.fk','Islas Malvinas'),
 ('.fm','Estados Federados de Micronesia'),
 ('.fr','Francia'),
 ('.ga','Gabon'),
 ('.gd','Granada'),
 ('.ge','Georgia'),
 ('.gf','Guayana Francesa'),
 ('.gh','Ghana'),
 ('.gi','Gibraltar'),
 ('.gl','Groenlandia'),
 ('.gm','Gambia'),
 ('.gn','Guinea'),
 ('.gq','Guinea Ecuatorial'),
 ('.gr','Grecia'),
 ('.gt','Guatemala'),
 ('.gw','Guinea-Bisau'),
 ('.gy','Guyana'),
 ('.hk','Hong Kong'),
 ('.hn','Honduras'),
 ('.hr','Croacia'),
 ('.ht','Haiti'),
 ('.hu','Hungria'),
 ('.id','Indonesia'),
 ('.ie','Irlanda'),
 ('.il','Israel'),
 ('.in','India'),
 ('.iq','Irak'),
 ('.ir','Iran'),
 ('.is','Islandia'),
 ('.it','Italia'),
 ('.jm','Jamaica'),
 ('.jo','Jordania'),
 ('.jp','Japon'),
 ('.ke','Kenia'),
 ('.kg','Kirguistan'),
 ('.kh','Camboya'),
 ('.ki','Kiribati'),
 ('.km','Comoras'),
 ('.kn','San Cristobal y Nieves'),
 ('.kp','Corea del Norte'),
 ('.kr','Corea del Sur'),
 ('.kw','Kuwait'),
 ('.kz','Kazajistan'),
 ('.la','Laos'),
 ('.lb','Libano'),
 ('.lc','Santa Lucia'),
 ('.li','Liechtenstein'),
 ('.lk','Sri Lanka'),
 ('.lr','Liberia'),
 ('.ls','Lesoto'),
 ('.lt','Lituania'),
 ('.lu','Luxemburgo'),
 ('.lv','Letonia'),
 ('.ly','Libia'),
 ('.ma','Marruecos'),
 ('.mc','Monaco'),
 ('.md','Moldavia'),
 ('.me','Montenegro'),
 ('.mg','Madagascar'),
 ('.mh','Islas Marshall'),
 ('.mk','Macedonia del Norte'),
 ('.ml','Mali'),
 ('.mm','Birmania'),
 ('.mn','Mongolia'),
 ('.mp','Islas Marianas del Norte'),
 ('.mq','Martinica'),
 ('.mr','Mauritania'),
 ('.mt','Malta'),
 ('.mu','Mauricio'),
 ('.mv','Maldivas'),
 ('.mw','Malaui'),
 ('.mx','Mexico'),
 ('.my','Malasia'),
 ('.mz','Mozambique'),
 ('.na','Namibia'),
 ('.ne','Niger'),
 ('.ng','Nigeria'),
 ('.ni','Nicaragua'),
 ('.nl','Paises Bajos'),
 ('.no','Noruega'),
 ('.np','Nepal'),
 ('.nr','Nauru'),
 ('.nz','Nueva Zelanda'),
 ('.om','Oman'),
 ('.pa','Panama'),
 ('.pe','Peru'),
 ('.pg','Papua Nueva Guinea'),
 ('.ph','Filipinas'),
 ('.pk','Pakistan'),
 ('.pl','Polonia'),
 ('.pr','Puerto Rico'),
 ('.ps','Palestina'),
 ('.pt','Portugal'),
 ('.pw','Palaos'),
 ('.py','Paraguay'),
 ('.qa','Catar'),
 ('.ro','Rumania'),
 ('.rs','Serbia'),
 ('.ru','Rusia'),
 ('.rw','Ruanda'),
 ('.sa','Arabia Saudita'),
 ('.sb','Islas Salomon'),
 ('.sc','Seychelles'),
 ('.sd','Sudan'),
 ('.se','Suecia'),
 ('.sg','Singapur'),
 ('.si','Eslovenia'),
 ('.sk','Eslovaquia'),
 ('.sl','Sierra Leona'),
 ('.sm','San Marino'),
 ('.sn','Senegal'),
 ('.so','Somalia'),
 ('.sr','Surinam'),
 ('.st','Santo Tome y Principe'),
 ('.sv','El Salvador'),
 ('.sy','Siria'),
 ('.sz','Suazilandia'),
 ('.tc','Islas Turcas y Caicos'),
 ('.td','Chad'),
 ('.tg','Togo'),
 ('.th','Tailandia'),
 ('.tj','Tayikistan'),
 ('.tk','Tokelau'),
 ('.tl','Timor Oriental'),
 ('.tm','Turkmenistan'),
 ('.tn','Tunez'),
 ('.to','Tonga'),
 ('.tr','Turquia'),
 ('.tt','Trinidad y Tobago'),
 ('.tv','Tuvalu'),
 ('.tw','Taiwan'),
 ('.tz','Tanzania'),
 ('.ua','Ucrania'),
 ('.ug','Uganda'),
 ('.uk','Reino Unido'),
 ('.us','Estados Unidos'),
 ('.uy','Uruguay'),
 ('.uz','Uzbekistan'),
 ('.va','Ciudad del Vaticano'),
 ('.vc','San Vicente y las Granadinas'),
 ('.ve','Venezuela'),
 ('.vg','Islas Virgenes Britanicas'),
 ('.vi','Islas Virgenes de los Estados Unidos'),
 ('.vn','Vietnam'),
 ('.vu','Vanuatu'),
 ('.ws','Samoa'),
 ('.ye','Yemen'),
 ('.za','Sudafrica'),
 ('.zm','Zambia'),
 ('.zw','Zimbabue');

 INSERT OR REPLACE INTO "guest" ("guest_id","name","surname","ci","age","country_id") VALUES ('070511xxxxx','Halia','Ochil','070511xxxxx',12,'.cu'),
 ('740420xxxxx','Ivan','Muñoz','740420xxxxx',45,'.uy'),
 ('760619xxxxx','Alexander','Carrazana','760619xxxxx',43,'.ca'),
 ('970221xxxxx','Denia','Toledo','970221xxxxx',22,'.cu'),
 ('970307xxxxx','Ignacio','Sena Chil','970307xxxxx',22,'.cu'),
 ('97082209601','Harold','Muñoz','97082209601',22,'.cu'),
 ('980802xxxxx','Analaura','Gonzalez','980802xxxxx',21,'.cu'),
 ('xx0913xxxx','Zoila','Perez','xx0913xxxx',0,'.uy'),
 ('xxxxxxxxxxx','Conrado','Benitez','xxxxxxxxxxx',0,'.it');
 
INSERT OR REPLACE INTO "stay" ("stay_id","guest_id","indate","outdate","room_id") VALUES ('2003-3-16-070511xxxxx','070511xxxxx','2003-3-16','2003-3-20','7'),
 ('2012-8-17-970307xxxxx','970307xxxxx','2012-8-17','2012-8-25','5'),
 ('2017-2-22-970221xxxxx','970221xxxxx','2017-2-22','2017-2-25','4'),
 ('2018-12-1-070511xxxxx','070511xxxxx','2018-12-1','2018-12-10','4'),
 ('2018-12-17-760619xxxxx','760619xxxxx','2018-12-17','2019-12-20','1'),
 ('2019-1-17-980802xxxxx','980802xxxxx','2019-1-17','2019-1-23','4'),
 ('2019-12-11-xx0913xxxx','xx0913xxxx','2019-12-11','2019-12-13','3'),
 ('2019-12-12-97082209601','97082209601','2019-12-12','2019-12-17','1'),
 ('2019-12-17-740420xxxxx','740420xxxxx','2019-12-17','2019-12-17','2'),
 ('2019-12-17-970221xxxxx','970221xxxxx','2019-12-17','2019-12-17','3'),
 ('2019-12-17-970307xxxxx','970307xxxxx','2019-12-17','2019-12-17','6'),
 ('2019-2-17-xxxxxxxxxxx','xxxxxxxxxxx','2019-2-17','2019-2-20','1');

 CREATE INDEX IF NOT EXISTS "stay_room" ON "stay" (
	"room_id"
);

CREATE INDEX IF NOT EXISTS "stay_indate" ON "stay" (
	"indate"
);

CREATE INDEX IF NOT EXISTS "stay_outdate" ON "stay" (
	"outdate"
);

CREATE INDEX IF NOT EXISTS "stay_id" ON "stay" (
	"stay_id"
);

CREATE INDEX IF NOT EXISTS "guest_name" ON "guest" (
	"name"
);

CREATE INDEX IF NOT EXISTS "guest_surname" ON "guest" (
	"surname"
);

CREATE INDEX IF NOT EXISTS "guest_ci" ON "guest" (
	"ci"
);

CREATE INDEX IF NOT EXISTS "guest_id" ON "guest" (
	"guest_id"
);

CREATE INDEX IF NOT EXISTS "country_id" ON "country" (
	"country_id"
);

CREATE INDEX IF NOT EXISTS "date_data" ON "date" (
	"year",
	"month",
	"day"
);

CREATE INDEX IF NOT EXISTS "date_id" ON "date" (
	"date_id"
);

COMMIT;

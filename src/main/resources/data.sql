INSERT INTO administrator VALUES
  (0, '$2a$04$eALQTd29tHawhmLI23eUVeqnHfRcbL8ulpb4R6dHTiMfZ85bPugPS', 'admin');
INSERT INTO role VALUES
  (0, 'ADMIN'), (1, 'TRAINER'), (2, 'ATTENDANT');
INSERT INTO user_roles VALUES
  (0, 0);

INSERT INTO region (id, name) VALUES (0, 'Nepoznata županija');
INSERT INTO region (id, name) VALUES (1, 'Zagrebačka');
INSERT INTO region (id, name) VALUES (10, 'Virovitičko-podravska');
INSERT INTO region (id, name) VALUES (11, 'Požeško-slavonska');
INSERT INTO region (id, name) VALUES (12, 'Brodsko-posavska');
INSERT INTO region (id, name) VALUES (13, 'Zadarska');
INSERT INTO region (id, name) VALUES (14, 'Osječko-baranjska');
INSERT INTO region (id, name) VALUES (15, 'Šibensko-kninska');
INSERT INTO region (id, name) VALUES (16, 'Vukovarsko-srijemska');
INSERT INTO region (id, name) VALUES (17, 'Splitsko-dalmatinska');
INSERT INTO region (id, name) VALUES (18, 'Istarska');
INSERT INTO region (id, name) VALUES (19, 'Dubrovačko-neretvanska');
INSERT INTO region (id, name) VALUES (2, 'Krapinsko-zagorska');
INSERT INTO region (id, name) VALUES (20, 'Međimurska');
INSERT INTO region (id, name) VALUES (21, 'Grad Zagreb');
INSERT INTO region (id, name) VALUES (3, 'Sisačko-moslavačka');
INSERT INTO region (id, name) VALUES (4, 'Karlovačka');
INSERT INTO region (id, name) VALUES (5, 'Varaždinska');
INSERT INTO region (id, name) VALUES (6, 'Koprivničko-križevačka');
INSERT INTO region (id, name) VALUES (7, 'Bjelovarsko-bilogorska');
INSERT INTO region (id, name) VALUES (8, 'Primorsko-goranska');
INSERT INTO region (id, name) VALUES (9, 'Ličko-senjska');

INSERT INTO city (id, name, region_id) VALUES (1, 'Zagreb', 21);
INSERT INTO city (id, name, region_id) VALUES (3, 'Zaprešić', 1);
INSERT INTO city (id, name, region_id) VALUES (4, 'Ivanić-Grad', 1);
INSERT INTO city (id, name, region_id) VALUES (5, 'Velika Gorica', 1);
INSERT INTO city (id, name, region_id) VALUES (6, 'Ploče', 19);
INSERT INTO city (id, name, region_id) VALUES (7, 'Metković', 19);
INSERT INTO city (id, name, region_id) VALUES (8, 'Split', 17);
INSERT INTO city (id, name, region_id) VALUES (9, 'Trogir', 17);
INSERT INTO city (id, name, region_id) VALUES (10, 'Šibenik', 15);
INSERT INTO city (id, name, region_id) VALUES (11, 'Drniš', 15);
INSERT INTO city (id, name, region_id) VALUES (12, 'Zadar', 13);
INSERT INTO city (id, name, region_id) VALUES (13, 'Benkovac', 13);
INSERT INTO city (id, name, region_id) VALUES (14, 'Podvršje', 13);
INSERT INTO city (id, name, region_id) VALUES (15, 'Đakovo', 14);
INSERT INTO city (id, name, region_id) VALUES (16, 'Vukovar', 16);
INSERT INTO city (id, name, region_id) VALUES (17, 'Slatina', 10);
INSERT INTO city (id, name, region_id) VALUES (18, 'Požega', 11);
INSERT INTO city (id, name, region_id) VALUES (19, 'Slavonski Brod', 12);
INSERT INTO city (id, name, region_id) VALUES (20, 'Čakovec', 20);
INSERT INTO city (id, name, region_id) VALUES (21, 'Varaždin', 5);
INSERT INTO city (id, name, region_id) VALUES (22, 'Čazma', 7);
INSERT INTO city (id, name, region_id) VALUES (23, 'Sisak', 3);
INSERT INTO city (id, name, region_id) VALUES (24, 'Petrinja', 3);
INSERT INTO city (id, name, region_id) VALUES (25, 'Karlovac', 4);
INSERT INTO city (id, name, region_id) VALUES (26, 'Koprivnica', 6);
INSERT INTO city (id, name, region_id) VALUES (27, 'Krapina', 2);
INSERT INTO city (id, name, region_id) VALUES (28, 'Rijeka', 8);
INSERT INTO city (id, name, region_id) VALUES (29, 'Pazin', 18);
INSERT INTO city (id, name, region_id) VALUES (30, 'Gospić', 9);

INSERT INTO sport (id, name) VALUES (1, 'Funkcionalni trening');
INSERT INTO sport (id, name) VALUES (2, 'Nogomet');
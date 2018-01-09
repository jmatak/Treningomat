INSERT INTO public.region (id, name) VALUES (0, 'Nepoznata županija');
INSERT INTO public.region (id, name) VALUES (1, 'Zagrebačka');
INSERT INTO public.region (id, name) VALUES (10, 'Virovitičko-podravska');
INSERT INTO public.region (id, name) VALUES (11, 'Požeško-slavonska');
INSERT INTO public.region (id, name) VALUES (12, 'Brodsko-posavska');
INSERT INTO public.region (id, name) VALUES (13, 'Zadarska');
INSERT INTO public.region (id, name) VALUES (14, 'Osječko-baranjska');
INSERT INTO public.region (id, name) VALUES (15, 'Šibensko-kninska');
INSERT INTO public.region (id, name) VALUES (16, 'Vukovarsko-srijemska');
INSERT INTO public.region (id, name) VALUES (17, 'Splitsko-dalmatinska');
INSERT INTO public.region (id, name) VALUES (18, 'Istarska');
INSERT INTO public.region (id, name) VALUES (19, 'Dubrovačko-neretvanska');
INSERT INTO public.region (id, name) VALUES (2, 'Krapinsko-zagorska');
INSERT INTO public.region (id, name) VALUES (20, 'Međimurska');
INSERT INTO public.region (id, name) VALUES (21, 'Grad Zagreb');
INSERT INTO public.region (id, name) VALUES (3, 'Sisačko-moslavačka');
INSERT INTO public.region (id, name) VALUES (4, 'Karlovačka');
INSERT INTO public.region (id, name) VALUES (5, 'Varaždinska');
INSERT INTO public.region (id, name) VALUES (6, 'Koprivničko-križevačka');
INSERT INTO public.region (id, name) VALUES (7, 'Bjelovarsko-bilogorska');
INSERT INTO public.region (id, name) VALUES (8, 'Primorsko-goranska');
INSERT INTO public.region (id, name) VALUES (9, 'Ličko-senjska');

INSERT INTO public.city (id, name, zip_code, region_id) VALUES (1, 'Zagreb', 10000, 21);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (3, 'Zaprešić', 10290, 1);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (4, 'Ivanić-Grad', 10310, 1);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (5, 'Velika Gorica', 10410, 1);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (6, 'Ploče', 20340, 19);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (7, 'Metković', 20350, 19);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (8, 'Split', 21000, 17);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (9, 'Trogir', 21220, 17);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (10, 'Šibenik', 22000, 15);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (11, 'Drniš', 22320, 15);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (12, 'Zadar', 23000, 13);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (13, 'Benkovac', 23420, 13);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (14, 'Podvršje', 23248, 13);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (15, 'Đakovo', 31400, 14);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (16, 'Vukovar', 32000, 16);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (17, 'Slatina', 33520, 10);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (18, 'Požega', 34000, 11);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (19, 'Slavonski Brod', 35000, 12);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (20, 'Čakovec', 40000, 20);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (21, 'Varaždin', 42000, 5);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (22, 'Čazma', 43240, 7);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (23, 'Sisak', 44000, 3);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (24, 'Petrinja', 44250, 3);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (25, 'Karlovac', 47000, 4);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (26, 'Koprivnica', 48000, 6);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (27, 'Krapina', 49000, 2);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (28, 'Rijeka', 51000, 8);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (29, 'Pazin', 52000, 18);
INSERT INTO public.city (id, name, zip_code, region_id) VALUES (30, 'Gospić', 53000, 9);

INSERT INTO public.role (id, role) VALUES (0, 'ADMIN');
INSERT INTO public.role (id, role) VALUES (1, 'TRAINER');
INSERT INTO public.role (id, role) VALUES (2, 'ATTENDANT');

INSERT INTO public.sport (id, name, description) VALUES (0, 'Funkcionalni trening','Funkcionalni trening označava koncept treninga kojim se utječe na istovremeni razvoj što većeg broja motoričkih i funkcionalnih sposobnosti i osobina te na sastav tijela.');
INSERT INTO public.sport (id, name, description) VALUES (1, 'Nogomet', 'Nogomet je sport u kojemu se dvije momčadi od 11 igrača nadmeću na pravokutnom igralištu travnate površine.');
INSERT INTO public.sport (id, name, description) VALUES (6, 'Politika','Politika (grč. ta politiká – poslovi vezani uz polis) je kolektivna djelatnost usmjerena ka donošenju odluke o rješenju problema i izvršenju te odluke koja je obvezna za sve članove zajednice.');


INSERT INTO public.trainer (id, password, username, pid, address, id_photo, name, phone_number, surname, city_id) VALUES (7, '$2a$10$SvLtXUPs.r5osEUe5CxgUuJUn/xj//Djj0wVSeY3yGeW9whw.vWL6', 'caca', 52478956314, 'dr. Luje Naletilića 1', 'trainer_caca.jpg', 'Ivo', '091 5846 332', 'Sanader', 8);
INSERT INTO public.trainer (id, password, username, pid, address, id_photo, name, phone_number, surname, city_id) VALUES (8, '$2a$10$ICJJe4A6UoJeMSV5ZKcaBujInisNvBJH4JlvubyNY/ae72CNBbmSm', 'gazda', 32148751425, 'Trg Dražena Petrovića 3', 'trainer_gazda.jpg', 'Ivica', '092 5478 621', 'Todorić', 1);

INSERT INTO public.trainer_sports (trainer_id, sports_id) VALUES (7, 6);
INSERT INTO public.trainer_sports (trainer_id, sports_id) VALUES (8, 6);

INSERT INTO public.attendant (id, password, username, pid, active, id_photo, name, street_and_number, surname, city_id, comment_subscription) VALUES (5, '$2a$10$T8iVEwWTSmeWhokrNiGSR.MDHcLhueOMeZ4yvUZgkgFhIwrv5QDZ.', 'koMeneSlusa', 25489795634, false, 'attendant_koMeneSlusa.jpg', 'Miro', 'Oca Ante Gabrića 4', 'Bulj', 7, true);
INSERT INTO public.attendant (id, password, username, pid, active, id_photo, name, street_and_number, surname, city_id, comment_subscription) VALUES (4, '$2a$10$K69x73iMo5pl5EzOEK30cuQjWDAsJMyvamGeSAUts736MwVWwiy0m', 'jabbaTheHut', 455631587956, false, 'attendant_jabbaTheHut.jpg', 'Anka', 'Ul. kneza Mislava 8', 'Mrak-Taritaš', 1, true);
INSERT INTO public.attendant (id, password, username, pid, active, id_photo, name, street_and_number, surname, city_id, comment_subscription) VALUES (1, '$2a$10$zEvn1uNdUqyjyRRwr2Iup.wNUiVAsbojiNiC3oYiev4rMOE9a6TnG', 'plenki', 599542214894, false, 'attendant_plenki.jpg', 'Andrej', 'Trg svetog Marka 2', 'Plenković', 1, true);
INSERT INTO public.attendant (id, password, username, pid, active, id_photo, name, street_and_number, surname, city_id, comment_subscription) VALUES (3, '$2a$10$qDu00lEsP8FAkyyzfIuMBe4X9rcNClV4QwoJC7soTtx0gkFIMvwYS', 'gospodinDelozacija', 75862465894, false, 'attendant_gospodinDelozacija.jpg', 'Ivan', 'Pavlenski put 5G', 'Pernar', 1, true);
INSERT INTO public.attendant (id, password, username, pid, active, id_photo, name, street_and_number, surname, city_id, comment_subscription) VALUES (2, '$2a$10$NvCKZvl/5so8PIhTr0ZKsur4B438wcHwiuNm1hLJ2C7A6TAQ5xmF6', 'lordSDP', 49521468951, false, 'attendant_lordSDP.jpg', 'Davor', 'Trg Drage Iblera 9', 'Bernardić', 1, true);

INSERT INTO public.administrator (id, password, username) VALUES (0, '$2a$04$eALQTd29tHawhmLI23eUVeqnHfRcbL8ulpb4R6dHTiMfZ85bPugPS', 'admin');

INSERT INTO public.user_roles (user_id, roles_id) VALUES (0, 0);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (1, 2);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (2, 2);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (3, 2);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (4, 2);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (5, 2);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (7, 1);
INSERT INTO public.user_roles (user_id, roles_id) VALUES (8, 1);

INSERT INTO public.training_group (id, amount, capacity, name, place, sport_id, trainer_id) VALUES (9, 100.00, 100, 'Desni blok', 'Markov Trg', 6, 7);
INSERT INTO public.training_group (id, amount, capacity, name, place, sport_id, trainer_id) VALUES (10, 150.00, 30, 'Oporba', 'Nije London', 6, 8);

INSERT INTO public.training_group_attendants (training_groups_id, attendants_id) VALUES (10, 5);
INSERT INTO public.training_group_attendants (training_groups_id, attendants_id) VALUES (10, 3);
INSERT INTO public.training_group_attendants (training_groups_id, attendants_id) VALUES (10, 4);

INSERT INTO public.training (id, description, ends_at, start_at, training_group_id) VALUES (17, 'Kako napraviti buku u saboru ?', '2018-01-05 20:00:00.000000', '2018-01-05 19:00:00.000000', 10);
INSERT INTO public.training (id, description, ends_at, start_at, training_group_id) VALUES (18, 'Kad će izbori ??', '2018-01-09 19:00:00.000000', '2018-01-09 18:00:00.000000', 10);

INSERT INTO public.group_request (id, attendant_id, to_training_group_id) VALUES (13, 1, 9);
INSERT INTO public.group_request (id, attendant_id, to_training_group_id) VALUES (16, 2, 10);

INSERT INTO public.receipt (id, confirmed, created_date, attendant_id, training_group_id) VALUES (0, false, '2018-01-06', 4, 10);
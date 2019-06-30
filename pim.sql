
INSERT INTO `employee` VALUES (1, '2019-06-17', 'Dinh', 'Hai', 1, 'HVD');
INSERT INTO `employee` VALUES (2, '2019-06-19', 'Dam Quang', 'Khai', 1, 'KQD');
INSERT INTO `employee` VALUES (3, '2019-06-17', 'Pham Viet', 'Luc', 1, 'PVL');
INSERT INTO `employee` VALUES (4, '2019-06-18', 'Tran Minh', 'Kha', 1, 'KTM');
INSERT INTO `employee` VALUES (5, '2019-06-20', 'Nguyen Duy', 'Hau', 1, 'NDH');


INSERT INTO `group` VALUES (1, 1, 1);
INSERT INTO `group` VALUES (2, 1, 2);

INSERT INTO `project` VALUES (1, 'Les Trtaites Populaires', '2019-06-28 10:09:08', 'Facturaton/Encaissements', 3116, '2019-06-26 10:09:31', 'NEW', 1, 1);
INSERT INTO `project` VALUES (2, 'GKB', '2019-06-29 10:10:00', 'GKBWEB', 3118, '2002-10-10 00:00:00', 'FIN', 1, 2);
INSERT INTO `project` VALUES (3, 'DKS', '2019-07-04 10:11:20', 'Maint2015', 7142, '2019-06-10 10:11:36', 'INP', 1, 1);
INSERT INTO `project` VALUES (4, 'SKE', '2019-06-18 10:12:29', 'Something', 4821, '2019-06-09 10:12:45', 'NEW', 1, 2);
INSERT INTO `project` VALUES (5, 'ITIEOW', '2019-06-04 10:13:10', 'Test', 4672, '2019-06-02 10:13:27', 'PLA', 1, 1);
INSERT INTO `project` VALUES (6, 'Les Trtaites Populaires', '2019-06-28 00:00:00', 'Facturaton/Encaissements', 3116, '2019-06-26 00:00:00', 'NEW', 0, 1);
INSERT INTO `project` VALUES (7, 'Les Trtaites Populaires AA', '2019-06-28 00:00:00', 'Facturaton/Encaissements', 3116, '2019-06-27 00:00:00', 'NEW', 0, 1);
INSERT INTO `project` VALUES (8, 'Les Trtaites Populaires', '2019-06-28 00:00:00', 'Facturaton/Encaissements', 3116, '2019-06-26 00:00:00', 'NEW', 0, 1);
INSERT INTO `project` VALUES (9, '235', '2019-07-05 00:00:00', '235', 235, '2019-06-11 00:00:00', 'NEW', 0, 1);


INSERT INTO `project_employee` VALUES (1, 1);
INSERT INTO `project_employee` VALUES (1, 3);
INSERT INTO `project_employee` VALUES (2, 2);
INSERT INTO `project_employee` VALUES (2, 4);
INSERT INTO `project_employee` VALUES (2, 5);
INSERT INTO `project_employee` VALUES (3, 1);
INSERT INTO `project_employee` VALUES (3, 2);
INSERT INTO `project_employee` VALUES (4, 2);
INSERT INTO `project_employee` VALUES (4, 5);
INSERT INTO `project_employee` VALUES (5, 1);
INSERT INTO `project_employee` VALUES (5, 3);
INSERT INTO `project_employee` VALUES (5, 5);
INSERT INTO `project_employee` VALUES (9, 4);
INSERT INTO `project_employee` VALUES (9, 5);


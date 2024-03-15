DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`(
    `role_id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(8) NOT NULL,
    PRIMARY KEY (`role_id`)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`(
    `admin_id` int NOT NULL AUTO_INCREMENT,
    `admin_name` varchar(40) NOT NULL,
    `email` varchar(60) NOT NULL,
    `password` varchar(255) NOT NULL,
    `phone` varchar(10) NOT NULL,
    `registered_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    `role_id` int NOT NULL,
     PRIMARY KEY (`admin_id`),
     FOREIGN KEY (`role_id`) REFERENCES `roles`(`role_id`)
);

DROP TABLE IF EXISTS `jurisdiction`;
CREATE TABLE `jurisdiction` (
    `jury_id` int NOT NULL AUTO_INCREMENT,
    `area` varchar(30) NOT NULL,
    `ward` varchar(8) NOT NULL,
    `layout` varchar(35) NOT NULL,
    `registered_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`jury_id`)
);

DROP TABLE IF EXISTS `officer`;
CREATE TABLE `officer` (
    `officer_id` int NOT NULL AUTO_INCREMENT,
    `officer_name` varchar(40) NOT NULL,
    `address` varchar(255) NOT NULL,
    `phone` varchar(10) NOT NULL,
    `email` varchar(60) NOT NULL,
    `password` varchar(255) NOT NULL,
    `created_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    `jury_id` int DEFAULT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`officer_id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
    FOREIGN KEY (`jury_id`) REFERENCES `jurisdiction` (`jury_id`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `user_name` varchar(15) NOT NULL,
    `first_name` varchar(15) NOT NULL,
    `last_name` varchar(15) DEFAULT NULL,
    `phone` varchar(10) NOT NULL,
    `email` varchar(60) NOT NULL,
    `password` varchar(255) NOT NULL,
    `verified` bit(1) NOT NULL,
    `role_id` int NOT NULL,
    `registered_date` datetime NOT NULL,
    `modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
);

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
    `compl_id` INT NOT NULL AUTO_INCREMENT,
    `issue` VARCHAR(30) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `landmark` VARCHAR(500), -- Change to nullable
    `comments` VARCHAR(2000) NOT NULL,
    `response` VARCHAR(1000) DEFAULT NULL,
    `location` VARCHAR(5000), -- Add data type
    `status` ENUM('WAITING', 'OPEN', 'IN_PROGRESS', 'CLOSED') NOT NULL,
    `created_date` DATETIME NOT NULL,
    `modified_date` DATETIME DEFAULT NULL,
    `jury_id` INT NOT NULL,
    `officer_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`compl_id`),
    FOREIGN KEY (`officer_id`) REFERENCES `officer` (`officer_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
    FOREIGN KEY (`jury_id`) REFERENCES `jurisdiction` (`jury_id`)
);


DROP TABLE IF EXISTS `email_otp`;
CREATE TABLE `email_otp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `expiration_date` datetime NOT NULL,
  `otp` varchar(6) NOT NULL,
  `creation_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER'),(3,'OFFICER');

INSERT INTO `admin` VALUES (1,'Ajay','hegdeaj21@gmail.com','$2a$12$/tqS8BsdKKF1Lm8IPONEfO4MRucGNL9IzW6P.M4ipqfZLe.WDztVS','9916878237','2023-12-28 11:05:50',NULL,1);

INSERT INTO `jurisdiction` VALUES 
(1,'Basaveshwara Nagar','Ward-100','Gayathri Layout','2024-01-10 10:31:43',NULL),
(2,'Basaveshwara Nagar','Ward-100','Bhadrappa Layout','2024-01-10 10:32:21',NULL),
(3,'Basaveshwara Nagar','Ward-100','BEML Layout','2024-01-10 10:32:35',NULL),
(4,'Kamakshipalya','Ward-101','Priyadarshini Layout','2024-01-10 10:33:16',NULL),
(5,'Kamakshipalya','Ward-101','Kempegowda Layout','2024-01-10 10:34:14',NULL),
(6,'Kamakshipalya','Ward-101','BDA Layout','2024-01-10 10:34:27',NULL),
(7,'Dr Rajkumar','Ward-106','Rajkumar Layout','2024-01-10 11:02:10',NULL),
(8,'Dr Rajkumar','Ward-106','Puneeth Rajkumar Layout','2024-01-10 11:02:31',NULL),
(9,'Chickpete','Ward-109','Balepete Layout','2024-01-10 11:05:41',NULL),
(10,'Chickpete','Ward-109','Basettypet Lane','2024-01-10 11:08:15',NULL),
(11,'Domlur','Ward-112','Domlur Layout','2024-01-10 11:09:58',NULL),
(12,'Domlur','Ward-112','Eshwara Layout','2024-01-10 11:10:15',NULL),
(13,'Vijaynagar','Ward-123','Vinayaka Layout','2024-01-10 11:11:16',NULL),
(14,'Vijaynagar','Ward-123','Kaveri Layout','2024-01-10 11:11:37',NULL),
(15,'Mudalapalya','Ward-127','Shivananada Layout','2024-01-10 11:18:37',NULL),
(16,'Nagharbavi','Ward-128','Malagala','2024-01-10 11:21:04',NULL),
(17,'Nagharbavi','Ward-128','Kottigepalya','2024-01-10 11:21:17',NULL),
(18,'Ullalu','Ward-130','Upkar Layout','2024-01-10 11:24:31',NULL),
(19,'Nayandahalli','Ward-131','Krishna Layout','2024-01-10 11:26:03',NULL),
(20,'Basavanagudi','Ward-154','Gandhi Nagar','2024-01-10 11:28:50',NULL),
(21, 'Jayanagar', 'Ward-155', 'Gandhi Bazaar', '2024-01-10 11:30:00', NULL),
(22, 'Malleshwaram', 'Ward-156', 'Sampige Road', '2024-01-10 11:32:00', NULL),
(23, 'Koramangala', 'Ward-157', 'Koramangala 1st Block', '2024-01-10 11:34:00', NULL),
(24, 'Indiranagar', 'Ward-158', 'HAL 2nd Stage', '2024-01-10 11:36:00', NULL),
(25, 'Whitefield', 'Ward-159', 'Brookefield', '2024-01-10 11:38:00', NULL),
(26, 'Electronic City', 'Ward-160', 'Phase 1', '2024-01-10 11:40:00', NULL),
(27, 'BTM Layout', 'Ward-161', 'BTM 1st Stage', '2024-01-10 11:42:00', NULL),
(28, 'Marathahalli', 'Ward-162', 'Outer Ring Road', '2024-01-10 11:44:00', NULL),
(29, 'Banashankari', 'Ward-163', 'Hanumanth Nagar', '2024-01-10 11:46:00', NULL),
(30, 'Rajajinagar', 'Ward-164', 'Basaveshwaranagar', '2024-01-10 11:48:00', NULL),
(31, 'HSR Layout', 'Ward-165', 'Sector 1', '2024-01-10 11:50:00', NULL),
(32, 'JP Nagar', 'Ward-166', 'JP Nagar 5th Phase', '2024-01-10 11:52:00', NULL),
(33, 'Yelahanka', 'Ward-167', 'Yelahanka New Town', '2024-01-10 11:54:00', NULL),
(34, 'Hebbal', 'Ward-168', 'Hebbal Kempapura', '2024-01-10 11:56:00', NULL),
(35, 'KR Puram', 'Ward-169', 'Dooravani Nagar', '2024-01-10 11:58:00', NULL),
(36, 'Bannerghatta', 'Ward-170', 'Bilekahalli', '2024-01-10 12:00:00', NULL),
(37, 'Jayamahal', 'Ward-171', 'Cottonpete', '2024-01-10 12:02:00', NULL),
(38, 'Jeevan Bima Nagar', 'Ward-172', 'Kaggadasapura', '2024-01-10 12:04:00', NULL),
(39, 'Shivajinagar', 'Ward-173', 'Russell Market', '2024-01-10 12:06:00', NULL),
(40, 'Cox Town', 'Ward-174', 'Frazer Town', '2024-01-10 12:08:00', NULL);

                  
INSERT INTO `officer` VALUES (1,'Ajay','Kengeri','9916878237','ajayhegde9886562623@gmail.com','$2a$12$/tqS8BsdKKF1Lm8IPONEfO4MRucGNL9IzW6P.M4ipqfZLe.WDztVS','2024-01-12 14:50:49',NULL,1,3),
			     (2,'Krithic ','BDA Layout','8227103847','krithic@gmail.com','$2a$12$lnACL4UAisRVpwgxW7221uVMd/tcqFDOnA2fx0jtj.vbLKw7z.rGm','2024-01-12 14:54:31',NULL,2,3),
			     (3,'Gagana C M','Hesaraghatta','9535925188','gaganacm2001@gmail.com','$2a$10$TW9q.7M349u5afRtv70XzO3z6PRSJcNF5HlaRCeBH.oYEH7rWp9mS','2024-01-12 14:55:07',NULL,3,3);
			     
                 
INSERT INTO `user` VALUES (1,'ajayhhegde21','Ajay','Hegde','9886562623','ajayhhegde21@gmail.com','$2a$12$trNoVvGSaPJdpYg3KiuWruHA9At93pG4kPR15K8h/5MrY9rxlDld6',_binary '\0',2,'2024-01-11 12:31:22',NULL),
			  (2,'krithic2001','Krithic','P','8072504697','krithic10@gmail.com','$2a$12$J2eyMlFdg82r8ndLuPYiyO9VAEWp/XbauEA820i9GaXGkF24qILo.',_binary '\0',2,'2024-01-11 15:08:03',NULL);
              

INSERT INTO `complaint` VALUES 
(1,'Water Scarcity','3rd Cross Subhash Nagar ','Near Hanuman Temple','Kaveri water not available from past 3-4 days.',NULL,"https://www.google.com/maps?q=12.931420989004803,7",'WAITING','2024-01-15 19:35:43',NULL,2,2,1),
(2,'Noise Pollution','14th Cross, Colony road ','Near Krishna cafe','Severe traffic from past few days.',NULL,"https://www.google.com/maps?q=12.931420989004803,7",'WAITING','2024-01-15 19:39:57',NULL,2,2,1),
(3,'Air Quality','3rd Cross, 4th main road Lalith nagar','Near Euro Kids playhome','Pollution has taken over the school.',NULL,"https://www.google.com/maps?q=12.931420989004803,7",'WAITING','2024-01-15 19:41:44',NULL,1,2,1),
(4,'Street Lighting','5th Main road, 1st Main road ','Near Ashoka Circle','The lights are down from past few days during nights and they flicker.',NULL,"https://www.google.com/maps?q=12.931420989004803,7",'WAITING','2024-01-15 19:43:44',NULL,1,2,1)
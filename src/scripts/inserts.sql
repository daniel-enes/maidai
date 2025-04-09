INSERT INTO `roles` VALUES (1,'administrador'),(2,'colaborador');

INSERT INTO `tipos_pessoa` VALUES (2,'bolsista'),(1,'orientador');

INSERT INTO `users` VALUES (1,'admin','$2a$12$Ft5sNY69V4hCkxZbfqz6ZOHCVFMbDrNtz1CT3SezCOnoP9Cqd5lGm','admin',_binary '','2025-03-21 14:01:45.785000','2025-03-27 16:26:40.895000'),(2,'colaborator','$2a$12$MuBN9tvDJfAsvQaebCEjfeYvplptlPriPthD25r4j.7i8LV9xGrj6','colaborator',_binary '','2025-03-27 15:33:21.285000','2025-03-27 17:28:05.988000');

INSERT INTO `users_roles` VALUES (1,1),(2,2);

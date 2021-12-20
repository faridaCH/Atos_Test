--
-- Structure de la table `user`
--
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      PRIMARY KEY (`id`),
                                      `name` varchar(50)  NOT NULL,
                                      `birthdate` date NOT NULL,
                                      `country` varchar(25)  NOT NULL,
                                      `phone_number` varchar(25)  DEFAULT NULL,
                                      `gender` enum('Man','Woman','Other')  DEFAULT NULL,
                                      `email` varchar(25)  NOT NULL,
                                      `Password` varchar(25)  NOT NULL

) ;
--
-- Chargement des données de la table `user`
--
INSERT INTO `user` (`id`, `name`, `birthdate`, `country`, `phone_number`, `gender`, `email`, `Password`) VALUES
                                                                                                             (1, 'Maria Dupont', '2000-12-12', 'France', '0987654321', 'Man', 'jean.paul@atos.fr', '1234'),
                                                                                                             (3, 'carine Dupont', '2003-09-10', 'France', '0987654321', 'Woman', 'carine.dupont@atos.fr', '1234'),
                                                                                                            (4, 'jad Dupont', '2000-09-10', 'France', '0987654321', 'Man', 'jad.dupont@atos.fr', '1234'),
                                                                                                             (6, 'M', '2000-12-12', 'France', '0987654321', 'Man', 'jean.paul@atos.fr', '1234');
//COMMIT;

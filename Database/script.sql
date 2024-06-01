CREATE DATABASE IF NOT EXISTS PokemonDB;

USE PokemonDB;

# DROP TABLE IF EXISTS user, pokemon, box, listing, bid;

CREATE TABLE IF NOT EXISTS user (
                                    username varchar(32) NOT NULL,
                                    password text NOT NULL,
                                    name varchar(16) NOT NULL,
                                    surname varchar(16) NOT NULL,
                                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS pokemon (
                                       ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       pokemonName varchar(16) NOT NULL,
                                       imageUrl varchar(1000) NOT NULL,
                                       primaryType varchar(16) NOT NULL,
                                       secondaryType varchar(16) NOT NULL,
                                       attack int NOT NULL,
                                       defense int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS box (
                                   ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                   username varchar(32) NOT NULL,
                                   pokemonID int NOT NULL,
                                   listed BOOLEAN DEFAULT FALSE,
                                   FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
                                   FOREIGN KEY (pokemonID) REFERENCES pokemon(ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS listing (
                                       ID INT PRIMARY KEY AUTO_INCREMENT,
                                       boxID int NOT NULL,
                                       winner varchar(32) NULL,
                                       timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       FOREIGN KEY (boxID) REFERENCES box(ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS offer (
                                     ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     listingID int NOT NULL,
                                     trader varchar(32) NOT NULL,
                                     boxID int NOT NULL,
                                     timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY (trader) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (boxID) REFERENCES box(ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

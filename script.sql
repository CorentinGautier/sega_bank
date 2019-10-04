CREATE TABLE agences
(
    id INT PRIMARY KEY NOT NULL auto_increment,
    code INT NOT NULL UNIQUE,
    adresse VARCHAR(255)
);

CREATE TABLE comptes
(
    id INT PRIMARY KEY NOT NULL auto_increment,
    solde FLOAT NOT NULL,
    agence INT NOT NULL,
    type INT NOT NULL,
    CONSTRAINT fk_code_agence
        FOREIGN KEY (agence)
        REFERENCES agence(code)
);

CREATE TABLE operations
(
    id INT PRIMARY KEY NOT NULL auto_increment,
    agence INT NOT NULL,
    compte INT NOT NULL,
    type INT NOT NULL,
    montant FLOAT NOT NULL,
    date_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_code_agence
        FOREIGN KEY (agence)
        REFERENCES agence(code),
    CONSTRAINT fk_id_compte
        FOREIGN KEY (compte)
        REFERENCES compte(id)
);

INSERT INTO agences (code, adresse)
 VALUES
 (44001, 'Nantes centre'),
 (44002, 'Nantes Rezé'),
 (44003, 'Guerande'),
 (75001, 'Paris');

 INSERT INTO comptes (solde, agence, type)
 VALUES
 (0.00, 44001, 1),
 (1000.00, 44001, 3),
 (-10.00, 44002, 1),
 (7.00, 44003, 3),
 (9000.50, 75001, 2),
 (100.00, 75001, 1),
 (50000.00, 750011, 2);

 INSERT INTO operations (agence, compte, type, montant, date_action)
 VALUES
 (44001, 1, 1, -20.00, CURRENT_TIMESTAMP),
 (75001, 7),
 (44002, 5),
 (44003, 6),
 (75001, 4),
 (75001, 3),
 (75001, 2);
I

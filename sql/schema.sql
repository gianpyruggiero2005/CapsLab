CREATE DATABASE IF NOT EXISTS capslab;
USE capslab;

CREATE TABLE utente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    ruolo ENUM('cliente', 'admin') DEFAULT 'cliente',
    data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione VARCHAR(255)
);

CREATE TABLE prodotto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descrizione TEXT,
    prezzo DECIMAL(10,2) NOT NULL,
    iva DECIMAL(4,2) NOT NULL DEFAULT 22.00,
    quantita_disponibile INT NOT NULL DEFAULT 0,
    immagine VARCHAR(255),
    categoria_id INT,
    layout_tastiera VARCHAR(50),
    tipo_switch VARCHAR(100),
    connessione VARCHAR(50),
    materiale_keycaps VARCHAR(100),
    retroilluminazione VARCHAR(50),
    attivo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE ordine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    utente_id INT NOT NULL,
    data_ordine TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totale DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (utente_id) REFERENCES utente(id)
);

CREATE TABLE riga_ordine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ordine_id INT NOT NULL,
    prodotto_id INT,
    nome_prodotto VARCHAR(200) NOT NULL,
    prezzo_unitario DECIMAL(10,2) NOT NULL,
    iva DECIMAL(4,2) NOT NULL,
    quantita INT NOT NULL,
    FOREIGN KEY (ordine_id) REFERENCES ordine(id),
    FOREIGN KEY (prodotto_id) REFERENCES prodotto(id) ON DELETE SET NULL
);

-- Admin di default (password: admin123)
INSERT INTO utente (nome, cognome, email, password_hash, ruolo)
VALUES ('Admin', 'CapsLab', 'admin@capslab.it', '$2a$12$5J5mEP2lxZgU.3C3Z4Yuz.CqXwc6N0O299kDOMFqhjqowTWvAgMXS', 'admin');

-- Categorie
INSERT INTO categoria (nome, descrizione) VALUES
('Tastiere Meccaniche', 'Tastiere con switch meccanici per typing e gaming'),
('Keycaps', 'Set di keycaps personalizzati in vari materiali e profili'),
('Switch', 'Switch meccanici sfusi per custom build'),
('Deskmat', 'Tappetini da scrivania per completare il setup'),
('Accessori', 'Cavi custom, wrist rest, tool per modding');

-- Prodotti di esempio
INSERT INTO prodotto (nome, descrizione, prezzo, iva, quantita_disponibile, immagine, categoria_id, layout_tastiera, tipo_switch, connessione, materiale_keycaps, retroilluminazione) VALUES
('Nova65 Midnight', 'Tastiera meccanica 65% con case in alluminio anodizzato nero, gasket mount, hot-swap. Ideale per un setup minimal e pulito.', 189.99, 22.00, 25, 'nova65.jpg', 1, '65%', 'Gateron Pro Yellow', 'USB-C / Bluetooth 5.0', 'PBT Double-shot', 'RGB per-key'),
('Phantom TKL', 'Tastiera TKL (tenkeyless) con plate in ottone, foam dampening e suono thock profondo. Design sobrio, performance premium.', 259.99, 22.00, 15, 'phantom-tkl.jpg', 1, 'TKL (80%)', 'Cherry MX Brown', 'USB-C', 'PBT Dye-sub', 'White backlit'),
('Drift75 Wireless', 'Tastiera 75% wireless con knob rotativo, batteria 4000mAh e tripla connessione. Perfetta per il desk setup senza cavi.', 219.99, 22.00, 30, 'drift75.jpg', 1, '75%', 'Kailh Box White', 'USB-C / BT / 2.4GHz', 'ABS Double-shot', 'RGB per-key'),
('Keycap Set Tokyo Nights', 'Set 138 keycaps in PBT cherry profile, tema ispirato alle notti di Tokyo. Compatibile con layout 60%-100%.', 59.99, 22.00, 80, 'tokyo-nights.jpg', 2, NULL, NULL, NULL, 'PBT Dye-sub Cherry Profile', NULL),
('Keycap Set Botanical', 'Set 160 keycaps in PBT con tema botanico verde/crema. Profilo KAT, leggende centrate.', 69.99, 22.00, 50, 'botanical.jpg', 2, NULL, NULL, NULL, 'PBT KAT Profile', NULL),
('Gateron Oil King (70pz)', 'Switch lineari pre-lubrificati con stem in nylon e molla 55g. Smooth e silenziosi, perfetti per chi cerca fluidità.', 34.99, 22.00, 120, 'oil-king.jpg', 3, NULL, 'Lineare 55g', NULL, NULL, NULL),
('Boba U4T (70pz)', 'Switch tattili con bump pronunciato e suono thock. Housing in nylon, no pre-travel. Il preferito della community.', 39.99, 22.00, 90, 'boba-u4t.jpg', 3, NULL, 'Tattile 62g', NULL, NULL, NULL),
('Deskmat Minimal Wave', 'Tappetino XL 900x400mm in tessuto microfiber con base antiscivolo. Design wave minimalista bianco/grigio.', 29.99, 22.00, 60, 'deskmat-wave.jpg', 4, NULL, NULL, NULL, NULL, NULL),
('Cavo Coiled Aviator Lavender', 'Cavo USB-C coiled artigianale con connettore aviator GX16. Guaina in paracord lavanda, spirale 15cm.', 44.99, 22.00, 40, 'cavo-lavender.jpg', 5, NULL, NULL, 'USB-C', NULL, NULL),
('Wrist Rest in Legno di Noce', 'Poggia polsi artigianale in legno di noce massello, compatibile con tastiere 65% e TKL. Finitura opaca naturale.', 39.99, 22.00, 35, 'wrist-rest.jpg', 5, NULL, NULL, NULL, NULL, NULL);

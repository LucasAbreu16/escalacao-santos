-- ==========================

-- Copa do Mundo 2026 — Escalações

-- ==========================



CREATE TABLE IF NOT EXISTS usuario (

    id SERIAL PRIMARY KEY,

    username VARCHAR(30) NOT NULL,

    senha VARCHAR(30) NOT NULL,

    enabled BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uq_usuario_username UNIQUE (username)

);



CREATE TABLE IF NOT EXISTS jogador (

    id SERIAL PRIMARY KEY,

    nome VARCHAR(60) NOT NULL,

    posicao VARCHAR(20) NOT NULL,

    selecao VARCHAR(20) NOT NULL,

    CONSTRAINT uq_jogador_nome_selecao UNIQUE (nome, selecao),

    CONSTRAINT ck_jogador_posicao CHECK (posicao IN ('GOLEIRO','LATERAL','ZAGUEIRO','MEIO_CAMPISTA','ATACANTE')),

    CONSTRAINT ck_jogador_selecao CHECK (selecao IN ('BRASIL','ESPANHA','FRANCA','PORTUGAL','ARGENTINA','INGLATERRA'))

);



CREATE TABLE IF NOT EXISTS escalacao (

    id SERIAL PRIMARY KEY,

    usuario_id INT NOT NULL,

    nome VARCHAR(60) NOT NULL,

    formacao VARCHAR(10) NOT NULL,

    selecao VARCHAR(20) NOT NULL,

    CONSTRAINT ck_escalacao_formacao CHECK (formacao IN ('4-4-2','4-3-3','4-5-1')),

    CONSTRAINT ck_escalacao_selecao CHECK (selecao IN ('BRASIL','ESPANHA','FRANCA','PORTUGAL','ARGENTINA','INGLATERRA')),

    CONSTRAINT fk_escalacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE

);



CREATE TABLE IF NOT EXISTS escalacao_jogador (

    escalacao_id INT NOT NULL,

    jogador_id INT NOT NULL,

    PRIMARY KEY (escalacao_id, jogador_id),

    CONSTRAINT fk_escalacao_jogador_escalacao FOREIGN KEY (escalacao_id) REFERENCES escalacao(id) ON DELETE CASCADE,

    CONSTRAINT fk_escalacao_jogador_jogador FOREIGN KEY (jogador_id) REFERENCES jogador(id)

);


INSERT INTO jogador (nome, posicao, selecao) VALUES

-- BRASIL
('Alisson', 'GOLEIRO', 'BRASIL'),
('Ederson', 'GOLEIRO', 'BRASIL'),
('Weverton', 'GOLEIRO', 'BRASIL'),
('Marquinhos', 'ZAGUEIRO', 'BRASIL'),
('Gabriel Magalhães', 'ZAGUEIRO', 'BRASIL'),
('Bremer', 'ZAGUEIRO', 'BRASIL'),
('Léo Pereira', 'ZAGUEIRO', 'BRASIL'),
('Ibanez', 'LATERAL', 'BRASIL'),
('Danilo', 'LATERAL', 'BRASIL'),
('Alex Sandro', 'LATERAL', 'BRASIL'),
('Douglas Santos', 'LATERAL', 'BRASIL'),
('Éderson', 'MEIO_CAMPISTA', 'BRASIL'),
('Casemiro', 'MEIO_CAMPISTA', 'BRASIL'),
('Bruno Guimarães', 'MEIO_CAMPISTA', 'BRASIL'),
('Fabinho', 'MEIO_CAMPISTA', 'BRASIL'),
('Danilo Santos', 'MEIO_CAMPISTA', 'BRASIL'),
('Lucas Paquetá', 'MEIO_CAMPISTA', 'BRASIL'),
('Vinicius Junior', 'ATACANTE', 'BRASIL'),
('Raphinha', 'ATACANTE', 'BRASIL'),
('Matheus Cunha', 'ATACANTE', 'BRASIL'),
('Luiz Henrique', 'ATACANTE', 'BRASIL'),
('Igor Thiago', 'ATACANTE', 'BRASIL'),
('Endrick', 'ATACANTE', 'BRASIL'),
('Gabriel Martinelli', 'ATACANTE', 'BRASIL'),
('Rayan', 'ATACANTE', 'BRASIL'),
('Neymar', 'MEIO_CAMPISTA', 'BRASIL'),

-- ESPANHA
('Unai Simon', 'GOLEIRO', 'ESPANHA'),
('David Raya', 'GOLEIRO', 'ESPANHA'),
('Joan Garcia', 'GOLEIRO', 'ESPANHA'),
('Aymeric Laporte', 'ZAGUEIRO', 'ESPANHA'),
('Pau Cubarsi', 'ZAGUEIRO', 'ESPANHA'),
('Eric Garcia', 'ZAGUEIRO', 'ESPANHA'),
('Marc Cucurella', 'LATERAL', 'ESPANHA'),
('Pedro Porro', 'LATERAL', 'ESPANHA'),
('Alex Grimaldo', 'LATERAL', 'ESPANHA'),
('Marc Pubill', 'LATERAL', 'ESPANHA'),
('Marcos Llorente', 'LATERAL', 'ESPANHA'),
('Rodri', 'MEIO_CAMPISTA', 'ESPANHA'),
('Fabian Ruiz', 'MEIO_CAMPISTA', 'ESPANHA'),
('Pedri', 'MEIO_CAMPISTA', 'ESPANHA'),
('Gavi', 'MEIO_CAMPISTA', 'ESPANHA'),
('Mikel Merino', 'MEIO_CAMPISTA', 'ESPANHA'),
('Martin Zubimendi', 'MEIO_CAMPISTA', 'ESPANHA'),
('Alex Baena', 'MEIO_CAMPISTA', 'ESPANHA'),
('Lamine Yamal', 'ATACANTE', 'ESPANHA'),
('Nico Williams', 'ATACANTE', 'ESPANHA'),
('Dani Olmo', 'ATACANTE', 'ESPANHA'),
('Ferran Torres', 'ATACANTE', 'ESPANHA'),
('Mikel Oyarzabal', 'ATACANTE', 'ESPANHA'),
('Yeremy Pino', 'ATACANTE', 'ESPANHA'),
('Borja Iglesias', 'ATACANTE', 'ESPANHA'),
('Victor Munoz', 'ATACANTE', 'ESPANHA'),

-- FRANÇA
('Mike Maignan', 'GOLEIRO', 'FRANCA'),
('Brice Samba', 'GOLEIRO', 'FRANCA'),
('Lucas Chevalier', 'GOLEIRO', 'FRANCA'),
('William Saliba', 'ZAGUEIRO', 'FRANCA'),
('Ibrahima Konate', 'ZAGUEIRO', 'FRANCA'),
('Dayot Upamecano', 'ZAGUEIRO', 'FRANCA'),
('Jules Kounde', 'LATERAL', 'FRANCA'),
('Theo Hernandez', 'LATERAL', 'FRANCA'),
('Ferland Mendy', 'LATERAL', 'FRANCA'),
('Benjamin Pavard', 'LATERAL', 'FRANCA'),
('Eduardo Camavinga', 'MEIO_CAMPISTA', 'FRANCA'),
('Aurelien Tchouameni', 'MEIO_CAMPISTA', 'FRANCA'),
('Adrien Rabiot', 'MEIO_CAMPISTA', 'FRANCA'),
('Warren Zaire-Emery', 'MEIO_CAMPISTA', 'FRANCA'),
('Youssouf Fofana', 'MEIO_CAMPISTA', 'FRANCA'),
('Kylian Mbappe', 'ATACANTE', 'FRANCA'),
('Ousmane Dembele', 'ATACANTE', 'FRANCA'),
('Marcus Thuram', 'ATACANTE', 'FRANCA'),
('Randal Kolo Muani', 'ATACANTE', 'FRANCA'),
('Bradley Barcola', 'ATACANTE', 'FRANCA'),
('Kingsley Coman', 'ATACANTE', 'FRANCA'),
('Michael Olise', 'ATACANTE', 'FRANCA'),
('Desire Doue', 'ATACANTE', 'FRANCA'),
('Christopher Nkunku', 'ATACANTE', 'FRANCA'),
('Mathys Tel', 'ATACANTE', 'FRANCA'),
('Olivier Giroud', 'ATACANTE', 'FRANCA'),

-- PORTUGAL
('Diogo Costa', 'GOLEIRO', 'PORTUGAL'),
('Jose Sa', 'GOLEIRO', 'PORTUGAL'),
('Rui Silva', 'GOLEIRO', 'PORTUGAL'),
('Ricardo Velho', 'GOLEIRO', 'PORTUGAL'),
('Ruben Dias', 'ZAGUEIRO', 'PORTUGAL'),
('Goncalo Inacio', 'ZAGUEIRO', 'PORTUGAL'),
('Renato Veiga', 'ZAGUEIRO', 'PORTUGAL'),
('Tomas Araujo', 'ZAGUEIRO', 'PORTUGAL'),
('Diogo Dalot', 'LATERAL', 'PORTUGAL'),
('Joao Cancelo', 'LATERAL', 'PORTUGAL'),
('Nuno Mendes', 'LATERAL', 'PORTUGAL'),
('Nelson Semedo', 'LATERAL', 'PORTUGAL'),
('Matheus Nunes', 'LATERAL', 'PORTUGAL'),
('Bruno Fernandes', 'MEIO_CAMPISTA', 'PORTUGAL'),
('Bernardo Silva', 'MEIO_CAMPISTA', 'PORTUGAL'),
('Vitinha', 'MEIO_CAMPISTA', 'PORTUGAL'),
('Joao Neves', 'MEIO_CAMPISTA', 'PORTUGAL'),
('Ruben Neves', 'MEIO_CAMPISTA', 'PORTUGAL'),
('Samuel Costa', 'MEIO_CAMPISTA', 'PORTUGAL'),
('Cristiano Ronaldo', 'ATACANTE', 'PORTUGAL'),
('Rafael Leao', 'ATACANTE', 'PORTUGAL'),
('Pedro Neto', 'ATACANTE', 'PORTUGAL'),
('Joao Felix', 'ATACANTE', 'PORTUGAL'),
('Francisco Conceicao', 'ATACANTE', 'PORTUGAL'),
('Francisco Trincao', 'ATACANTE', 'PORTUGAL'),
('Goncalo Ramos', 'ATACANTE', 'PORTUGAL'),
('Goncalo Guedes', 'ATACANTE', 'PORTUGAL'),

-- ARGENTINA
('Emiliano Martinez', 'GOLEIRO', 'ARGENTINA'),
('Juan Musso', 'GOLEIRO', 'ARGENTINA'),
('Geronimo Rulli', 'GOLEIRO', 'ARGENTINA'),
('Cristian Romero', 'ZAGUEIRO', 'ARGENTINA'),
('Nicolas Otamendi', 'ZAGUEIRO', 'ARGENTINA'),
('Lisandro Martinez', 'ZAGUEIRO', 'ARGENTINA'),
('Leonardo Balerdi', 'ZAGUEIRO', 'ARGENTINA'),
('Facundo Medina', 'ZAGUEIRO', 'ARGENTINA'),
('Nahuel Molina', 'LATERAL', 'ARGENTINA'),
('Gonzalo Montiel', 'LATERAL', 'ARGENTINA'),
('Nicolas Tagliafico', 'LATERAL', 'ARGENTINA'),
('Rodrigo De Paul', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Enzo Fernandez', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Alexis Mac Allister', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Giovani Lo Celso', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Exequiel Palacios', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Leandro Paredes', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Valentin Barco', 'MEIO_CAMPISTA', 'ARGENTINA'),
('Lionel Messi', 'ATACANTE', 'ARGENTINA'),
('Lautaro Martinez', 'ATACANTE', 'ARGENTINA'),
('Julian Alvarez', 'ATACANTE', 'ARGENTINA'),
('Thiago Almada', 'ATACANTE', 'ARGENTINA'),
('Nicolas Gonzalez', 'ATACANTE', 'ARGENTINA'),
('Nicolas Paz', 'ATACANTE', 'ARGENTINA'),
('Giuliano Simeone', 'ATACANTE', 'ARGENTINA'),
('Jose Manuel Lopez', 'ATACANTE', 'ARGENTINA'),

-- INGLATERRA
('Dean Henderson', 'GOLEIRO', 'INGLATERRA'),
('Jordan Pickford', 'GOLEIRO', 'INGLATERRA'),
('James Trafford', 'GOLEIRO', 'INGLATERRA'),
('Dan Burn', 'ZAGUEIRO', 'INGLATERRA'),
('Marc Guehi', 'ZAGUEIRO', 'INGLATERRA'),
('Ezri Konsa', 'ZAGUEIRO', 'INGLATERRA'),
('Jarell Quansah', 'ZAGUEIRO', 'INGLATERRA'),
('John Stones', 'ZAGUEIRO', 'INGLATERRA'),
('Reece James', 'LATERAL', 'INGLATERRA'),
('Tino Livramento', 'LATERAL', 'INGLATERRA'),
('Nico O''Reilly', 'LATERAL', 'INGLATERRA'),
('Djed Spence', 'LATERAL', 'INGLATERRA'),
('Elliott Anderson', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Jude Bellingham', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Eberechi Eze', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Jordan Henderson', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Kobbie Mainoo', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Declan Rice', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Morgan Rogers', 'MEIO_CAMPISTA', 'INGLATERRA'),
('Anthony Gordon', 'ATACANTE', 'INGLATERRA'),
('Harry Kane', 'ATACANTE', 'INGLATERRA'),
('Noni Madueke', 'ATACANTE', 'INGLATERRA'),
('Marcus Rashford', 'ATACANTE', 'INGLATERRA'),
('Bukayo Saka', 'ATACANTE', 'INGLATERRA'),
('Ivan Toney', 'ATACANTE', 'INGLATERRA'),
('Ollie Watkins', 'ATACANTE', 'INGLATERRA')
ON CONFLICT (nome, selecao) DO NOTHING;

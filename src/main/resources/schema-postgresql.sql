-- ==========================
-- Seleção Santástica 2010/2011
-- ==========================

CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_usuario_username UNIQUE (username)
    --Garante que o username seja único
);

CREATE TABLE IF NOT EXISTS jogador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    posicao VARCHAR(20) NOT NULL,
    foto VARCHAR(255),
    CONSTRAINT uq_jogador_nome UNIQUE (nome),
    CONSTRAINT ck_jogador_posicao CHECK (posicao IN ('GOLEIRO','LATERAL','ZAGUEIRO','MEIO_CAMPISTA','ATACANTE'))
    --Garante que o nome seja único e que a posição seja válida
);

CREATE TABLE IF NOT EXISTS escalacao (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    nome VARCHAR(60) NOT NULL,
    formacao VARCHAR(10) NOT NULL,
    CONSTRAINT ck_escalacao_formacao CHECK (formacao IN ('4-4-2','4-3-3','4-5-1')),
    CONSTRAINT fk_escalacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
    --Garante que a escalação seja válida e associada a um usuário
);

CREATE TABLE IF NOT EXISTS escalacao_jogador (
    escalacao_id INT NOT NULL,
    jogador_id INT NOT NULL,
    PRIMARY KEY (escalacao_id, jogador_id),
    CONSTRAINT fk_escalacao_jogador_escalacao FOREIGN KEY (escalacao_id) REFERENCES escalacao(id) ON DELETE CASCADE,
    CONSTRAINT fk_escalacao_jogador_jogador FOREIGN KEY (jogador_id) REFERENCES jogador(id)
    --Relaciona as escalacoes com os jogadores
);


INSERT INTO jogador (nome, posicao, foto) VALUES
('Rafael Cabral','GOLEIRO','/img/jogadores/rafael-cabral.jfif'),
('Vladimir','GOLEIRO','/img/jogadores/vladimir.jfif'),
('Aranha','GOLEIRO','/img/jogadores/aranha.jfif'),

('Danilo','LATERAL','/img/jogadores/danilo.jfif'),
('Léo','LATERAL','/img/jogadores/leo.jfif'),
('Jonathan','LATERAL','/img/jogadores/jonathan.jfif'),
('Pará','LATERAL','/img/jogadores/para.jfif'),
('Alex Sandro','LATERAL','/img/jogadores/alex-sandro.jfif'),
('Juan','LATERAL','/img/jogadores/juan.jfif'),

('Edu Dracena','ZAGUEIRO','/img/jogadores/edu-dracena.jfif'),
('Durval','ZAGUEIRO','/img/jogadores/durval.jfif'),
('Bruno Rodrigo','ZAGUEIRO','/img/jogadores/bruno-rodrigo.jfif'),
('Bruno Aguiar','ZAGUEIRO','/img/jogadores/bruno-aguiar.jfif'),

('Arouca','MEIO_CAMPISTA','/img/jogadores/arouca.jfif'),
('Paulo Henrique Ganso','MEIO_CAMPISTA','/img/jogadores/ganso.jfif'),
('Elano','MEIO_CAMPISTA','/img/jogadores/elano.jfif'),
('Adriano Pagode','MEIO_CAMPISTA','/img/jogadores/adriano-pagode.jfif'),
('Felipe Anderson','MEIO_CAMPISTA','/img/jogadores/felipe-anderson.jfif'),
('Alan Patrick','MEIO_CAMPISTA','/img/jogadores/alan-patrick.jfif'),
('Ibson','MEIO_CAMPISTA','/img/jogadores/ibson.jfif'),
('Marquinhos','MEIO_CAMPISTA','/img/jogadores/marquinhos.jfif'),

('Neymar','ATACANTE','/img/jogadores/neymar.jfif'),
('Robinho','ATACANTE','/img/jogadores/robinho.jfif'),
('Madson','ATACANTE','/img/jogadores/madson.jfif'),
('Borges','ATACANTE','/img/jogadores/borges.jfif'),
('Zé Love','ATACANTE','/img/jogadores/ze-love.jfif'),
('Keirrison','ATACANTE','/img/jogadores/keirrison.jfif'),
('Alan Kardec','ATACANTE','/img/jogadores/alan-kardec.jfif'),
('Maikon Leite','ATACANTE','/img/jogadores/maikon-leite.jfif'),
('Rentería','ATACANTE','/img/jogadores/renteria.jfif')
ON CONFLICT (nome) DO NOTHING;
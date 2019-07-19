CREATE TABLE categorias (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categorias (nome) values ('Lazer');
INSERT INTO categorias (nome) values ('Alimentação');
INSERT INTO categorias (nome) values ('Supermercado');
INSERT INTO categorias (nome) values ('Farmácia');
INSERT INTO categorias (nome) values ('Outros');
CREATE TABLE usuarios (
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE permissoes (
	id BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE usuarios_permissoes (
	usuario_id BIGINT(20) NOT NULL,
	permissao_id BIGINT(20) NOT NULL,
	PRIMARY KEY (usuario_id, permissao_id),
	FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
	FOREIGN KEY (permissao_id) REFERENCES permissoes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuarios (id, nome, email, senha) values (1, 'Administrador', 'admin@eskinfotechweb.com', '$2a$10$BHfWFRZ8Wg6ZAxxap0CZTuEk2ornYk9zFbWFuMKHuxj.NpV/Ol3GO');
INSERT INTO usuarios (id, nome, email, senha) values (2, 'Maria Silva', 'maria@eskinfotechweb.com', '$2a$10$YUdKx5MnG1FJBTLCCpULk.dLsGGUiKsEG9.rm9u0uUm59Ng8Lq.Fm');

INSERT INTO permissoes (id, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissoes (id, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissoes (id, descricao) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissoes (id, descricao) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissoes (id, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permissoes (id, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 1);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 2);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 3);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 4);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 5);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 6);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 7);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (1, 8);

-- maria
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (2, 2);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (2, 5);
INSERT INTO usuarios_permissoes (usuario_id, permissao_id) values (2, 8);
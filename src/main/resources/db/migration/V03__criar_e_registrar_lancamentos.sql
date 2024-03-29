CREATE TABLE lancamentos (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo INTEGER(11) NOT NULL,
	categoria_id BIGINT(20) NOT NULL,
	pessoa_id BIGINT(20) NOT NULL,
	FOREIGN KEY (categoria_id) REFERENCES categorias(id),
	FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 1, 1, 1);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 2, 2, 2);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Top Club', '2017-06-10', null, 120, null, 1, 3, 3);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 1, 3, 4);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('DMAE', '2017-06-10', null, 200.30, null, 2, 3, 5);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 1, 4, 6);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Bahamas', '2017-06-10', null, 500, null, 1, 1, 7);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 2, 4, 8);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 2, 3, 9);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 1, 5, 10);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Café', '2017-06-10', null, 8.32, null, 2, 1, 5);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 2, 5, 4);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 2, 4, 3);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 2, 4, 2);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, categoria_id, pessoa_id) values ('Lanche', '2017-06-10', null, 10.20, null, 2, 4, 1); 

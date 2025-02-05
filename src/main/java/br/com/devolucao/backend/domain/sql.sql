CREATE TABLE safra (
    NM_MARCA VARCHAR(255),
    CD_OPERADORA INT,
    NR_CONTRATO INT,
    NM_TIPO_DESCONEXAO VARCHAR(255),
    NM_MUNICIPIO VARCHAR(255),
    DT_CADASTRO_PENDENTE TIMESTAMP,
    NR_MAC VARCHAR(255),
    NR_SERIAL VARCHAR(255),
    NM_EQUIPAMENTO_MODELO VARCHAR(255),
    NM_EQUIPAMENTO_TIPO VARCHAR(255)
);

CREATE TABLE equipamentos_descricao (
    CIDADE VARCHAR(255),
    CD_OPERADORA INT,
    NUM_CONTRATO INT,
    COD_OS VARCHAR(255),
    DATA_ABERTURA_OS TIMESTAMP,
    TIPO_OS VARCHAR(255),
    STATUS_OS VARCHAR(255),
    IMEDIATA VARCHAR(255),
    DT_AGENDAMENTO TIMESTAMP,
    PERIODO VARCHAR(255),
    STATUS_CONTRATO VARCHAR(255),
    STATUS_EBT VARCHAR(255),
    PRODUTO_PRINCIPAL VARCHAR(255),
    PRODUTO_VIRTUA VARCHAR(255),
    PRODUTO_VOIP VARCHAR(255),
    COD_ENDERECAVEL VARCHAR(255),
    NRO_SERIE VARCHAR(255),
    NM_MODELO VARCHAR(255),
    NM_TIPO_EQUIPAMENTO VARCHAR(255)
);

CREATE TABLE tb_devolucao_aparelho (
    id SERIAL PRIMARY KEY,
    codigo_aparelho VARCHAR(255) NOT NULL,
    aprovado BOOLEAN NOT NULL,
    data_devolucao TIMESTAMP NOT NULL,
    chamado_id BIGINT NOT NULL,
    FOREIGN KEY (chamado_id) REFERENCES tb_chamado(id)
);

CREATE TABLE public.tb_chamado (
	id bigserial NOT NULL,
	data_agendamento timestamp(6) NULL,
	estabelecimento_id int8 NULL,
	situacao int4 NULL,
	user_id int8 NULL,
	CONSTRAINT tb_chamado_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tb_chamado ADD CONSTRAINT fkgk6cte3fcoknt893dg10or5br FOREIGN KEY (estabelecimento_id) REFERENCES public.tb_estabelecimento(id);
ALTER TABLE public.tb_chamado ADD CONSTRAINT fkm5p4a0m9kx1uer54x60nw7otp FOREIGN KEY (user_id) REFERENCES public.users(id);

CREATE TABLE public.tb_chamado_imagem (
	id bigserial NOT NULL,
	image_base64 text NULL,
	chamado_id int8 NULL,
	CONSTRAINT tb_chamado_imagem_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tb_chamado_imagem ADD CONSTRAINT fk7nye77a302i50snrmwioimaj4 FOREIGN KEY (chamado_id) REFERENCES public.tb_chamado(id);


CREATE TABLE public.tb_estabelecimento (
	id bigserial NOT NULL,
	bairro varchar(255) NULL,
	cep varchar(255) NULL,
	complemento varchar(255) NULL,
	identificacao varchar(255) NULL,
	logradouro varchar(255) NULL,
	municipio varchar(255) NULL,
	numero varchar(255) NULL,
	uf varchar(255) NULL,
	CONSTRAINT tb_estabelecimento_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users (
	id bigserial NOT NULL,
	login varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	"role" int2 NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
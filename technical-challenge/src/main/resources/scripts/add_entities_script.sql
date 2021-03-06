-- Table: public.account

-- DROP TABLE public.account;

CREATE TABLE public.account
(
    id bigserial NOT NULL,
    account_non_expired boolean,
    account_non_locked boolean,
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    credentials_non_expired boolean,
    enabled boolean NOT NULL,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    password character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT username_key UNIQUE (username)

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.account
    OWNER to challenge;


-- Table: public.role

-- DROP TABLE public.role;

CREATE TABLE public.role
(
    id bigserial NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT name_key UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.role
    OWNER to challenge;


-- Table: public.account_role

-- DROP TABLE public.account_role;

CREATE TABLE public.account_role
(
    id bigserial NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    account_id bigint,
    role_id bigint,
    CONSTRAINT account_role_pkey PRIMARY KEY (id),
    CONSTRAINT account_role_account_id_fkey FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT account_role_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.account_role
    OWNER to challenge;


-- Table: public.movie

-- DROP TABLE public.movie;

CREATE TABLE public.movie
(
    id bigserial NOT NULL,
    availability boolean NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    dislikes bigint,
    image text COLLATE pg_catalog."default",
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    likes bigint,
    rental_price numeric(19,2),
    sale_price numeric(19,2),
    stock integer,
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT movie_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.movie
    OWNER to challenge;


-- Table: public.revinfo

-- DROP TABLE public.revinfo;

CREATE TABLE public.revinfo
(
    rev integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    revtstmp bigint,
    CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.revinfo
    OWNER to challenge;


-- Table: public.movie_aud_log

-- DROP TABLE public.movie_aud_log;

CREATE TABLE public.movie_aud_log
(
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype bigint NOT NULL,
    rental_price numeric(19,2),
    sale_price numeric(19,2),
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT movie_aud_log_pkey PRIMARY KEY (id, rev),
    CONSTRAINT movie_aud_log_rev_fkey FOREIGN KEY (rev)
        REFERENCES public.revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.movie_aud_log
    OWNER to challenge;


-- Table: public.user_info

-- DROP TABLE public.user_info;

CREATE TABLE public.user_info
(
    id bigserial NOT NULL,
    address character varying(255) COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    last_name character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    account_id bigint,
    CONSTRAINT user_info_pkey PRIMARY KEY (id),
    CONSTRAINT user_info_account_id_fkey FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_info
    OWNER to challenge;


-- Table: public.purchase_order

-- DROP TABLE public.purchase_order;

CREATE TABLE public.purchase_order
(
    id bigserial NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    total_amount numeric(19,2),
    user_info_id bigint,
    CONSTRAINT purchase_order_pkey PRIMARY KEY (id),
    CONSTRAINT purchase_order_user_info_id_fkey FOREIGN KEY (user_info_id)
        REFERENCES public.user_info (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.purchase_order
    OWNER to challenge;


-- Table: public.purchase_order_detail

-- DROP TABLE public.purchase_order_detail;

CREATE TABLE public.purchase_order_detail
(
    id bigserial NOT NULL,
    amount numeric(19,2),
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    deadline timestamp without time zone,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    quantity integer,
    renting boolean NOT NULL,
    purchase_order_id bigint,
    CONSTRAINT purchase_order_detail_pkey PRIMARY KEY (id),
    CONSTRAINT purchase_order_detail_purchase_order_id_fkey FOREIGN KEY (purchase_order_id)
        REFERENCES public.purchase_order (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.purchase_order_detail
    OWNER to challenge;


-- Table: public.transaction_log

-- DROP TABLE public.transaction_log;

CREATE TABLE public.transaction_log
(
    id bigserial NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone,
    last_modified_by character varying(255) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    quantity integer,
    transaction_date timestamp without time zone,
    transaction_type character varying(255) COLLATE pg_catalog."default",
    user_info_id bigint,
    CONSTRAINT transaction_log_pkey PRIMARY KEY (id),
    CONSTRAINT transaction_log_user_info_id_fkey FOREIGN KEY (user_info_id)
        REFERENCES public.user_info (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.transaction_log
    OWNER to challenge;


DROP TABLE IF EXISTS public.user_authority;
DROP TABLE IF EXISTS public.pap_user;
DROP TABLE IF EXISTS public.pap_authority;

CREATE TABLE public.pap_user (
	id int8 NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	activated bool NOT NULL,
	activation_key varchar(20) NULL,
	email varchar(254) NULL,
	first_name varchar(50) NULL,
	image_url varchar(256) NULL,
	lang_key varchar(10) NULL,
	last_name varchar(50) NULL,
	login varchar(50) NOT NULL,
	password_hash varchar(60) NOT NULL,
	reset_date timestamp NULL,
	reset_key varchar(20) NULL,
	CONSTRAINT pap_user_pkey PRIMARY KEY (id),
	CONSTRAINT uk_3jhfjponb7d1u7to0tiv1ocoj UNIQUE (email),
	CONSTRAINT uk_eglcwjdb9eoldphvb3rlm0eae UNIQUE (login)
);

CREATE TABLE public.pap_authority (
	"name" varchar(50) NOT NULL,
	CONSTRAINT pap_authority_pkey PRIMARY KEY (name)
);

CREATE TABLE public.user_authority (
	user_id int8 NOT NULL,
	authority_name varchar(50) NOT NULL,
	CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_name)
);


-- public.user_authority foreign keys

ALTER TABLE public.user_authority ADD CONSTRAINT fkojh9puircu7j07a5rat2wtcx5 FOREIGN KEY (user_id) REFERENCES pap_user(id);
ALTER TABLE public.user_authority ADD CONSTRAINT fksbktvlg8jp618hvatuluvtl1u FOREIGN KEY (authority_name) REFERENCES pap_authority(name);

INSERT INTO public.pap_user
(id,created_by,created_date,last_modified_by,last_modified_date,activated,activation_key,email,first_name,image_url,lang_key,last_name,login,password_hash,reset_key)
VALUES(1,'admin',null,'admin',null,true,null,'pap@gmail.com','admin','','en','','admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC',''),
(2,'user',null,'user',null,true,null,'user@gmail.com','user','','en','','user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','');

INSERT into public.pap_authority
(name)
VALUES('ROLE_ADMIN'),('ROLE_USER');

INSERT INTO public.user_authority
(user_id, authority_name)
VALUES(1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');






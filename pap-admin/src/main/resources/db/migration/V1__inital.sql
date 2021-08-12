DROP TABLE IF EXISTS public.user_authority;
DROP TABLE IF EXISTS public.category_restaurant;
DROP TABLE IF EXISTS public.order_detail;
DROP TABLE IF EXISTS public.review;
DROP TABLE IF EXISTS public.report;
DROP TABLE IF EXISTS public.order_cancelled;
DROP TABLE IF EXISTS public.complain;
DROP TABLE IF EXISTS public.schedule_restaurant;
DROP TABLE IF EXISTS public.order_product;
DROP TABLE IF EXISTS public.option_item_child;
DROP TABLE IF EXISTS public.option_item;
DROP TABLE IF EXISTS public.item;
DROP TABLE IF EXISTS public.discount;
DROP TABLE IF EXISTS public.category_item;
DROP TABLE IF EXISTS public.pap_user;
DROP TABLE IF EXISTS public.pap_authority;
DROP TABLE IF EXISTS public.notice_courier;
DROP TABLE IF EXISTS public.notice_customer;
DROP TABLE IF EXISTS public.notice_restaurant;
DROP TABLE IF EXISTS public.manager_restaurant;
DROP TABLE IF EXISTS public.customer;
DROP TABLE IF EXISTS public.courier;
DROP TABLE IF EXISTS public.category;

-- public.category definition

-- Drop table

-- DROP TABLE public.category;

CREATE TABLE public.category (
	id int4 NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	code varchar(20) NULL,
	"content" varchar(1000) NULL,
	summary varchar(500) NULL,
	title varchar(250) NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id),
	CONSTRAINT uk_acatplu22q5d1andql2jbvjy7 UNIQUE (code)
);

-- public.complain definition

-- Drop table

-- DROP TABLE public.complain;

CREATE TABLE public.complain (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"content" varchar(1000) NULL,
	customer_phone varchar(255) NULL,
	order_id varchar(255) NULL,
	restaurant_id varchar(255) NULL,
	CONSTRAINT complain_pkey PRIMARY KEY (id)
);


-- public.courier definition

-- Drop table

-- DROP TABLE public.courier;

CREATE TABLE public.courier (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	activated bool NOT NULL,
	address varchar(500) NULL,
	avatar varchar(256) NULL,
	bank_number varchar(20) NULL,
	branch_bank varchar(256) NULL,
	date_cmnd date NULL,
	email varchar(254) NULL,
	full_name varchar(250) NULL,
	full_name_bank varchar(256) NULL,
	image_cavet varchar(256) NULL,
	image_first_cccd varchar(256) NULL,
	image_first_cmnd varchar(256) NULL,
	image_gplx varchar(256) NULL,
	image_last_cccd varchar(256) NULL,
	image_last_cmnd varchar(256) NULL,
	image_motorbike varchar(256) NULL,
	license_plate varchar(255) NOT NULL,
	name_bank varchar(256) NULL,
	password_hash varchar(60) NOT NULL,
	phone varchar(20) NOT NULL,
	so_cccd varchar(12) NULL,
	so_cmnd varchar(12) NULL,
	status varchar(255) NOT NULL,
	CONSTRAINT courier_pkey PRIMARY KEY (id),
	CONSTRAINT uk_ea0e0gs39d7ygj2iju7uc6dtt UNIQUE (phone),
	CONSTRAINT uk_p8w4x7yxidpum590r44h0fb4y UNIQUE (license_plate),
	CONSTRAINT uk_pqhnql5qw4w1j3fldfsugrt6m UNIQUE (email)
);


-- public.customer definition

-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE public.customer (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	activated bool NOT NULL,
	address varchar(250) NULL,
	avatar varchar(256) NULL,
	email varchar(254) NULL,
	full_name varchar(250) NULL,
	phone varchar(20) NOT NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (id),
	CONSTRAINT uk_dwk6cx0afu8bs9o4t536v1j5v UNIQUE (email),
	CONSTRAINT uk_o3uty20c6csmx5y4uk2tc5r4m UNIQUE (phone)
);

-- public.manager_restaurant definition

-- Drop table

-- DROP TABLE public.manager_restaurant;

CREATE TABLE public.manager_restaurant (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	activated bool NOT NULL,
	address varchar(255) NOT NULL,
	avatar varchar(256) NULL,
	bank_number varchar(20) NULL,
	branch_bank varchar(256) NULL,
	"content" varchar(255) NULL,
	date_cmnd date NULL,
	email varchar(254) NULL,
	full_name varchar(250) NULL,
	full_name_bank varchar(256) NULL,
	image_first_cccd varchar(256) NULL,
	image_first_cmnd varchar(256) NULL,
	image_last_cccd varchar(256) NULL,
	image_last_cmnd varchar(256) NULL,
	image_restaurant varchar(256) NULL,
	is_partner bool NOT NULL,
	name_bank varchar(256) NULL,
	name_restaurant varchar(500) NOT NULL,
	password_hash varchar(60) NOT NULL,
	phone varchar(20) NOT NULL,
	rate float4 NULL,
	"role" varchar(255) NOT NULL,
	sharing int4 NULL,
	so_cccd varchar(12) NULL,
	so_cmnd varchar(12) NULL,
	so_dkkd varchar(255) NULL,
	status bool NOT NULL,
	summary varchar(255) NULL,
	type_business varchar(255) NULL,
	CONSTRAINT manager_restaurant_pkey PRIMARY KEY (id),
	CONSTRAINT uk_1eiqwporogr4ml1iwxemst4ig UNIQUE (phone),
	CONSTRAINT uk_elvpdpqxl6e8eo3ie2hq49ajb UNIQUE (email)
);


-- public.notice_courier definition

-- Drop table

-- DROP TABLE public.notice_courier;

CREATE TABLE public.notice_courier (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"content" text NOT NULL,
	title varchar(50) NOT NULL,
	CONSTRAINT notice_courier_pkey PRIMARY KEY (id)
);


-- public.notice_customer definition

-- Drop table

-- DROP TABLE public.notice_customer;

CREATE TABLE public.notice_customer (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"content" text NOT NULL,
	title varchar(50) NOT NULL,
	CONSTRAINT notice_customer_pkey PRIMARY KEY (id)
);


-- public.notice_restaurant definition

-- Drop table

-- DROP TABLE public.notice_restaurant;

CREATE TABLE public.notice_restaurant (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"content" text NOT NULL,
	title varchar(50) NOT NULL,
	CONSTRAINT notice_restaurant_pkey PRIMARY KEY (id)
);


-- public.pap_authority definition

-- Drop table

-- DROP TABLE public.pap_authority;

CREATE TABLE public.pap_authority (
	"name" varchar(50) NOT NULL,
	CONSTRAINT pap_authority_pkey PRIMARY KEY (name)
);


-- public.pap_user definition

-- Drop table

-- DROP TABLE public.pap_user;

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

-- public.category_item definition

-- Drop table

-- DROP TABLE public.category_item;

CREATE TABLE public.category_item (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"name" varchar(256) NOT NULL,
	restaurant_id bpchar(36) NOT NULL,
	CONSTRAINT category_item_pkey PRIMARY KEY (id),
	CONSTRAINT fk877wqva08u1dswc18rb48q6go FOREIGN KEY (restaurant_id) REFERENCES manager_restaurant(id)
);


-- public.category_restaurant definition

-- Drop table

-- DROP TABLE public.category_restaurant;

CREATE TABLE public.category_restaurant (
	category_id int4 NOT NULL,
	restaurant_id bpchar(36) NOT NULL,
	CONSTRAINT category_restaurant_pkey PRIMARY KEY (category_id, restaurant_id),
	CONSTRAINT fkcmskcq29qach2xgh0bladl3bu FOREIGN KEY (restaurant_id) REFERENCES manager_restaurant(id),
	CONSTRAINT fkpf970irx03tqnmoqc9lupgo8y FOREIGN KEY (category_id) REFERENCES category(id)
);


-- public.discount definition

-- Drop table

-- DROP TABLE public.discount;

CREATE TABLE public.discount (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	code varchar(10) NOT NULL,
	"content" varchar(1000) NOT NULL,
	from_date timestamp NULL,
	image_url varchar(256) NULL,
	is_deleted int2 NULL,
	min_order_price numeric(19,2) NULL,
	price numeric(19,2) NULL,
	quantity int4 NULL,
	quantity_customer int4 NULL,
	quantity_customer_day int4 NULL,
	quantity_day int4 NULL,
	sale int4 NULL,
	title varchar(256) NOT NULL,
	to_date timestamp NULL,
	restaurant_id bpchar(36) NOT NULL,
	CONSTRAINT discount_pkey PRIMARY KEY (id),
	CONSTRAINT fkfj7jq6s3yoqu71g6pdf99lq1t FOREIGN KEY (restaurant_id) REFERENCES manager_restaurant(id)
);


-- public.item definition

-- Drop table

-- DROP TABLE public.item;

CREATE TABLE public.item (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"content" varchar(1000) NULL,
	count_ordered int4 NOT NULL,
	image_url varchar(256) NULL,
	"name" varchar(256) NOT NULL,
	price numeric(19,2) NOT NULL,
	quantity int4 NULL,
	summary varchar(500) NULL,
	category_item_id bpchar(36) NOT NULL,
	CONSTRAINT item_pkey PRIMARY KEY (id),
	CONSTRAINT fksprg03fr5deunf8fj9tw5ljhx FOREIGN KEY (category_item_id) REFERENCES category_item(id)
);


-- public.option_item definition

-- Drop table

-- DROP TABLE public.option_item;

CREATE TABLE public.option_item (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"name" varchar(256) NOT NULL,
	item_id bpchar(36) NOT NULL,
	CONSTRAINT option_item_pkey PRIMARY KEY (id),
	CONSTRAINT fkg3ilcqrtcnngdrpfcf9r29ije FOREIGN KEY (item_id) REFERENCES item(id)
);


-- public.option_item_child definition

-- Drop table

-- DROP TABLE public.option_item_child;

CREATE TABLE public.option_item_child (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"name" varchar(256) NOT NULL,
	price numeric(19,2) NULL,
	option_id bpchar(36) NOT NULL,
	CONSTRAINT option_item_child_pkey PRIMARY KEY (id),
	CONSTRAINT fkkljvvo0ycore6b66gf01yt74b FOREIGN KEY (option_id) REFERENCES option_item(id)
);


-- public.order_product definition

-- Drop table

-- DROP TABLE public.order_product;

CREATE TABLE public.order_product (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	discount_id varchar(255) NULL,
	discount_price numeric(19,2) NULL,
	hand_delivery numeric(19,2) NULL,
	image_bill varchar(500) NULL,
	image_courier varchar(500) NULL,
	image_product varchar(500) NULL,
	image_restaurant varchar(500) NULL,
	name_courier varchar(255) NULL,
	name_order varchar(255) NOT NULL,
	note varchar(500) NULL,
	paid bool NOT NULL,
	phone varchar(20) NOT NULL,
	price_origin numeric(19,2) NOT NULL,
	price_service numeric(19,2) NULL,
	price_total numeric(19,2) NOT NULL,
	"range" float4 NOT NULL,
	shipping_fee numeric(19,2) NOT NULL,
	status int4 NOT NULL,
	courier_id bpchar(36) NOT NULL,
	customer_id bpchar(36) NOT NULL,
	CONSTRAINT order_product_pkey PRIMARY KEY (id),
	CONSTRAINT fkf759i0isryvfa8xe361w8xmof FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT fkia97ax2h4uwpid9gpxxcx09m1 FOREIGN KEY (courier_id) REFERENCES courier(id)
);


-- public.report definition

-- Drop table

-- DROP TABLE public.report;

CREATE TABLE public.report (
	id bpchar(36) NOT NULL,
	"content" varchar(1000) NULL,
	subject_id varchar(255) NULL,
	type_user varchar(255) NULL,
	type_subject varchar(255) NULL,
	user_id varchar(255) NULL,
	order_id bpchar(36) NULL,
	CONSTRAINT report_pkey PRIMARY KEY (id),
	CONSTRAINT fkqvvcskeq0umqyigogqn59v7f7 FOREIGN KEY (order_id) REFERENCES order_product(id)
);


-- public.review definition

-- Drop table

-- DROP TABLE public.review;

CREATE TABLE public.review (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	"content" varchar(1000) NULL,
	customer_id varchar(255) NULL,
	name_review varchar(255) NOT NULL,
	point int4 NULL,
	restaurant_id bpchar(36) NOT NULL,
	CONSTRAINT review_pkey PRIMARY KEY (id),
	CONSTRAINT fkhb0j56qgyums72sme4i82vgni FOREIGN KEY (restaurant_id) REFERENCES manager_restaurant(id)
);


-- public.schedule_restaurant definition

-- Drop table

-- DROP TABLE public.schedule_restaurant;

CREATE TABLE public.schedule_restaurant (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	from_friday time NULL,
	from_monday time NULL,
	from_saturday time NULL,
	from_sunday time NULL,
	from_thursday time NULL,
	from_tuesday time NULL,
	from_wednesday time NULL,
	list_day_off varchar(255) NULL,
	to_friday time NULL,
	to_monday time NULL,
	to_saturday time NULL,
	to_sunday time NULL,
	to_thursday time NULL,
	to_tuesday time NULL,
	to_wednesday time NULL,
	restaurant_id bpchar(36) NULL,
	CONSTRAINT schedule_restaurant_pkey PRIMARY KEY (id),
	CONSTRAINT fkj9dmhb5ehhe7mehlryfpaapcy FOREIGN KEY (restaurant_id) REFERENCES manager_restaurant(id)
);


-- public.user_authority definition

-- Drop table

-- DROP TABLE public.user_authority;

CREATE TABLE public.user_authority (
	user_id int8 NOT NULL,
	authority_name varchar(50) NOT NULL,
	CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_name),
	CONSTRAINT fkojh9puircu7j07a5rat2wtcx5 FOREIGN KEY (user_id) REFERENCES pap_user(id),
	CONSTRAINT fksbktvlg8jp618hvatuluvtl1u FOREIGN KEY (authority_name) REFERENCES pap_authority(name)
);

-- public.order_cancelled definition

-- Drop table

-- DROP TABLE public.order_cancelled;

CREATE TABLE public.order_cancelled (
	id bpchar(36) NOT NULL,
	"content" varchar(1000) NULL,
	type_user varchar(255) NULL,
	user_id varchar(255) NULL,
	order_id bpchar(36) NULL,
	CONSTRAINT order_cancelled_pkey PRIMARY KEY (id),
	CONSTRAINT fknk4tlfbep3o09u4ve787kryh0 FOREIGN KEY (order_id) REFERENCES order_product(id)
);


-- public.order_detail definition

-- Drop table

-- DROP TABLE public.order_detail;

CREATE TABLE public.order_detail (
	id bpchar(36) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	item_id varchar(255) NOT NULL,
	"name" varchar(256) NOT NULL,
	note varchar(500) NULL,
	price numeric(19,2) NOT NULL,
	quantity int4 NOT NULL,
	order_id bpchar(36) NOT NULL,
	CONSTRAINT order_detail_pkey PRIMARY KEY (id),
	CONSTRAINT fkrg4kxl9gq4hnhvm49sixgjydf FOREIGN KEY (order_id) REFERENCES order_product(id)
);
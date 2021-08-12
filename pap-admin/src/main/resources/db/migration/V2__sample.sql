INSERT INTO public.category
(id, created_by, created_date, last_modified_by, last_modified_date, "content", summary, title, code)
VALUES(1, 'admin', '2021-08-04', 'admin', '2021-08-04', '', '', 'Cơm', 'COM'), (2, 'admin', '2021-08-04', 'admin', '2021-08-04', '', '', 'Ăn vặt', 'ANVAT')
,(3, 'admin', '2021-08-04', 'admin', '2021-08-04', '', '', 'Trà sữa', 'TRASUA'),(4, 'admin', '2021-08-04', 'admin', '2021-08-04', '', '', 'Đồ uống', 'DOUONG');


INSERT into public.pap_authority
(name)
VALUES('ROLE_ADMIN'),('ROLE_USER');


INSERT INTO public.pap_user
(id,created_by,created_date,last_modified_by,last_modified_date,activated,activation_key,email,first_name,image_url,lang_key,last_name,login,password_hash,reset_key)
VALUES(1,'admin',null,'admin',null,true,null,'pap@gmail.com','admin','','en','','admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC',''),
(2,'user',null,'user',null,true,null,'user@gmail.com','user','','en','','user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','');


INSERT INTO public.user_authority
(user_id, authority_name)
VALUES(1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');

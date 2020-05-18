INSERT INTO public.user_info
(created_by, created_date, last_modified_by, last_modified_date, last_name, "name", account_id)
VALUES('admin', '2020-05-18 22:20:16.172', 'admin', '2020-05-18 22:20:16.172', 'Admin', 'Admin', (select id from public.account where username = "admin"));
INSERT INTO public.user_info
(created_by, created_date, last_modified_by, last_modified_date, last_name, "name", account_id)
VALUES('admin', '2020-05-18 22:20:16.172', 'admin', '2020-05-18 22:20:16.172', 'User', 'user', (select id from public.account where username = "user"));

INSERT INTO public.movie
(availability, created_by, created_date, description, dislikes, image, last_modified_by, last_modified_date, likes, rental_price, sale_price, stock, title)
VALUES(true, 'admin', '2020-05-18 21:44:34.931', 'Description', 0, NULL, 'admin', '2020-05-18 21:44:34.931', 0, 2.50, 44.99, 15, 'Title 1');ser"));
INSERT INTO public.movie
(availability, created_by, created_date, description, dislikes, image, last_modified_by, last_modified_date, likes, rental_price, sale_price, stock, title)
VALUES(true, 'admin', '2020-05-18 21:44:34.931', 'Description', 0, NULL, 'admin', '2020-05-18 21:44:34.931', 0, 2.00, 39.99, 15, 'Title 2');

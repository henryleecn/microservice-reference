    INSERT INTO roles (id, name) VALUES (1,1),(2,2),(3,3),(4,4),(5,5);
    INSERT INTO users (id, username, password) VALUES (1, 'user', 'user'), (2, 'admin', 'admin'); 
    INSERT INTO user_roles_map (user_id, role_id) VALUES (2, 4), (1, 2);
    INSERT INTO product (id, name, msrp, imageurl, thumbnail) VALUES (1, 'Spring Bag', 100, '/static/img/bag_large.jpg', '/static/img/bag_tn.jpg');
    INSERT INTO sale (id, product_id, startprice, reserveprice, starttime, endtime, description) VALUES (1,1,0,0,CURRENT_TIMESTAMP(),DATEADD('DAY', 1, CURRENT_TIMESTAMP()),'Sale 1 desc');
    INSERT INTO sale (id, product_id, startprice, reserveprice, starttime, endtime, description) VALUES (2,1,0,0,CURRENT_TIMESTAMP(),DATEADD('DAY', 10, CURRENT_TIMESTAMP()),'Sale 2 desc');
    INSERT INTO sale (id, product_id, startprice, reserveprice, starttime, endtime, description) VALUES (3,1,0,0,DATEADD('DAY', 1, CURRENT_TIMESTAMP()),DATEADD('DAY', 5, CURRENT_TIMESTAMP()),'Sale 3 desc');
    
    INSERT INTO sale (id, product, start_price, reserve_price, start_time, end_time, description, created_by, created_date, currency_code) VALUES (1,1,0,0,CURRENT_TIMESTAMP(),DATEADD('DAY', 1, CURRENT_TIMESTAMP()),'sale 1 desc', 3, CURRENT_TIMESTAMP(), 'INR'  );
    INSERT INTO sale (id, product, start_price, reserve_price, start_time, end_time, description, created_by, created_date, currency_code) VALUES (2,1,0,0,CURRENT_TIMESTAMP(),DATEADD('DAY', 1, CURRENT_TIMESTAMP()),'sale 2 desc', 3, CURRENT_TIMESTAMP(), 'INR'  );
    INSERT INTO sale (id, product, start_price, reserve_price, start_time, end_time, description, created_by, created_date, currency_code) VALUES (3,1,0,0,DATEADD('DAY', 1, CURRENT_TIMESTAMP()), DATEADD('DAY', 2, CURRENT_TIMESTAMP()),'sale 3 desc', 3, CURRENT_TIMESTAMP(), 'INR'  );
    
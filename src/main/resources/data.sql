INSERT INTO categories(name) VALUES('本');
INSERT INTO categories(name) VALUES('DVD');
INSERT INTO categories(name) VALUES('ゲーム');

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(1, 2, '桜前線北上中！', 2500, 76, 3, 2);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(1, 2, 'あめのおと　１巻', 980, 43, 1, 2);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(1, 2, 'ファミリー　イン　ザ　スカイ', 1200, 87, 0, 2);

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(2, 3, '架空世界史総集編', 2000, 24, 0, 2);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(2, 3, 'Slide Doors', 1000, 12, 0, 2);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(2, 3, 'フューチャーヴィジョン　吹替版', 1800, 34, 0, 2);

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(3, 4, 'クリスタルメモリーズ3', 5600, 53, 1, 2);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(3, 4, 'アンティーク・ギア', 3400, 54, 0, 2);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(3, 4, 'Yubel リメイク版', 2200, 45, 0, 2);

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(1, 2, 'Escape From Hell', 2200, 45, 0, 1);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count, status) VALUES(1, 2, 'Gone to Heven', 2400, 0, 0, 0);

INSERT INTO modes(name) VALUES('管理者');
INSERT INTO modes(name) VALUES('ショップ');
INSERT INTO modes(name) VALUES('一般');

INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(1, 'admin', '', '', 'admin', 'admin');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(2, '佐々木小次郎', '東京都', '090-1111-3333', 'sasaki@aaa.com', 'syoten');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(2, '山本次郎', '東京都', '090-1111-4444', 'yamamoto@aaa.com', 'video');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(2, '坂本幸次郎', '東京都', '090-1111-5555', 'sakamoto@aaa.com', 'game');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(3, '田中太郎', '東京都', '090-1111-1111', 'tanaka@aaa.com', 'himitu');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(3, '鈴木一郎', '大阪府', '090-1111-2222', 'suzuki@aaa.com', 'himi2');

INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(1, '佐々木小次郎', '東京都', '090-1111-3333', 'sasaki@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(2, '山本次郎', '東京都', '090-1111-4444', 'yamamoto@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(3, '坂本幸次郎', '東京都', '090-1111-5555', 'sakamoto@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(4, '田中太郎', '東京都', '090-1111-1111', 'tanaka@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(5, '鈴木一郎', '大阪府', '090-1111-2222', 'suzuki@aaa.com');

INSERT INTO orders(customer_id, contact_id, ordered_on, total_price) VALUES(5, 0, '2024/4/12', 6760);
INSERT INTO orders(customer_id, contact_id, ordered_on, total_price) VALUES(6, 0, '2024/4/13', 2500);

INSERT INTO order_details(order_id, item_id, quantity) VALUES(1, 1, 2);
INSERT INTO order_details(order_id, item_id, quantity) VALUES(1, 2, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES(1, 7, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES(2, 1, 1);

INSERT INTO plans(name) VALUES('プレミアム');
INSERT INTO plans(name) VALUES('エコノミー');

INSERT INTO shops(plan_id, customer_id, name, status) VALUES(1, 1, 'テストショップ', 0);
INSERT INTO shops(plan_id, customer_id, name, status) VALUES(1, 2, '佐々木書店', 1);
INSERT INTO shops(plan_id, customer_id, name, status) VALUES(2, 3, 'YAMAMOTOビデオショップ', 1);
INSERT INTO shops(plan_id, customer_id, name, status) VALUES(2, 4, 'サカモトゲーム', 1);

INSERT INTO reviews(item_id, customer_id, reviewed_on, title, review_text, score) VALUES(1, 5, '2024/04/15', '最高の教材', 'とてもわかりやすかったです', 5);

INSERT INTO categories(name) VALUES('本');
INSERT INTO categories(name) VALUES('DVD');
INSERT INTO categories(name) VALUES('ゲーム');

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(1, 1, 'Javaの基本', 2500, 76, 3);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(1, 1, 'MLB Fun', 980, 43, 1);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(1, 1, '料理BOOK!', 1200, 87, 0);

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(2, 2, 'なつかしのアニメシリーズ', 2000, 24, 0);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(2, 2, 'The Racer', 1000, 12, 0);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(2, 2, 'Space Wars 3', 1800, 34, 0);

INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(3, 3, 'パズルゲーム', 780, 53, 1);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(3, 3, 'Invader Fighter', 3400, 54, 0);
INSERT INTO items(category_id, shop_id, name, price, stock_count, sell_count) VALUES(3, 3, 'Play the BascketBall', 2200, 45, 0);

INSERT INTO modes(name) VALUES('管理者');
INSERT INTO modes(name) VALUES('ショップ');
INSERT INTO modes(name) VALUES('一般');

INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(1, 'admin', '', '', 'admin', 'admin');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(2, '佐々木小次郎', '東京都', '090-1111-3333', 'sasaki@aaa.com', 'syoten');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(2, '山本次郎', '東京都', '090-1111-4444', 'yamamoto@aaa.com', 'video');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(2, '坂本幸次郎', '東京都', '090-1111-5555', 'sakamoto@aaa.com', 'game');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(3, '田中太郎', '東京都', '090-1111-1111', 'tanaka@aaa.com', 'himitu');
INSERT INTO customers(mode_id, name, address, tel, email, password) VALUES(3, '鈴木一郎', '大阪府', '090-1111-2222', 'suzuki@aaa.com', 'himi2');

INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(1, 'admin', '', '', 'admin');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(2, '佐々木小次郎', '東京都', '090-1111-3333', 'sasaki@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(3, '山本次郎', '東京都', '090-1111-4444', 'yamamoto@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(4, '坂本幸次郎', '東京都', '090-1111-5555', 'sakamoto@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(5, '田中太郎', '東京都', '090-1111-1111', 'tanaka@aaa.com');
INSERT INTO contacts(customer_id, name, address, tel, email) VALUES(6, '鈴木一郎', '大阪府', '090-1111-2222', 'suzuki@aaa.com');

INSERT INTO wallets(name) VALUES('現金');
INSERT INTO wallets(name) VALUES('クレジットカード');
INSERT INTO wallets(name) VALUES('paypoi');

INSERT INTO user_wallets(customer_id, wallet_id, wallet_number, wallet_key) VALUES(5, 2, '1111000022223333', '123');

INSERT INTO orders(customer_id, contact_id, ordered_on, total_price) VALUES(5, 0, '2024/4/12', 6760);
INSERT INTO orders(customer_id, contact_id, ordered_on, total_price) VALUES(6, 0, '2024/4/13', 2500);

INSERT INTO order_details(order_id, item_id, quantity) VALUES(1, 1, 2);
INSERT INTO order_details(order_id, item_id, quantity) VALUES(1, 2, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES(1, 7, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES(2, 1, 1);

INSERT INTO plans(name) VALUES('プレミアム');
INSERT INTO plans(name) VALUES('エコノミー');

INSERT INTO shops(plan_id, customer_id, name) VALUES(1, 2, '佐々木書店');
INSERT INTO shops(plan_id, customer_id, name) VALUES(2, 3, 'YAMAMOTOビデオショップ');
INSERT INTO shops(plan_id, customer_id, name) VALUES(2, 4, 'サカモトゲーム');

INSERT INTO reviews(item_id, customer_id, reviewed_on, title, review_text, score) VALUES(1, 5, '2024/04/15', '最高の教材', 'とてもわかりやすかったです', 5);

-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS modes;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS user_wallets;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS plans;
DROP TABLE IF EXISTS shops;
DROP TABLE IF EXISTS reviews;
-- カテゴリーテーブル
CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   name TEXT
);
-- 商品テーブル
CREATE TABLE items
(
   id SERIAL PRIMARY KEY,
   category_id INTEGER,
   shop_id INTEGER,
   name TEXT,
   price INTEGER,
   stock_count INTEGER,
   sell_count INTEGER
);
-- モードテーブル
CREATE TABLE modes
(
   id SERIAL PRIMARY KEY,
   name TEXT
);
-- 顧客テーブル
CREATE TABLE customers
(
   id SERIAL PRIMARY KEY,
   mode_id INTEGER,
   name TEXT,
   address TEXT,
   tel TEXT,
   email TEXT,
   password TEXT
);
-- 送り先テーブル
CREATE TABLE contacts
(
   id SERIAL PRIMARY KEY,
   customer_id INTEGER,
   name TEXT,
   address TEXT,
   tel TEXT,
   email TEXT
);
-- 決済方法テーブル
CREATE TABLE wallets
(
   id SERIAL PRIMARY KEY,
   name TEXT
);
-- ユーザー決済方法テーブル
CREATE TABLE user_wallets
(
   id SERIAL PRIMARY KEY,
   customer_id INTEGER,
   wallet_id INTEGER,
   wallet_number TEXT,
   wallet_key TEXT
);
-- 注文テーブル
CREATE TABLE orders
(
   id SERIAL PRIMARY KEY,
   customer_id INTEGER,
   ordered_on DATE,
   total_price INTEGER
);
-- 注文明細テーブル
CREATE TABLE order_details
(
   id SERIAL PRIMARY KEY,
   order_id INTEGER,
   item_id INTEGER,
   quantity INTEGER
);
-- プランテーブル
CREATE TABLE plans
(
   id SERIAL PRIMARY KEY,
   name TEXT
);
-- ショップテーブル
CREATE TABLE shops
(
   id SERIAL PRIMARY KEY,
   plan_id INTEGER,
   customer_id INTEGER,
   name TEXT
);
-- レビューテーブル
CREATE TABLE reviews
(
   id SERIAL PRIMARY KEY,
   item_id INTEGER,
   customer_id INTEGER,
   reviewed_on DATE,
   title TEXT,
   review_text TEXT,
   score INTEGER
);

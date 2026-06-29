/* データベース作成 */
CREATE DATABASE d4;
USE d4;


/* Userテーブル作成 */
CREATE TABLE User (
`mail` VARCHAR (100) PRIMARY KEY,
`pass` VARCHAR (30) NOT NULL,
`name` VARCHAR (15),
`target` INT,
`trans` VARCHAR (50) NOT NULL  DEFAULT '/d4/MyPageServlet'
);

/* Purposeテーブル作成 */
CREATE TABLE Purpose(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`mail` VARCHAR(100) NOT NULL, 
`text` VARCHAR(30) NOT NULL, 
FOREIGN KEY(mail) REFERENCES User(mail)
);

/* Imgテーブル作成 */
CREATE TABLE Img(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`mail` VARCHAR(100) NOT NULL,
`path` VARCHAR(500) NOT NULL,
FOREIGN KEY(mail) REFERENCES User(mail)
);

/* Categoryテーブル作成 */
CREATE TABLE Category(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`mail` VARCHAR(100) NOT NULL,
`number` INT NOT NULL,
`name` VARCHAR(15) NOT NULL,
`kind` INT CHECK(kind IN(1, 2)),
FOREIGN KEY(mail) REFERENCES User(mail)
);

/* Bpテーブル作成 */
CREATE TABLE Bp(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`mail` VARCHAR(100) NOT NULL,
`cid` INT NOT NULL,
`money` INT NOT NULL,
`memo` VARCHAR(150),
`year` CHAR(4) NOT NULL,
`month` CHAR(2) NOT NULL,
`day` CHAR(2) NOT NULL,
FOREIGN KEY(mail) REFERENCES User(mail),
FOREIGN KEY(cid) REFERENCES Category(id)
);


/* Userデモデータ */
INSERT INTO User (`mail`, `pass`, `name`, `target`) VALUES
('demo.user@example.com', 'DemoPass123', '山田マイケル', 500000);
SELECT * FROM User;

/* Purposeデモデータ */
INSERT INTO Purpose (`mail`, `text`) VALUES
('demo.user@example.com', '海外旅行'),
('demo.user@example.com', '車を買う'),
('demo.user@example.com', '');

/* Categoryデモデータ */
INSERT INTO Category (id, `mail`, `number`, `name`, `kind`) VALUES
(1, 'demo.user@example.com', 1, '給料', 1),
(2, 'demo.user@example.com', 2, '', 1),
(3, 'demo.user@example.com', 3, '', 1),
(4, 'demo.user@example.com', 4, '', 1),
(5, 'demo.user@example.com', 5, '', 1),
(6, 'demo.user@example.com', 6, '', 1),
(7, 'demo.user@example.com', 7, '', 1),
(8, 'demo.user@example.com', 8, '', 1),
(9, 'demo.user@example.com', 9, '', 1),
(10, 'demo.user@example.com', 10, '', 1),
(11, 'demo.user@example.com', 1, '買い物', 2),
(12, 'demo.user@example.com', 2, '固定費', 2),
(13, 'demo.user@example.com', 3, '娯楽', 2),
(14, 'demo.user@example.com', 4, '', 2),
(15, 'demo.user@example.com', 5, '', 2),
(16, 'demo.user@example.com', 6, '', 2),
(17, 'demo.user@example.com', 7, '', 2),
(18, 'demo.user@example.com', 8, '', 2),
(19, 'demo.user@example.com', 9, '', 2),
(20,'demo.user@example.com', 10, '', 2);

/* Bpデモデータ */
INSERT INTO Bp (`mail`, `cid`, `money`, `memo`, `year`, `month`, `day`) VALUES
('demo.user@example.com', 12, 62000, '家賃', '2026', '06', '01'),
('demo.user@example.com', 11, 5800, 'スーパーで食材購入', '2026', '06', '12'),
('demo.user@example.com', 1, 250000, '6月分の給料', '2026', '06', '25'),
('demo.user@example.com', 13, 3200, '映画鑑賞', '2026', '06', '27'),
('demo.user@example.com', 1, 250000, '1月分の給料', '2026', '01', '25'),
('demo.user@example.com', 1, 250000, '2月分の給料', '2026', '02', '25'),
('demo.user@example.com', 1, 250000, '3月分の給料', '2026', '03', '25'),
('demo.user@example.com', 1, 250000, '4月分の給料', '2026', '04', '25'),
('demo.user@example.com', 1, 250000, '5月分の給料', '2026', '05', '25'),
('demo.user@example.com', 1, 250000, '6月分の給料', '2026', '06', '25'),
('demo.user@example.com', 1, 250000, '7月分の給料', '2026', '07', '25'),
('demo.user@example.com', 1, 250000, '8月分の給料', '2026', '08', '25'),
('demo.user@example.com', 1, 250000, '9月分の給料', '2026', '09', '25'),
('demo.user@example.com', 1, 250000, '10月分の給料', '2026', '10', '25'),
('demo.user@example.com', 1, 250000, '11月分の給料', '2026', '11', '25'),
('demo.user@example.com', 1, 250000, '12月分の給料', '2026', '12', '25'),
('demo.user@example.com', 1, 230000, '1月分の給料', '2025', '01', '25'),
('demo.user@example.com', 1, 230000, '2月分の給料', '2025', '02', '25'),
('demo.user@example.com', 1, 230000, '3月分の給料', '2025', '03', '25'),
('demo.user@example.com', 1, 230000, '4月分の給料', '2025', '04', '25'),
('demo.user@example.com', 1, 230000, '5月分の給料', '2025', '05', '25'),
('demo.user@example.com', 1, 230000, '6月分の給料', '2025', '06', '25'),
('demo.user@example.com', 1, 230000, '7月分の給料', '2025', '07', '25'),
('demo.user@example.com', 1, 230000, '8月分の給料', '2025', '08', '25'),
('demo.user@example.com', 1, 230000, '9月分の給料', '2025', '09', '25'),
('demo.user@example.com', 1, 230000, '10月分の給料', '2025', '10', '25'),
('demo.user@example.com', 1, 230000, '11月分の給料', '2025', '11', '25'),
('demo.user@example.com', 1, 230000, '12月分の給料', '2025', '12', '25'),
('demo.user@example.com', 11, 5800, 'スーパーで買い物', '2026', '01', '12'),
('demo.user@example.com', 11, 3200, 'ドラッグストア', '2026', '01', '20'),
('demo.user@example.com', 11, 980, 'コンビニ', '2026', '02', '03'),
('demo.user@example.com', 11, 4500, '日用品購入', '2026', '02', '18'),
('demo.user@example.com', 11, 12000, '服を購入', '2026', '03', '10'),
('demo.user@example.com', 11, 2100, 'お菓子まとめ買い', '2025', '04', '03'),
('demo.user@example.com', 11, 3500, '雑貨購入', '2025', '04', '12'),
('demo.user@example.com', 11, 890, 'コンビニ', '2025', '05', '02'),
('demo.user@example.com', 11, 4200, 'キッチン用品', '2025', '05', '14'),
('demo.user@example.com', 11, 1600, '文房具購入', '2025', '05', '21'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '01', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '02', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '03', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '04', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '05', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '06', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '07', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '08', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '09', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '10', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '11', '01'),
('demo.user@example.com', 12, 62000, '家賃', '2026', '12', '01'),
('demo.user@example.com', 13, 3200, '映画鑑賞', '2026', '01', '20'),
('demo.user@example.com', 13, 1500, 'カフェ', '2026', '02', '05'),
('demo.user@example.com', 13, 7800, 'ライブ', '2026', '02', '22'),
('demo.user@example.com', 13, 2400, 'カラオケ', '2026', '03', '15'),
('demo.user@example.com', 13, 5000, 'ゲーム購入', '2026', '03', '28'),
('demo.user@example.com', 13, 2000, '課金', '2025', '04', '18'),
('demo.user@example.com', 13, 2400, 'カラオケ', '2025', '04', '25'),
('demo.user@example.com', 13, 3400, '友達と外食', '2025', '05', '09'),
('demo.user@example.com', 13, 900, 'スイーツ購入', '2025', '05', '17'),
('demo.user@example.com', 13, 10000, 'ライブ', '2025', '05', '30'),
('demo.user@example.com', 13, 10000, 'コンサート', '2024', '05', '11');
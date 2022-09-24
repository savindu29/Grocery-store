SHOW DATABASES;
CREATE DATABASE IF NOT EXISTS GroceryShop;
USE GroceryShop;
CREATE TABLE Customer(
     id VARCHAR(6) NOT NULL,
     name VARCHAR(30),
     address VARCHAR(30),
     salary DECIMAL(10,2),
     CONSTRAINT PRIMARY KEY (id)
);
CREATE TABLE Item(
     code VARCHAR(6) NOT NULL,
     description VARCHAR(50),
     unitPrice DECIMAL(8,2),
     qtyOnHand INT(5),
     CONSTRAINT PRIMARY KEY (code)
);
CREATE TABLE Orders(
       id VARCHAR(6) NOT NULL,
       date DATE,
       customerId VARCHAR(6) NOT NULL,
       total DOUBLE,
       CONSTRAINT PRIMARY KEY (id),
       CONSTRAINT FOREIGN KEY(customerId) REFERENCES Customer(id) on Delete Cascade on Update
           Cascade
);


CREATE TABLE OrderDetail(
        orderId VARCHAR(6) NOT NULL,
        itemCode VARCHAR(6) NOT NULL,
        qty INT(11),
        unitPrice DECIMAL(8,2),
        CONSTRAINT PRIMARY KEY (orderId,itemCode),
        CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(id) on Delete Cascade on Update
            Cascade,
        CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(code) on Delete Cascade on Update
            Cascade
);
#--------------------Customer------------------------------------------
INSERT INTO Customer VALUES('C001','Danapala','Panadura',54000);
INSERT INTO Customer VALUES('C002','Gunapala','Matara',44000);
INSERT INTO Customer VALUES('C003','Somapala','Galle',82000);
INSERT INTO Customer VALUES('C004','Siripala','Galle',24000);
INSERT INTO Customer VALUES('C005','Jinadasa','Panadura',94000);
INSERT INTO Customer VALUES('C006','Sepalika','Kalutara',58000);
INSERT INTO Customer VALUES('C007','Jinasena','Ambalangoda',51000);
INSERT INTO Customer VALUES('C008','Somadasa','Baddegama',34000);
INSERT INTO Customer VALUES('C009','Danasiri','Moratuwa',29000);
INSERT INTO Customer VALUES('C010','Somasiri','Kandy',64000);
#--------------------Item----------------------------------------------
INSERT INTO Item VALUES('P001','Keerisamba Retail',105.00,3000);
INSERT INTO Item VALUES('P002','Keerisamba 5Kg ',525.00,200);
INSERT INTO Item VALUES('P003','Keerisamba 10Kg',995.00,36);
INSERT INTO Item VALUES('P004','Keerisamba 50Kg',4100.00,36);
INSERT INTO Item VALUES('P005','Red Raw Rice',60.00,6000);
INSERT INTO Item VALUES('P006','Red Raw Rice 10Kg Pack',560.00,300);
INSERT INTO Item VALUES('P007','Red Raw Rice 50Kg Pack',5230.00,80);
INSERT INTO Item VALUES('P008','White Raw Rice 5Kg Pack',275.00,130);
INSERT INTO Item VALUES('P009','White Raw Rice 50Kg Pack',2600.00,13);
INSERT INTO Item VALUES('P010','Wattana Dhal 500g',90.00,83);
INSERT INTO Item VALUES('P011','Wattana Dhal 1Kg',170.00,40);
INSERT INTO Item VALUES('P012','Mysoor Dhal 500g',90.00,89);
INSERT INTO Item VALUES('P013','Mysoor Dhal 1Kg',180.00,59);
INSERT INTO Item VALUES('P014','Orient Green Gram 500g',118.00,39);
INSERT INTO Item VALUES('P015','Orient Green Gram 1Kg',220.00,12);
INSERT INTO Item VALUES('P016','Anchor F/C Milk powder 450g',220.00,93);
INSERT INTO Item VALUES('P017','Anchor F/C Milk powder 1Kg',580.00,40);
INSERT INTO Item VALUES('P018','Anchor N/F Milk powder 1Kg',560.00,33);
INSERT INTO Item VALUES('P019','Milo Packet 400g',240.00,33);
INSERT INTO Item VALUES('P020','Lipton Ceylon Tea 100g',107.00,40);

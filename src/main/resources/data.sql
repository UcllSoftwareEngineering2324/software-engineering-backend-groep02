
INSERT INTO "PUBLIC"."CAR" VALUES
(TRUE, 2, 5, FALSE, 1, 'Toyota', '1-ABC-123', 'Corolla', 'Sedan'),
(TRUE, 1, 6, TRUE, 2, 'Ford', '1-DEF-456', 'F-150', 'Pickup'),
(FALSE, 2, 5, FALSE, 3, 'Honda', '1-GHI-789', 'Civic', 'Sedan'),
(TRUE, 2, 8, TRUE, 4, 'Chevrolet', '1-JKL-012', 'Suburban', 'SUV'),
(FALSE, 2, 5, FALSE, 5, 'Tesla', '1-MNO-345', 'Model 3', 'Sedan'),       
(TRUE, 3, 7, FALSE, 6, 'BMW', '2-ABC-234', '320i', 'Sedan'),
(FALSE, 2, 5, TRUE, 7, 'Audi', '2-DEF-567', 'A4', 'Sedan'),
(TRUE, 4, 9, FALSE, 8, 'Mercedes', '2-GHI-890', 'GLE', 'SUV'),
(FALSE, 2, 6, FALSE, 9, 'Volkswagen', '2-JKL-345', 'Golf', 'Hatchback'),
(TRUE, 2, 5, TRUE, 10, 'Nissan', '2-MNO-678', 'Altima', 'Sedan');       



INSERT INTO "PUBLIC"."RENTAL" VALUES
(DATE '2024-04-07', 0, DATE '2024-04-01', 0, 1, 1, 'New York', 'nyrental@example.com', '123-456-7890', NULL),
(DATE '2024-04-26', 0, DATE '2024-04-15', 0, 2, 2, 'Los Angeles', 'larental@example.com', '987-654-3210', NULL),
(DATE '2024-05-31', 0, DATE '2024-04-24', 0, 3, 3, 'Chicago', 'chirental@example.com', '555-123-4567', NULL),      
(DATE '2024-06-15', 0, DATE '2024-06-01', 0, 4, 4, 'Houston', 'hourental@example.com', '321-654-9870', NULL),
(DATE '2024-07-20', 0, DATE '2024-07-05', 0, 5, 5, 'Miami', 'miamirental@example.com', '456-789-1234', NULL);



INSERT INTO "PUBLIC"."RENT" VALUES
(DATE '2001-06-01', DATE '2024-04-07', DATE '2024-04-01', 1, 1, 'renter1@email.com', '0123456789', '11.11.11-111.11', '0465544532'), 
(DATE '1999-01-21', DATE '2024-04-26', DATE '2024-04-15', 2, 2, 'renter2@example.com', '9876543210', '22.22.22-222.22', '0497754654'),      
(DATE '1988-02-19', DATE '2024-05-31', DATE '2024-04-24', 3, 3, 'renter3@outlook.com', '0000000000', '33.33.33-333.33', '0454429856');      

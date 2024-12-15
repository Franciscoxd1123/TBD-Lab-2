INSERT INTO Categoria (nombre) VALUES
('Electrónica'),
('Hogar'),
('Ropa'),
('Juguetes'),
('Libros');

INSERT INTO Cliente (nombre, direccion, email, telefono, password, latitud, longitud, location) VALUES
('Juan Pérez', 'Av. Providencia 1000, Santiago, Chile', 'juan.perez@example.com', '555-1234', '$2a$10$3Z1c5.cPpPQ6E7sHJ35qe1imZT4R8iR3YkbTyk9A5mhnjmnq8cSAC', -33.425262, -70.612862, ST_SetSRID(ST_MakePoint(-70.612862, -33.425262), 4326)), --mypassword
('Ana Gómez', 'Bellavista 594, Recoleta, Chile', 'ana.gomez@example.com', '555-5678', '$2a$10$ZjPSlJbHhW1yQhQhg3PQQOZVFY6UwhgkVo9lRuBvpfPj8iHHhCVKu', -33.432222, -70.647661, ST_SetSRID(ST_MakePoint(-70.647661, -33.432222), 4326)), --123123123123
('Carlos López', 'Plaza de Armas 1, Santiago, Chile', 'carlos.lopez@example.com', '555-8765', '$2a$10$F7V7gGGiXjNOnReAxWLVMeBhRG2kpH2UpkxDlgyX4kSFEw5yOHNK2', -33.437914, -70.651255, ST_SetSRID(ST_MakePoint(-70.651255, -33.437914), 4326)),  --youarenotreal
('Marta Díaz', 'Av. Independencia 2400, Santiago, Chile', 'marta.diaz@example.com', '555-4321', '$2a$10$kzNbgD1fn7PjsPsdjXh1D1NwjdLpm.xKxYlywIdm49MhM8e01oD6a', -33.403400, -70.663920, ST_SetSRID(ST_MakePoint(-70.663920, -33.403400), 4326)), --asdasd123321
('Luis Torres', 'Av. Santa María 1234, Santiago, Chile', 'luis.torres@example.com', '555-9876', '$2a$10$Y8OVwL6ftJrSIZwEjjX2JuaIek8H.Q7SEl9Wi6tzUBXFT8gH41eF2', -33.432650, -70.648456, ST_SetSRID(ST_MakePoint(-70.648456, -33.432650), 4326)); --passworddefault

INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES
('Teléfono Móvil', 'Smartphone de última generación', 499.99, 50, 'Disponible', 1),
('Aspiradora', 'Aspiradora potente y eficiente', 199.99, 30, 'Disponible', 2),
('Camiseta', 'Camiseta de algodón 100% de alta calidad', 19.99, 100, 'Disponible', 3),
('Juego de Construcción', 'Juego educativo para niños mayores de 6 años', 29.99, 40, 'Disponible', 4),
('Novela de Ciencia Ficción', 'Libro bestseller de ciencia ficción', 14.99, 75, 'Disponible', 5);

INSERT INTO Orden (fecha_orden, estado, id_cliente, total) VALUES
('2024-11-01 10:30:00', 'Pendiente', 1, 549.98),
('2024-11-03 08:00:00', 'Pagada', 1, 19.99), 
('2024-11-03 10:00:00', 'Pagada', 1, 14.99),
('2024-11-02 14:45:00', 'Enviada', 2, 199.99),
('2024-11-03 09:15:00', 'Pagada', 3, 19.99),
('2024-11-04 16:20:00', 'Pendiente', 4, 44.98),
('2024-11-10 12:30:00', 'Pagada', 4, 29.99), 
('2024-11-10 18:00:00', 'Enviada', 4, 499.99),
('2024-11-05 11:00:00', 'Enviada', 5, 14.99);

INSERT INTO Detalle_Orden (id_orden, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 499.99),
(1, 3, 1, 19.99),
(2, 3, 1, 19.99), 
(3, 5, 1, 14.99),
(4, 2, 1, 199.99),
(5, 3, 1, 19.99),
(6, 4, 2, 22.49),
(7, 4, 1, 29.99),
(8, 1, 1, 499.99),
(9, 5, 1, 14.99);

INSERT INTO Almacen (nombre, direccion, latitud, longitud, location) VALUES
('Almacen Central', 'Av. Los Industriales 500, Santiago, Chile', -33.56017, -70.55456, ST_SetSRID(ST_MakePoint(-70.55456, -33.56017), 4326)),
('Almacen Norte', 'Av. El Peñalolén 350, Santiago, Chile', -33.4890, -70.5736, ST_SetSRID(ST_MakePoint(-70.5736, -33.4890), 4326)),
('Almacen Sur', 'Calle Rancagua 2000, Santiago, Chile', -33.4436, -70.6253, ST_SetSRID(ST_MakePoint(-70.6253, -33.4436), 4326)),
('Almacen Este', 'Av. Vicuña Mackenna 1300, Santiago, Chile', -33.4672, -70.6350, ST_SetSRID(ST_MakePoint(-70.6350, -33.4672), 4326)),
('Almacen Oeste', 'Calle San Pablo 6500, Santiago, Chile', -33.4362, -70.6639, ST_SetSRID(ST_MakePoint(-70.6639, -33.4362), 4326));

INSERT INTO Almacen_Producto (id_almacen, id_producto, cantidad, fecha_entrada, fecha_salida) VALUES
(1, 1, 25, '2024-11-01', NULL),
(1, 2, 10, '2024-11-02', NULL),
(1, 3, 50, '2024-11-03', NULL),
(2, 4, 30, '2024-11-04', NULL),
(2, 5, 75, '2024-11-05', NULL),
(3, 1, 40, '2024-11-06', NULL),
(3, 4, 20, '2024-11-07', NULL),
(4, 2, 35, '2024-11-08', NULL),
(4, 5, 60, '2024-11-09', NULL),
(5, 3, 15, '2024-11-10', NULL);
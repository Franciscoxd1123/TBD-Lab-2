INSERT INTO Categoria (nombre) VALUES
('Electrónica'),
('Hogar'),
('Ropa'),
('Juguetes'),
('Libros');

INSERT INTO Cliente (nombre, direccion, email, telefono, password) VALUES
('Juan Pérez', 'Calle Falsa 123', 'juan.perez@example.com', '555-1234', 'mypassword'),
('Ana Gómez', 'Av. Principal 456', 'ana.gomez@example.com', '555-5678', '123123123123'),
('Carlos López', 'Plaza Central 789', 'carlos.lopez@example.com', '555-8765', 'youarenotreal'),
('Marta Díaz', 'Calle Verde 321', 'marta.diaz@example.com', '555-4321', 'asdasd123321'),
('Luis Torres', 'Av. Norte 654', 'luis.torres@example.com', '555-9876', 'passworddefault');

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
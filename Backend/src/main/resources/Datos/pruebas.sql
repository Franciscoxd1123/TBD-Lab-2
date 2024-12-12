SELECT * FROM categoria;

SELECT * FROM cliente;

SELECT * FROM detalle_orden;

SELECT * FROM log_auditoria;

SELECT * FROM orden;

SELECT * FROM producto;

INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria)
VALUES ('Auto de madera', 'Auto de juguete', 50.99, 1, 'Disponible', 4);

INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria)
VALUES ('Herramienta de jardinería', 'Herramienta para jardinear', 20.00, 1, 'Disponible', 6);

INSERT INTO Categoria (nombre) VALUES
('Jardinería');
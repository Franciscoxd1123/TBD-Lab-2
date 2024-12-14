CREATE DATABASE Ecommerce;

DROP TABLE IF EXISTS log_auditoria;
DROP TABLE IF EXISTS Detalle_Orden;
DROP TABLE IF EXISTS Almacen_Producto;
DROP TABLE IF EXISTS Orden;
DROP TABLE IF EXISTS Producto;
DROP TABLE IF EXISTS Almacen;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Categoria;

CREATE TABLE Categoria(
	id_categoria SERIAL NOT NULL,
	nombre VARCHAR(100),
	PRIMARY KEY (id_categoria)
);

CREATE TABLE Cliente(
	id_cliente SERIAL NOT NULL,
	nombre VARCHAR(255),
	direccion VARCHAR(255),
	email VARCHAR(100),
	telefono VARCHAR(20),
	password VARCHAR(100),
	latitud DOUBLE PRECISION,
	longitud DOUBLE PRECISION,
	location GEOMETRY(Point, 4326),
	PRIMARY KEY (id_cliente)
);

CREATE TABLE Producto(
	id_producto SERIAL NOT NULL,
	nombre VARCHAR(255),
	descripcion TEXT,
	precio DECIMAL(10, 2),
	stock INT,
	estado VARCHAR(50),
	id_categoria INT,
	PRIMARY KEY (id_producto),
	FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

CREATE TABLE Orden(
	id_orden SERIAL NOT NULL,
	fecha_orden TIMESTAMP,
	estado VARCHAR(50),
	id_cliente INT,
	total DECIMAL(10, 2),
	PRIMARY KEY (id_orden),
	FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Detalle_Orden(
	id_detalle SERIAL NOT NULL,
	id_orden INT,
	id_producto INT,
	cantidad INT,
	precio_unitario DECIMAL (10, 2),
	PRIMARY KEY (id_detalle),
	FOREIGN KEY (id_orden) REFERENCES Orden(id_orden),
	FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

CREATE TABLE Almacen (
    id_almacen SERIAL NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    latitud DOUBLE PRECISION,
    longitud DOUBLE PRECISION,
    location GEOMETRY(Point, 4326),
    PRIMARY KEY (id_almacen)
);

CREATE TABLE Almacen_Producto (
    id_almacen_producto SERIAL NOT NULL,
    id_almacen INT,
    id_producto INT,
    cantidad INT NOT NULL,
    fecha_entrada DATE,
    fecha_salida DATE,
    PRIMARY KEY (id_almacen_producto),
    FOREIGN KEY (id_almacen) REFERENCES Almacen(id_almacen),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);

CREATE TABLE log_auditoria (
    id_log SERIAL PRIMARY KEY,
    usuario VARCHAR(255),
    operacion VARCHAR(50),
    tabla VARCHAR(50),
    datos JSONB,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
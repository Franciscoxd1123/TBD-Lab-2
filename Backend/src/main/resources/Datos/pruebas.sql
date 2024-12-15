SELECT * FROM almacen ORDER BY id_almacen;

SELECT * FROM almacen_producto ORDER BY id_almacen_producto;

SELECT * FROM categoria ORDER BY id_categoria;

SELECT * FROM cliente ORDER BY id_cliente;

SELECT * FROM detalle_orden ORDER BY id_detalle;

SELECT * FROM log_auditoria ORDER BY id_log;

SELECT * FROM orden ORDER BY id_orden;

SELECT * FROM producto ORDER BY id_producto ASC;

CALL reporte_usuarios_mas_activos();
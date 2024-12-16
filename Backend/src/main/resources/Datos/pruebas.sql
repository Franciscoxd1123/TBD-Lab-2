SELECT * FROM almacen ORDER BY id_almacen ASC;

SELECT * FROM almacen_producto ORDER BY id_almacen_producto ASC;

SELECT * FROM categoria ORDER BY id_categoria ASC;

SELECT * FROM cliente ORDER BY id_cliente ASC;

SELECT * FROM detalle_orden ORDER BY id_detalle ASC;

SELECT * FROM log_auditoria ORDER BY id_log ASC;

SELECT * FROM orden ORDER BY id_orden ASC;

SELECT * FROM producto ORDER BY id_producto ASC;

CALL reporte_usuarios_mas_activos();
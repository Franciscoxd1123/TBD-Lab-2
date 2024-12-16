/*Ej: CURRENT_DATE = 2024-11-20 entonces DATE_TRUNC('month', CURRENT_DATE) devuelve 2024-11-01 - INTERVAL '1 month' = 2024-10-01 y
fecha_orden >= seria filtrar desde el mes anterior hasta la fecha actual*/

/*¿Cuántos clientes realizaron más de una compra en un mismo día durante el último mes y cuáles fueron los productos que compraron?*/
WITH OrdenesUltimoMes AS (
	SELECT id_cliente, id_orden, DATE(fecha_orden) AS fecha
	FROM Orden
	WHERE fecha_orden >= DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month'
  	AND estado IN ('Pagada', 'Enviada')
),
ClientesMultiplesCompras AS (
    SELECT id_cliente, fecha
    FROM OrdenesUltimoMes
    GROUP BY id_cliente, fecha
    HAVING COUNT(id_orden) > 1
)
SELECT c.id_cliente, c.nombre AS nombre_cliente, p.nombre AS nombre_producto, cmc.fecha AS fecha_compra
FROM ClientesMultiplesCompras cmc
JOIN OrdenesUltimoMes oum ON cmc.id_cliente = oum.id_cliente AND cmc.fecha = oum.fecha
JOIN Detalle_Orden d ON oum.id_orden = d.id_orden
JOIN Producto p ON d.id_producto = p.id_producto
JOIN Cliente c ON cmc.id_cliente = c.id_cliente
ORDER BY c.id_cliente, cmc.fecha, p.nombre;

--Seleccionar las ordenes para verificación
Select id_cliente, id_orden, estado, DATE(fecha_orden) AS fecha
From Orden;

-- Verificar distancias entre los almacenes y los clientes con órdenes enviadas, distancia <= 10km
SELECT
   a.id_almacen,
   a.nombre as almacen,
   c.id_cliente,
   c.nombre as cliente,
   o.id_orden,
   ROUND(ST_Distance(a.location::geography, c.location::geography)::numeric / 1000, 2) as distancia_km
FROM Almacen a
CROSS JOIN Cliente c
INNER JOIN Orden o ON c.id_cliente = o.id_cliente
WHERE a.id_almacen = 1
AND o.estado = 'Enviada'
AND ST_Distance(a.location::geography, c.location::geography) <= 10000; -- 10km en metros

-- Obtener el almacén más cercano a un cliente específico
SELECT
    a.id_almacen,
    a.nombre as almacen,
    c.id_cliente,
    c.nombre as cliente,
    c.direccion as direccion_cliente,
    ROUND(ST_Distance(a.location::geography, c.location::geography)::numeric / 1000, 2) as distancia_km
FROM Cliente c CROSS JOIN Almacen a
WHERE c.id_cliente = 1
ORDER BY distancia_km ASC
LIMIT 1;

--Obtener la ruta más corta entre un almacen y un cliente especifico
SELECT ROUND(CAST(ST_Distance(a.location::geography, c.location::geography) AS numeric), 2) AS distancia
FROM Almacen a, Cliente c
WHERE c.id_cliente = 1 AND a.id_almacen = 1;
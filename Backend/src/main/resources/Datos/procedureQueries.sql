CREATE OR REPLACE PROCEDURE reporte_usuarios_mas_activos()
LANGUAGE plpgsql

DECLARE
    reporte_general RECORD;
    reporte_detallado RECORD;
BEGIN
    -- Reportar los usuarios más activos en general
    RAISE NOTICE 'Usuarios con más operaciones en total:';
    FOR reporte_general IN 
        SELECT 
            usuario,
            COUNT(*) AS total_operaciones
        FROM log_auditoria
        GROUP BY usuario
        ORDER BY total_operaciones DESC
        LIMIT 10
    LOOP
        RAISE NOTICE 'Usuario: %, Total de Operaciones: %', reporte_general.usuario, reporte_general.total_operaciones;
    END LOOP;

    -- Reportar los detalles de operaciones por usuario
    RAISE NOTICE 'Detalle de operaciones por usuario:';
    FOR reporte_detallado IN 
        SELECT 
            usuario,
            operacion,
            COUNT(*) AS total_por_operacion
        FROM log_auditoria
        GROUP BY usuario, operacion
        ORDER BY usuario, total_por_operacion DESC
    LOOP
        RAISE NOTICE 'Usuario: %, Operación: %, Total por Operación: %', reporte_detallado.usuario, reporte_detallado.operacion, reporte_detallado.total_por_operacion;
    END LOOP;
END;

CALL reporte_usuarios_mas_activos();
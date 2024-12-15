/*Trigger que almacene las queries de inserción, actualización o eliminación realizadas por el backend de su aplicación con la respectiva información de usuario, 
tiempo de llamada, etc.*/

-- Función que manejará el trigger
CREATE OR REPLACE FUNCTION fn_registrar_auditoria()
RETURNS TRIGGER AS $$
DECLARE
    datos JSONB;
    operacion VARCHAR(50);
BEGIN
    -- Obtener el tipo de operación
    CASE TG_OP
        WHEN 'INSERT' THEN
            operacion := 'INSERT';
            datos := to_jsonb(NEW);
        WHEN 'UPDATE' THEN
            operacion := 'UPDATE';
            datos := jsonb_build_object(
                'anterior', to_jsonb(OLD),
                'nuevo', to_jsonb(NEW)
            );
        WHEN 'DELETE' THEN
            operacion := 'DELETE';
            datos := to_jsonb(OLD);
    END CASE;

    -- Insertar en la tabla de auditoría
    INSERT INTO log_auditoria (
        usuario,
        operacion,
        tabla,
        datos,
        fecha
    ) VALUES (
        CURRENT_USER,         -- Usuario actual de la base de datos
        operacion,           -- Tipo de operación (INSERT, UPDATE, DELETE)
        TG_TABLE_NAME,       -- Nombre de la tabla
        datos,              -- Datos en formato JSON
        CURRENT_TIMESTAMP    -- Fecha y hora actual
    );

    -- Retornar NEW para INSERT/UPDATE o OLD para DELETE
    IF TG_OP = 'DELETE' THEN
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auditoria_almacen
AFTER INSERT OR UPDATE OR DELETE ON Almacen
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();

CREATE TRIGGER trg_auditoria_almacen_producto
AFTER INSERT OR UPDATE OR DELETE ON Almacen_Producto
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();

CREATE TRIGGER trg_auditoria_categoria
AFTER INSERT OR UPDATE OR DELETE ON Categoria
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();

CREATE TRIGGER trg_auditoria_cliente
AFTER INSERT OR UPDATE OR DELETE ON Cliente
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();

CREATE TRIGGER trg_auditoria_detalle_orden
AFTER INSERT OR UPDATE OR DELETE ON Detalle_Orden
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();

CREATE TRIGGER trg_auditoria_orden
AFTER INSERT OR UPDATE OR DELETE ON Orden
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();

CREATE TRIGGER trg_auditoria_producto
AFTER INSERT OR UPDATE OR DELETE ON Producto
FOR EACH ROW EXECUTE FUNCTION fn_registrar_auditoria();
-- Función unificada para gestionar el inventario con estados
CREATE OR REPLACE FUNCTION fn_gestionar_inventario()
RETURNS TRIGGER AS $$
BEGIN
    -- Determinar la operación
    CASE TG_OP
        WHEN 'INSERT' THEN
            -- Restar del inventario cuando se agrega un producto
            UPDATE producto 
            SET 
                stock = stock - NEW.cantidad,
                estado = CASE 
                    WHEN (stock - NEW.cantidad) <= 0 THEN 'Agotado'
                    ELSE 'Disponible'
                END
            WHERE id_producto = NEW.id_producto;
            
        WHEN 'DELETE' THEN
            -- Sumar al inventario cuando se elimina un producto
            UPDATE producto 
            SET 
                stock = stock + OLD.cantidad,
                estado = CASE 
                    WHEN (stock + OLD.cantidad) > 0 THEN 'Disponible'
                    ELSE 'Agotado'
                END
            WHERE id_producto = OLD.id_producto;
    END CASE;

    -- Retornar el valor apropiado según la operación
    IF TG_OP = 'DELETE' THEN
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Trigger para la tabla detalle_orden
CREATE OR REPLACE TRIGGER trg_gestionar_inventario_detalle
AFTER INSERT OR DELETE ON detalle_orden
FOR EACH ROW
EXECUTE FUNCTION fn_gestionar_inventario();
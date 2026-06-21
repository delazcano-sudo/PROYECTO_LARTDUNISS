-- Creación y selección de la base de datos principal para pedidos
CREATE DATABASE IF NOT EXISTS db_lart_pedidos;
USE db_lart_pedidos;

-- 1. Tabla de Pedidos (Estructura JPA)
CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    fecha_creacion DATE,
    monto_total DOUBLE NOT NULL,
    estado_pedido VARCHAR(50) NOT NULL,
    observaciones VARCHAR(255)
);

-- Poblamiento Inicial (Inserts obligatorios por rúbrica)
INSERT INTO pedidos (id, cliente_id, fecha_creacion, monto_total, estado_pedido, observations) 
VALUES (10000, 55, '2026-06-16', 15500.00, 'PENDIENTE_PAGO', 'Entregar después de las 18:00 hrs');

INSERT INTO pedidos (cliente_id, fecha_creacion, monto_total, estado_pedido, observations) 
VALUES (56, '2026-06-20', 45000.50, 'PAGADO_TOTAL', 'Despacho prioritario a domicilio');

INSERT INTO pedidos (cliente_id, fecha_creacion, monto_total, estado_pedido, observations) 
VALUES (57, '2026-06-21', 12300.00, 'CANCELADO', 'El cliente solicitó anulación');

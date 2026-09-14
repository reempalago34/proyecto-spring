-- V6__seed_data.sql
-- Seed data para todas las tablas del proyecto
-- NOTA: solo inserta si las tablas están vacías

INSERT INTO fincas (nombre, propietario, vereda, municipio, hectareas)
SELECT 'La Esperanza', 'Carlos Rueda', 'El Gualilo', 'Vélez', 12.5
WHERE NOT EXISTS (SELECT 1 FROM fincas LIMIT 1);

INSERT INTO fincas (nombre, propietario, vereda, municipio, hectareas)
SELECT 'La Floresta', 'María Gómez', 'San José', 'Vélez', 8.0
WHERE NOT EXISTS (SELECT 1 FROM fincas WHERE nombre = 'La Floresta');

INSERT INTO cultivos (nombre, tipo, ciclo_dias)
SELECT 'Café Arábica', 'permanente', 365
WHERE NOT EXISTS (SELECT 1 FROM cultivos LIMIT 1);

INSERT INTO cultivos (nombre, tipo, ciclo_dias)
SELECT 'Frijol', 'transitorio', 120
WHERE NOT EXISTS (SELECT 1 FROM cultivos WHERE nombre = 'Frijol');

INSERT INTO categorias (nombre, descripcion)
SELECT 'Bebidas', 'Productos líquidos para consumo'
WHERE NOT EXISTS (SELECT 1 FROM categorias LIMIT 1);

INSERT INTO categorias (nombre, descripcion)
SELECT 'Granos', 'Productos agrícolas secos'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Granos');

INSERT INTO productos (nombre, precio, stock, categoria_id)
SELECT 'Café Premium', 25000.0, 100, (SELECT id FROM categorias WHERE nombre = 'Bebidas')
WHERE NOT EXISTS (SELECT 1 FROM productos LIMIT 1);

INSERT INTO productos (nombre, precio, stock, categoria_id)
SELECT 'Frijol Cargamento', 8000.0, 200, (SELECT id FROM categorias WHERE nombre = 'Granos')
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Frijol Cargamento');

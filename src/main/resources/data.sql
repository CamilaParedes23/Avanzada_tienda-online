-- ===============================
-- DATOS INICIALES DE PRUEBA
-- ===============================

-- Insertar categorías de prueba
INSERT INTO categorias (nombre, descripcion, activa) VALUES
('Camisas', 'Camisas casuales y formales para toda ocasión', true),
('Pantalones', 'Pantalones de vestir y casuales', true),
('Chaquetas', 'Chaquetas y abrigos para todas las estaciones', true),
('Zapatos', 'Calzado elegante y deportivo', true),
('Accesorios', 'Accesorios de moda', true);

-- Insertar productos de prueba
INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id, talla, color, imagen_url, activo) VALUES
('Camisa Blanca Clásica', 'Camisa blanca de algodón premium para oficina', 45.99, 25, 1, 'M', 'Blanco', NULL, true),
('Camisa Azul Oxford', 'Camisa azul estilo oxford para ocasiones casuales', 39.99, 30, 1, 'L', 'Azul', NULL, true),
('Pantalón Negro Formal', 'Pantalón de vestir negro de corte clásico', 89.99, 15, 2, 'L', 'Negro', NULL, true),
('Pantalón Jean Clásico', 'Jean de mezclilla azul tradicional', 59.99, 40, 2, 'M', 'Azul', NULL, true),
('Chaqueta de Cuero', 'Chaqueta de cuero genuino estilo motociclista', 199.99, 8, 3, 'XL', 'Marrón', NULL, true),
('Chaqueta Deportiva', 'Chaqueta deportiva ligera resistente al agua', 79.99, 20, 3, 'M', 'Negro', NULL, true),
('Zapatos Oxford', 'Zapatos de cuero marrón estilo Oxford', 129.99, 12, 4, '42', 'Marrón', NULL, true),
('Zapatillas Deportivas', 'Zapatillas cómodas para uso diario', 89.99, 35, 4, '41', 'Blanco', NULL, true);

-- Insertar clientes de prueba
INSERT INTO clientes (nombre, apellido, email, telefono, direccion, ciudad, codigo_postal, fecha_registro, activo) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', '0991234567', 'Av. Principal 123', 'Quito', '170150', CURRENT_TIMESTAMP, true),
('María', 'González', 'maria.gonzalez@email.com', '0987654321', 'Calle Secundaria 456', 'Guayaquil', '090150', CURRENT_TIMESTAMP, true),
('Carlos', 'López', 'carlos.lopez@email.com', '0998765432', 'Av. Central 789', 'Cuenca', '010150', CURRENT_TIMESTAMP, true);


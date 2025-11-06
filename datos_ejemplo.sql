-- Script SQL para poblar la base de datos con datos de ejemplo
-- Ejecutar después de crear la base de datos tienda_ropa

USE tienda_ropa;

-- Insertar categorías de ejemplo
INSERT INTO categorias (nombre, descripcion, activa) VALUES
('Camisetas', 'Camisetas y tops para todas las ocasiones', true),
('Pantalones', 'Pantalones, jeans y leggins', true),
('Vestidos', 'Vestidos elegantes y casuales', true),
('Zapatos', 'Calzado deportivo y formal', true),
('Accesorios', 'Complementos y accesorios de moda', true);

-- Insertar clientes de ejemplo
INSERT INTO clientes (nombre, apellido, email, telefono, direccion, ciudad, codigo_postal, fecha_registro, activo) VALUES
('María', 'García', 'maria.garcia@email.com', '0991234567', 'Av. Amazonas 123', 'Quito', '170101', NOW(), true),
('Carlos', 'López', 'carlos.lopez@email.com', '0987654321', 'Calle 10 de Agosto 456', 'Quito', '170102', NOW(), true),
('Ana', 'Martínez', 'ana.martinez@email.com', '0976543210', 'Av. 6 de Diciembre 789', 'Quito', '170103', NOW(), true),
('Juan', 'Rodríguez', 'juan.rodriguez@email.com', '0965432109', 'Calle Sucre 321', 'Guayaquil', '090101', NOW(), true),
('Sofía', 'González', 'sofia.gonzalez@email.com', '0954321098', 'Av. 9 de Octubre 654', 'Guayaquil', '090102', NOW(), true);

-- Insertar productos de ejemplo
INSERT INTO productos (nombre, descripcion, precio, categoria, talla, color, stock, imagen_url) VALUES
('Camiseta Básica', 'Camiseta de algodón 100% con corte clásico', 19.99, 'Camisetas', 'M', 'Blanco', 25, 'https://via.placeholder.com/300x300?text=Camiseta+Blanca'),
('Camiseta Básica', 'Camiseta de algodón 100% con corte clásico', 19.99, 'Camisetas', 'L', 'Negro', 30, 'https://via.placeholder.com/300x300?text=Camiseta+Negra'),
('Jeans Clásicos', 'Pantalón denim con corte recto y lavado clásico', 59.99, 'Pantalones', '32', 'Azul', 15, 'https://via.placeholder.com/300x300?text=Jeans+Azul'),
('Jeans Clásicos', 'Pantalón denim con corte recto y lavado clásico', 59.99, 'Pantalones', '34', 'Negro', 20, 'https://via.placeholder.com/300x300?text=Jeans+Negro'),
('Vestido Floral', 'Vestido con estampado floral, perfecto para el verano', 79.99, 'Vestidos', 'S', 'Rosa', 12, 'https://via.placeholder.com/300x300?text=Vestido+Rosa'),
('Vestido Floral', 'Vestido con estampado floral, perfecto para el verano', 79.99, 'Vestidos', 'M', 'Azul', 18, 'https://via.placeholder.com/300x300?text=Vestido+Azul'),
('Sneakers Deportivos', 'Zapatillas cómodas para uso diario', 89.99, 'Zapatos', '42', 'Blanco', 8, 'https://via.placeholder.com/300x300?text=Sneakers+Blancos'),
('Sneakers Deportivos', 'Zapatillas cómodas para uso diario', 89.99, 'Zapatos', '43', 'Negro', 10, 'https://via.placeholder.com/300x300?text=Sneakers+Negros'),
('Gorra Deportiva', 'Gorra ajustable con logo bordado', 24.99, 'Accesorios', 'Única', 'Rojo', 35, 'https://via.placeholder.com/300x300?text=Gorra+Roja'),
('Bufanda de Lana', 'Bufanda tejida perfecta para el invierno', 34.99, 'Accesorios', 'Única', 'Gris', 22, 'https://via.placeholder.com/300x300?text=Bufanda+Gris'),
('Polo Elegante', 'Polo de manga corta con cuello clásico', 39.99, 'Camisetas', 'L', 'Verde', 16, 'https://via.placeholder.com/300x300?text=Polo+Verde'),
('Chaqueta Denim', 'Chaqueta de mezclilla con bolsillos frontales', 69.99, 'Pantalones', 'M', 'Azul', 14, 'https://via.placeholder.com/300x300?text=Chaqueta+Denim'),
('Vestido Cocktail', 'Vestido elegante para ocasiones especiales', 129.99, 'Vestidos', 'M', 'Negro', 6, 'https://via.placeholder.com/300x300?text=Vestido+Cocktail'),
('Botas de Cuero', 'Botas resistentes con suela antideslizante', 149.99, 'Zapatos', '41', 'Marrón', 7, 'https://via.placeholder.com/300x300?text=Botas+Marron'),
('Cinturón de Cuero', 'Cinturón genuino con hebilla metálica', 49.99, 'Accesorios', 'Única', 'Negro', 25, 'https://via.placeholder.com/300x300?text=Cinturon+Negro');

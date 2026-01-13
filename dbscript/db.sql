DROP DATABASE IF EXISTS bd_minimarket;
CREATE DATABASE bd_minimarket;
USE bd_minimarket;

CREATE TABLE tusuario (
    idUsuario CHAR(36) NOT NULL,
    userName VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    rol VARCHAR(30) NOT NULL DEFAULT 'USUARIO',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    PRIMARY KEY (idUsuario),
    UNIQUE KEY uk_usuario_username (userName)
) ENGINE = InnoDB;

CREATE TABLE tcategoria (
    idCategoria CHAR(36) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    PRIMARY KEY (idCategoria)
) ENGINE = InnoDB;

CREATE TABLE tproveedor (
    idProveedor CHAR(36) NOT NULL,
    ruc CHAR(11) NOT NULL,
    razonSocial VARCHAR(150) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    email VARCHAR(100),
    contacto VARCHAR(100),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    PRIMARY KEY (idProveedor),
    UNIQUE KEY uk_proveedor_ruc (ruc)
) ENGINE = InnoDB;

CREATE TABLE tproducto (
    idProducto CHAR(36) NOT NULL,
    codigo VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(300),
    idCategoria CHAR(36) NOT NULL,
    idProveedor CHAR(36),
    precioCompra DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    precioVenta DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    stockActual INT NOT NULL DEFAULT 0,
    stockMinimo INT NOT NULL DEFAULT 5,
    unidadMedida VARCHAR(30) NOT NULL DEFAULT 'UNIDAD',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    PRIMARY KEY (idProducto),
    UNIQUE KEY uk_producto_codigo (codigo),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (idCategoria) REFERENCES tcategoria(idCategoria),
    CONSTRAINT fk_producto_proveedor FOREIGN KEY (idProveedor) REFERENCES tproveedor(idProveedor)
) ENGINE = InnoDB;

CREATE TABLE tmovimiento_inventario (
    idMovimiento CHAR(36) NOT NULL,
    idProducto CHAR(36) NOT NULL,
    tipoMovimiento ENUM('ENTRADA', 'SALIDA') NOT NULL,
    cantidad INT NOT NULL,
    stockAnterior INT NOT NULL,
    stockResultante INT NOT NULL,
    motivo VARCHAR(50) NOT NULL,
    observacion VARCHAR(300),
    documentoReferencia VARCHAR(50),
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    PRIMARY KEY (idMovimiento),
    CONSTRAINT fk_movimiento_producto FOREIGN KEY (idProducto) REFERENCES tproducto(idProducto)
) ENGINE = InnoDB;

INSERT INTO tusuario (idUsuario, userName, password, nombre, email, rol, activo, createdAt, updatedAt) VALUES
('u1a1a1a1-1111-1111-1111-111111111111', 'admin', '123456', 'Administrador del Sistema', 'admin@minimarket.com', 'ADMIN', TRUE, NOW(), NOW()),
('u2a2a2a2-2222-2222-2222-222222222222', 'vendedor', '123456', 'Juan Vendedor', 'vendedor@minimarket.com', 'VENDEDOR', TRUE, NOW(), NOW()),
('u3a3a3a3-3333-3333-3333-333333333333', 'almacen', '123456', 'Pedro Almacén', 'almacen@minimarket.com', 'ALMACEN', TRUE, NOW(), NOW());

INSERT INTO tcategoria (idCategoria, nombre, descripcion, activo, createdAt, updatedAt) VALUES
('c1a1a1a1-1111-1111-1111-111111111111', 'Bebidas', 'Refrescos, jugos, agua y bebidas alcohólicas', TRUE, NOW(), NOW()),
('c2a2a2a2-2222-2222-2222-222222222222', 'Snacks', 'Galletas, papas fritas, dulces y golosinas', TRUE, NOW(), NOW()),
('c3a3a3a3-3333-3333-3333-333333333333', 'Lácteos', 'Leche, queso, yogurt y derivados', TRUE, NOW(), NOW()),
('c4a4a4a4-4444-4444-4444-444444444444', 'Panadería', 'Pan, pasteles y productos de repostería', TRUE, NOW(), NOW()),
('c5a5a5a5-5555-5555-5555-555555555555', 'Limpieza', 'Productos de aseo y limpieza del hogar', TRUE, NOW(), NOW()),
('c6a6a6a6-6666-6666-6666-666666666666', 'Abarrotes', 'Arroz, fideos, aceite y productos básicos', TRUE, NOW(), NOW());

INSERT INTO tproveedor (idProveedor, ruc, razonSocial, direccion, telefono, email, contacto, activo, createdAt, updatedAt) VALUES
('p1a1a1a1-1111-1111-1111-111111111111', '20123456789', 'Distribuidora Gloria S.A.', 'Av. República de Panamá 2461, La Victoria', '01-4705555', 'ventas@gloria.com.pe', 'Carlos Mendoza', TRUE, NOW(), NOW()),
('p2a2a2a2-2222-2222-2222-222222222222', '20234567890', 'Alicorp S.A.A.', 'Av. Argentina 4793, Callao', '01-3150800', 'contacto@alicorp.com.pe', 'María García', TRUE, NOW(), NOW()),
('p3a3a3a3-3333-3333-3333-333333333333', '20345678901', 'Backus y Johnston S.A.A.', 'Jr. Chiclayo 594, Rímac', '01-4621800', 'ventas@backus.com.pe', 'Juan Pérez', TRUE, NOW(), NOW()),
('p4a4a4a4-4444-4444-4444-444444444444', '20456789012', 'Procter & Gamble Perú', 'Av. Petit Thouars 4653, Miraflores', '01-6170000', 'peru.info@pg.com', 'Ana López', TRUE, NOW(), NOW());

INSERT INTO tproducto (idProducto, codigo, nombre, descripcion, idCategoria, idProveedor, precioCompra, precioVenta, stockActual, stockMinimo, unidadMedida, activo, createdAt, updatedAt) VALUES
('pr1a1a1a-1111-1111-1111-111111111111', 'BEB001', 'Gaseosa Coca-Cola 500ml', 'Botella personal de Coca-Cola', 'c1a1a1a1-1111-1111-1111-111111111111', 'p3a3a3a3-3333-3333-3333-333333333333', 1.80, 2.50, 48, 10, 'UNIDAD', TRUE, NOW(), NOW()),
('pr2a2a2a-2222-2222-2222-222222222222', 'BEB002', 'Agua San Luis 625ml', 'Botella de agua mineral', 'c1a1a1a1-1111-1111-1111-111111111111', 'p3a3a3a3-3333-3333-3333-333333333333', 0.80, 1.50, 72, 20, 'UNIDAD', TRUE, NOW(), NOW()),
('pr3a3a3a-3333-3333-3333-333333333333', 'SNK001', 'Galletas Oreo 119g', 'Paquete de galletas Oreo', 'c2a2a2a2-2222-2222-2222-222222222222', 'p2a2a2a2-2222-2222-2222-222222222222', 2.20, 3.50, 36, 12, 'PAQUETE', TRUE, NOW(), NOW()),
('pr4a4a4a-4444-4444-4444-444444444444', 'LAC001', 'Leche Gloria 1L', 'Tarro de leche evaporada', 'c3a3a3a3-3333-3333-3333-333333333333', 'p1a1a1a1-1111-1111-1111-111111111111', 3.80, 5.00, 60, 15, 'TARRO', TRUE, NOW(), NOW()),
('pr5a5a5a-5555-5555-5555-555555555555', 'LAC002', 'Yogurt Gloria 1L', 'Yogurt natural de 1 litro', 'c3a3a3a3-3333-3333-3333-333333333333', 'p1a1a1a1-1111-1111-1111-111111111111', 4.50, 6.50, 24, 8, 'BOTELLA', TRUE, NOW(), NOW()),
('pr6a6a6a-6666-6666-6666-666666666666', 'LIM001', 'Detergente Ariel 850g', 'Detergente en polvo para ropa', 'c5a5a5a5-5555-5555-5555-555555555555', 'p4a4a4a4-4444-4444-4444-444444444444', 12.00, 16.00, 18, 5, 'BOLSA', TRUE, NOW(), NOW()),
('pr7a7a7a-7777-7777-7777-777777777777', 'ABR001', 'Arroz Costeño 5kg', 'Bolsa de arroz extra de 5 kilos', 'c6a6a6a6-6666-6666-6666-666666666666', 'p2a2a2a2-2222-2222-2222-222222222222', 18.00, 23.00, 30, 10, 'BOLSA', TRUE, NOW(), NOW()),
('pr8a8a8a-8888-8888-8888-888888888888', 'ABR002', 'Aceite Primor 1L', 'Botella de aceite vegetal', 'c6a6a6a6-6666-6666-6666-666666666666', 'p2a2a2a2-2222-2222-2222-222222222222', 7.50, 10.00, 42, 12, 'BOTELLA', TRUE, NOW(), NOW());

INSERT INTO tmovimiento_inventario (idMovimiento, idProducto, tipoMovimiento, cantidad, stockAnterior, stockResultante, motivo, observacion, documentoReferencia, createdAt, updatedAt) VALUES
('m1a1a1a1-1111-1111-1111-111111111111', 'pr1a1a1a-1111-1111-1111-111111111111', 'ENTRADA', 50, 0, 50, 'COMPRA', 'Compra inicial de inventario', 'FAC-001-0001', NOW(), NOW()),
('m2a2a2a2-2222-2222-2222-222222222222', 'pr1a1a1a-1111-1111-1111-111111111111', 'SALIDA', 2, 50, 48, 'VENTA', 'Venta al público', 'BOL-001-0001', NOW(), NOW()),
('m3a3a3a3-3333-3333-3333-333333333333', 'pr4a4a4a-4444-4444-4444-444444444444', 'ENTRADA', 60, 0, 60, 'COMPRA', 'Reposición de stock', 'FAC-001-0002', NOW(), NOW());
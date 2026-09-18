-- CREATE DATABASE crea una nueva base de datos.
CREATE DATABASE GestionPedidos;

-- USE selecciona la base de datos donde se crearán las tablas.
USE GestionPedidos;

-- CREATE TABLE crea una tabla con sus columnas, claves y restricciones.
CREATE TABLE CLIENTES (
    ID_Cliente INT PRIMARY KEY,
    NombreCliente VARCHAR(100),
    Direccion VARCHAR(150),
    Telefono VARCHAR(15)
);

CREATE TABLE PROVEEDORES (
    ID_Proveedor INT PRIMARY KEY,
    NombreProveedor VARCHAR(100),
    DireccionProveedor VARCHAR(150),
    TelefonoProveedor VARCHAR(15)
);

CREATE TABLE PRODUCTOS (
    ID_Producto INT PRIMARY KEY,
    NombreProducto VARCHAR(100),
    Precio DECIMAL(10, 2),
    Stock INT,
    ID_Proveedor INT,
    FOREIGN KEY (ID_Proveedor) REFERENCES PROVEEDORES(ID_Proveedor)
);

CREATE TABLE PEDIDOS (
    ID_Pedido INT PRIMARY KEY,
    FechaPedido DATE,
    ID_Cliente INT,
    ID_Producto INT,
    Cantidad INT,
    PUnitario DECIMAL(10, 2),
    FOREIGN KEY (ID_Cliente) REFERENCES CLIENTES(ID_Cliente),
    FOREIGN KEY (ID_Producto) REFERENCES PRODUCTOS(ID_Producto)
);
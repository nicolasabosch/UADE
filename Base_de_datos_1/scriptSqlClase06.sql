-- Paso 1: Creo la base de datos
CREATE DATABASE Empresa;
GO

USE Empresa;
GO


-- Paso 2: Crear el esquema RecursosHumanos
CREATE SCHEMA RecursosHumanos;
GO

-- Paso 3: Crear la tabla Departamento
CREATE TABLE RecursosHumanos.Departamento
(
    IdDepartamento     INT          NOT NULL,
    NombreDepartamento NVARCHAR(50) NOT NULL,

    CONSTRAINT PK_Departamento
        PRIMARY KEY (IdDepartamento)
);
GO

-- Paso 4: Crear la tabla Categoria
CREATE TABLE RecursosHumanos.Categoria
(
    IdCategoria     INT          NOT NULL,
    NombreCategoria NVARCHAR(50) NOT NULL,

    CONSTRAINT PK_Categoria
        PRIMARY KEY (IdCategoria)
);
GO

-- Paso 5: Crear la tabla Empleados y sus claves foraneas
CREATE TABLE RecursosHumanos.Empleados
(
    IdEmpleado      INT            NOT NULL,
    Nombre          NVARCHAR(50)   NOT NULL,
    Salario         DECIMAL(10, 2) NOT NULL,
    IdDepartamento  INT            NULL,
    IdCategoria     INT            NULL,

    CONSTRAINT PK_Empleados
        PRIMARY KEY (IdEmpleado),

    CONSTRAINT FK_Empleados_Departamento
        FOREIGN KEY (IdDepartamento)
        REFERENCES RecursosHumanos.Departamento (IdDepartamento),

    CONSTRAINT FK_Empleados_Categoria
        FOREIGN KEY (IdCategoria)
        REFERENCES RecursosHumanos.Categoria (IdCategoria)
);
GO

-- Paso 6: Crear la tabla Hijos
CREATE TABLE RecursosHumanos.Hijos
(
    IdHijo          INT          NOT NULL,
    IdEmpleado      INT          NULL,
    NombreHijo      NVARCHAR(50) NULL,
    FechaNacimiento DATE         NULL,

    CONSTRAINT PK_Hijos
        PRIMARY KEY (IdHijo),

    CONSTRAINT FK_Hijos_Empleados
        FOREIGN KEY (IdEmpleado)
        REFERENCES RecursosHumanos.Empleados (IdEmpleado)
);
GO

-- Paso 7: Crear la tabla Estructura
CREATE TABLE RecursosHumanos.Estructura
(
    IdEstructura INT           NOT NULL,
    Descripcion  NVARCHAR(100) NULL,

    CONSTRAINT PK_Estructura
        PRIMARY KEY (IdEstructura)
);
GO

-- Paso 8: Crear inicialmente la tabla Estudios
CREATE TABLE RecursosHumanos.Estudios
(
    IdEstudio  INT           NOT NULL,
    Descripcion NVARCHAR(100) NULL,

    CONSTRAINT PK_Estudios
        PRIMARY KEY (IdEstudio)
);
GO

-- Paso 9: Crear indices en las columnas Nombre y Salario de Empleados
CREATE INDEX IX_Empleados_Nombre
    ON RecursosHumanos.Empleados (Nombre);
GO

CREATE INDEX IX_Empleados_Salario
    ON RecursosHumanos.Empleados (Salario);
GO

-- Paso 10: Asegurar que todo empleado tenga un salario mayor o igual a 1000
ALTER TABLE RecursosHumanos.Empleados
    ADD CONSTRAINT CK_Empleados_SalarioMinimo
        CHECK (Salario >= 1000);
GO

-- Paso 11: Impedir nombres nulos en la tabla Hijos
ALTER TABLE RecursosHumanos.Hijos
    ALTER COLUMN NombreHijo NVARCHAR(50) NOT NULL;
GO

-- Paso 12: Agregar FechaContratacion a Empleados
ALTER TABLE RecursosHumanos.Empleados
    ADD FechaContratacion DATE NULL;
GO

-- Paso 13: Eliminar FechaContratacion y eliminar completamente Estudios
ALTER TABLE RecursosHumanos.Empleados
    DROP COLUMN FechaContratacion;
GO

DROP TABLE RecursosHumanos.Estudios;
GO

-- Paso 14: Crear nuevamente Estudios y relacionarla con Empleados
-- La clave foranea en Estudios permite registrar varios estudios por empleado.
CREATE TABLE RecursosHumanos.Estudios
(
    IdEstudio  INT           NOT NULL,
    IdEmpleado INT           NOT NULL,
    Descripcion NVARCHAR(100) NULL,

    CONSTRAINT PK_Estudios
        PRIMARY KEY (IdEstudio),

    CONSTRAINT FK_Estudios_Empleados
        FOREIGN KEY (IdEmpleado)
        REFERENCES RecursosHumanos.Empleados (IdEmpleado)
);
GO
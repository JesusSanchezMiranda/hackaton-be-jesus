USE master;

-- Verificar si la base de datos existe antes de eliminarla
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'fichasBD')
BEGIN
    -- Desconectar a todos los usuarios de la base de datos
    ALTER DATABASE fichasBD SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    -- Eliminar la base de datos si existe
    DROP DATABASE fichasBD;
    PRINT 'Base de datos eliminada.'
END
ELSE
BEGIN
    PRINT 'La base de datos no existe, no es necesario eliminarla.'
END
GO

-- Crear la base de datos
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'fichasBD')
BEGIN
    CREATE DATABASE fichasBD;
    PRINT 'Base de datos creada.'
END
ELSE
BEGIN
    PRINT 'La base de datos ya existe.'
END
GO


USE fichasBD;

CREATE TABLE fichas (
    id INT IDENTITY(1,1),
    autor NVARCHAR(255) NOT NULL CHECK (autor NOT LIKE '%[^A-Za-z áéíóúÁÉÍÓÚñÑ]%'),
    titulo NVARCHAR(255) NOT NULL,
    anio_publicacion DATE NOT NULL CHECK (YEAR(anio_publicacion) BETWEEN 1000 AND YEAR(GETDATE())),
    tema NVARCHAR(100) NOT NULL,
    fecha_agregada DATE NOT NULL DEFAULT GETDATE(),

    tipo_ficha NVARCHAR(20) NOT NULL CHECK (
        tipo_ficha IN ('libro', 'artículo', 'tesis', 'video', 'periódico', 'repositorio')
    ),

    editorial NVARCHAR(150) NULL,
    numero_edicion INT NULL CHECK (numero_edicion > 0),
    numero_paginas INT NULL CHECK (numero_paginas > 0),

    estado BIT NOT NULL DEFAULT 1

	CONSTRAINT fiichas_pk PRIMARY KEY (id),
	CONSTRAINT unique_titulo UNIQUE (titulo)
);


INSERT INTO fichas (autor, titulo, anio_publicacion, tema, tipo_ficha, editorial, numero_edicion, numero_paginas)
VALUES ('Gabriel García Márquez', 'Cien Años de Soledad', '1967-05-30', 'Literatura', 'libro', 'Editorial Sudamericana', 1, 417);


INSERT INTO fichas (autor, titulo, anio_publicacion, tema, tipo_ficha, editorial, numero_edicion, numero_paginas)
VALUES ('Ana Torres', 'Impacto del cambio climático en los Andes', '2022-11-12', 'Ciencias Ambientales', 'artículo', 'Revista Andina de Medio Ambiente', 2, 12);



INSERT INTO fichas (autor, titulo, anio_publicacion, tema, tipo_ficha, editorial, numero_edicion, numero_paginas)
VALUES ('Carlos Mendoza', 'Análisis de algoritmos genéticos en inteligencia artificial', '2021-07-01', 'Ingeniería de Sistemas', 'tesis', 'Universidad Nacional', 1, 150);


INSERT INTO fichas (autor, titulo, anio_publicacion, tema, tipo_ficha)
VALUES ('Laura Ramírez', 'La historia de la computación', '2020-09-10', 'Tecnología', 'video');

INSERT INTO fichas (autor, titulo, anio_publicacion, tema, tipo_ficha)
VALUES ('Diario El Comercio', 'Avances en la ciencia médica peruana', '2023-03-20', 'Salud', 'periódico');


INSERT INTO fichas (autor, titulo, anio_publicacion, tema, tipo_ficha)
VALUES ('Repositorio UNMSM', 'Base de datos de investigaciones 2023', '2023-12-01', 'Investigación Académica', 'repositorio');




SELECT * FROM fichas WHERE estado = 1;

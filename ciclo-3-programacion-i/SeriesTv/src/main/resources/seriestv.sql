-- Crear la base de datos 

CREATE DATABASE seriestv; 

-- Seleccionar la base de datos para usarla 

USE seriestv; 

 

-- Crear la tabla serie 

CREATE TABLE serie ( 

    id INT(11) AUTO_INCREMENT PRIMARY KEY, 

    codigo VARCHAR(10) NOT NULL, 

    titulo VARCHAR(200) NOT NULL, 

    fecha DATE NOT NULL, 

    temporadas VARCHAR(45) NOT NULL, 

    genero VARCHAR(45) NOT NULL, 

    actores VARCHAR(200) NOT NULL, 

    sinopsis TEXT 

); 
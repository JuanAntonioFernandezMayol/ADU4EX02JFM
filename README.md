Descripción del Proyecto

Este proyecto es un ejemplo básico del uso de Hibernate para gestionar la persistencia de datos en una base de datos relacional. En este caso, modelamos una relación entre dos entidades: Autor y Llibre (libro en catalán), donde un autor puede tener varios libros asociados.

El programa realiza las siguientes operaciones principales:

Configura Hibernate utilizando un archivo hibernate.cfg.xml.

Crea y persiste un autor y su libro asociado en la base de datos.

Gestiona transacciones y el manejo de errores para garantizar la integridad de los datos.

Estructura del Proyecto

Clases

Autor:

Representa a un autor con atributos como id, nom (nombre), y dataNaixement (fecha de nacimiento).

Tiene una relación de uno a muchos con la clase Llibre.

Llibre:

Representa un libro con atributos como id, titol (título), y anyPublicacio (año de publicación).

Tiene una relación de muchos a uno con la clase Autor.

Main:

Contiene el método principal (main) que realiza las operaciones de persistencia.

Configura Hibernate, inicia una sesión y una transacción, persiste los objetos y maneja posibles errores.

Relación entre las Clases

Autor tiene una lista de libros (llibreList).

Llibre contiene una referencia al autor al que pertenece.

Estas relaciones se gestionan utilizando las anotaciones de JPA (Jakarta Persistence API):

@OneToMany y @ManyToOne.

@JoinColumn para definir la clave foránea en la tabla correspondiente.

Archivos de Configuración

hibernate.cfg.xml: Archivo de configuración de Hibernate que contiene los detalles de la conexión a la base de datos, como la URL, el nombre de usuario, la contraseña y el dialecto de SQL.

Requisitos

Java 22 o superior.

Hibernate y sus dependencias (incluidas en el archivo pom.xml si se utiliza Maven).

Una base de datos relacional compatible con Hibernate (por ejemplo, MySQL, PostgreSQL, H2, etc.).

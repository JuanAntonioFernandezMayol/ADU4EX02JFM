Descripción del Proyecto

Este proyecto usa Hibernate para gestionar la persistencia de datos en una base de datos relacional. En este caso, modelamos una relación entre dos entidades: Autor y Llibre, donde un autor puede tener varios libros asociados.
El programa realiza las siguientes operaciones principales:

Configuración dinámica de Hibernate: Las credenciales de la base de datos se leen desde un archivo externo (bbdd_connection.txt).
Gestión de operaciones CRUD: Permite añadir, listar, editar y eliminar autores y sus libros relacionados.
Control de transacciones: Garantiza la integridad de los datos mediante transacciones gestionadas con Hibernate.
Interfaz por consola: El usuario interactúa mediante un menú básico para realizar las operaciones.

Estructura del Proyecto
Clases

Autor:
Representa a un autor con atributos como id, nom (nombre), y dataNaixement (fecha de nacimiento).
Tiene una relación de uno a muchos con la clase Llibre.

Llibre:
Representa un libro con atributos como id, titol (título), y anyPublicacio (año de publicación).
Tiene una relación de muchos a uno con la clase Autor.

Main:
Contiene el método principal (main) que realiza las operaciones de persistencia y gestiona el flujo del programa.
Configura Hibernate dinámicamente utilizando las propiedades leídas del archivo externo.
Implementa las operaciones CRUD con menús interactivos y manejo básico de errores.

Relación entre las Clases
Autor tiene una lista de libros (llibreList).
Llibre contiene una referencia al autor al que pertenece.

Estas relaciones se gestionan utilizando las anotaciones de JPA (Jakarta Persistence API):
@OneToMany y @ManyToOne.
@JoinColumn para definir la clave foránea en la tabla correspondiente.

Archivos de Configuración
bbdd_connection.txt
Archivo de texto externo que contiene las credenciales de la base de datos.

Requisitos
Java 22 o superior.
Hibernate y sus dependencias (incluidas en el archivo pom.xml si se utiliza Maven).
Una base de datos relacional compatible con Hibernate (por ejemplo, MySQL, PostgreSQL, H2, etc.).
package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    // Fábrica de sesiones de Hibernate
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        // Cargar las propiedades de la base de datos desde un archivo externo
        Properties dbProperties = loadDatabaseProperties("C:\\Users\\pukyt\\IdeaProjects\\ADU4EX1\\src\\main\\java\\org\\example\\bbdd_connection.txt");

        // Configurar Hibernate con las propiedades cargadas
        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.url", dbProperties.getProperty("url")) // URL de la base de datos
                .setProperty("hibernate.connection.username", dbProperties.getProperty("username")) // Usuario
                .setProperty("hibernate.connection.password", dbProperties.getProperty("password")) // Contraseña
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect") // Dialecto de MySQL
                .setProperty("hibernate.hbm2ddl.auto", "update") // Actualizar esquema automáticamente
                .addAnnotatedClass(Autor.class) // Añadir la clase Autor
                .addAnnotatedClass(Llibre.class) // Añadir la clase Llibre
                .buildSessionFactory();

        try (Scanner scanner = new Scanner(System.in)) {
            // Menú principal para el usuario
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Añadir autor y libro");
                System.out.println("2. Listar autores y libros");
                System.out.println("3. Editar autor");
                System.out.println("4. Eliminar autor");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                // Ejecutar la opción seleccionada
                switch (opcion) {
                    case 1:
                        addAuthorAndBook(scanner);
                        break;
                    case 2:
                        listAuthorsAndBooks();
                        break;
                    case 3:
                        editAuthor(scanner);
                        break;
                    case 4:
                        deleteAuthor(scanner);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        return; // Salir del programa
                    default:
                        System.out.println("Opción no válida."); // Validar entrada del usuario
                }
            }
        } finally {
            // Cerrar la fábrica de sesiones al terminar
            sessionFactory.close();
        }
    }

    // Método para cargar las propiedades desde un archivo de texto
    private static Properties loadDatabaseProperties(String filePath) {
        Properties properties = new Properties();
        try {
            // Leer el archivo con las propiedades
            properties.load(Files.newBufferedReader(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de configuración de la base de datos", e);
        }
        return properties;
    }

    // Añadir un nuevo autor y su libro
    private static void addAuthorAndBook(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction(); // Iniciar transacción

            System.out.print("Nombre del autor: ");
            String nombreAutor = scanner.nextLine();

            // Verificar si el autor ya existe en la base de datos
            Query<Autor> query = session.createQuery("FROM Autor WHERE nom = :nombre", Autor.class);
            query.setParameter("nombre", nombreAutor);
            Autor autorExistente = query.uniqueResult();

            if (autorExistente != null) {
                System.out.println("El autor ya existe con ID: " + autorExistente.getId());
            } else {
                // Crear un nuevo autor
                Autor autor = new Autor();
                autor.setNom(nombreAutor);

                System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
                autor.setDataNaixement(java.sql.Date.valueOf(scanner.nextLine()));

                // Crear un nuevo libro asociado al autor
                System.out.print("Título del libro: ");
                String tituloLibro = scanner.nextLine();

                System.out.print("Año de publicación: ");
                int anyPublicacio = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                Llibre llibre = new Llibre();
                llibre.setTitol(tituloLibro);
                llibre.setAnyPublicacio(anyPublicacio);
                llibre.setAutor(autor);

                autor.setLlibreList(List.of(llibre)); // Asociar el libro al autor

                session.persist(autor); // Guardar el autor (y el libro) en la base de datos
                session.getTransaction().commit();
                System.out.println("Autor y libro añadidos correctamente.");
            }
        }
    }

    // Listar todos los autores y sus libros
    private static void listAuthorsAndBooks() {
        try (Session session = sessionFactory.openSession()) {
            // Obtener todos los autores de la base de datos
            List<Autor> autores = session.createQuery("FROM Autor", Autor.class).list();
            for (Autor autor : autores) {
                System.out.println("Autor: " + autor.getNom() + " (ID: " + autor.getId() + ")");
                for (Llibre llibre : autor.getLlibreList()) {
                    System.out.println("  Libro: " + llibre.getTitol() + " (" + llibre.getAnyPublicacio() + ")");
                }
            }
        }
    }

    // Editar un autor existente
    private static void editAuthor(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction(); // Iniciar transacción

            System.out.print("ID del autor a editar: ");
            int autorId = scanner.nextInt();
            scanner.nextLine();

            Autor autor = session.get(Autor.class, autorId); // Buscar autor por ID
            if (autor == null) {
                System.out.println("Autor no encontrado.");
                return;
            }

            System.out.print("Nuevo nombre del autor: ");
            autor.setNom(scanner.nextLine());

            System.out.print("Nueva fecha de nacimiento (yyyy-MM-dd): ");
            autor.setDataNaixement(java.sql.Date.valueOf(scanner.nextLine()));

            session.getTransaction().commit(); // Confirmar los cambios
            System.out.println("Autor actualizado correctamente.");
        }
    }

    // Eliminar un autor por ID
    private static void deleteAuthor(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("ID del autor a eliminar: ");
            int autorId = scanner.nextInt();
            scanner.nextLine();

            Autor autor = session.get(Autor.class, autorId); // Buscar autor por ID
            if (autor == null) {
                System.out.println("Autor no encontrado.");
                return;
            }

            session.remove(autor); // Eliminar autor y sus libros asociados
            session.getTransaction().commit();
            System.out.println("Autor eliminado correctamente.");
        }
    }
}

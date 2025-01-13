package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.example.Autor;
import org.example.Llibre;
import java.util.List;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Configuración de Hibernate
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // Inicia una transacción
            session.beginTransaction();

            // Nombre del autor que se desea agregar
            String nombreAutor = "Juan Antonio Fernandez Mayol";

            // Verificar si el autor ya existe en la base de datos
            Query<Autor> query = session.createQuery("FROM Autor WHERE nom = :nombre", Autor.class);
            query.setParameter("nombre", nombreAutor);
            Autor autorExistente = query.uniqueResult();

            if (autorExistente != null) {
                // Si el autor ya existe, mostrar un mensaje y no insertar los datos
                System.out.println("El autor con el nombre '" + nombreAutor + "' ya existe en la base de datos con ID: " + autorExistente.getId());
            } else {
                // Crear un Autor nuevo
                Autor autor = new Autor();
                autor.setNom(nombreAutor); // Nombre del autor
                autor.setDataNaixement(new Date()); // Fecha de nacimiento actual

                // Crear un Llibre asociado
                Llibre llibre = new Llibre();
                llibre.setTitol("Un atardecer bonito"); // Título del libro
                llibre.setAnyPublicacio(1937); // Año de publicación
                llibre.setAutor(autor); // Asociar el libro al autor

                // Añadir el libro al autor
                autor.setLlibreList(List.of(llibre));

                // Persistir los objetos
                session.persist(autor);

                // Confirmar la transacción
                session.getTransaction().commit();
                System.out.println("Datos insertados correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}

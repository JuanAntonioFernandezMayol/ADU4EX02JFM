package org.example;

import jakarta.persistence.*;

@Entity // Indica que esta clase es una entidad gestionada por Hibernate.
public class Llibre {
    @Id // Define el atributo `id` como la clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate genera el ID automáticamente.
    private int id;

    private String titol; // Título del libro.
    private int anyPublicacio; // Año de publicación del libro.

    @ManyToOne // Relación de muchos a uno con la clase `Autor`.
    @JoinColumn(name = "autor_id") // Especifica la clave foránea que conecta con la tabla `Autor`.
    private Autor autor;

    // Métodos getter y setter para cada atributo.
    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public int getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(int anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}


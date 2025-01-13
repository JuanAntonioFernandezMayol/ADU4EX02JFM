package org.example;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity // Indica que esta clase es una entidad gestionada por Hibernate.
public class Autor {
    @Id // Define el atributo `id` como la clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate genera el ID automáticamente.
    private int id;

    private String nom; // Nombre del autor.

    @Temporal(TemporalType.DATE) // Indica que el atributo es una fecha.
    private Date dataNaixement; // Fecha de nacimiento del autor.

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true) // Relación uno a muchos con `Llibre`.
    private List<Llibre> llibreList; // Lista de libros asociados al autor.

    // Métodos getter y setter para cada atributo.
    public List<Llibre> getLlibreList() {
        return llibreList;
    }

    public void setLlibreList(List<Llibre> llibreList) {
        this.llibreList = llibreList;
    }

    public Date getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

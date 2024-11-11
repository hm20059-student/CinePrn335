package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "pelicula", schema = "public")

@NamedQueries({
        @NamedQuery(name = "Pelicula.findAll", query = "SELECT p FROM Pelicula p"),
        @NamedQuery(name = "Pelicula.countAll", query = "SELECT Count(p) FROM Pelicula p"),
        @NamedQuery(name = "Pelicula.findByIdPelicula", query = "SELECT p FROM Pelicula p WHERE p.idPelicula = :idPelicula"),
        @NamedQuery(name = "Pelicula.findByNombre", query = "SELECT p FROM Pelicula p WHERE p.nombre = :nombre"),
        @NamedQuery(name = "Pelicula.findBySinopsis", query = "SELECT p FROM Pelicula p WHERE p.sinopsis = :sinopsis")
})

public class Pelicula implements Serializable {
    @Id
    @Column(name = "id_pelicula", nullable = false)
    private Long idPelicula;

    @NotBlank
    @Size(max = 255, min = 5)
    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "sinopsis")
    private String sinopsis;

    public Pelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Pelicula() {

    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long id) {
        this.idPelicula = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

}
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;

@Entity
@Table(name = "tipo_sala", schema = "public")
public class TipoSala implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_sala", nullable = false)
    private Integer idTipoSala;

//    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d\\d\\d-\\d", message = "agregar un DUI valido")
    @NotBlank(message = "debe tener un nombre valido")
//    @Min(value=5, message = "nombre debe de poseer almenos 5 catarteres")
//    @Max(value=155, message = "nombre debe de poseer maximo 155 catarteres")
    @Size(max = 155, min = 3)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "expresion_regular")
    private String expresionRegular;

    public TipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public TipoSala() {
    }

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer id) {
        this.idTipoSala = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

}
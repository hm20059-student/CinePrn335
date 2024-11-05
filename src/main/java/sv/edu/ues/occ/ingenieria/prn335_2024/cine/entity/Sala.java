package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "sala", schema = "public")

@NamedQueries({
        @NamedQuery(name= "Sala.findByIdTipoSala",
                query= "SELECT s FROM SalaCaracteristica sc JOIN sc.idSala s WHERE sc.idTipoSala.idTipoSala=:idTipoSala GROUP BY s.idSala ORDER BY s.nombre ASC")
})

//query= "select s from SalaCaracteristica sc where sc.idTipoSala.idTipoSala="+idTipoSala+" " +
//        "JOIN sc.idSala s group by s.idSala order by s.nombre ASC"
public class Sala implements Serializable {
    @Id
    @Column(name = "id_sala", nullable = false)
    private Integer idSala;
//aqui no me interesa el lazy porque no quiero saber que asientos permanece la sala
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal")
    private Sucursal idSucursal;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    public Sala(Integer idSala) {
        this.idSala = idSala;
    }
    public Sala() {

    }

    @OneToMany(fetch =  FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idSala")

//    public Integer getIdSala() {
//        return idSala;
//    }

//    public void setIdSala(Integer id) {
//        this.idSala = id;
//    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
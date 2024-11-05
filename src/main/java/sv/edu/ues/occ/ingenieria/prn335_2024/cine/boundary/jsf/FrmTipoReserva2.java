package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoReserva2 implements Serializable {
    @Inject
    TipoReservaBean trBean;
@Inject
FacesContext fc;

    TipoReserva registro;
    List<TipoReserva> registros;

    ESTADO_CRUD estado;

    @PostConstruct
    public void inicializar(){
//        estado = ESTADO_CRUD.NINGUNO ;
        this.registros = trBean.findRange(1,1000000);
    }
    public TipoReserva getRegistro() {
        return registro;
    }

    public void setRegistro(TipoReserva registro) {
        this.registro = registro;
    }

    public List<TipoReserva> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoReserva> registros) {
        this.registros = registros;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

}

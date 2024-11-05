package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoSala2 implements Serializable {
    @Inject
    TipoSalaBean tsBean;
    ESTADO_CRUD estado;

    @Inject
    FacesContext facesContext;

     List<TipoSala> registros;
     TipoSala registro;

     @PostConstruct
    public void init() {
        estado = ESTADO_CRUD.NINGUNO;
        registros = getUpdateList();
    }
    public List<TipoSala> getRegistros() {
        return registros;
    }
    public void setRegistros(List<TipoSala> registros) {
        this.registros = registros;
    }
    public void btnSeleccionarRegistroHandler(final Integer idTipoSala) {
        if (idTipoSala!=null){
            this.registro = this.registros.stream().filter(t -> t.getIdTipoSala() == idTipoSala).
                    findFirst().orElse(null);
            this.estado=ESTADO_CRUD.MODIFICAR;
            return;
        }
        this.registro=null;

    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = new TipoSala();
        this.registro.setActivo(true);
        this.registro.setExpresionRegular(".");
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void btnGuardarHandler(ActionEvent actionEvent) {

        this.tsBean.create(registro);
        this.registro = null;
        this.registros = tsBean.findRange(0, 100000);
        this.estado = ESTADO_CRUD.NINGUNO;
    }



    public void btnModificarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje = new FacesMessage();
        TipoSala actualizado = tsBean.update(registro);
        if (actualizado == null) {
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro modificado con exito");
        }else {
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setDetail("No se pudo guardar el registro, revise sus datos");
        }
        facesContext.addMessage(null, mensaje);

    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje = new FacesMessage();
        tsBean.delete(registro);
        mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
        mensaje.setSummary("registro eliminado con exito");
        facesContext.addMessage(null, mensaje);
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;

    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }
    public Integer getSeleccionado() {
        if (registro == null) {
            return null;
        }
        return registro.getIdTipoSala();
    }
    public void setSelecionado(Integer selecionado) {
        if (selecionado == null || this.registros == null) {
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            return;
        }

        this.registro = this.registros.stream()
                .filter(r -> r.getIdTipoSala().equals(selecionado))
                .findFirst()
                .orElse(null);

        if (this.registro == null) {
            this.estado = ESTADO_CRUD.NINGUNO;
        }
    }


//    public void setSeleccionado(Integer seleccionado) {
//        this.registro = this.registros.stream().filter(r -> r.getIdTipoSala() == seleccionado).collect(Collectors.toList()).getFirst();
//        if (this.registro != null) {
//            this.estado = ESTADO_CRUD.MODIFICAR;
//        }
////        this.registro=this.registros.stream().filter(r -> r.getIdTipoSala()==seleccionado).collect(Collectors.toList()).getFirst();
//    }

    public TipoSala getRegistro() {
        return registro;
    }

    public void setRegistro(TipoSala registro) {
        this.registro = registro;
    }



    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }
    public List<TipoSala> getUpdateList() {
        return tsBean.findRange(0,10000);
    }

}

package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoSala extends AbstractFrm<TipoSala> implements Serializable {

    @Inject
    TipoSalaBean tsBean;
    @Inject
    FacesContext facesContext;
    ESTADO_CRUD estado;

    @Override
    public void instanciarRegistro() {
        registro=new TipoSala();
        registro.setActivo(true);
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Sala";
    }

    @Override
    public AbstractDataPersistence<TipoSala> getTAbstractDataPersistence() {
        return tsBean;
    }

    @Override
    public FacesContext getFacesContext() {return facesContext;}

    @Override
    public String getIdPorObjeto(TipoSala object) {
        if (object.getIdTipoSala()!=null){
            return object.getIdTipoSala().toString();
        }
        return null;
    }

    @Override
    public TipoSala getObjetoPorId(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null){

            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdTipoSala().toString())).findFirst().
                    orElseGet(()-> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO,"No se ha encontrado objeto");
                        return null;});
        }
        return null;
    }

    @Override
    public void selecFila(SelectEvent<TipoSala> event) {
        TipoSala filaSelect = event.getObject();
        FacesMessage mensaje=new FacesMessage("Tipo de pago selecionado co exito");
        facesContext.addMessage(null, mensaje);
        this.registro=filaSelect;
        this.estado=ESTADO_CRUD.MODIFICAR;
    }


}

package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.logging.Logger;

public class FrmTipoAsiento extends AbstractFrm<TipoAsiento> implements Serializable {
@Inject
FacesContext facesContext;
@Inject
    TipoAsientoBean daBean;


    @Override
    public void instanciarRegistro() {
        registro = new TipoAsiento();
        registro.setActivo(true);
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Asiento";
    }

    @Override
    public AbstractDataPersistence<TipoAsiento> getTAbstractDataPersistence() {
        return null;
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public String getIdPorObjeto(TipoAsiento object) {
        if (object.getIdTipoAsiento() != null){
            return object.getIdTipoAsiento().toString();
        }
        return null;
    }

    @Override
    public TipoAsiento getObjetoPorId(String id) {
        if (id!=null && this.modelo!=null && this.modelo.getWrappedData()!=null){
            return this.modelo.getWrappedData().stream().
                    filter(t -> t.getIdTipoAsiento() == Integer.parseInt(id)).findFirst().orElseGet(()->{
                        Logger.getLogger("no hay objeto");
                        return null;
                    });
        }
        Logger.getLogger("problema para obtener objeto  Tipo producto por id");
        return null;
    }

    @Override
    public void selecFila(SelectEvent<TipoAsiento> event) {
        TipoAsiento selectedProduct = event.getObject();
        FacesMessage msg = new FacesMessage("Producto seleccionado", selectedProduct.getNombre());
        facesContext.addMessage(null, msg);

        this.registro=selectedProduct;
        estado=ESTADO_CRUD.MODIFICAR;

    }
//    public void btnSelecionarRegistroHandler(Object id) {
//        if (id!=null){
//            this.registro = this.registros.stream().filter(t -> t.getIdTipoAsiento() == id).
//                    findFirst().orElse(null);
//            this.estado=ESTADO_CRUD.MODIFICAR;
//            return;
//        }
//        this.registro=null;
//    }
}

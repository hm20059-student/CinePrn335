package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.ext.ParamConverter;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmPelicula extends AbstractFrm<Pelicula> implements Serializable {
    @Inject
    PeliculaBean peliculaBean;
    @Inject
    FacesContext facesContext;


    @Override
    public void instanciarRegistro() {
        this.registro=new Pelicula();

    }

    @Override
    public String getTituloPagina() {
        return "Pelicula";
    }

    @Override
    public AbstractDataPersistence<Pelicula> getTAbstractDataPersistence() {
        return peliculaBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public String getIdPorObjeto(Pelicula object) {
        if (object.getIdPelicula()!=null){
            return object.getIdPelicula().toString();
        }
        return null;
    }

    @Override
    public Pelicula getObjetoPorId(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null){

            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdPelicula().toString())).findFirst().
                    orElseGet(()-> {
                        Logger.getLogger(getClass().getName()).log(Level.INFO,"No se ha encontrado objeto");
                        return null;});
        }
        return null;
    }

    @Override
    public void selecFila(SelectEvent<Pelicula> event) {
        Pelicula peliculaSelected = (Pelicula) event.getObject();
        FacesMessage mensaje=new FacesMessage("pelicula selecionada ", peliculaSelected.getNombre());
        facesContext.addMessage(null,mensaje);
        this.registro=peliculaSelected;
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

}

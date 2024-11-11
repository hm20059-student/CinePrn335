package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;

@Named
@Dependent
public class FrmPeliculaCaracteristica extends AbstractFrm<PeliculaCaracteristica> implements Serializable {

    @Inject
    PeliculaCaracteristicaBean pcBean;

    @Inject
    FacesContext facesContext;
    @Inject
    TipoPeliculaBean tpBean;

    List<TipoPelicula> tipoPeliculaList;

    Long idPelicula;

    @Override
    public void instanciarRegistro() {

    }

    @Override
    public String getTituloPagina() {
        return "";
    }

    @Override
    public AbstractDataPersistence<PeliculaCaracteristica> getTAbstractDataPersistence() {
        return null;
    }

    @Override
    public FacesContext getFacesContext() {
        return null;
    }

    @Override
    public String getIdPorObjeto(PeliculaCaracteristica object) {
        return "";
    }

    @Override
    public PeliculaCaracteristica getObjetoPorId(String id) {
        return null;
    }

    @Override
    public void selecFila(SelectEvent<PeliculaCaracteristica> event) {

    }
}

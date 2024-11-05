package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class FrmTipoPago implements Serializable {
    @Inject
    TipoPagoBean dataTipoPagoBean;
    ESTADO_CRUD estado;

    @Inject
    FacesContext fc;

    LazyDataModel<TipoPago> modelo;
    TipoPago registro;

    @PostConstruct
    public void inicializar() {
        estado = ESTADO_CRUD.NINGUNO;
        modelo = new LazyDataModel<TipoPago>() {
            @Override
            public String getRowKey(TipoPago object) {
                if(object!=null && object.getIdTipoPago()!=null){
                    return object.getIdTipoPago().toString();
                }
                return null;
            }

            @Override
            public TipoPago getRowData(String rowKey) {
                if(rowKey!=null && getWrappedData()!=null){
                    return getWrappedData().stream().filter(r->rowKey.equals(r.getIdTipoPago().toString())).findFirst().orElse(null);
                }
                return null;
            }
            @Override
            public int count(Map<String, FilterMeta> map) {
                try{
                    return dataTipoPagoBean.count(); // falta el intValue()
                }catch (Exception e){
                    e.printStackTrace();
                    //TODO: Enviar mensaje de error de acceso
                }
                return 0;
            }

            @Override
            public List<TipoPago> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try{
                    if (desde>=0 && max>0){
                        return dataTipoPagoBean.findRange(desde, max);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //TODO:enviar mensaje de error en el reporsitorio
                }

                return List.of();
            }
        };
    }

    public LazyDataModel<TipoPago> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoPago> modelo) {
        this.modelo = modelo;
    }

    public TipoPago getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPago registro) {
        this.registro = registro;
    }

    
    public Integer getRegistroPoPagina() {
        return registro.getIdTipoPago();
    }

    public MethodExpression getCambiarSeleccion() {
        return null;
    }


    public ActionListener getBtnNuevoHandler() {
        return null;
    }

    public Object getTituloDePagina() {
        return null;
    }

    public ActionListener getBtnCancelarHandler() {
        return null;
    }

    public ActionListener getBtnGuardarHandler() {
        return null;
    }

    public ActionListener getBtnModificarHandler() {
        return null;
    }

    public ActionListener getBtnEliminarHandler() {
        return null;
    }

}
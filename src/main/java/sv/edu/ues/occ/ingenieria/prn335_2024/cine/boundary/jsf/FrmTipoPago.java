package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionListener;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoPago extends AbstractFrm<TipoPago> implements Serializable {
    @Inject
    TipoPagoBean dataBean;
    ESTADO_CRUD estado;

    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        registro=new TipoPago();
        registro.setActivo(true);
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Pago";
    }

    String Titulo="TipoPago";
    @Override
    public AbstractDataPersistence<TipoPago> getTAbstractDataPersistence() {
        return dataBean;
    }


    @Override
    public FacesContext getFacesContext() {
        return null;
    }

    @Override
    public String getIdPorObjeto(TipoPago object) {
        if (object.getIdTipoPago()!=null){
            return object.getIdTipoPago().toString();
        }
        return null;
    }

    @Override
    public TipoPago getObjetoPorId(String id) {
        if (id!=null & modelo!=null & modelo.getWrappedData()!=null){
            return modelo.getWrappedData().stream().
                    filter(r->id.equals(r.getIdTipoPago().toString())).findFirst().
                    orElseGet(()->{
                        Logger.getLogger("no se ha encontrado el objeto");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecFila(SelectEvent<TipoPago> event) {
        TipoPago filaSelelcted = event.getObject();
        FacesMessage mensaje=new FacesMessage("Tipo de pago selecionado co exito");
        fc.addMessage(null, mensaje);
        this.registro=filaSelelcted;
        this.estado=ESTADO_CRUD.MODIFICAR;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
//    @PostConstruct
//    public void inicializar() {
//        estado = ESTADO_CRUD.NINGUNO;
//        modelo = new LazyDataModel<TipoPago>() {
//            @Override
//            public String getRowKey(TipoPago object) {
//                if(object!=null && object.getIdTipoPago()!=null){
//                    return object.getIdTipoPago().toString();
//                }
//                return null;
//            }
//
//            @Override
//            public TipoPago getRowData(String rowKey) {
//                if(rowKey!=null && getWrappedData()!=null){
//                    return getWrappedData().stream().filter(r->rowKey.equals(r.getIdTipoPago().toString())).findFirst().orElse(null);
//                }
//                return null;
//            }
//            @Override
//            public int count(Map<String, FilterMeta> map) {
//                try{
//                    return dataBean.count(); // falta el intValue()
//                }catch (Exception e){
//                    e.printStackTrace();
//                    //TODO: Enviar mensaje de error de acceso
//                }
//                return 0;
//            }
//
//            @Override
//            public List<TipoPago> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
//                try{
//                    if (desde>=0 && max>0){
//                        return dataBean.findRange(desde, max);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    //TODO:enviar mensaje de error en el reporsitorio
//                }
//
//                return List.of();
//            }
//        };
//    }


}
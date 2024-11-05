package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.orden.LazySorted;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import java.io.Serializable;
import java.util.List;

public abstract class AbstractFrm <T> implements Serializable {
   // todo: metodos que son abstractos
   public abstract void instanciarRegistro();
    public abstract AbstractDataPersistence<T> getTAbstractDataPersistence();
    public  abstract T inicilizarObjeto();
    public abstract FacesContext getFacesContext();

    public abstract String getIdPorObjeto(T object);
    public abstract T getObjetoPorId(String id);
    public abstract void selecFila(SelectEvent<T> event);

    //todo: propiedades a utilizar

    T registro=null;
    @Inject
    FacesContext facesContext;
    List<T> registros;
    LazyDataModel<T> modelo;
    ESTADO_CRUD estado;


@PostConstruct
public void inicializar() {
    inicializarRegistros();
    estado=ESTADO_CRUD.NINGUNO;
}
public void inicializarRegistros() {
    this.modelo=new LazyDataModel<T>() {

        @Override
        public String getRowKey(T object) {
            if(object!=null ){
                return getIdPorObjeto(object);
            }
            return null;
        }

        @Override
        public T getRowData(String rowKey) {
            if(rowKey!=null){
return getObjetoPorId(rowKey);            }
            return null;
        }

        @Override
        public int count(Map<String, FilterMeta> map) {
            AbstractDataPersistence<T> cBean=getTAbstractDataPersistence();
            int resultado=0;
            try{
                resultado  = cBean.count(); // falta el intValue()
            }catch (Exception e){
                Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, null, e);
                //TODO: Enviar mensaje de error de acceso
            }
            return resultado;
        }

        @Override
        public List<T> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
            if (desde>=0 && max>0){
                try{
                   AbstractDataPersistence<T> cBean=getTAbstractDataPersistence();
                   if(!map.isEmpty()){
                       String ordenar=map.values().stream().findFirst().get().getOrder().toString();
                       String direccion=map.values().stream().findFirst().get().getOrder().toString();
                       return cBean.findRange(desde,max,ordenar,direccion);
                   }
                }catch (Exception e){
                    Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, null, e);
                    //TODO:enviar mensaje de error en el reporsitorio
                }
                return List.of();
            }


            return List.of();
        }
    };
}



 //todo: botones crud
public void btnNuevoHandler(ActionEvent event) {
    inicilizarObjeto();

}
//    public void btnGuardarHandler(ActionEvent event) {
//        //TODO: validar o incluir try cath
//        this.getDataPersist().create(registro);
//        FacesMessage mensaje=new FacesMessage();
//        mensaje.setSummary("creado con exito");
//        mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
//        facesContext.addMessage(null, mensaje);
//    }
//
//
//    public LazyDataModel<T> getModelo() {
//        return modelo;
//    }
//
//    public void setModelo(LazyDataModel<T> modelo) {
//        this.modelo = modelo;
//    }
//
//    public ESTADO_CRUD getEstado() {
//        return estado;
//    }
//
//    public void setEstado(ESTADO_CRUD estado) {
//        this.estado = estado;
//    }
//
//    public T getRegistro() {
//        return registro;
//    }
//
//    public void setRegistro(T registro) {
//        this.registro = registro;
//    }
}



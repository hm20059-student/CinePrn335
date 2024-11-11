package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.event.ActionEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractFrm<T> implements Serializable {
    // todo: metodos que son abstractos
    public abstract void instanciarRegistro();
    public abstract String getTituloPagina();
    public abstract AbstractDataPersistence<T> getTAbstractDataPersistence();

    //    public  abstract T inicilizarObjeto();
    public abstract FacesContext getFacesContext();

    public abstract String getIdPorObjeto(T object);

    public abstract T getObjetoPorId(String id);

    public abstract void selecFila(SelectEvent<T> event);

    //todo: propiedades a utilizar
    T registro;
    //List<T> registros;
    LazyDataModel<T> modelo;
    ESTADO_CRUD estado;


    @PostConstruct
    public void inicializar() {
        inicializarRegistros();
        estado = ESTADO_CRUD.NINGUNO;

    }

    //todo: metodos contar y cargar para tener lista limitada de registros para soportar paginación en la interfaz.
    public int contar() {
        try {
            return getTAbstractDataPersistence().count();
        } catch (Exception e) {
            Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, "Error al contar registros", e);
            return 0; // Retorna 0 si hay un error
        }
    }

    public List<T> cargar(int desde, int max) {
        try {
            return getTAbstractDataPersistence().findRange(desde, max);
        } catch (Exception e) {
            Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, "Error al cargar registros", e);
            return Collections.emptyList(); // Retorna una lista vacía si hay un error
        }
    }


    public void inicializarRegistros() {
        this.modelo = new LazyDataModel<T>() {

            @Override
            public String getRowKey(T object) {
                if (object != null) {
                    return getIdPorObjeto(object);
                }
                return null;
            }

            @Override
            public T getRowData(String rowKey) {
                if (rowKey != null) {
                    return getObjetoPorId(rowKey);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                AbstractDataPersistence<T> dataBean = getTAbstractDataPersistence();
                int resultado = 0;
                try {
                    resultado = contar();// metodo contar
                } catch (Exception e) {
                    Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, null, e);
                    //TODO: Enviar mensaje de error de acceso
                }
                return resultado;
            }

            @Override
            public List<T> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                if (desde >= 0 && max > 0) {
                    try {
                        AbstractDataPersistence<T> dataBean = getTAbstractDataPersistence();
                        return cargar(desde, max); // metodo cargar
                    } catch (Exception e) {
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
        instanciarRegistro();
        estado = ESTADO_CRUD.CREAR;

    }

    public void btnCancelarHandler(ActionEvent event) {
        instanciarRegistro();
        estado = ESTADO_CRUD.NINGUNO;
        registro = null;
    }

    public void btnGuardarHandler(ActionEvent event) {
        instanciarRegistro();
        FacesMessage mensaje = new FacesMessage();
        try {
            AbstractDataPersistence<T> cBean = null;
            cBean = getTAbstractDataPersistence();
            cBean.create(registro);
            this.estado = ESTADO_CRUD.NINGUNO;
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro guardado");
            registro = null;
        } catch (Exception e) {
            Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, null, e);
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("no se pudo guardar el dato");
            mensaje.setDetail(e.getMessage());
            getFacesContext().addMessage(null, mensaje);

        }
    }

    public void btnModificarHandler(ActionEvent event) {
        T modificado = null;
        FacesMessage mensaje = new FacesMessage();
        try {
            AbstractDataPersistence<T> dataBean = null;
            dataBean = getTAbstractDataPersistence();
            modificado = dataBean.update(registro);
            if (modificado != null) {
                mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
                mensaje.setSummary("registro modificado");
                getFacesContext().addMessage(null, mensaje);
            }
        } catch (Exception e) {
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("no se pudo modificar el dato");
            mensaje.setDetail(e.getMessage());
            getFacesContext().addMessage(null, mensaje);

        }
        estado = ESTADO_CRUD.NINGUNO;
        registro = null;
    }

    public void btneEliminarHandler(ActionEvent ex) {
        FacesMessage mensaje = new FacesMessage();
        ;
        try {
            AbstractDataPersistence<T> dataBean = null;
            dataBean = getTAbstractDataPersistence();
            dataBean.delete(registro);
            this.estado = ESTADO_CRUD.NINGUNO;
            this.registro = null;
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro eliminado");
            getFacesContext().addMessage(null, mensaje);
            return;
        } catch (Exception e) {
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro no se pudo eliminar");
            mensaje.setDetail(e.getMessage());
            getFacesContext().addMessage(null, mensaje);
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

        }
    }

    public LazyDataModel<T> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<T> modelo) {
        this.modelo = modelo;
    }

    public List<T> getUpdateList() {
        AbstractDataPersistence<T> clBean = getTAbstractDataPersistence();
        List<T> list;
        int max = clBean.count();
        try {
            list = clBean.findRange(0, max);
            return list;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }


}



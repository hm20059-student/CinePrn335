package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoReserva extends AbstractFrm<TipoReserva> {

    @Inject
    TipoReservaBean dataBean;

    @Inject
    FacesContext facesContext;

//    LazyDataModel<TipoReserva> modelo;
//    TipoReserva registro;

    @Override
    public void instanciarRegistro() {
        registro = new TipoReserva();
        registro.setActivo(true);
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Reserva";
    }

    @Override
    public AbstractDataPersistence<TipoReserva> getTAbstractDataPersistence() {
        return dataBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public String getIdPorObjeto(TipoReserva object) {
        if (object.getIdTipoReserva() != null) {
            return object.getIdTipoReserva().toString();
        }
        return null;
    }

    @Override
    public TipoReserva getObjetoPorId(String id) {
        if (id != null && modelo != null && modelo.getWrappedData()!=null) {
            return  modelo.getWrappedData().stream().
                    filter(r -> r.getIdTipoReserva().toString().equals(id)).findFirst().
                    orElseGet(()->{
                        Logger.getLogger("el object no se ha encontrado");
                        return null;
                    });
        }
        return null;
    }

    @Override
    public void selecFila(SelectEvent<TipoReserva> event) {
        TipoReserva tipoReservaSelect = (TipoReserva) event.getObject();
        FacesMessage message=new FacesMessage("Tipo de reserva seleccionado con sucesso",tipoReservaSelect.getNombre());
        facesContext.addMessage(null, message);
        estado=ESTADO_CRUD.MODIFICAR;
    }


//    @PostConstruct
//    public void inicializar() {
//       modelo=new LazyDataModel<TipoReserva>() {
//           @Override
//           public String getRowKey(TipoReserva object) {
//               if(object!=null && object.getIdTipoReserva()!=null){
//                   return object.getIdTipoReserva().toString();
//               }
//               return null;
//           }
//
//           @Override
//           public TipoReserva getRowData(String rowKey) {
//               if(rowKey!=null && getWrappedData()!=null){
//                   return getWrappedData().stream().filter(r->rowKey.equals(r.getIdTipoReserva().toString())).findFirst().orElse(null);
//               }
//               return null;
//           }
//
//           @Override
//           public int count(Map<String, FilterMeta> map) {
//               try{
//                   return dataBean.count(); // falta el intValue()
//               }catch (Exception e){
//                   e.printStackTrace();
//                   //TODO: Enviar mensaje de error de acceso
//               }
//               return 0;
//           }
//
//           @Override
//           public List<TipoReserva> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
//               try{
//                   if (desde>=0 && max>0){
//                      return dataBean.findRange(desde, max);
//                   }
//               }catch (Exception e){
//                   e.printStackTrace();
//                   //TODO:enviar mensaje de error en el reporsitorio
//               }
//
//               return List.of();
//           }
//       };
//    }


//    public TipoReserva getRegistro() {
//        return registro;
//    }
//
//    public void setRegistro(TipoReserva registro) {
//        this.registro = registro;
//    }
//
//    public LazyDataModel<TipoReserva> getModelo() {
//        return modelo;
//    }
//
//    public void setModelo(LazyDataModel<TipoReserva> modelo) {
//        this.modelo = modelo;
//    }
//
//    public void btnNuevoHandler(ActionEvent event) {
//        this.registro = new TipoReserva();
//        this.registro.setActivo(true);
//    }
//    public void btnCrearHandler(ActionEvent event) {
//        //TODO: validar o incluir try cath
//        dataBean.create(registro);
//        FacesMessage mensaje=new FacesMessage();
//        mensaje.setSummary("creado con exito");
//        mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
//        facesContext.addMessage(null, mensaje);
//    }
}

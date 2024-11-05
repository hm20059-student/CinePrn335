package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ContadorBean;

import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class FrmWebSocket implements Serializable {

    @Inject
    @Push(channel = "chepe")
    PushContext pushContext;

    @Inject
    ContadorBean contadorBean;

    int cuenta=0;
    UUID uid;

    public void enviarMensaje() {
        uid=UUID.randomUUID();
        contadorBean.contarDespacio(cuenta,uid,mensaje->recibirMensaje(mensaje));
        pushContext.send("mensaje enciado"+System.currentTimeMillis());
    }
    public void recibirMensaje(JsonObject respuesta){
        if(respuesta!=null){
            if(respuesta.getString("tipo_respuesta").equals(ContadorBean.TIPO_RESPUESTA.EXITO.toString()))){
                cuenta=respuesta.getInt("cuenta");
                pushContext.send("mensaje recibido"+System.currentTimeMillis());
            }
        }
    }
}

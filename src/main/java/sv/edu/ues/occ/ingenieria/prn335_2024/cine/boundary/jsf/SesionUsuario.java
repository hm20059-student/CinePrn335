package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
public class SesionUsuario implements Serializable {
    @Inject
    FacesContext facesContext;
    Map<String, Locale> idiomas =new HashMap<>();

    String idiomaSelect;
    @PostConstruct
    public void init() {
        idiomas.put("English", new Locale.Builder().setLanguage("en").build());
        idiomas.put("Español", new Locale.Builder().setLanguage("es").build());
    }

    public String getIdiomaSelect() {
        return idiomaSelect;
    }

    public void setIdiomaSelect(String idiomaSelect) {
        this.idiomaSelect = idiomaSelect;
    }



    public Map<String, Locale> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Map<String, Locale> idiomas) {
        this.idiomas = idiomas;
    }

    public void CambiarIdioma(ValueChangeEvent event){
        idiomaSelect = event.getNewValue().toString();
        for (Map.Entry<String,Locale> entry : idiomas.entrySet()) {
            if (entry.getKey().equals(idiomaSelect)) {
                facesContext.getViewRoot().setLocale(entry.getValue());
            }
        }
    }
}

package com.horarios.jsf.converter;
import com.horarios.Acceso;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@FacesConverter("com.horarios.jsf.converter.AccesoConverter")
public class AccesoConverter implements Converter   {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return Acceso.findAcceso(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Acceso ? ((Acceso) value).getIdacceso().toString() : "";
    }
}

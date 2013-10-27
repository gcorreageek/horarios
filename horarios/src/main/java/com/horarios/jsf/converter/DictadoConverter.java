package com.horarios.jsf.converter;
import com.horarios.Dictado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("com.horarios.jsf.converter.DictadoConverter")
@Configurable
public class DictadoConverter  implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return Dictado.findDictado(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Dictado ? ((Dictado) value).getIddictado().toString() : "";
    }
}

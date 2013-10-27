package com.horarios.jsf.converter;
import com.horarios.Cargo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("com.horarios.jsf.converter.CargoConverter")
@Configurable
public class CargoConverter  implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return Cargo.findCargo(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Cargo ? ((Cargo) value).getIdcargo().toString() : "";
    }
}

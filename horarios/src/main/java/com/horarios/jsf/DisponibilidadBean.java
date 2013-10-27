package com.horarios.jsf;
import com.horarios.Disponibilidad;
import com.horarios.Docente;
import com.horarios.Hora;
import com.horarios.jsf.converter.DocenteConverter;
import com.horarios.jsf.converter.HoraConverter;
import com.horarios.jsf.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean(name = "disponibilidadBean")
@SessionScoped
@Configurable
public class DisponibilidadBean  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Disponibilidads";

	private Disponibilidad disponibilidad;

	private List<Disponibilidad> allDisponibilidads;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("dia");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Disponibilidad> getAllDisponibilidads() {
        return allDisponibilidads;
    }

	public void setAllDisponibilidads(List<Disponibilidad> allDisponibilidads) {
        this.allDisponibilidads = allDisponibilidads;
    }

	public String findAllDisponibilidads() {
        allDisponibilidads = Disponibilidad.findAllDisponibilidads();
        dataVisible = !allDisponibilidads.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public HtmlPanelGrid getCreatePanelGrid() {
        if (createPanelGrid == null) {
            createPanelGrid = populateCreatePanel();
        }
        return createPanelGrid;
    }

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
        this.createPanelGrid = createPanelGrid;
    }

	public HtmlPanelGrid getEditPanelGrid() {
        if (editPanelGrid == null) {
            editPanelGrid = populateEditPanel();
        }
        return editPanelGrid;
    }

	public void setEditPanelGrid(HtmlPanelGrid editPanelGrid) {
        this.editPanelGrid = editPanelGrid;
    }

	public HtmlPanelGrid getViewPanelGrid() {
        return populateViewPanel();
    }

	public void setViewPanelGrid(HtmlPanelGrid viewPanelGrid) {
        this.viewPanelGrid = viewPanelGrid;
    }

	public HtmlPanelGrid populateCreatePanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel iddocenteCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        iddocenteCreateOutput.setFor("iddocenteCreateInput");
        iddocenteCreateOutput.setId("iddocenteCreateOutput");
        iddocenteCreateOutput.setValue("Iddocente:");
        htmlPanelGrid.getChildren().add(iddocenteCreateOutput);
        
        AutoComplete iddocenteCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        iddocenteCreateInput.setId("iddocenteCreateInput");
        iddocenteCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.iddocente}", Docente.class));
        iddocenteCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{disponibilidadBean.completeIddocente}", List.class, new Class[] { String.class }));
        iddocenteCreateInput.setDropdown(true);
        iddocenteCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "iddocente", String.class));
        iddocenteCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{iddocente.nomdocente} #{iddocente.iddocente}", String.class));
        iddocenteCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{iddocente}", Docente.class));
        iddocenteCreateInput.setConverter(new DocenteConverter());
        iddocenteCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(iddocenteCreateInput);
        
        Message iddocenteCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        iddocenteCreateInputMessage.setId("iddocenteCreateInputMessage");
        iddocenteCreateInputMessage.setFor("iddocenteCreateInput");
        iddocenteCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(iddocenteCreateInputMessage);
        
        OutputLabel idhoraCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idhoraCreateOutput.setFor("idhoraCreateInput");
        idhoraCreateOutput.setId("idhoraCreateOutput");
        idhoraCreateOutput.setValue("Idhora:");
        htmlPanelGrid.getChildren().add(idhoraCreateOutput);
        
        AutoComplete idhoraCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idhoraCreateInput.setId("idhoraCreateInput");
        idhoraCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.idhora}", Hora.class));
        idhoraCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{disponibilidadBean.completeIdhora}", List.class, new Class[] { String.class }));
        idhoraCreateInput.setDropdown(true);
        idhoraCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idhora", String.class));
        idhoraCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idhora.horainicio} #{idhora.horafin} #{idhora.idhora}", String.class));
        idhoraCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idhora}", Hora.class));
        idhoraCreateInput.setConverter(new HoraConverter());
        idhoraCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idhoraCreateInput);
        
        Message idhoraCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idhoraCreateInputMessage.setId("idhoraCreateInputMessage");
        idhoraCreateInputMessage.setFor("idhoraCreateInput");
        idhoraCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idhoraCreateInputMessage);
        
        OutputLabel diaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        diaCreateOutput.setFor("diaCreateInput");
        diaCreateOutput.setId("diaCreateOutput");
        diaCreateOutput.setValue("Dia:");
        htmlPanelGrid.getChildren().add(diaCreateOutput);
        
        Spinner diaCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        diaCreateInput.setId("diaCreateInput");
        diaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.dia}", Integer.class));
        diaCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(diaCreateInput);
        
        Message diaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        diaCreateInputMessage.setId("diaCreateInputMessage");
        diaCreateInputMessage.setFor("diaCreateInput");
        diaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(diaCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel iddocenteEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        iddocenteEditOutput.setFor("iddocenteEditInput");
        iddocenteEditOutput.setId("iddocenteEditOutput");
        iddocenteEditOutput.setValue("Iddocente:");
        htmlPanelGrid.getChildren().add(iddocenteEditOutput);
        
        AutoComplete iddocenteEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        iddocenteEditInput.setId("iddocenteEditInput");
        iddocenteEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.iddocente}", Docente.class));
        iddocenteEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{disponibilidadBean.completeIddocente}", List.class, new Class[] { String.class }));
        iddocenteEditInput.setDropdown(true);
        iddocenteEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "iddocente", String.class));
        iddocenteEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{iddocente.nomdocente} #{iddocente.iddocente}", String.class));
        iddocenteEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{iddocente}", Docente.class));
        iddocenteEditInput.setConverter(new DocenteConverter());
        iddocenteEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(iddocenteEditInput);
        
        Message iddocenteEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        iddocenteEditInputMessage.setId("iddocenteEditInputMessage");
        iddocenteEditInputMessage.setFor("iddocenteEditInput");
        iddocenteEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(iddocenteEditInputMessage);
        
        OutputLabel idhoraEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idhoraEditOutput.setFor("idhoraEditInput");
        idhoraEditOutput.setId("idhoraEditOutput");
        idhoraEditOutput.setValue("Idhora:");
        htmlPanelGrid.getChildren().add(idhoraEditOutput);
        
        AutoComplete idhoraEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idhoraEditInput.setId("idhoraEditInput");
        idhoraEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.idhora}", Hora.class));
        idhoraEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{disponibilidadBean.completeIdhora}", List.class, new Class[] { String.class }));
        idhoraEditInput.setDropdown(true);
        idhoraEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idhora", String.class));
        idhoraEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idhora.horainicio} #{idhora.horafin} #{idhora.idhora}", String.class));
        idhoraEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idhora}", Hora.class));
        idhoraEditInput.setConverter(new HoraConverter());
        idhoraEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idhoraEditInput);
        
        Message idhoraEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idhoraEditInputMessage.setId("idhoraEditInputMessage");
        idhoraEditInputMessage.setFor("idhoraEditInput");
        idhoraEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idhoraEditInputMessage);
        
        OutputLabel diaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        diaEditOutput.setFor("diaEditInput");
        diaEditOutput.setId("diaEditOutput");
        diaEditOutput.setValue("Dia:");
        htmlPanelGrid.getChildren().add(diaEditOutput);
        
        Spinner diaEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        diaEditInput.setId("diaEditInput");
        diaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.dia}", Integer.class));
        diaEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(diaEditInput);
        
        Message diaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        diaEditInputMessage.setId("diaEditInputMessage");
        diaEditInputMessage.setFor("diaEditInput");
        diaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(diaEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText iddocenteLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        iddocenteLabel.setId("iddocenteLabel");
        iddocenteLabel.setValue("Iddocente:");
        htmlPanelGrid.getChildren().add(iddocenteLabel);
        
        HtmlOutputText iddocenteValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        iddocenteValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.iddocente}", Docente.class));
        iddocenteValue.setConverter(new DocenteConverter());
        htmlPanelGrid.getChildren().add(iddocenteValue);
        
        HtmlOutputText idhoraLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idhoraLabel.setId("idhoraLabel");
        idhoraLabel.setValue("Idhora:");
        htmlPanelGrid.getChildren().add(idhoraLabel);
        
        HtmlOutputText idhoraValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idhoraValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.idhora}", Hora.class));
        idhoraValue.setConverter(new HoraConverter());
        htmlPanelGrid.getChildren().add(idhoraValue);
        
        HtmlOutputText diaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        diaLabel.setId("diaLabel");
        diaLabel.setValue("Dia:");
        htmlPanelGrid.getChildren().add(diaLabel);
        
        HtmlOutputText diaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        diaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{disponibilidadBean.disponibilidad.dia}", String.class));
        htmlPanelGrid.getChildren().add(diaValue);
        
        return htmlPanelGrid;
    }

	public Disponibilidad getDisponibilidad() {
        if (disponibilidad == null) {
            disponibilidad = new Disponibilidad();
        }
        return disponibilidad;
    }

	public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

	public List<Docente> completeIddocente(String query) {
        List<Docente> suggestions = new ArrayList<Docente>();
        for (Docente docente : Docente.findAllDocentes()) {
            String docenteStr = String.valueOf(docente.getNomdocente() +  " "  + docente.getIddocente());
            if (docenteStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(docente);
            }
        }
        return suggestions;
    }

	public List<Hora> completeIdhora(String query) {
        List<Hora> suggestions = new ArrayList<Hora>();
        for (Hora hora : Hora.findAllHoras()) {
            String horaStr = String.valueOf(hora.getHorainicio() +  " "  + hora.getHorafin() +  " "  + hora.getIdhora());
            if (horaStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(hora);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        return null;
    }

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllDisponibilidads();
        return "disponibilidad";
    }

	public String displayCreateDialog() {
        disponibilidad = new Disponibilidad();
        createDialogVisible = true;
        return "disponibilidad";
    }

	public String persist() {
        String message = "";
        if (disponibilidad.getIddisponibilidad() != null) {
            disponibilidad.merge();
            message = "message_successfully_updated";
        } else {
            disponibilidad.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Disponibilidad");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDisponibilidads();
    }

	public String delete() {
        disponibilidad.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Disponibilidad");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDisponibilidads();
    }

	public void reset() {
        disponibilidad = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}

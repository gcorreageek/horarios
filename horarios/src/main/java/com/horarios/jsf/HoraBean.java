package com.horarios.jsf;
import com.horarios.Disponibilidad;
import com.horarios.Hora;
import com.horarios.jsf.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.faces.convert.DateTimeConverter;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "horaBean")
@SessionScoped
public class HoraBean  {

	private static final long serialVersionUID = 1L;

	private String name = "Horas";

	private Hora hora;

	private List<Hora> allHoras;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Disponibilidad> selectedDisponibilidads;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("horainicio");
        columns.add("horafin");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Hora> getAllHoras() {
        return allHoras;
    }

	public void setAllHoras(List<Hora> allHoras) {
        this.allHoras = allHoras;
    }

	public String findAllHoras() {
        allHoras = Hora.findAllHoras();
        dataVisible = !allHoras.isEmpty();
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
        
        HtmlOutputText disponibilidadsCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsCreateOutput.setId("disponibilidadsCreateOutput");
        disponibilidadsCreateOutput.setValue("Disponibilidads:");
        htmlPanelGrid.getChildren().add(disponibilidadsCreateOutput);
        
        HtmlOutputText disponibilidadsCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsCreateInput.setId("disponibilidadsCreateInput");
        disponibilidadsCreateInput.setValue("This relationship is managed from the Disponibilidad side");
        htmlPanelGrid.getChildren().add(disponibilidadsCreateInput);
        
        Message disponibilidadsCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        disponibilidadsCreateInputMessage.setId("disponibilidadsCreateInputMessage");
        disponibilidadsCreateInputMessage.setFor("disponibilidadsCreateInput");
        disponibilidadsCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(disponibilidadsCreateInputMessage);
        
        OutputLabel horainicioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        horainicioCreateOutput.setFor("horainicioCreateInput");
        horainicioCreateOutput.setId("horainicioCreateOutput");
        horainicioCreateOutput.setValue("Horainicio:");
        htmlPanelGrid.getChildren().add(horainicioCreateOutput);
        
        Calendar horainicioCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        horainicioCreateInput.setId("horainicioCreateInput");
        horainicioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{horaBean.hora.horainicio}", Date.class));
        horainicioCreateInput.setNavigator(true);
        horainicioCreateInput.setEffect("slideDown");
        horainicioCreateInput.setPattern("dd/MM/yyyy");
        horainicioCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(horainicioCreateInput);
        
        Message horainicioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        horainicioCreateInputMessage.setId("horainicioCreateInputMessage");
        horainicioCreateInputMessage.setFor("horainicioCreateInput");
        horainicioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(horainicioCreateInputMessage);
        
        OutputLabel horafinCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        horafinCreateOutput.setFor("horafinCreateInput");
        horafinCreateOutput.setId("horafinCreateOutput");
        horafinCreateOutput.setValue("Horafin:");
        htmlPanelGrid.getChildren().add(horafinCreateOutput);
        
        Calendar horafinCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        horafinCreateInput.setId("horafinCreateInput");
        horafinCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{horaBean.hora.horafin}", Date.class));
        horafinCreateInput.setNavigator(true);
        horafinCreateInput.setEffect("slideDown");
        horafinCreateInput.setPattern("dd/MM/yyyy");
        horafinCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(horafinCreateInput);
        
        Message horafinCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        horafinCreateInputMessage.setId("horafinCreateInputMessage");
        horafinCreateInputMessage.setFor("horafinCreateInput");
        horafinCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(horafinCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText disponibilidadsEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsEditOutput.setId("disponibilidadsEditOutput");
        disponibilidadsEditOutput.setValue("Disponibilidads:");
        htmlPanelGrid.getChildren().add(disponibilidadsEditOutput);
        
        HtmlOutputText disponibilidadsEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsEditInput.setId("disponibilidadsEditInput");
        disponibilidadsEditInput.setValue("This relationship is managed from the Disponibilidad side");
        htmlPanelGrid.getChildren().add(disponibilidadsEditInput);
        
        Message disponibilidadsEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        disponibilidadsEditInputMessage.setId("disponibilidadsEditInputMessage");
        disponibilidadsEditInputMessage.setFor("disponibilidadsEditInput");
        disponibilidadsEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(disponibilidadsEditInputMessage);
        
        OutputLabel horainicioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        horainicioEditOutput.setFor("horainicioEditInput");
        horainicioEditOutput.setId("horainicioEditOutput");
        horainicioEditOutput.setValue("Horainicio:");
        htmlPanelGrid.getChildren().add(horainicioEditOutput);
        
        Calendar horainicioEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        horainicioEditInput.setId("horainicioEditInput");
        horainicioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{horaBean.hora.horainicio}", Date.class));
        horainicioEditInput.setNavigator(true);
        horainicioEditInput.setEffect("slideDown");
        horainicioEditInput.setPattern("dd/MM/yyyy");
        horainicioEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(horainicioEditInput);
        
        Message horainicioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        horainicioEditInputMessage.setId("horainicioEditInputMessage");
        horainicioEditInputMessage.setFor("horainicioEditInput");
        horainicioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(horainicioEditInputMessage);
        
        OutputLabel horafinEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        horafinEditOutput.setFor("horafinEditInput");
        horafinEditOutput.setId("horafinEditOutput");
        horafinEditOutput.setValue("Horafin:");
        htmlPanelGrid.getChildren().add(horafinEditOutput);
        
        Calendar horafinEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        horafinEditInput.setId("horafinEditInput");
        horafinEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{horaBean.hora.horafin}", Date.class));
        horafinEditInput.setNavigator(true);
        horafinEditInput.setEffect("slideDown");
        horafinEditInput.setPattern("dd/MM/yyyy");
        horafinEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(horafinEditInput);
        
        Message horafinEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        horafinEditInputMessage.setId("horafinEditInputMessage");
        horafinEditInputMessage.setFor("horafinEditInput");
        horafinEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(horafinEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText disponibilidadsLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsLabel.setId("disponibilidadsLabel");
        disponibilidadsLabel.setValue("Disponibilidads:");
        htmlPanelGrid.getChildren().add(disponibilidadsLabel);
        
        HtmlOutputText disponibilidadsValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsValue.setId("disponibilidadsValue");
        disponibilidadsValue.setValue("This relationship is managed from the Disponibilidad side");
        htmlPanelGrid.getChildren().add(disponibilidadsValue);
        
        HtmlOutputText horainicioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        horainicioLabel.setId("horainicioLabel");
        horainicioLabel.setValue("Horainicio:");
        htmlPanelGrid.getChildren().add(horainicioLabel);
        
        HtmlOutputText horainicioValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        horainicioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{horaBean.hora.horainicio}", Date.class));
        DateTimeConverter horainicioValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        horainicioValueConverter.setPattern("dd/MM/yyyy");
        horainicioValue.setConverter(horainicioValueConverter);
        htmlPanelGrid.getChildren().add(horainicioValue);
        
        HtmlOutputText horafinLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        horafinLabel.setId("horafinLabel");
        horafinLabel.setValue("Horafin:");
        htmlPanelGrid.getChildren().add(horafinLabel);
        
        HtmlOutputText horafinValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        horafinValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{horaBean.hora.horafin}", Date.class));
        DateTimeConverter horafinValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        horafinValueConverter.setPattern("dd/MM/yyyy");
        horafinValue.setConverter(horafinValueConverter);
        htmlPanelGrid.getChildren().add(horafinValue);
        
        return htmlPanelGrid;
    }

	public Hora getHora() {
        if (hora == null) {
            hora = new Hora();
        }
        return hora;
    }

	public void setHora(Hora hora) {
        this.hora = hora;
    }

	public List<Disponibilidad> getSelectedDisponibilidads() {
        return selectedDisponibilidads;
    }

	public void setSelectedDisponibilidads(List<Disponibilidad> selectedDisponibilidads) {
        if (selectedDisponibilidads != null) {
            hora.setDisponibilidads(new HashSet<Disponibilidad>(selectedDisponibilidads));
        }
        this.selectedDisponibilidads = selectedDisponibilidads;
    }

	public String onEdit() {
        if (hora != null && hora.getDisponibilidads() != null) {
            selectedDisponibilidads = new ArrayList<Disponibilidad>(hora.getDisponibilidads());
        }
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
        findAllHoras();
        return "hora";
    }

	public String displayCreateDialog() {
        hora = new Hora();
        createDialogVisible = true;
        return "hora";
    }

	public String persist() {
        String message = "";
        if (hora.getIdhora() != null) {
            hora.merge();
            message = "message_successfully_updated";
        } else {
            hora.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Hora");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllHoras();
    }

	public String delete() {
        hora.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Hora");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllHoras();
    }

	public void reset() {
        hora = null;
        selectedDisponibilidads = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}

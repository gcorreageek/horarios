package com.horarios.jsf;
import com.horarios.Area;
import com.horarios.Cargo;
import com.horarios.jsf.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.validator.LengthValidator;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "areaBean")
@SessionScoped
public class AreaBean implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String name = "Areas";

	private Area area;

	private List<Area> allAreas;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Cargo> selectedCargoes;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nomarea");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Area> getAllAreas() {
        return allAreas;
    }

	public void setAllAreas(List<Area> allAreas) {
        this.allAreas = allAreas;
    }

	public String findAllAreas() {
        allAreas = Area.findAllAreas();
        dataVisible = !allAreas.isEmpty();
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
        
        HtmlOutputText cargoesCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoesCreateOutput.setId("cargoesCreateOutput");
        cargoesCreateOutput.setValue("Cargoes:");
        htmlPanelGrid.getChildren().add(cargoesCreateOutput);
        
        HtmlOutputText cargoesCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoesCreateInput.setId("cargoesCreateInput");
        cargoesCreateInput.setValue("This relationship is managed from the Cargo side");
        htmlPanelGrid.getChildren().add(cargoesCreateInput);
        
        Message cargoesCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cargoesCreateInputMessage.setId("cargoesCreateInputMessage");
        cargoesCreateInputMessage.setFor("cargoesCreateInput");
        cargoesCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cargoesCreateInputMessage);
        
        OutputLabel nomareaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomareaCreateOutput.setFor("nomareaCreateInput");
        nomareaCreateOutput.setId("nomareaCreateOutput");
        nomareaCreateOutput.setValue("Nomarea:");
        htmlPanelGrid.getChildren().add(nomareaCreateOutput);
        
        InputTextarea nomareaCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomareaCreateInput.setId("nomareaCreateInput");
        nomareaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{areaBean.area.nomarea}", String.class));
        LengthValidator nomareaCreateInputValidator = new LengthValidator();
        nomareaCreateInputValidator.setMaximum(200);
        nomareaCreateInput.addValidator(nomareaCreateInputValidator);
        nomareaCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomareaCreateInput);
        
        Message nomareaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomareaCreateInputMessage.setId("nomareaCreateInputMessage");
        nomareaCreateInputMessage.setFor("nomareaCreateInput");
        nomareaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomareaCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText cargoesEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoesEditOutput.setId("cargoesEditOutput");
        cargoesEditOutput.setValue("Cargoes:");
        htmlPanelGrid.getChildren().add(cargoesEditOutput);
        
        HtmlOutputText cargoesEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoesEditInput.setId("cargoesEditInput");
        cargoesEditInput.setValue("This relationship is managed from the Cargo side");
        htmlPanelGrid.getChildren().add(cargoesEditInput);
        
        Message cargoesEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cargoesEditInputMessage.setId("cargoesEditInputMessage");
        cargoesEditInputMessage.setFor("cargoesEditInput");
        cargoesEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cargoesEditInputMessage);
        
        OutputLabel nomareaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomareaEditOutput.setFor("nomareaEditInput");
        nomareaEditOutput.setId("nomareaEditOutput");
        nomareaEditOutput.setValue("Nomarea:");
        htmlPanelGrid.getChildren().add(nomareaEditOutput);
        
        InputTextarea nomareaEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomareaEditInput.setId("nomareaEditInput");
        nomareaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{areaBean.area.nomarea}", String.class));
        LengthValidator nomareaEditInputValidator = new LengthValidator();
        nomareaEditInputValidator.setMaximum(200);
        nomareaEditInput.addValidator(nomareaEditInputValidator);
        nomareaEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomareaEditInput);
        
        Message nomareaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomareaEditInputMessage.setId("nomareaEditInputMessage");
        nomareaEditInputMessage.setFor("nomareaEditInput");
        nomareaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomareaEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText cargoesLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoesLabel.setId("cargoesLabel");
        cargoesLabel.setValue("Cargoes:");
        htmlPanelGrid.getChildren().add(cargoesLabel);
        
        HtmlOutputText cargoesValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoesValue.setId("cargoesValue");
        cargoesValue.setValue("This relationship is managed from the Cargo side");
        htmlPanelGrid.getChildren().add(cargoesValue);
        
        HtmlOutputText nomareaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomareaLabel.setId("nomareaLabel");
        nomareaLabel.setValue("Nomarea:");
        htmlPanelGrid.getChildren().add(nomareaLabel);
        
        InputTextarea nomareaValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomareaValue.setId("nomareaValue");
        nomareaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{areaBean.area.nomarea}", String.class));
        nomareaValue.setReadonly(true);
        nomareaValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nomareaValue);
        
        return htmlPanelGrid;
    }

	public Area getArea() {
        if (area == null) {
            area = new Area();
        }
        return area;
    }

	public void setArea(Area area) {
        this.area = area;
    }

	public List<Cargo> getSelectedCargoes() {
        return selectedCargoes;
    }

	public void setSelectedCargoes(List<Cargo> selectedCargoes) {
        if (selectedCargoes != null) {
            area.setCargoes(new HashSet<Cargo>(selectedCargoes));
        }
        this.selectedCargoes = selectedCargoes;
    }

	public String onEdit() {
        if (area != null && area.getCargoes() != null) {
            selectedCargoes = new ArrayList<Cargo>(area.getCargoes());
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
        findAllAreas();
        return "area";
    }

	public String displayCreateDialog() {
        area = new Area();
        createDialogVisible = true;
        return "area";
    }

	public String persist() {
        String message = "";
        if (area.getIdarea() != null) {
            area.merge();
            message = "message_successfully_updated";
        } else {
            area.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Area");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllAreas();
    }

	public String delete() {
        area.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Area");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllAreas();
    }

	public void reset() {
        area = null;
        selectedCargoes = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}

package com.horarios.jsf;
import com.horarios.Acceso;
import com.horarios.Area;
import com.horarios.Cargo;
import com.horarios.Usuario;
import com.horarios.jsf.converter.AreaConverter;
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
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "cargoBean")
@SessionScoped
public class CargoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Cargoes";

	private Cargo cargo;

	private List<Cargo> allCargoes;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Acceso> selectedAccesoes;

	private List<Usuario> selectedUsuarios;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nomcargo");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Cargo> getAllCargoes() {
        return allCargoes;
    }

	public void setAllCargoes(List<Cargo> allCargoes) {
        this.allCargoes = allCargoes;
    }

	public String findAllCargoes() {
        allCargoes = Cargo.findAllCargoes();
        dataVisible = !allCargoes.isEmpty();
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
        
        HtmlOutputText accesoesCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        accesoesCreateOutput.setId("accesoesCreateOutput");
        accesoesCreateOutput.setValue("Accesoes:");
        htmlPanelGrid.getChildren().add(accesoesCreateOutput);
        
        HtmlOutputText accesoesCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        accesoesCreateInput.setId("accesoesCreateInput");
        accesoesCreateInput.setValue("This relationship is managed from the Acceso side");
        htmlPanelGrid.getChildren().add(accesoesCreateInput);
        
        Message accesoesCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        accesoesCreateInputMessage.setId("accesoesCreateInputMessage");
        accesoesCreateInputMessage.setFor("accesoesCreateInput");
        accesoesCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(accesoesCreateInputMessage);
        
        HtmlOutputText usuariosCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        usuariosCreateOutput.setId("usuariosCreateOutput");
        usuariosCreateOutput.setValue("Usuarios:");
        htmlPanelGrid.getChildren().add(usuariosCreateOutput);
        
        HtmlOutputText usuariosCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        usuariosCreateInput.setId("usuariosCreateInput");
        usuariosCreateInput.setValue("This relationship is managed from the Usuario side");
        htmlPanelGrid.getChildren().add(usuariosCreateInput);
        
        Message usuariosCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        usuariosCreateInputMessage.setId("usuariosCreateInputMessage");
        usuariosCreateInputMessage.setFor("usuariosCreateInput");
        usuariosCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(usuariosCreateInputMessage);
        
        OutputLabel idareaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idareaCreateOutput.setFor("idareaCreateInput");
        idareaCreateOutput.setId("idareaCreateOutput");
        idareaCreateOutput.setValue("Idarea:");
        htmlPanelGrid.getChildren().add(idareaCreateOutput);
        
        AutoComplete idareaCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idareaCreateInput.setId("idareaCreateInput");
        idareaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargoBean.cargo.idarea}", Area.class));
        idareaCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{cargoBean.completeIdarea}", List.class, new Class[] { String.class }));
        idareaCreateInput.setDropdown(true);
        idareaCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idarea", String.class));
        idareaCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idarea.nomarea} #{idarea.idarea}", String.class));
        idareaCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idarea}", Area.class));
        idareaCreateInput.setConverter(new AreaConverter());
        idareaCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idareaCreateInput);
        
        Message idareaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idareaCreateInputMessage.setId("idareaCreateInputMessage");
        idareaCreateInputMessage.setFor("idareaCreateInput");
        idareaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idareaCreateInputMessage);
        
        OutputLabel nomcargoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomcargoCreateOutput.setFor("nomcargoCreateInput");
        nomcargoCreateOutput.setId("nomcargoCreateOutput");
        nomcargoCreateOutput.setValue("Nomcargo:");
        htmlPanelGrid.getChildren().add(nomcargoCreateOutput);
        
        InputTextarea nomcargoCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomcargoCreateInput.setId("nomcargoCreateInput");
        nomcargoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargoBean.cargo.nomcargo}", String.class));
        LengthValidator nomcargoCreateInputValidator = new LengthValidator();
        nomcargoCreateInputValidator.setMaximum(200);
        nomcargoCreateInput.addValidator(nomcargoCreateInputValidator);
        nomcargoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomcargoCreateInput);
        
        Message nomcargoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomcargoCreateInputMessage.setId("nomcargoCreateInputMessage");
        nomcargoCreateInputMessage.setFor("nomcargoCreateInput");
        nomcargoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomcargoCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText accesoesEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        accesoesEditOutput.setId("accesoesEditOutput");
        accesoesEditOutput.setValue("Accesoes:");
        htmlPanelGrid.getChildren().add(accesoesEditOutput);
        
        HtmlOutputText accesoesEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        accesoesEditInput.setId("accesoesEditInput");
        accesoesEditInput.setValue("This relationship is managed from the Acceso side");
        htmlPanelGrid.getChildren().add(accesoesEditInput);
        
        Message accesoesEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        accesoesEditInputMessage.setId("accesoesEditInputMessage");
        accesoesEditInputMessage.setFor("accesoesEditInput");
        accesoesEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(accesoesEditInputMessage);
        
        HtmlOutputText usuariosEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        usuariosEditOutput.setId("usuariosEditOutput");
        usuariosEditOutput.setValue("Usuarios:");
        htmlPanelGrid.getChildren().add(usuariosEditOutput);
        
        HtmlOutputText usuariosEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        usuariosEditInput.setId("usuariosEditInput");
        usuariosEditInput.setValue("This relationship is managed from the Usuario side");
        htmlPanelGrid.getChildren().add(usuariosEditInput);
        
        Message usuariosEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        usuariosEditInputMessage.setId("usuariosEditInputMessage");
        usuariosEditInputMessage.setFor("usuariosEditInput");
        usuariosEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(usuariosEditInputMessage);
        
        OutputLabel idareaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idareaEditOutput.setFor("idareaEditInput");
        idareaEditOutput.setId("idareaEditOutput");
        idareaEditOutput.setValue("Idarea:");
        htmlPanelGrid.getChildren().add(idareaEditOutput);
        
        AutoComplete idareaEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idareaEditInput.setId("idareaEditInput");
        idareaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargoBean.cargo.idarea}", Area.class));
        idareaEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{cargoBean.completeIdarea}", List.class, new Class[] { String.class }));
        idareaEditInput.setDropdown(true);
        idareaEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idarea", String.class));
        idareaEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idarea.nomarea} #{idarea.idarea}", String.class));
        idareaEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idarea}", Area.class));
        idareaEditInput.setConverter(new AreaConverter());
        idareaEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idareaEditInput);
        
        Message idareaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idareaEditInputMessage.setId("idareaEditInputMessage");
        idareaEditInputMessage.setFor("idareaEditInput");
        idareaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idareaEditInputMessage);
        
        OutputLabel nomcargoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomcargoEditOutput.setFor("nomcargoEditInput");
        nomcargoEditOutput.setId("nomcargoEditOutput");
        nomcargoEditOutput.setValue("Nomcargo:");
        htmlPanelGrid.getChildren().add(nomcargoEditOutput);
        
        InputTextarea nomcargoEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomcargoEditInput.setId("nomcargoEditInput");
        nomcargoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargoBean.cargo.nomcargo}", String.class));
        LengthValidator nomcargoEditInputValidator = new LengthValidator();
        nomcargoEditInputValidator.setMaximum(200);
        nomcargoEditInput.addValidator(nomcargoEditInputValidator);
        nomcargoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomcargoEditInput);
        
        Message nomcargoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomcargoEditInputMessage.setId("nomcargoEditInputMessage");
        nomcargoEditInputMessage.setFor("nomcargoEditInput");
        nomcargoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomcargoEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText accesoesLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        accesoesLabel.setId("accesoesLabel");
        accesoesLabel.setValue("Accesoes:");
        htmlPanelGrid.getChildren().add(accesoesLabel);
        
        HtmlOutputText accesoesValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        accesoesValue.setId("accesoesValue");
        accesoesValue.setValue("This relationship is managed from the Acceso side");
        htmlPanelGrid.getChildren().add(accesoesValue);
        
        HtmlOutputText usuariosLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        usuariosLabel.setId("usuariosLabel");
        usuariosLabel.setValue("Usuarios:");
        htmlPanelGrid.getChildren().add(usuariosLabel);
        
        HtmlOutputText usuariosValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        usuariosValue.setId("usuariosValue");
        usuariosValue.setValue("This relationship is managed from the Usuario side");
        htmlPanelGrid.getChildren().add(usuariosValue);
        
        HtmlOutputText idareaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idareaLabel.setId("idareaLabel");
        idareaLabel.setValue("Idarea:");
        htmlPanelGrid.getChildren().add(idareaLabel);
        
        HtmlOutputText idareaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idareaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargoBean.cargo.idarea}", Area.class));
        idareaValue.setConverter(new AreaConverter());
        htmlPanelGrid.getChildren().add(idareaValue);
        
        HtmlOutputText nomcargoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomcargoLabel.setId("nomcargoLabel");
        nomcargoLabel.setValue("Nomcargo:");
        htmlPanelGrid.getChildren().add(nomcargoLabel);
        
        InputTextarea nomcargoValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomcargoValue.setId("nomcargoValue");
        nomcargoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargoBean.cargo.nomcargo}", String.class));
        nomcargoValue.setReadonly(true);
        nomcargoValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nomcargoValue);
        
        return htmlPanelGrid;
    }

	public Cargo getCargo() {
        if (cargo == null) {
            cargo = new Cargo();
        }
        return cargo;
    }

	public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

	public List<Acceso> getSelectedAccesoes() {
        return selectedAccesoes;
    }

	public void setSelectedAccesoes(List<Acceso> selectedAccesoes) {
        if (selectedAccesoes != null) {
            cargo.setAccesoes(new HashSet<Acceso>(selectedAccesoes));
        }
        this.selectedAccesoes = selectedAccesoes;
    }

	public List<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

	public void setSelectedUsuarios(List<Usuario> selectedUsuarios) {
        if (selectedUsuarios != null) {
            cargo.setUsuarios(new HashSet<Usuario>(selectedUsuarios));
        }
        this.selectedUsuarios = selectedUsuarios;
    }

	public List<Area> completeIdarea(String query) {
        List<Area> suggestions = new ArrayList<Area>();
        for (Area area : Area.findAllAreas()) {
            String areaStr = String.valueOf(area.getNomarea() +  " "  + area.getIdarea());
            if (areaStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(area);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        if (cargo != null && cargo.getAccesoes() != null) {
            selectedAccesoes = new ArrayList<Acceso>(cargo.getAccesoes());
        }
        if (cargo != null && cargo.getUsuarios() != null) {
            selectedUsuarios = new ArrayList<Usuario>(cargo.getUsuarios());
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
        findAllCargoes();
        return "cargo";
    }

	public String displayCreateDialog() {
        cargo = new Cargo();
        createDialogVisible = true;
        return "cargo";
    }

	public String persist() {
        String message = "";
        if (cargo.getIdcargo() != null) {
            cargo.merge();
            message = "message_successfully_updated";
        } else {
            cargo.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Cargo");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCargoes();
    }

	public String delete() {
        cargo.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Cargo");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCargoes();
    }

	public void reset() {
        cargo = null;
        selectedAccesoes = null;
        selectedUsuarios = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}

package com.horarios.jsf;
import com.horarios.Acceso;
import com.horarios.Cargo;
import com.horarios.Menu;
import com.horarios.jsf.converter.CargoConverter;
import com.horarios.jsf.converter.MenuConverter;
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
import javax.faces.validator.LengthValidator;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "accesoBean")
@SessionScoped
public class AccesoBean  {

	private static final long serialVersionUID = 1L;

	private String name = "Accesoes";

	private Acceso acceso;

	private List<Acceso> allAccesoes;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("habilitado");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Acceso> getAllAccesoes() {
        return allAccesoes;
    }

	public void setAllAccesoes(List<Acceso> allAccesoes) {
        this.allAccesoes = allAccesoes;
    }

	public String findAllAccesoes() {
        allAccesoes = Acceso.findAllAccesoes();
        dataVisible = !allAccesoes.isEmpty();
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
        
        OutputLabel idcargoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idcargoCreateOutput.setFor("idcargoCreateInput");
        idcargoCreateOutput.setId("idcargoCreateOutput");
        idcargoCreateOutput.setValue("Idcargo:");
        htmlPanelGrid.getChildren().add(idcargoCreateOutput);
        
        AutoComplete idcargoCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idcargoCreateInput.setId("idcargoCreateInput");
        idcargoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.idcargo}", Cargo.class));
        idcargoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{accesoBean.completeIdcargo}", List.class, new Class[] { String.class }));
        idcargoCreateInput.setDropdown(true);
        idcargoCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idcargo", String.class));
        idcargoCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idcargo.nomcargo} #{idcargo.idcargo}", String.class));
        idcargoCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idcargo}", Cargo.class));
        idcargoCreateInput.setConverter(new CargoConverter());
        idcargoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idcargoCreateInput);
        
        Message idcargoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idcargoCreateInputMessage.setId("idcargoCreateInputMessage");
        idcargoCreateInputMessage.setFor("idcargoCreateInput");
        idcargoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idcargoCreateInputMessage);
        
        OutputLabel idmenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idmenuCreateOutput.setFor("idmenuCreateInput");
        idmenuCreateOutput.setId("idmenuCreateOutput");
        idmenuCreateOutput.setValue("Idmenu:");
        htmlPanelGrid.getChildren().add(idmenuCreateOutput);
        
        AutoComplete idmenuCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idmenuCreateInput.setId("idmenuCreateInput");
        idmenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.idmenu}", Menu.class));
        idmenuCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{accesoBean.completeIdmenu}", List.class, new Class[] { String.class }));
        idmenuCreateInput.setDropdown(true);
        idmenuCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idmenu", String.class));
        idmenuCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idmenu.nommenu} #{idmenu.urlmenu} #{idmenu.icomenu} #{idmenu.tipomenu}", String.class));
        idmenuCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idmenu}", Menu.class));
        idmenuCreateInput.setConverter(new MenuConverter());
        idmenuCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idmenuCreateInput);
        
        Message idmenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idmenuCreateInputMessage.setId("idmenuCreateInputMessage");
        idmenuCreateInputMessage.setFor("idmenuCreateInput");
        idmenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idmenuCreateInputMessage);
        
        OutputLabel habilitadoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        habilitadoCreateOutput.setFor("habilitadoCreateInput");
        habilitadoCreateOutput.setId("habilitadoCreateOutput");
        habilitadoCreateOutput.setValue("Habilitado:");
        htmlPanelGrid.getChildren().add(habilitadoCreateOutput);
        
        InputTextarea habilitadoCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        habilitadoCreateInput.setId("habilitadoCreateInput");
        habilitadoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.habilitado}", String.class));
        LengthValidator habilitadoCreateInputValidator = new LengthValidator();
        habilitadoCreateInputValidator.setMaximum(200);
        habilitadoCreateInput.addValidator(habilitadoCreateInputValidator);
        habilitadoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(habilitadoCreateInput);
        
        Message habilitadoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        habilitadoCreateInputMessage.setId("habilitadoCreateInputMessage");
        habilitadoCreateInputMessage.setFor("habilitadoCreateInput");
        habilitadoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(habilitadoCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel idcargoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idcargoEditOutput.setFor("idcargoEditInput");
        idcargoEditOutput.setId("idcargoEditOutput");
        idcargoEditOutput.setValue("Idcargo:");
        htmlPanelGrid.getChildren().add(idcargoEditOutput);
        
        AutoComplete idcargoEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idcargoEditInput.setId("idcargoEditInput");
        idcargoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.idcargo}", Cargo.class));
        idcargoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{accesoBean.completeIdcargo}", List.class, new Class[] { String.class }));
        idcargoEditInput.setDropdown(true);
        idcargoEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idcargo", String.class));
        idcargoEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idcargo.nomcargo} #{idcargo.idcargo}", String.class));
        idcargoEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idcargo}", Cargo.class));
        idcargoEditInput.setConverter(new CargoConverter());
        idcargoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idcargoEditInput);
        
        Message idcargoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idcargoEditInputMessage.setId("idcargoEditInputMessage");
        idcargoEditInputMessage.setFor("idcargoEditInput");
        idcargoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idcargoEditInputMessage);
        
        OutputLabel idmenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idmenuEditOutput.setFor("idmenuEditInput");
        idmenuEditOutput.setId("idmenuEditOutput");
        idmenuEditOutput.setValue("Idmenu:");
        htmlPanelGrid.getChildren().add(idmenuEditOutput);
        
        AutoComplete idmenuEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idmenuEditInput.setId("idmenuEditInput");
        idmenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.idmenu}", Menu.class));
        idmenuEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{accesoBean.completeIdmenu}", List.class, new Class[] { String.class }));
        idmenuEditInput.setDropdown(true);
        idmenuEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idmenu", String.class));
        idmenuEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idmenu.nommenu} #{idmenu.urlmenu} #{idmenu.icomenu} #{idmenu.tipomenu}", String.class));
        idmenuEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idmenu}", Menu.class));
        idmenuEditInput.setConverter(new MenuConverter());
        idmenuEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idmenuEditInput);
        
        Message idmenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idmenuEditInputMessage.setId("idmenuEditInputMessage");
        idmenuEditInputMessage.setFor("idmenuEditInput");
        idmenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idmenuEditInputMessage);
        
        OutputLabel habilitadoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        habilitadoEditOutput.setFor("habilitadoEditInput");
        habilitadoEditOutput.setId("habilitadoEditOutput");
        habilitadoEditOutput.setValue("Habilitado:");
        htmlPanelGrid.getChildren().add(habilitadoEditOutput);
        
        InputTextarea habilitadoEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        habilitadoEditInput.setId("habilitadoEditInput");
        habilitadoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.habilitado}", String.class));
        LengthValidator habilitadoEditInputValidator = new LengthValidator();
        habilitadoEditInputValidator.setMaximum(200);
        habilitadoEditInput.addValidator(habilitadoEditInputValidator);
        habilitadoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(habilitadoEditInput);
        
        Message habilitadoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        habilitadoEditInputMessage.setId("habilitadoEditInputMessage");
        habilitadoEditInputMessage.setFor("habilitadoEditInput");
        habilitadoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(habilitadoEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText idcargoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idcargoLabel.setId("idcargoLabel");
        idcargoLabel.setValue("Idcargo:");
        htmlPanelGrid.getChildren().add(idcargoLabel);
        
        HtmlOutputText idcargoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idcargoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.idcargo}", Cargo.class));
        idcargoValue.setConverter(new CargoConverter());
        htmlPanelGrid.getChildren().add(idcargoValue);
        
        HtmlOutputText idmenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idmenuLabel.setId("idmenuLabel");
        idmenuLabel.setValue("Idmenu:");
        htmlPanelGrid.getChildren().add(idmenuLabel);
        
        HtmlOutputText idmenuValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idmenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.idmenu}", Menu.class));
        idmenuValue.setConverter(new MenuConverter());
        htmlPanelGrid.getChildren().add(idmenuValue);
        
        HtmlOutputText habilitadoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        habilitadoLabel.setId("habilitadoLabel");
        habilitadoLabel.setValue("Habilitado:");
        htmlPanelGrid.getChildren().add(habilitadoLabel);
        
        InputTextarea habilitadoValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        habilitadoValue.setId("habilitadoValue");
        habilitadoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{accesoBean.acceso.habilitado}", String.class));
        habilitadoValue.setReadonly(true);
        habilitadoValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(habilitadoValue);
        
        return htmlPanelGrid;
    }

	public Acceso getAcceso() {
        if (acceso == null) {
            acceso = new Acceso();
        }
        return acceso;
    }

	public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }

	public List<Cargo> completeIdcargo(String query) {
        List<Cargo> suggestions = new ArrayList<Cargo>();
        for (Cargo cargo : Cargo.findAllCargoes()) {
            String cargoStr = String.valueOf(cargo.getNomcargo() +  " "  + cargo.getIdcargo());
            if (cargoStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(cargo);
            }
        }
        return suggestions;
    }

	public List<Menu> completeIdmenu(String query) {
        List<Menu> suggestions = new ArrayList<Menu>();
        for (Menu menu : Menu.findAllMenus()) {
            String menuStr = String.valueOf(menu.getNommenu() +  " "  + menu.getUrlmenu() +  " "  + menu.getIcomenu() +  " "  + menu.getTipomenu());
            if (menuStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(menu);
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
        findAllAccesoes();
        return "acceso";
    }

	public String displayCreateDialog() {
        acceso = new Acceso();
        createDialogVisible = true;
        return "acceso";
    }

	public String persist() {
        String message = "";
        if (acceso.getIdacceso() != null) {
            acceso.merge();
            message = "message_successfully_updated";
        } else {
            acceso.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Acceso");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllAccesoes();
    }

	public String delete() {
        acceso.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Acceso");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllAccesoes();
    }

	public void reset() {
        acceso = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}

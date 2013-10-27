package com.horarios.jsf;
import com.horarios.Acceso;
import com.horarios.Menu;
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
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean  {

	private static final long serialVersionUID = 1L;

	private String name = "Menus";

	private Menu menu;

	private List<Menu> allMenus;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Acceso> selectedAccesoes;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nommenu");
        columns.add("urlmenu");
        columns.add("icomenu");
        columns.add("tipomenu");
        columns.add("idsubmenu");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Menu> getAllMenus() {
        return allMenus;
    }

	public void setAllMenus(List<Menu> allMenus) {
        this.allMenus = allMenus;
    }

	public String findAllMenus() {
        allMenus = Menu.findAllMenus();
        dataVisible = !allMenus.isEmpty();
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
        
        OutputLabel nommenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nommenuCreateOutput.setFor("nommenuCreateInput");
        nommenuCreateOutput.setId("nommenuCreateOutput");
        nommenuCreateOutput.setValue("Nommenu:");
        htmlPanelGrid.getChildren().add(nommenuCreateOutput);
        
        InputTextarea nommenuCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nommenuCreateInput.setId("nommenuCreateInput");
        nommenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.nommenu}", String.class));
        LengthValidator nommenuCreateInputValidator = new LengthValidator();
        nommenuCreateInputValidator.setMaximum(200);
        nommenuCreateInput.addValidator(nommenuCreateInputValidator);
        nommenuCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nommenuCreateInput);
        
        Message nommenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nommenuCreateInputMessage.setId("nommenuCreateInputMessage");
        nommenuCreateInputMessage.setFor("nommenuCreateInput");
        nommenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nommenuCreateInputMessage);
        
        OutputLabel urlmenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        urlmenuCreateOutput.setFor("urlmenuCreateInput");
        urlmenuCreateOutput.setId("urlmenuCreateOutput");
        urlmenuCreateOutput.setValue("Urlmenu:");
        htmlPanelGrid.getChildren().add(urlmenuCreateOutput);
        
        InputTextarea urlmenuCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        urlmenuCreateInput.setId("urlmenuCreateInput");
        urlmenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.urlmenu}", String.class));
        LengthValidator urlmenuCreateInputValidator = new LengthValidator();
        urlmenuCreateInputValidator.setMaximum(200);
        urlmenuCreateInput.addValidator(urlmenuCreateInputValidator);
        urlmenuCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(urlmenuCreateInput);
        
        Message urlmenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        urlmenuCreateInputMessage.setId("urlmenuCreateInputMessage");
        urlmenuCreateInputMessage.setFor("urlmenuCreateInput");
        urlmenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(urlmenuCreateInputMessage);
        
        OutputLabel icomenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        icomenuCreateOutput.setFor("icomenuCreateInput");
        icomenuCreateOutput.setId("icomenuCreateOutput");
        icomenuCreateOutput.setValue("Icomenu:");
        htmlPanelGrid.getChildren().add(icomenuCreateOutput);
        
        InputTextarea icomenuCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        icomenuCreateInput.setId("icomenuCreateInput");
        icomenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.icomenu}", String.class));
        LengthValidator icomenuCreateInputValidator = new LengthValidator();
        icomenuCreateInputValidator.setMaximum(200);
        icomenuCreateInput.addValidator(icomenuCreateInputValidator);
        icomenuCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(icomenuCreateInput);
        
        Message icomenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        icomenuCreateInputMessage.setId("icomenuCreateInputMessage");
        icomenuCreateInputMessage.setFor("icomenuCreateInput");
        icomenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(icomenuCreateInputMessage);
        
        OutputLabel tipomenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        tipomenuCreateOutput.setFor("tipomenuCreateInput");
        tipomenuCreateOutput.setId("tipomenuCreateOutput");
        tipomenuCreateOutput.setValue("Tipomenu:");
        htmlPanelGrid.getChildren().add(tipomenuCreateOutput);
        
        Spinner tipomenuCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        tipomenuCreateInput.setId("tipomenuCreateInput");
        tipomenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.tipomenu}", Integer.class));
        tipomenuCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(tipomenuCreateInput);
        
        Message tipomenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        tipomenuCreateInputMessage.setId("tipomenuCreateInputMessage");
        tipomenuCreateInputMessage.setFor("tipomenuCreateInput");
        tipomenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(tipomenuCreateInputMessage);
        
        OutputLabel idsubmenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idsubmenuCreateOutput.setFor("idsubmenuCreateInput");
        idsubmenuCreateOutput.setId("idsubmenuCreateOutput");
        idsubmenuCreateOutput.setValue("Idsubmenu:");
        htmlPanelGrid.getChildren().add(idsubmenuCreateOutput);
        
        Spinner idsubmenuCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        idsubmenuCreateInput.setId("idsubmenuCreateInput");
        idsubmenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.idsubmenu}", Integer.class));
        idsubmenuCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(idsubmenuCreateInput);
        
        Message idsubmenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idsubmenuCreateInputMessage.setId("idsubmenuCreateInputMessage");
        idsubmenuCreateInputMessage.setFor("idsubmenuCreateInput");
        idsubmenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idsubmenuCreateInputMessage);
        
        OutputLabel ordenmenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        ordenmenuCreateOutput.setFor("ordenmenuCreateInput");
        ordenmenuCreateOutput.setId("ordenmenuCreateOutput");
        ordenmenuCreateOutput.setValue("Ordenmenu:");
        htmlPanelGrid.getChildren().add(ordenmenuCreateOutput);
        
        Spinner ordenmenuCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        ordenmenuCreateInput.setId("ordenmenuCreateInput");
        ordenmenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.ordenmenu}", Integer.class));
        ordenmenuCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(ordenmenuCreateInput);
        
        Message ordenmenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        ordenmenuCreateInputMessage.setId("ordenmenuCreateInputMessage");
        ordenmenuCreateInputMessage.setFor("ordenmenuCreateInput");
        ordenmenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(ordenmenuCreateInputMessage);
        
        OutputLabel mastermenuCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        mastermenuCreateOutput.setFor("mastermenuCreateInput");
        mastermenuCreateOutput.setId("mastermenuCreateOutput");
        mastermenuCreateOutput.setValue("Mastermenu:");
        htmlPanelGrid.getChildren().add(mastermenuCreateOutput);
        
        Spinner mastermenuCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        mastermenuCreateInput.setId("mastermenuCreateInput");
        mastermenuCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.mastermenu}", Integer.class));
        mastermenuCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(mastermenuCreateInput);
        
        Message mastermenuCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        mastermenuCreateInputMessage.setId("mastermenuCreateInputMessage");
        mastermenuCreateInputMessage.setFor("mastermenuCreateInput");
        mastermenuCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(mastermenuCreateInputMessage);
        
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
        
        OutputLabel nommenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nommenuEditOutput.setFor("nommenuEditInput");
        nommenuEditOutput.setId("nommenuEditOutput");
        nommenuEditOutput.setValue("Nommenu:");
        htmlPanelGrid.getChildren().add(nommenuEditOutput);
        
        InputTextarea nommenuEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nommenuEditInput.setId("nommenuEditInput");
        nommenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.nommenu}", String.class));
        LengthValidator nommenuEditInputValidator = new LengthValidator();
        nommenuEditInputValidator.setMaximum(200);
        nommenuEditInput.addValidator(nommenuEditInputValidator);
        nommenuEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nommenuEditInput);
        
        Message nommenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nommenuEditInputMessage.setId("nommenuEditInputMessage");
        nommenuEditInputMessage.setFor("nommenuEditInput");
        nommenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nommenuEditInputMessage);
        
        OutputLabel urlmenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        urlmenuEditOutput.setFor("urlmenuEditInput");
        urlmenuEditOutput.setId("urlmenuEditOutput");
        urlmenuEditOutput.setValue("Urlmenu:");
        htmlPanelGrid.getChildren().add(urlmenuEditOutput);
        
        InputTextarea urlmenuEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        urlmenuEditInput.setId("urlmenuEditInput");
        urlmenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.urlmenu}", String.class));
        LengthValidator urlmenuEditInputValidator = new LengthValidator();
        urlmenuEditInputValidator.setMaximum(200);
        urlmenuEditInput.addValidator(urlmenuEditInputValidator);
        urlmenuEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(urlmenuEditInput);
        
        Message urlmenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        urlmenuEditInputMessage.setId("urlmenuEditInputMessage");
        urlmenuEditInputMessage.setFor("urlmenuEditInput");
        urlmenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(urlmenuEditInputMessage);
        
        OutputLabel icomenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        icomenuEditOutput.setFor("icomenuEditInput");
        icomenuEditOutput.setId("icomenuEditOutput");
        icomenuEditOutput.setValue("Icomenu:");
        htmlPanelGrid.getChildren().add(icomenuEditOutput);
        
        InputTextarea icomenuEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        icomenuEditInput.setId("icomenuEditInput");
        icomenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.icomenu}", String.class));
        LengthValidator icomenuEditInputValidator = new LengthValidator();
        icomenuEditInputValidator.setMaximum(200);
        icomenuEditInput.addValidator(icomenuEditInputValidator);
        icomenuEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(icomenuEditInput);
        
        Message icomenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        icomenuEditInputMessage.setId("icomenuEditInputMessage");
        icomenuEditInputMessage.setFor("icomenuEditInput");
        icomenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(icomenuEditInputMessage);
        
        OutputLabel tipomenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        tipomenuEditOutput.setFor("tipomenuEditInput");
        tipomenuEditOutput.setId("tipomenuEditOutput");
        tipomenuEditOutput.setValue("Tipomenu:");
        htmlPanelGrid.getChildren().add(tipomenuEditOutput);
        
        Spinner tipomenuEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        tipomenuEditInput.setId("tipomenuEditInput");
        tipomenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.tipomenu}", Integer.class));
        tipomenuEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(tipomenuEditInput);
        
        Message tipomenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        tipomenuEditInputMessage.setId("tipomenuEditInputMessage");
        tipomenuEditInputMessage.setFor("tipomenuEditInput");
        tipomenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(tipomenuEditInputMessage);
        
        OutputLabel idsubmenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idsubmenuEditOutput.setFor("idsubmenuEditInput");
        idsubmenuEditOutput.setId("idsubmenuEditOutput");
        idsubmenuEditOutput.setValue("Idsubmenu:");
        htmlPanelGrid.getChildren().add(idsubmenuEditOutput);
        
        Spinner idsubmenuEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        idsubmenuEditInput.setId("idsubmenuEditInput");
        idsubmenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.idsubmenu}", Integer.class));
        idsubmenuEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(idsubmenuEditInput);
        
        Message idsubmenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idsubmenuEditInputMessage.setId("idsubmenuEditInputMessage");
        idsubmenuEditInputMessage.setFor("idsubmenuEditInput");
        idsubmenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idsubmenuEditInputMessage);
        
        OutputLabel ordenmenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        ordenmenuEditOutput.setFor("ordenmenuEditInput");
        ordenmenuEditOutput.setId("ordenmenuEditOutput");
        ordenmenuEditOutput.setValue("Ordenmenu:");
        htmlPanelGrid.getChildren().add(ordenmenuEditOutput);
        
        Spinner ordenmenuEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        ordenmenuEditInput.setId("ordenmenuEditInput");
        ordenmenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.ordenmenu}", Integer.class));
        ordenmenuEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(ordenmenuEditInput);
        
        Message ordenmenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        ordenmenuEditInputMessage.setId("ordenmenuEditInputMessage");
        ordenmenuEditInputMessage.setFor("ordenmenuEditInput");
        ordenmenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(ordenmenuEditInputMessage);
        
        OutputLabel mastermenuEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        mastermenuEditOutput.setFor("mastermenuEditInput");
        mastermenuEditOutput.setId("mastermenuEditOutput");
        mastermenuEditOutput.setValue("Mastermenu:");
        htmlPanelGrid.getChildren().add(mastermenuEditOutput);
        
        Spinner mastermenuEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        mastermenuEditInput.setId("mastermenuEditInput");
        mastermenuEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.mastermenu}", Integer.class));
        mastermenuEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(mastermenuEditInput);
        
        Message mastermenuEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        mastermenuEditInputMessage.setId("mastermenuEditInputMessage");
        mastermenuEditInputMessage.setFor("mastermenuEditInput");
        mastermenuEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(mastermenuEditInputMessage);
        
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
        
        HtmlOutputText nommenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nommenuLabel.setId("nommenuLabel");
        nommenuLabel.setValue("Nommenu:");
        htmlPanelGrid.getChildren().add(nommenuLabel);
        
        InputTextarea nommenuValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nommenuValue.setId("nommenuValue");
        nommenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.nommenu}", String.class));
        nommenuValue.setReadonly(true);
        nommenuValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nommenuValue);
        
        HtmlOutputText urlmenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        urlmenuLabel.setId("urlmenuLabel");
        urlmenuLabel.setValue("Urlmenu:");
        htmlPanelGrid.getChildren().add(urlmenuLabel);
        
        InputTextarea urlmenuValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        urlmenuValue.setId("urlmenuValue");
        urlmenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.urlmenu}", String.class));
        urlmenuValue.setReadonly(true);
        urlmenuValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(urlmenuValue);
        
        HtmlOutputText icomenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        icomenuLabel.setId("icomenuLabel");
        icomenuLabel.setValue("Icomenu:");
        htmlPanelGrid.getChildren().add(icomenuLabel);
        
        InputTextarea icomenuValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        icomenuValue.setId("icomenuValue");
        icomenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.icomenu}", String.class));
        icomenuValue.setReadonly(true);
        icomenuValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(icomenuValue);
        
        HtmlOutputText tipomenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        tipomenuLabel.setId("tipomenuLabel");
        tipomenuLabel.setValue("Tipomenu:");
        htmlPanelGrid.getChildren().add(tipomenuLabel);
        
        HtmlOutputText tipomenuValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        tipomenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.tipomenu}", String.class));
        htmlPanelGrid.getChildren().add(tipomenuValue);
        
        HtmlOutputText idsubmenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idsubmenuLabel.setId("idsubmenuLabel");
        idsubmenuLabel.setValue("Idsubmenu:");
        htmlPanelGrid.getChildren().add(idsubmenuLabel);
        
        HtmlOutputText idsubmenuValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idsubmenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.idsubmenu}", String.class));
        htmlPanelGrid.getChildren().add(idsubmenuValue);
        
        HtmlOutputText ordenmenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        ordenmenuLabel.setId("ordenmenuLabel");
        ordenmenuLabel.setValue("Ordenmenu:");
        htmlPanelGrid.getChildren().add(ordenmenuLabel);
        
        HtmlOutputText ordenmenuValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        ordenmenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.ordenmenu}", String.class));
        htmlPanelGrid.getChildren().add(ordenmenuValue);
        
        HtmlOutputText mastermenuLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        mastermenuLabel.setId("mastermenuLabel");
        mastermenuLabel.setValue("Mastermenu:");
        htmlPanelGrid.getChildren().add(mastermenuLabel);
        
        HtmlOutputText mastermenuValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        mastermenuValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{menuBean.menu.mastermenu}", String.class));
        htmlPanelGrid.getChildren().add(mastermenuValue);
        
        return htmlPanelGrid;
    }

	public Menu getMenu() {
        if (menu == null) {
            menu = new Menu();
        }
        return menu;
    }

	public void setMenu(Menu menu) {
        this.menu = menu;
    }

	public List<Acceso> getSelectedAccesoes() {
        return selectedAccesoes;
    }

	public void setSelectedAccesoes(List<Acceso> selectedAccesoes) {
        if (selectedAccesoes != null) {
            menu.setAccesoes(new HashSet<Acceso>(selectedAccesoes));
        }
        this.selectedAccesoes = selectedAccesoes;
    }

	public String onEdit() {
        if (menu != null && menu.getAccesoes() != null) {
            selectedAccesoes = new ArrayList<Acceso>(menu.getAccesoes());
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
        findAllMenus();
        return "menu";
    }

	public String displayCreateDialog() {
        menu = new Menu();
        createDialogVisible = true;
        return "menu";
    }

	public String persist() {
        String message = "";
        if (menu.getIdmenu() != null) {
            menu.merge();
            message = "message_successfully_updated";
        } else {
            menu.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Menu");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllMenus();
    }

	public String delete() {
        menu.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Menu");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllMenus();
    }

	public void reset() {
        menu = null;
        selectedAccesoes = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}

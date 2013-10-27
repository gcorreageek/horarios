package com.horarios.jsf;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import com.horarios.Cargo;
import com.horarios.Usuario;
import com.horarios.jsf.converter.CargoConverter;
import com.horarios.jsf.util.MessageFactory;

@Configurable
@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final Logger log = Logger.getLogger(UsuarioBean.class) ;
 

	private String name = "Usuarios";

	private Usuario usuario;

	private List<Usuario> allUsuarios;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nomusuario");
        columns.add("emailusuario");
        columns.add("loginusuario");
        columns.add("passusuario");
        columns.add("habilitado");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Usuario> getAllUsuarios() {
        return allUsuarios;
    }

	public void setAllUsuarios(List<Usuario> allUsuarios) {
        this.allUsuarios = allUsuarios;
    }

	public String findAllUsuarios() {
        allUsuarios = Usuario.findAllUsuarios();
        dataVisible = !allUsuarios.isEmpty();
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

	public HtmlPanelGrid populateCreatePanel() {System.out.println("sss");
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
        idcargoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.idcargo}", Cargo.class));
        idcargoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{usuarioBean.completeIdcargo}", List.class, new Class[] { String.class }));
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
        
        OutputLabel nomusuarioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomusuarioCreateOutput.setFor("nomusuarioCreateInput");
        nomusuarioCreateOutput.setId("nomusuarioCreateOutput");
        nomusuarioCreateOutput.setValue("Nomusuario:");
        htmlPanelGrid.getChildren().add(nomusuarioCreateOutput);
        
        InputTextarea nomusuarioCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomusuarioCreateInput.setId("nomusuarioCreateInput");
        nomusuarioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.nomusuario}", String.class));
        LengthValidator nomusuarioCreateInputValidator = new LengthValidator();
        nomusuarioCreateInputValidator.setMaximum(200);
        nomusuarioCreateInput.addValidator(nomusuarioCreateInputValidator);
        nomusuarioCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomusuarioCreateInput);
        
        Message nomusuarioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomusuarioCreateInputMessage.setId("nomusuarioCreateInputMessage");
        nomusuarioCreateInputMessage.setFor("nomusuarioCreateInput");
        nomusuarioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomusuarioCreateInputMessage);
        
        OutputLabel emailusuarioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        emailusuarioCreateOutput.setFor("emailusuarioCreateInput");
        emailusuarioCreateOutput.setId("emailusuarioCreateOutput");
        emailusuarioCreateOutput.setValue("Emailusuario:");
        htmlPanelGrid.getChildren().add(emailusuarioCreateOutput);
        
        InputTextarea emailusuarioCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        emailusuarioCreateInput.setId("emailusuarioCreateInput");
        emailusuarioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.emailusuario}", String.class));
        LengthValidator emailusuarioCreateInputValidator = new LengthValidator();
        emailusuarioCreateInputValidator.setMaximum(200);
        emailusuarioCreateInput.addValidator(emailusuarioCreateInputValidator);
        emailusuarioCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(emailusuarioCreateInput);
        
        Message emailusuarioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        emailusuarioCreateInputMessage.setId("emailusuarioCreateInputMessage");
        emailusuarioCreateInputMessage.setFor("emailusuarioCreateInput");
        emailusuarioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(emailusuarioCreateInputMessage);
        
        OutputLabel loginusuarioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        loginusuarioCreateOutput.setFor("loginusuarioCreateInput");
        loginusuarioCreateOutput.setId("loginusuarioCreateOutput");
        loginusuarioCreateOutput.setValue("Loginusuario:");
        htmlPanelGrid.getChildren().add(loginusuarioCreateOutput);
        
        InputTextarea loginusuarioCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        loginusuarioCreateInput.setId("loginusuarioCreateInput");
        loginusuarioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.loginusuario}", String.class));
        LengthValidator loginusuarioCreateInputValidator = new LengthValidator();
        loginusuarioCreateInputValidator.setMaximum(200);
        loginusuarioCreateInput.addValidator(loginusuarioCreateInputValidator);
        loginusuarioCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(loginusuarioCreateInput);
        
        Message loginusuarioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        loginusuarioCreateInputMessage.setId("loginusuarioCreateInputMessage");
        loginusuarioCreateInputMessage.setFor("loginusuarioCreateInput");
        loginusuarioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(loginusuarioCreateInputMessage);
        
        OutputLabel passusuarioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        passusuarioCreateOutput.setFor("passusuarioCreateInput");
        passusuarioCreateOutput.setId("passusuarioCreateOutput");
        passusuarioCreateOutput.setValue("Passusuario:");
        htmlPanelGrid.getChildren().add(passusuarioCreateOutput);
        
        InputTextarea passusuarioCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        passusuarioCreateInput.setId("passusuarioCreateInput");
        passusuarioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.passusuario}", String.class));
        LengthValidator passusuarioCreateInputValidator = new LengthValidator();
        passusuarioCreateInputValidator.setMaximum(200);
        passusuarioCreateInput.addValidator(passusuarioCreateInputValidator);
        passusuarioCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(passusuarioCreateInput);
        
        Message passusuarioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        passusuarioCreateInputMessage.setId("passusuarioCreateInputMessage");
        passusuarioCreateInputMessage.setFor("passusuarioCreateInput");
        passusuarioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(passusuarioCreateInputMessage);
        
        OutputLabel habilitadoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        habilitadoCreateOutput.setFor("habilitadoCreateInput");
        habilitadoCreateOutput.setId("habilitadoCreateOutput");
        habilitadoCreateOutput.setValue("Habilitado:");
        htmlPanelGrid.getChildren().add(habilitadoCreateOutput);
        
        InputTextarea habilitadoCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        habilitadoCreateInput.setId("habilitadoCreateInput");
        habilitadoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.habilitado}", String.class));
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
        idcargoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.idcargo}", Cargo.class));
        idcargoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{usuarioBean.completeIdcargo}", List.class, new Class[] { String.class }));
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
        
        OutputLabel nomusuarioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomusuarioEditOutput.setFor("nomusuarioEditInput");
        nomusuarioEditOutput.setId("nomusuarioEditOutput");
        nomusuarioEditOutput.setValue("Nomusuario:");
        htmlPanelGrid.getChildren().add(nomusuarioEditOutput);
        
        InputTextarea nomusuarioEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomusuarioEditInput.setId("nomusuarioEditInput");
        nomusuarioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.nomusuario}", String.class));
        LengthValidator nomusuarioEditInputValidator = new LengthValidator();
        nomusuarioEditInputValidator.setMaximum(200);
        nomusuarioEditInput.addValidator(nomusuarioEditInputValidator);
        nomusuarioEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomusuarioEditInput);
        
        Message nomusuarioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomusuarioEditInputMessage.setId("nomusuarioEditInputMessage");
        nomusuarioEditInputMessage.setFor("nomusuarioEditInput");
        nomusuarioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomusuarioEditInputMessage);
        
        OutputLabel emailusuarioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        emailusuarioEditOutput.setFor("emailusuarioEditInput");
        emailusuarioEditOutput.setId("emailusuarioEditOutput");
        emailusuarioEditOutput.setValue("Emailusuario:");
        htmlPanelGrid.getChildren().add(emailusuarioEditOutput);
        
        InputTextarea emailusuarioEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        emailusuarioEditInput.setId("emailusuarioEditInput");
        emailusuarioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.emailusuario}", String.class));
        LengthValidator emailusuarioEditInputValidator = new LengthValidator();
        emailusuarioEditInputValidator.setMaximum(200);
        emailusuarioEditInput.addValidator(emailusuarioEditInputValidator);
        emailusuarioEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(emailusuarioEditInput);
        
        Message emailusuarioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        emailusuarioEditInputMessage.setId("emailusuarioEditInputMessage");
        emailusuarioEditInputMessage.setFor("emailusuarioEditInput");
        emailusuarioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(emailusuarioEditInputMessage);
        
        OutputLabel loginusuarioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        loginusuarioEditOutput.setFor("loginusuarioEditInput");
        loginusuarioEditOutput.setId("loginusuarioEditOutput");
        loginusuarioEditOutput.setValue("Loginusuario:");
        htmlPanelGrid.getChildren().add(loginusuarioEditOutput);
        
        InputTextarea loginusuarioEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        loginusuarioEditInput.setId("loginusuarioEditInput");
        loginusuarioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.loginusuario}", String.class));
        LengthValidator loginusuarioEditInputValidator = new LengthValidator();
        loginusuarioEditInputValidator.setMaximum(200);
        loginusuarioEditInput.addValidator(loginusuarioEditInputValidator);
        loginusuarioEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(loginusuarioEditInput);
        
        Message loginusuarioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        loginusuarioEditInputMessage.setId("loginusuarioEditInputMessage");
        loginusuarioEditInputMessage.setFor("loginusuarioEditInput");
        loginusuarioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(loginusuarioEditInputMessage);
        
        OutputLabel passusuarioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        passusuarioEditOutput.setFor("passusuarioEditInput");
        passusuarioEditOutput.setId("passusuarioEditOutput");
        passusuarioEditOutput.setValue("Passusuario:");
        htmlPanelGrid.getChildren().add(passusuarioEditOutput);
        
        InputTextarea passusuarioEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        passusuarioEditInput.setId("passusuarioEditInput");
        passusuarioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.passusuario}", String.class));
        LengthValidator passusuarioEditInputValidator = new LengthValidator();
        passusuarioEditInputValidator.setMaximum(200);
        passusuarioEditInput.addValidator(passusuarioEditInputValidator);
        passusuarioEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(passusuarioEditInput);
        
        Message passusuarioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        passusuarioEditInputMessage.setId("passusuarioEditInputMessage");
        passusuarioEditInputMessage.setFor("passusuarioEditInput");
        passusuarioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(passusuarioEditInputMessage);
        
        OutputLabel habilitadoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        habilitadoEditOutput.setFor("habilitadoEditInput");
        habilitadoEditOutput.setId("habilitadoEditOutput");
        habilitadoEditOutput.setValue("Habilitado:");
        htmlPanelGrid.getChildren().add(habilitadoEditOutput);
        
        InputTextarea habilitadoEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        habilitadoEditInput.setId("habilitadoEditInput");
        habilitadoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.habilitado}", String.class));
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
        idcargoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.idcargo}", Cargo.class));
        idcargoValue.setConverter(new CargoConverter());
        htmlPanelGrid.getChildren().add(idcargoValue);
        
        HtmlOutputText nomusuarioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomusuarioLabel.setId("nomusuarioLabel");
        nomusuarioLabel.setValue("Nomusuario:");
        htmlPanelGrid.getChildren().add(nomusuarioLabel);
        
        InputTextarea nomusuarioValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomusuarioValue.setId("nomusuarioValue");
        nomusuarioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.nomusuario}", String.class));
        nomusuarioValue.setReadonly(true);
        nomusuarioValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nomusuarioValue);
        
        HtmlOutputText emailusuarioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        emailusuarioLabel.setId("emailusuarioLabel");
        emailusuarioLabel.setValue("Emailusuario:");
        htmlPanelGrid.getChildren().add(emailusuarioLabel);
        
        InputTextarea emailusuarioValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        emailusuarioValue.setId("emailusuarioValue");
        emailusuarioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.emailusuario}", String.class));
        emailusuarioValue.setReadonly(true);
        emailusuarioValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(emailusuarioValue);
        
        HtmlOutputText loginusuarioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        loginusuarioLabel.setId("loginusuarioLabel");
        loginusuarioLabel.setValue("Loginusuario:");
        htmlPanelGrid.getChildren().add(loginusuarioLabel);
        
        InputTextarea loginusuarioValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        loginusuarioValue.setId("loginusuarioValue");
        loginusuarioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.loginusuario}", String.class));
        loginusuarioValue.setReadonly(true);
        loginusuarioValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(loginusuarioValue);
        
        HtmlOutputText passusuarioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        passusuarioLabel.setId("passusuarioLabel");
        passusuarioLabel.setValue("Passusuario:");
        htmlPanelGrid.getChildren().add(passusuarioLabel);
        
        InputTextarea passusuarioValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        passusuarioValue.setId("passusuarioValue");
        passusuarioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.passusuario}", String.class));
        passusuarioValue.setReadonly(true);
        passusuarioValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(passusuarioValue);
        
        HtmlOutputText habilitadoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        habilitadoLabel.setId("habilitadoLabel");
        habilitadoLabel.setValue("Habilitado:");
        htmlPanelGrid.getChildren().add(habilitadoLabel);
        
        InputTextarea habilitadoValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        habilitadoValue.setId("habilitadoValue");
        habilitadoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuarioBean.usuario.habilitado}", String.class));
        habilitadoValue.setReadonly(true);
        habilitadoValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(habilitadoValue);
        
        return htmlPanelGrid;
    }

	public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

	public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        findAllUsuarios();
        return "usuario";
    }

	public String displayCreateDialog() {
        usuario = new Usuario();
        createDialogVisible = true;
        return "usuario";
    }

	public String persist() {
        String message = "";
        if (usuario.getIdusuario() != null) {
            usuario.merge();
            message = "message_successfully_updated";
        } else {
            usuario.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Usuario");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllUsuarios();
    }

	public String delete() {
        usuario.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Usuario");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllUsuarios();
    }

	public void reset() {
        usuario = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
	
	
	public void  validaIngreso(){
		Map<String, Object> lasesion; 
		log.debug("usuario:"+usuario.getLoginusuario());
		Usuario u = Usuario.getUsuariosXLogin(usuario);
		
		if(u==null){
			log.info("Es nulo!");
		}else{
			if(u.getPassusuario().equals(usuario.getPassusuario())){
				  		ExternalContext ctx =  FacesContext.getCurrentInstance().getExternalContext();
					  String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

					  
					  try { 
					    ((HttpSession) ctx.getSession(false)).invalidate();
 
					    Map<String, Object> mapUsuario = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
						mapUsuario.put("USUARIO", u);
						log.info("ruta:"+ctxPath + "/pages/main2.jsf");
					    ctx.redirect(ctxPath + "/pages/main2.jsf");
					  } catch (IOException ex) {
						  log.error("Error al redireccionar",ex);
					  } 
				log.info("Es OK!");
			}else{
				log.info("Esta mal el Password!");
			} 
		} 
		
		
	}
	
	
	
	
	
	
	
	
	
	
}

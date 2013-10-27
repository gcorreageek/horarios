package com.horarios.jsf;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Configurable;

import com.horarios.Curso;
import com.horarios.Dictado;
import com.horarios.Disponibilidad;
import com.horarios.Docente;
import com.horarios.jsf.util.MessageFactory;

@ManagedBean(name = "docenteBean")
@SessionScoped
@Configurable
public class DocenteBean implements Serializable {
	private final Logger log = Logger.getLogger(getClass()) ;
	private String name = "Docentes";

	private Docente docente;

	private List<Docente> allDocentes;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Dictado> selectedDictadoes;

	private List<Disponibilidad> selectedDisponibilidads;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nomdocente");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Docente> getAllDocentes() {
        return allDocentes;
    }

	public void setAllDocentes(List<Docente> allDocentes) {
        this.allDocentes = allDocentes;
    }

	public String findAllDocentes() {
        allDocentes = Docente.findAllDocentes();
        dataVisible = !allDocentes.isEmpty();
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
        
        HtmlOutputText dictadoesCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dictadoesCreateOutput.setId("dictadoesCreateOutput");
        dictadoesCreateOutput.setValue("Dictadoes:");
        htmlPanelGrid.getChildren().add(dictadoesCreateOutput);
        
        HtmlOutputText dictadoesCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dictadoesCreateInput.setId("dictadoesCreateInput");
        dictadoesCreateInput.setValue("This relationship is managed from the Dictado side");
        htmlPanelGrid.getChildren().add(dictadoesCreateInput);
        
        Message dictadoesCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        dictadoesCreateInputMessage.setId("dictadoesCreateInputMessage");
        dictadoesCreateInputMessage.setFor("dictadoesCreateInput");
        dictadoesCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(dictadoesCreateInputMessage);
        
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
        
        OutputLabel nomdocenteCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomdocenteCreateOutput.setFor("nomdocenteCreateInput");
        nomdocenteCreateOutput.setId("nomdocenteCreateOutput");
        nomdocenteCreateOutput.setValue("Nomdocente:");
        htmlPanelGrid.getChildren().add(nomdocenteCreateOutput);
        
        InputTextarea nomdocenteCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomdocenteCreateInput.setId("nomdocenteCreateInput");
        nomdocenteCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{docenteBean.docente.nomdocente}", String.class));
        LengthValidator nomdocenteCreateInputValidator = new LengthValidator();
        nomdocenteCreateInputValidator.setMaximum(200);
        nomdocenteCreateInput.addValidator(nomdocenteCreateInputValidator);
        nomdocenteCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomdocenteCreateInput);
        
        Message nomdocenteCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomdocenteCreateInputMessage.setId("nomdocenteCreateInputMessage");
        nomdocenteCreateInputMessage.setFor("nomdocenteCreateInput");
        nomdocenteCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomdocenteCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText dictadoesEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dictadoesEditOutput.setId("dictadoesEditOutput");
        dictadoesEditOutput.setValue("Dictadoes:");
        htmlPanelGrid.getChildren().add(dictadoesEditOutput);
        
        HtmlOutputText dictadoesEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dictadoesEditInput.setId("dictadoesEditInput");
        dictadoesEditInput.setValue("This relationship is managed from the Dictado side");
        htmlPanelGrid.getChildren().add(dictadoesEditInput);
        
        Message dictadoesEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        dictadoesEditInputMessage.setId("dictadoesEditInputMessage");
        dictadoesEditInputMessage.setFor("dictadoesEditInput");
        dictadoesEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(dictadoesEditInputMessage);
        
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
        
        OutputLabel nomdocenteEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomdocenteEditOutput.setFor("nomdocenteEditInput");
        nomdocenteEditOutput.setId("nomdocenteEditOutput");
        nomdocenteEditOutput.setValue("Nomdocente:");
        htmlPanelGrid.getChildren().add(nomdocenteEditOutput);
        
        InputTextarea nomdocenteEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomdocenteEditInput.setId("nomdocenteEditInput");
        nomdocenteEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{docenteBean.docente.nomdocente}", String.class));
        LengthValidator nomdocenteEditInputValidator = new LengthValidator();
        nomdocenteEditInputValidator.setMaximum(200);
        nomdocenteEditInput.addValidator(nomdocenteEditInputValidator);
        nomdocenteEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomdocenteEditInput);
        
        Message nomdocenteEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomdocenteEditInputMessage.setId("nomdocenteEditInputMessage");
        nomdocenteEditInputMessage.setFor("nomdocenteEditInput");
        nomdocenteEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomdocenteEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText dictadoesLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dictadoesLabel.setId("dictadoesLabel");
        dictadoesLabel.setValue("Dictadoes:");
        htmlPanelGrid.getChildren().add(dictadoesLabel);
        
        HtmlOutputText dictadoesValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dictadoesValue.setId("dictadoesValue");
        dictadoesValue.setValue("This relationship is managed from the Dictado side");
        htmlPanelGrid.getChildren().add(dictadoesValue);
        
        HtmlOutputText disponibilidadsLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsLabel.setId("disponibilidadsLabel");
        disponibilidadsLabel.setValue("Disponibilidads:");
        htmlPanelGrid.getChildren().add(disponibilidadsLabel);
        
        HtmlOutputText disponibilidadsValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        disponibilidadsValue.setId("disponibilidadsValue");
        disponibilidadsValue.setValue("This relationship is managed from the Disponibilidad side");
        htmlPanelGrid.getChildren().add(disponibilidadsValue);
        
        HtmlOutputText nomdocenteLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomdocenteLabel.setId("nomdocenteLabel");
        nomdocenteLabel.setValue("Nomdocente:");
        htmlPanelGrid.getChildren().add(nomdocenteLabel);
        
        InputTextarea nomdocenteValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomdocenteValue.setId("nomdocenteValue");
        nomdocenteValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{docenteBean.docente.nomdocente}", String.class));
        nomdocenteValue.setReadonly(true);
        nomdocenteValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nomdocenteValue);
        
        return htmlPanelGrid;
    }

	public Docente getDocente() {
        if (docente == null) {
            docente = new Docente();
        }
        return docente;
    }

	public void setDocente(Docente docente) {
        this.docente = docente;
    }

	public List<Dictado> getSelectedDictadoes() {
        return selectedDictadoes;
    }

	public void setSelectedDictadoes(List<Dictado> selectedDictadoes) {
        if (selectedDictadoes != null) {
            docente.setDictadoes(new HashSet<Dictado>(selectedDictadoes));
        }
        this.selectedDictadoes = selectedDictadoes;
    }

	public List<Disponibilidad> getSelectedDisponibilidads() {
        return selectedDisponibilidads;
    }

	public void setSelectedDisponibilidads(List<Disponibilidad> selectedDisponibilidads) {
        if (selectedDisponibilidads != null) {
            docente.setDisponibilidads(new HashSet<Disponibilidad>(selectedDisponibilidads));
        }
        this.selectedDisponibilidads = selectedDisponibilidads;
    }

	public String onEdit() {
        if (docente != null && docente.getDictadoes() != null) {
            selectedDictadoes = new ArrayList<Dictado>(docente.getDictadoes());
        }
        if (docente != null && docente.getDisponibilidads() != null) {
            selectedDisponibilidads = new ArrayList<Disponibilidad>(docente.getDisponibilidads());
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
        findAllDocentes();
        return "docente";
    }

	public String displayCreateDialog() {
        docente = new Docente();
        createDialogVisible = true;
        return "docente";
    }

	public String persist() {
        String message = "";
        if (docente.getIddocente() != null) {
            docente.merge();
            message = "message_successfully_updated";
        } else {
            docente.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Docente");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDocentes();
    }

	public String delete() {
        docente.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Docente");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDocentes();
    }

	public void reset() {
        docente = null;
        selectedDictadoes = null;
        selectedDisponibilidads = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String cargaExcel(){
		log.info("pasa por el cargar excel");
		return "docenteexcel";
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
//		log.info("ruta:"+event.getFile().getFileName());
		boolean r= true;
		FacesMessage msg ;
		FacesMessage msg2 ;
		try {
			POIFSFileSystem excelByte = new POIFSFileSystem(event.getFile().getInputstream());
			HSSFWorkbook excel = new HSSFWorkbook(excelByte);
			HSSFSheet hojaVendedores = excel.getSheetAt(0);
			boolean esCabecera = true;
			Docente docente = null;
			List<Docente> lstDocente = new ArrayList<Docente>();
			log.info("Volcado de excel!");
			for (Iterator iterator = hojaVendedores.iterator(); iterator.hasNext();) {
				HSSFRow fila = (HSSFRow) iterator.next();
				if (esCabecera){ 
					esCabecera = false;
				}else {  
					docente = new Docente();
//					docente.setNomcurso(fila.getCell(0).getRichStringCellValue().toString());
					docente.setNomdocente(fila.getCell(0).getRichStringCellValue().toString());
					lstDocente.add(docente);
				}
			}
			
			log.info("Se comienza a ingresar!");
			
			for (Docente docente2 : lstDocente) {
				setDocente(docente2);
				this.persist();
			}
			log.info("Se termino de ingresar");
			
			log.info("copia el archivo!");
			copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
			log.info("termina de copiar");
		} catch (Exception e) {
			log.error(e);
			 r= false;
		} 
		if (r) {
			log.info("se ingresa y sale mensaje");
			 msg = new FacesMessage("Bien ", event.getFile().getFileName() + " docente.");  
//			 msg2 = new FacesMessage("Bien ", event.getFile().getFileName() + " cargo.");  
		}else{
			 msg = new FacesMessage("Mal ", event.getFile().getFileName() + " docente.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg); 
        
    } 
	
	

	private String destination = "D:\\tmp\\";

	public void copyFile(String fileName, InputStream in) {
		try {

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(destination
					+ fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
			 

			log.info("Nuevo file creado  yeah!");
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	
	
}

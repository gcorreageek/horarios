package com.horarios.jsf;
import com.horarios.Aula;
import com.horarios.Curso;
import com.horarios.Dictado;
import com.horarios.jsf.util.MessageFactory;

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

@Configurable
@ManagedBean(name = "cursoBean")
@SessionScoped
public class CursoBean implements Serializable {
	private final Logger log = Logger.getLogger(getClass()) ;

	private static final long serialVersionUID = 1L;

	private String name = "Cursoes";

	private Curso curso;

	private List<Curso> allCursoes;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Dictado> selectedDictadoes;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nomcurso");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Curso> getAllCursoes() {
        return allCursoes;
    }

	public void setAllCursoes(List<Curso> allCursoes) {
        this.allCursoes = allCursoes;
    }

	public String findAllCursoes() {
        allCursoes = Curso.findAllCursoes();
        dataVisible = !allCursoes.isEmpty();
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
        
        OutputLabel nomcursoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomcursoCreateOutput.setFor("nomcursoCreateInput");
        nomcursoCreateOutput.setId("nomcursoCreateOutput");
        nomcursoCreateOutput.setValue("Nomcurso:");
        htmlPanelGrid.getChildren().add(nomcursoCreateOutput);
        
        InputTextarea nomcursoCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomcursoCreateInput.setId("nomcursoCreateInput");
        nomcursoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cursoBean.curso.nomcurso}", String.class));
        LengthValidator nomcursoCreateInputValidator = new LengthValidator();
        nomcursoCreateInputValidator.setMaximum(200);
        nomcursoCreateInput.addValidator(nomcursoCreateInputValidator);
        nomcursoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomcursoCreateInput);
        
        Message nomcursoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomcursoCreateInputMessage.setId("nomcursoCreateInputMessage");
        nomcursoCreateInputMessage.setFor("nomcursoCreateInput");
        nomcursoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomcursoCreateInputMessage);
        
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
        
        OutputLabel nomcursoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomcursoEditOutput.setFor("nomcursoEditInput");
        nomcursoEditOutput.setId("nomcursoEditOutput");
        nomcursoEditOutput.setValue("Nomcurso:");
        htmlPanelGrid.getChildren().add(nomcursoEditOutput);
        
        InputTextarea nomcursoEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomcursoEditInput.setId("nomcursoEditInput");
        nomcursoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cursoBean.curso.nomcurso}", String.class));
        LengthValidator nomcursoEditInputValidator = new LengthValidator();
        nomcursoEditInputValidator.setMaximum(200);
        nomcursoEditInput.addValidator(nomcursoEditInputValidator);
        nomcursoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomcursoEditInput);
        
        Message nomcursoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomcursoEditInputMessage.setId("nomcursoEditInputMessage");
        nomcursoEditInputMessage.setFor("nomcursoEditInput");
        nomcursoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomcursoEditInputMessage);
        
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
        
        HtmlOutputText nomcursoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomcursoLabel.setId("nomcursoLabel");
        nomcursoLabel.setValue("Nomcurso:");
        htmlPanelGrid.getChildren().add(nomcursoLabel);
        
        InputTextarea nomcursoValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomcursoValue.setId("nomcursoValue");
        nomcursoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cursoBean.curso.nomcurso}", String.class));
        nomcursoValue.setReadonly(true);
        nomcursoValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nomcursoValue);
        
        return htmlPanelGrid;
    }

	public Curso getCurso() {
        if (curso == null) {
            curso = new Curso();
        }
        return curso;
    }

	public void setCurso(Curso curso) {
        this.curso = curso;
    }

	public List<Dictado> getSelectedDictadoes() {
        return selectedDictadoes;
    }

	public void setSelectedDictadoes(List<Dictado> selectedDictadoes) {
        if (selectedDictadoes != null) {
            curso.setDictadoes(new HashSet<Dictado>(selectedDictadoes));
        }
        this.selectedDictadoes = selectedDictadoes;
    }

	public String onEdit() {
        if (curso != null && curso.getDictadoes() != null) {
            selectedDictadoes = new ArrayList<Dictado>(curso.getDictadoes());
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
        findAllCursoes();
        return "curso";
    }

	public String displayCreateDialog() {
        curso = new Curso();
        createDialogVisible = true;
        return "curso";
    }

	public String persist() {
        String message = "";
        if (curso.getIdcurso() != null) {
            curso.merge();
            message = "message_successfully_updated";
        } else {
            curso.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Curso");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCursoes();
    }

	public String delete() {
        curso.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Curso");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCursoes();
    }

	public void reset() {
        curso = null;
        selectedDictadoes = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	public String cargaExcel(){
		log.info("pasa por el cargar excel");
		return "cursoexcel";
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
			Curso curso = null;
			List<Curso> lstCurso = new ArrayList<Curso>();
			log.info("Volcado de excel!");
			for (Iterator iterator = hojaVendedores.iterator(); iterator.hasNext();) {
				HSSFRow fila = (HSSFRow) iterator.next();
				if (esCabecera){ 
					esCabecera = false;
				}else {  
					curso = new Curso();
					curso.setNomcurso(fila.getCell(0).getRichStringCellValue().toString());
//					Nomaula(fila.getCell(0).getRichStringCellValue().toString());
					lstCurso.add(curso);
				}
			}
			
			log.info("Se comienza a ingresar!");
			
			for (Curso curso2 : lstCurso) {
				setCurso(curso2);
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
			 msg = new FacesMessage("Bien ", event.getFile().getFileName() + " curso.");  
//			 msg2 = new FacesMessage("Bien ", event.getFile().getFileName() + " cargo.");  
		}else{
			 msg = new FacesMessage("Mal ", event.getFile().getFileName() + " curso.");
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

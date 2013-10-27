package com.horarios.jsf;
import com.horarios.Aula;
import com.horarios.Curso;
import com.horarios.Dictado;
import com.horarios.Docente;
import com.horarios.jsf.converter.CursoConverter;
import com.horarios.jsf.converter.DocenteConverter;
import com.horarios.jsf.util.MessageFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "dictadoBean")
@SessionScoped
public class DictadoBean  implements Serializable {

	private final Logger log = Logger.getLogger(getClass()) ;
	private String name = "Dictadoes";

	private Dictado dictado;

	private List<Dictado> allDictadoes;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Dictado> getAllDictadoes() {
        return allDictadoes;
    }

	public void setAllDictadoes(List<Dictado> allDictadoes) {
        this.allDictadoes = allDictadoes;
    }

	public String findAllDictadoes() {
        allDictadoes = Dictado.findAllDictadoes();
        dataVisible = !allDictadoes.isEmpty();
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
        
        OutputLabel idcursoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idcursoCreateOutput.setFor("idcursoCreateInput");
        idcursoCreateOutput.setId("idcursoCreateOutput");
        idcursoCreateOutput.setValue("Idcurso:");
        htmlPanelGrid.getChildren().add(idcursoCreateOutput);
        
        AutoComplete idcursoCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idcursoCreateInput.setId("idcursoCreateInput");
        idcursoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{dictadoBean.dictado.idcurso}", Curso.class));
        idcursoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.completeIdcurso}", List.class, new Class[] { String.class }));
        idcursoCreateInput.setDropdown(true);
        idcursoCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idcurso", String.class));
        idcursoCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idcurso.nomcurso} #{idcurso.idcurso}", String.class));
        idcursoCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idcurso}", Curso.class));
        idcursoCreateInput.setConverter(new CursoConverter());
        idcursoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idcursoCreateInput);
        
        Message idcursoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idcursoCreateInputMessage.setId("idcursoCreateInputMessage");
        idcursoCreateInputMessage.setFor("idcursoCreateInput");
        idcursoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idcursoCreateInputMessage);
        
        OutputLabel iddocenteCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        iddocenteCreateOutput.setFor("iddocenteCreateInput");
        iddocenteCreateOutput.setId("iddocenteCreateOutput");
        iddocenteCreateOutput.setValue("Iddocente:");
        htmlPanelGrid.getChildren().add(iddocenteCreateOutput);
        
        AutoComplete iddocenteCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        iddocenteCreateInput.setId("iddocenteCreateInput");
        iddocenteCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{dictadoBean.dictado.iddocente}", Docente.class));
        iddocenteCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.completeIddocente}", List.class, new Class[] { String.class }));
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
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel idcursoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idcursoEditOutput.setFor("idcursoEditInput");
        idcursoEditOutput.setId("idcursoEditOutput");
        idcursoEditOutput.setValue("Idcurso:");
        htmlPanelGrid.getChildren().add(idcursoEditOutput);
        
        AutoComplete idcursoEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        idcursoEditInput.setId("idcursoEditInput");
        idcursoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{dictadoBean.dictado.idcurso}", Curso.class));
        idcursoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.completeIdcurso}", List.class, new Class[] { String.class }));
        idcursoEditInput.setDropdown(true);
        idcursoEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "idcurso", String.class));
        idcursoEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{idcurso.nomcurso} #{idcurso.idcurso}", String.class));
        idcursoEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{idcurso}", Curso.class));
        idcursoEditInput.setConverter(new CursoConverter());
        idcursoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(idcursoEditInput);
        
        Message idcursoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idcursoEditInputMessage.setId("idcursoEditInputMessage");
        idcursoEditInputMessage.setFor("idcursoEditInput");
        idcursoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idcursoEditInputMessage);
        
        OutputLabel iddocenteEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        iddocenteEditOutput.setFor("iddocenteEditInput");
        iddocenteEditOutput.setId("iddocenteEditOutput");
        iddocenteEditOutput.setValue("Iddocente:");
        htmlPanelGrid.getChildren().add(iddocenteEditOutput);
        
        AutoComplete iddocenteEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        iddocenteEditInput.setId("iddocenteEditInput");
        iddocenteEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{dictadoBean.dictado.iddocente}", Docente.class));
        iddocenteEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.completeIddocente}", List.class, new Class[] { String.class }));
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
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText idcursoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idcursoLabel.setId("idcursoLabel");
        idcursoLabel.setValue("Idcurso:");
        htmlPanelGrid.getChildren().add(idcursoLabel);
        
        HtmlOutputText idcursoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idcursoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{dictadoBean.dictado.idcurso}", Curso.class));
        idcursoValue.setConverter(new CursoConverter());
        htmlPanelGrid.getChildren().add(idcursoValue);
        
        HtmlOutputText iddocenteLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        iddocenteLabel.setId("iddocenteLabel");
        iddocenteLabel.setValue("Iddocente:");
        htmlPanelGrid.getChildren().add(iddocenteLabel);
        
        HtmlOutputText iddocenteValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        iddocenteValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{dictadoBean.dictado.iddocente}", Docente.class));
        iddocenteValue.setConverter(new DocenteConverter());
        htmlPanelGrid.getChildren().add(iddocenteValue);
        
        return htmlPanelGrid;
    }

	public Dictado getDictado() {
        if (dictado == null) {
            dictado = new Dictado();
        }
        return dictado;
    }

	public void setDictado(Dictado dictado) {
        this.dictado = dictado;
    }

	public List<Curso> completeIdcurso(String query) {
        List<Curso> suggestions = new ArrayList<Curso>();
        for (Curso curso : Curso.findAllCursoes()) {
            String cursoStr = String.valueOf(curso.getNomcurso() +  " "  + curso.getIdcurso());
            if (cursoStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(curso);
            }
        }
        return suggestions;
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
        findAllDictadoes();
        return "dictado";
    }

	public String displayCreateDialog() {
        dictado = new Dictado();
        createDialogVisible = true;
        return "dictado";
    }

	public String persist() {
        String message = "";
        if (dictado.getIddictado() != null) {
            dictado.merge();
            message = "message_successfully_updated";
        } else {
            dictado.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Dictado");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDictadoes();
    }

	public String delete() {
        dictado.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Dictado");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDictadoes();
    }

	public void reset() {
        dictado = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String cargaExcel(){
		log.info("pasa por el cargar excel");
		return "dictadoexcel";
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
			
			List<Dictado> lstDictado = new ArrayList<Dictado>();
			log.info("Volcado de excel!");
			String nomCurso0 = "";
			Curso c = null;
			for (Iterator iterator = hojaVendedores.iterator(); iterator.hasNext();) {
				HSSFRow fila = (HSSFRow) iterator.next();
				if (esCabecera){ 
					esCabecera = false;
				}else {  
					Dictado dictado  = new Dictado();
					
					String nomCurso = fila.getCell(0).getRichStringCellValue().toString().trim();
					String nomDocente = fila.getCell(1).getRichStringCellValue().toString().trim();
					log.info("michi"+nomCurso+"|"+nomDocente);
					
					if(nomCurso.isEmpty() || nomCurso == null){
						log.info("entra ki poco");
					}else{
						nomCurso0 = nomCurso;
						c = new Curso();
						c.setNomcurso(nomCurso0);
						c = Curso.getCursoXNombre(c); 
					}
					Docente d = new Docente();
					d.setNomdocente(nomDocente);
					d = Docente.getDocenteXNombre(d);
					dictado.setIdcurso(c);
					dictado.setIddocente(d); 
					lstDictado.add(dictado);
				}
			}
			
			log.info("Se comienza a ingresar!!"+lstDictado.size());
			
			for (Dictado dictado2 : lstDictado) {
				log.info("norkis no pasa nada:"+dictado2.getIdcurso()+"|"+dictado2.getIddocente());
				setDictado(dictado2);
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
			 msg = new FacesMessage("Bien ", event.getFile().getFileName() + " dictado.");   
		}else{
			 msg = new FacesMessage("Mal ", event.getFile().getFileName() + " dictado.");
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

package com.horarios.jsf;
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

import com.horarios.Aula;
import com.horarios.jsf.util.MessageFactory;

@Configurable
@ManagedBean(name = "aulaBean")
@SessionScoped
public class AulaBean implements Serializable {
	private final Logger log = Logger.getLogger(getClass()) ;

	private static final long serialVersionUID = 1L;

	private String name = "Aulas";

	private Aula aula;

	private List<Aula> allAulas;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nomaula");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Aula> getAllAulas() {
        return allAulas;
    }

	public void setAllAulas(List<Aula> allAulas) {
        this.allAulas = allAulas;
    }

	public String findAllAulas() {
        allAulas = Aula.findAllAulas();
        dataVisible = !allAulas.isEmpty();
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
        
        OutputLabel nomaulaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomaulaCreateOutput.setFor("nomaulaCreateInput");
        nomaulaCreateOutput.setId("nomaulaCreateOutput");
        nomaulaCreateOutput.setValue("Nomaula:");
        htmlPanelGrid.getChildren().add(nomaulaCreateOutput);
        
        InputTextarea nomaulaCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomaulaCreateInput.setId("nomaulaCreateInput");
        nomaulaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{aulaBean.aula.nomaula}", String.class));
        LengthValidator nomaulaCreateInputValidator = new LengthValidator();
        nomaulaCreateInputValidator.setMaximum(200);
        nomaulaCreateInput.addValidator(nomaulaCreateInputValidator);
        nomaulaCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomaulaCreateInput);
        
        Message nomaulaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomaulaCreateInputMessage.setId("nomaulaCreateInputMessage");
        nomaulaCreateInputMessage.setFor("nomaulaCreateInput");
        nomaulaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomaulaCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel nomaulaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomaulaEditOutput.setFor("nomaulaEditInput");
        nomaulaEditOutput.setId("nomaulaEditOutput");
        nomaulaEditOutput.setValue("Nomaula:");
        htmlPanelGrid.getChildren().add(nomaulaEditOutput);
        
        InputTextarea nomaulaEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomaulaEditInput.setId("nomaulaEditInput");
        nomaulaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{aulaBean.aula.nomaula}", String.class));
        LengthValidator nomaulaEditInputValidator = new LengthValidator();
        nomaulaEditInputValidator.setMaximum(200);
        nomaulaEditInput.addValidator(nomaulaEditInputValidator);
        nomaulaEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomaulaEditInput);
        
        Message nomaulaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomaulaEditInputMessage.setId("nomaulaEditInputMessage");
        nomaulaEditInputMessage.setFor("nomaulaEditInput");
        nomaulaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomaulaEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nomaulaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomaulaLabel.setId("nomaulaLabel");
        nomaulaLabel.setValue("Nomaula:");
        htmlPanelGrid.getChildren().add(nomaulaLabel);
        
        InputTextarea nomaulaValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        nomaulaValue.setId("nomaulaValue");
        nomaulaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{aulaBean.aula.nomaula}", String.class));
        nomaulaValue.setReadonly(true);
        nomaulaValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(nomaulaValue);
        
        return htmlPanelGrid;
    }

	public Aula getAula() {
        if (aula == null) {
            aula = new Aula();
        }
        return aula;
    }

	public void setAula(Aula aula) {
        this.aula = aula;
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
        findAllAulas();
        return "aula";
    }

	public String displayCreateDialog() {
        aula = new Aula();
        createDialogVisible = true;
        return "aula";
    }

	public String persist() {
        String message = "";
        if (aula.getIdaula() != null) {
            aula.merge();
            message = "message_successfully_updated";
        } else {
            aula.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Aula");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllAulas();
    }

	public String delete() {
        aula.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Aula");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllAulas();
    }

	public void reset() {
        aula = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String cargaExcel(){
		log.info("pasa por el cargar excel");
		return "aulaexcel";
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
			Aula aula = null;
			List<Aula> lstAula = new ArrayList<Aula>();
			log.info("Volcado de excel!");
			for (Iterator iterator = hojaVendedores.iterator(); iterator.hasNext();) {
				HSSFRow fila = (HSSFRow) iterator.next();
				if (esCabecera){ 
					esCabecera = false;
				}else {  
					aula = new Aula();
					aula.setNomaula(fila.getCell(0).getRichStringCellValue().toString());
					lstAula.add(aula);
				}
			}
			
			log.info("Se comienza a ingresar!");
			
			for (Aula aula2 : lstAula) {
				setAula(aula2);
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
			 msg = new FacesMessage("Bien ", event.getFile().getFileName() + " cargo.");  
//			 msg2 = new FacesMessage("Bien ", event.getFile().getFileName() + " cargo.");  
		}else{
			 msg = new FacesMessage("Mal ", event.getFile().getFileName() + " cargo.");
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
	
	
	
//	public voif importaExcelVendedor(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		FormArchivo formArchivo = (FormArchivo) form;
//		FormFile archivo = formArchivo.getArchivo();
//
//		POIFSFileSystem excelByte = new POIFSFileSystem(archivo
//				.getInputStream());
//		HSSFWorkbook excel = new HSSFWorkbook(excelByte);
//		HSSFSheet hojaVendedores = excel.getSheetAt(0);
//
//		ArrayList<BeanVendedor> lista = new ArrayList<BeanVendedor>();
//		BeanVendedor bean = null;
//		boolean esCabecera = true;
//		for (Iterator iterator = hojaVendedores.iterator(); iterator.hasNext();) {
//			HSSFRow fila = (HSSFRow) iterator.next();
//			if (esCabecera)
//				esCabecera = false;
//			else {
//				bean = new BeanVendedor();
//				bean.setCodigo((int)fila.getCell(0).getNumericCellValue());
//				bean.setNombre(fila.getCell(1).getRichStringCellValue().toString());
//				bean.setApellido(fila.getCell(2).getRichStringCellValue().toString());
//				bean.setDistrito(fila.getCell(5).getRichStringCellValue().toString());
//				bean.setEstadoCivil(fila.getCell(6).getRichStringCellValue().toString());
//				lista.add(bean);
//			}
//		}
//
//		request.getSession().setAttribute("excelVendedor", lista);
//
//		return mapping.findForward("listado");
//
//	}
		
}

package com.horarios.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean
@RequestScoped
@Configurable
public class ApplicationBean {

	private MenuModel menuModel;
	
    public String getColumnName(String column) {
        if (column == null || column.length() == 0) {
            return column;
        }
        final Pattern p = Pattern.compile("[A-Z][^A-Z]*");
        final Matcher m = p.matcher(Character.toUpperCase(column.charAt(0)) + column.substring(1));
        final StringBuilder builder = new StringBuilder();
        while (m.find()) {
            builder.append(m.group()).append(" ");
        }
        return builder.toString().trim();
    }

	

	@PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        menuModel = new DefaultMenuModel();
        Submenu submenu;
        MenuItem item;
        
//        submenu = new Submenu();
//        submenu.setId("accesoSubmenu");
//        submenu.setLabel("Acceso");
//        item = new MenuItem();
//        item.setId("createAccesoMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{accesoBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listAccesoMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{accesoBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
        
//        submenu = new Submenu();
//        submenu.setId("areaSubmenu");
//        submenu.setLabel("Area");
//        item = new MenuItem();
//        item.setId("createAreaMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{areaBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listAreaMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{areaBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("aulaSubmenu");
        submenu.setLabel("Aula");
        
        item = new MenuItem();
        item.setId("createAulaMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{aulaBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("listAulaMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{aulaBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("excelAulaMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_excel}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{aulaBean.cargaExcel}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-copy");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        menuModel.addSubmenu(submenu);
        
//        submenu = new Submenu();
//        submenu.setId("cargoSubmenu");
//        submenu.setLabel("Cargo");
//        item = new MenuItem();
//        item.setId("createCargoMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cargoBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listCargoMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cargoBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("cursoSubmenu");
        submenu.setLabel("Curso");
        
        item = new MenuItem();
        item.setId("createCursoMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cursoBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("listCursoMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cursoBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("excelCursoMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_excel}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cursoBean.cargaExcel}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-copy");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        menuModel.addSubmenu(submenu);
        
        
        
        submenu = new Submenu();
        submenu.setId("dictadoSubmenu");
        submenu.setLabel("Dictado");
        
        item = new MenuItem();
        item.setId("createDictadoMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("listDictadoMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("excelDictadoMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_excel}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{dictadoBean.cargaExcel}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-copy");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("disponibilidadSubmenu");
        submenu.setLabel("Disponibilidad");
        item = new MenuItem();
        item.setId("createDisponibilidadMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{disponibilidadBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setId("listDisponibilidadMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{disponibilidadBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("docenteSubmenu");
        submenu.setLabel("Docente");
        
        item = new MenuItem();
        item.setId("createDocenteMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{docenteBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("listDocenteMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{docenteBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        item = new MenuItem();
        item.setId("excelDocenteMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_excel}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{docenteBean.cargaExcel}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-copy");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        
        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("horaSubmenu");
        submenu.setLabel("Hora");
        item = new MenuItem();
        item.setId("createHoraMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{horaBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setId("listHoraMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{horaBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        menuModel.addSubmenu(submenu);
        
//        submenu = new Submenu();
//        submenu.setId("menuSubmenu");
//        submenu.setLabel("Menu");
//        item = new MenuItem();
//        item.setId("createMenuMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{menuBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listMenuMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{menuBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
        
//        submenu = new Submenu();
//        submenu.setId("usuarioSubmenu");
//        submenu.setLabel("Usuario");
//        item = new MenuItem();
//        item.setId("createUsuarioMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{usuarioBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listUsuarioMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{usuarioBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
    }

	public MenuModel getMenuModel() {
        return menuModel;
    }

	public String getAppName() {
        return "Horarios";
    }
}
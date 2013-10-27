package com.horarios.jsf;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mainBean")
public class MainBean {

	private String paramName; // +getter+setter

	public void check() {
	    if (paramName == null) {
	        try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("logueo.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	
	
}

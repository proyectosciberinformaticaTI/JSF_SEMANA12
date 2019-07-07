package com.mitocode.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensajeManager {
	public synchronized static void mostrarMensaje(String titulo, String cuerpo, String severidad) {
		switch (severidad) {
		case "INFO":
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, cuerpo));
			break;
		case "WARN":
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, cuerpo));
			break;
		case "ERROR":
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, cuerpo));
			break;
		case "FATAL":
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, cuerpo));
			break;
		}
	}
}

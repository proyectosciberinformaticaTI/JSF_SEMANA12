package com.mitocode.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.restfb.types.User;

@Named
@ViewScoped
public class SesionBean implements Serializable {

	private User usuario;
	private String urlFoto;
	private List<String> idiomas;
		
	@PostConstruct
	public void init(){
		idiomas = new ArrayList<>();
		urlFoto = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("urlFoto");
		usuario = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		usuario.getLanguages().forEach(x -> idiomas.add(x.getName()));
	}
	
	public void autenticar() {
		usuario = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");				
		
		try {
			if (usuario == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?faces-redirect=true");
			}
		} catch (Exception e) {

		}

	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<String> idiomas) {
		this.idiomas = idiomas;
	}
	
}


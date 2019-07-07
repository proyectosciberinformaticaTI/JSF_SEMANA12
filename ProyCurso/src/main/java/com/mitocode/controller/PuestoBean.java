package com.mitocode.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.mitocode.model.Puesto;
import com.mitocode.service.IPuestoService;

@Named
@ViewScoped
public class PuestoBean extends SesionBean implements Serializable {

	@Inject
	private IPuestoService service;
	private List<Puesto> lstPuestos;	

	@PostConstruct
	public void init() {
		this.autenticar();
		lstPuestos = new ArrayList<>();		
		this.listar();
	}

	public void listar() {
		try {
			lstPuestos = service.listar();
		} catch (Exception e) {

		}

	}

	public void seleccionar(Puesto pue) throws Exception {
		//Enviar por omnifaces
		Faces.setFlashAttribute("idPuesto", pue.getIdPuesto());
	}

	public List<Puesto> getLstPuestos() {
		return lstPuestos;
	}

	public void setLstPuestos(List<Puesto> lstPuestos) {
		this.lstPuestos = lstPuestos;
	}


}

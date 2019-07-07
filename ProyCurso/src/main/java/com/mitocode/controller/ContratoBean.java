package com.mitocode.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.mitocode.model.Contrato;
import com.mitocode.model.Persona;
import com.mitocode.model.Puesto;
import com.mitocode.service.IContratoService;
import com.mitocode.service.IPersonaService;
import com.mitocode.service.IPuestoService;

@Named
@ViewScoped
public class ContratoBean extends SesionBean implements Serializable {

	@Inject
	private IContratoService contratoService;
	private List<Contrato> lstContratos;

	@PostConstruct
	public void init() {	
		this.autenticar();
		this.lstContratos = new ArrayList<>();
		this.listarContratos();
	}

	

	public void listarContratos() {
		try {
			this.lstContratos = contratoService.listar();
		} catch (Exception e) {

		}
	}

	public void seleccionar(Contrato cont) throws Exception {
		Faces.setFlashAttribute("contrato", cont);		
	}

	public List<Contrato> getLstContratos() {
		return lstContratos;
	}

	public void setLstContratos(List<Contrato> lstContratos) {
		this.lstContratos = lstContratos;
	}

}

package com.mitocode.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.mitocode.model.Config;
import com.mitocode.model.Contrato;
import com.mitocode.model.Persona;
import com.mitocode.model.Puesto;
import com.mitocode.service.IConfigService;
import com.mitocode.service.IContratoService;
import com.mitocode.service.IPersonaService;
import com.mitocode.service.IPuestoService;
import com.mitocode.util.MensajeManager;

@Named
@ViewScoped
public class ContratoFormBean implements Serializable{

	@Inject
	private Contrato contrato;
	@Inject
	private Persona persona;
	@Inject
	private Puesto puesto;
	@Inject
	private IPuestoService puestoService;
	@Inject
	private IPersonaService personaService;
	@Inject
	private IContratoService contratoService;
	@Inject
	private IConfigService configService;
	private List<Persona> lstPersonas;
	private List<Puesto> lstPuestos;

	@PostConstruct
	public void init() {
		this.listarPersonas();
		this.listarPuestos();		
		Contrato cont = (Contrato)Faces.getFlashAttribute("contrato");
		if(cont != null){			
			this.leer(cont);
		}else{
			this.leerSueldoMinimo();	
		}			
	}
	
	public void leer(Contrato cont){
		try {
			contrato = contratoService.listarPorId(cont);
			this.persona = contrato.getPersona();
			this.puesto = contrato.getPuesto();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		}		
	}	
	
	public void leerSueldoMinimo(){
		try{
			InputStream inputStream = ContratoFormBean.class.getClassLoader().getResourceAsStream("/parametros.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			
			String parametro = properties.getProperty("sueldo_minimo");
			Config conf = configService.leerParametro(parametro);
			double salarioMinimo = conf.getValor() != null ? Double.parseDouble(conf.getValor()) : 0.0;  
			this.contrato.setSalario(salarioMinimo);
		}catch(Exception e){
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		}
	}
	
	public void listarPersonas() {
		try {
			lstPersonas = personaService.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		}
	}

	public void listarPuestos() {
		try {
			lstPuestos = puestoService.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		}
	}

	public void registrar() {
		try {
			contrato.setIdContrato(contratoService.getSiguientePK(persona));
			contrato.setPersona(persona);
			contrato.setPuesto(puesto);
			contratoService.registrar(contrato);
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		}
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public List<Persona> getLstPersonas() {
		return lstPersonas;
	}

	public void setLstPersonas(List<Persona> lstPersonas) {
		this.lstPersonas = lstPersonas;
	}

	public List<Puesto> getLstPuestos() {
		return lstPuestos;
	}

	public void setLstPuestos(List<Puesto> lstPuestos) {
		this.lstPuestos = lstPuestos;
	}	
}

package com.mitocode.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.mitocode.model.Funcion;
import com.mitocode.model.Puesto;
import com.mitocode.service.IFuncionService;
import com.mitocode.service.IPuestoService;

@Named
@ViewScoped
public class PuestoFormBean implements Serializable {

	@Inject
	private IPuestoService service;
	@Inject
	private IFuncionService funcionService;
	@Inject
	private Puesto puesto;
	@Inject
	private Funcion funcion;
	private List<Funcion> lstFunciones;

	@PostConstruct
	public void init() {
		this.lstFunciones = new ArrayList<>();
		Integer idPuesto = Faces.getFlashAttribute("idPuesto");
		if(idPuesto != null && idPuesto > 0){
			puesto.setIdPuesto(idPuesto);
			this.leer(puesto);
		}		
	}
	
	public void leer(Puesto pue){
		try {
			puesto = service.listarPorId(pue);
			this.lstFunciones = funcionService.listar(puesto);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void operar() throws Exception {
		puesto.setFunciones(lstFunciones);

		if (puesto.getIdPuesto() > 0) {
			service.modificar(puesto);
		} else {
			service.registrar(puesto);
		}
	}

	public void agregar() {
		Funcion func = new Funcion();
		func.setNombre(funcion.getNombre());
		func.setDescripcion(funcion.getDescripcion());
		func.setPuesto(puesto);
		this.getLstFunciones().add(func);
	}

	public void remover(Funcion func) {
		this.lstFunciones.remove(func);
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public List<Funcion> getLstFunciones() {
		return lstFunciones;
	}

	public void setLstFunciones(List<Funcion> lstFunciones) {
		this.lstFunciones = lstFunciones;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

}

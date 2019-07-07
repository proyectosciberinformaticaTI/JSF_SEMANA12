package com.mitocode.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.mitocode.model.Config;
import com.mitocode.service.IConfigService;
import com.mitocode.util.MensajeManager;

@Named
@ViewScoped
public class ConfigBean extends SesionBean implements Serializable {

	@Inject
	private IConfigService service;
	@Inject
	private Config config;
	private String accion;
	private List<Config> lstConfig;

	@PostConstruct
	public void init() {
		this.autenticar();
		accion = "Registrar";
		lstConfig = new ArrayList<>();
		this.listar();
	}

	public void listar() {
		try {
			lstConfig = service.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		}

	}

	public void seleccionar(Config con) throws Exception {
		config = service.listarPorId(con);
		accion = "Modificar";
	}

	public void operar() throws Exception {
		try {
			if (config.getId() > 0) {
				service.modificar(config);
				MensajeManager.mostrarMensaje("Aviso", "Modificación exitosa", "INFO");
			} else {
				service.registrar(config);
				MensajeManager.mostrarMensaje("Aviso", "Registro exitoso", "INFO");
			}
			this.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), "ERROR");
		} finally {
			this.limpiarControles();
		}
	}

	public void limpiarControles() {
		this.config.setId((short) 0);
		this.config.setNombre(null);
		this.config.setValor(null);
		this.accion = "Registrar";
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public List<Config> getLstConfig() {
		return lstConfig;
	}

	public void setLstConfig(List<Config> lstConfig) {
		this.lstConfig = lstConfig;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}

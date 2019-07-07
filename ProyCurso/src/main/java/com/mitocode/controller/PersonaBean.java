package com.mitocode.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import com.mitocode.model.Persona;
import com.mitocode.model.Telefono;
import com.mitocode.service.IPersonaService;
import com.mitocode.util.MensajeManager;

@Named
@ViewScoped
public class PersonaBean extends SesionBean implements Serializable {

	@Inject
	private IPersonaService personaService;
	private List<Persona> lstPersonas;
	@Inject
	private Persona persona;
	@Inject
	private Telefono telefono;
	private List<Telefono> lstTelefono;
	private Date fechaSeleccionada;
	private UploadedFile foto;
	private String titulo;
	private String nombreUsuario;

	@PostConstruct
	public void init() {
		this.autenticar();
		this.lstTelefono = new ArrayList<>();
		this.listar();
		this.titulo = "Registrar";
		
		if(this.getUsuario() != null){
			this.nombreUsuario = this.getUsuario().getName();
		}
	}

	public void seleccionar(Persona per) throws Exception {
		persona = personaService.listarPorId(per);
		Calendar cal = Calendar.getInstance();
		cal.set(persona.getFechaNac().getYear(), persona.getFechaNac().getMonthValue() - 1,
				persona.getFechaNac().getDayOfMonth());
		this.fechaSeleccionada = cal.getTime();
		this.lstTelefono = persona.getTelefonos();
		this.titulo = "Modificar";
	}

	public void operar() {
		try {
			if (foto != null) {
				persona.setFoto(foto.getContents());
			}

			if (fechaSeleccionada != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaSeleccionada);
				int anio = cal.get(Calendar.YEAR);
				int mes = cal.get(Calendar.MONTH) + 1;
				int dia = cal.get(Calendar.DATE);
				LocalDate local = LocalDate.of(anio, mes, dia);
				persona.setFechaNac(local);
			}

			persona.setTelefonos(lstTelefono);

			if (persona.getIdPersona() > 0) {
				personaService.modificar(persona);
				MensajeManager.mostrarMensaje("Aviso", "Modificación Exitosa", "INFO");
			} else {
				personaService.registrar(persona);
				MensajeManager.mostrarMensaje("Aviso", "Registro Exitoso", "INFO");
			}
			this.listar();
			// RequestContext.getCurrentInstance().execute("PF('wdlgPersona').hide()");
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Error", e.getMessage(), "ERROR");
		}
	}

	public void listar() {
		try {
			lstPersonas = personaService.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Error", e.getMessage(), "ERROR");
		}

	}

	public void agregar() {
		Telefono tel = new Telefono();
		tel.setNumero(telefono.getNumero());
		tel.setPersona(persona);
		this.lstTelefono.add(tel);
	}

	public void remover(Telefono tel) {
		this.lstTelefono.remove(tel);
	}

	public void limpiarControles() {
		this.titulo = "Registrar";
		this.persona.setIdPersona(0);
		this.persona.setNombres(null);
		this.persona.setApellidos(null);
		this.persona.setDireccion(null);
		this.persona.setSexo(null);
		this.fechaSeleccionada = null;
		this.lstTelefono = new ArrayList<>();
	}

	/**
	 * getters & setters
	 * 
	 * @return
	 */

	public List<Persona> getLstPersonas() {
		return lstPersonas;
	}

	public void setLstPersonas(List<Persona> lstPersonas) {
		this.lstPersonas = lstPersonas;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Date getFechaSeleccionada() {
		return fechaSeleccionada;
	}

	public void setFechaSeleccionada(Date fechaSeleccionada) {
		this.fechaSeleccionada = fechaSeleccionada;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Telefono getTelefono() {
		return telefono;
	}

	public void setTelefono(Telefono telefono) {
		this.telefono = telefono;
	}

	public List<Telefono> getLstTelefono() {
		return lstTelefono;
	}

	public void setLstTelefono(List<Telefono> lstTelefono) {
		this.lstTelefono = lstTelefono;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

}

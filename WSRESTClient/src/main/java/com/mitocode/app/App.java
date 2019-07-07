package com.mitocode.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mitocode.model.Persona;

public class App {

	public static void main(String[] args) throws Exception {
		//URL url = new URL("http://localhost:8080/WSREST/rest/persona/leer/1");
		//URL url = new URL("http://localhost:8080/WSREST/rest/persona/listar");
		URL url = new URL("http://localhost:8080/WSREST/rest/persona/modificar/1");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setDoOutput(true);
		//conn.setRequestMethod("GET");
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
		
		String entrada = "{\"nombres\":\"Mitosss\",\"apellidos\":\"Medina\",\"fechaNac\":\"1991-01-21\",\"sexo\":\"M\",\"direccion\":\"xyz\"}";
		
		OutputStream os = conn.getOutputStream();
		os.write(entrada.getBytes());
		os.flush();
		
		if(conn.getResponseCode() != 200){
			throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String salida;
		StringBuilder sb = new StringBuilder();
		while((salida = br.readLine()) != null){
			sb.append(salida);
		}
		
		System.out.println(sb.toString());
		
		Gson gson = new Gson();
		/*Persona per = gson.fromJson(sb.toString(), Persona.class);
		System.out.println(per.getIdPersona());
		System.out.println(per.getApellidos());
		System.out.println(per.getNombres());
		System.out.println(per.getFechaNac());
		System.out.println(per.getSexo());*/
		
		//List<Persona> lista = gson.fromJson(sb.toString(), new TypeToken<List<Persona>>(){}.getType());
		//lista.forEach(s -> System.out.println(s.getIdPersona() + "-" + s.getNombres()));
		
		conn.disconnect();
	}
}

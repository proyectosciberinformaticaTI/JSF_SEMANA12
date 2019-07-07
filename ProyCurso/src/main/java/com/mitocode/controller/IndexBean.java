package com.mitocode.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.mitocode.model.Usuario;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

@Named
@ViewScoped
public class IndexBean implements Serializable{
	
	private String token;
	
	public String iniciarFb(){
		String dominio = "http://localhost:8080/ProyCurso/persona.xhtml"; //LA URL QUE INDICASTE EN LA APP DE FB
		String idApp = "la_id_de_tu_app_fb";
		
		String ruta = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + idApp + "&redirect_uri="
				+ dominio + "&scope=user_about_me,"
				+ "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
				+ "user_events,user_photos,user_friends,user_games_activity,user_hometown,user_likes,user_location,user_photos,user_relationship_details,"
				+ "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
				+ "manage_pages,publish_actions,read_insights,read_page_mailboxes,rsvp_event";
		
		System.setProperty("webdriver.chrome.driver",
				"D:\\Proyectos\\MitoCode Network\\Java Enterprise Developer\\chromedriver.exe"); //la ruta de tu driver de chrome
		WebDriver driver = new ChromeDriver();
		driver.get(ruta);
		
		while(true){
			if(!driver.getCurrentUrl().contains("facebook.com")){
				String url = driver.getCurrentUrl();
				token = url.replaceAll(".*#access_token=(.+)&.*", "$1");
				
				driver.quit();
				
				FacebookClient cliente = new DefaultFacebookClient(token, Version.LATEST);
				User usuarioFB = cliente.fetchObject("me", User.class, Parameter.with("fields", "name, birthday, languages"));
				
				JsonObject js = 
						   cliente.fetchObject("/me/picture", JsonObject.class, 
						       Parameter.with("type","large"), 
						       Parameter.with("redirect","false"));
						
				Gson gson = new Gson();
				Usuario usuarioModel = gson.fromJson(js.get("data").toString(), Usuario.class);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioFB);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("urlFoto", usuarioModel.getUrl().replaceAll("&", "&amp;"));					
				
				return "persona.xhtml?faces-redirect=true";
			}
		}
	}	
}

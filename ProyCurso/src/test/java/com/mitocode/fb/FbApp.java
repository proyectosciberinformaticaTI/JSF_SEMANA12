package com.mitocode.fb;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.GraphResponse;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FbApp {

	public static void main(String[] args) throws InterruptedException {
		
		String token = "tu_token_de_tu_cuenta_fb";
		FacebookClient cliente = new DefaultFacebookClient(token, Version.LATEST);
		
		User usuario = cliente.fetchObject("me", User.class, Parameter.with("fields", "name, birthday"));
		Page pagina = cliente.fetchObject("mitocode", Page.class, Parameter.with("fields", "fan_count"));
		
		System.out.println(usuario.getName());
		System.out.println(usuario.getBirthday());
		
		System.out.println("MitoCode tiene " + pagina.getFanCount());
		
		Connection<Post> muro = cliente.fetchConnection("me/feed", Post.class);
		int cont = 0;
		for (List<Post> elemento : muro) {
			for (Post post : elemento) {
				if (cont == 4) {
					break;
				}
				System.out.println("Post: " + post.getMessage());
				cont++;
			}
		}
		
		GraphResponse response = cliente.publish("me/feed", GraphResponse.class, Parameter.with("message", "Facebook API - MitoCode Premium Test"));
		System.out.println(response.getId());
		Thread.sleep(30000);
		cliente.deleteObject(response.getId());
	}
}

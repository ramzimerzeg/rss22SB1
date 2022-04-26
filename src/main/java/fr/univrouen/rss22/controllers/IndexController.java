package fr.univrouen.rss22.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String getAccueil(Model model) {
		/*StringBuilder pageweb = new StringBuilder();
				pageweb.append(
				 "<html>"
				+"<head>"
				+"  <title>Projet : Service REST  &  Client</title>"
				+"</head>");
				pageweb.append(
				 "<body>"
				+"  <h1>Projet : Service REST  &  Client</h1>"
				+"  <h3>Version 1.1</h3>"
				+"  <h4>Merzeg Ramzi</h4>"
				);*/

		return "accueil";
	}

	@GetMapping("/help")
	public String getHelp(Model model) {
		return "help";
	}

}
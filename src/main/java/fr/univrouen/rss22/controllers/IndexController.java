package fr.univrouen.rss22.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@GetMapping("/")
	public StringBuilder index() {
		
		StringBuilder pageweb = new StringBuilder();
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
				);
		return pageweb;
	}
}
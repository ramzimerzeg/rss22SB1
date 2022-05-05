package fr.univrouen.rss22.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class IndexController {

	@GetMapping("/")

	public String getAccueil(Model model) {
		return "accueil";
	}

	@GetMapping("/help")
	public String getHelp(Model model) {
		return "help";
	}

}
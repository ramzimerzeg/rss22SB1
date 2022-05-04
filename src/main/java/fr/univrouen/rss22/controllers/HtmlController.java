package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class HtmlController {

	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("/rss22/resume/html")
	public String getAllItemsAsHtml(Model model) {
		List<Item> items = itemRepository.findAll();

		model.addAttribute("items", items);
		model.addAttribute("item", new Item());

		return "all-items";
	}

	@GetMapping("/rss22/html/{guid}")
	public String getItemAsHtml(@PathVariable("guid") String guid , Model model) {
		Optional<Item> optionalItem = itemRepository.findById(guid);

		if (optionalItem.isPresent()) {
			Item item = optionalItem.get();
			model.addAttribute("item", item);
			return "item";
		}

		else {
			model.addAttribute("item", null);
			model.addAttribute("guid", guid);
			return "item";
		}
	}

}
package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HtmlController {

	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("/rss22/resume/html")
	public String getAllItemsAsHtml(Model model) {
		List<Item> items = itemRepository.findAll();

		model.addAttribute("items", items);

		return "all-items";
	}

	@PostMapping(
			value = "/insert"
	)
	public String insertItem(@ModelAttribute Item newItem, Model model) {
		model.addAttribute("item", newItem);
		System.out.println("***********************-----------------------\n" + newItem.getGuid());
		return "insert";
	}
	
}
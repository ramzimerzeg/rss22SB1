package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.models.Item;
import fr.univrouen.rss22.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping(value = "/insert", produces = MediaType.APPLICATION_XML_VALUE)
	public String insertItem(@RequestBody Item newItem, Model model) {
		model.addAttribute("item", newItem);
		itemRepository.save(newItem);
		System.out.println("***********************-----------------------\n" + newItem.getGuid());
		return "insert";
	}

	/*@ResponseBody
	public String insertItem(@ModelAttribute Item newItem, Model model) {
		model.addAttribute("item", newItem);
		System.out.println("***********************-----------------------\n" + newItem.getGuid());
		return "insert";
	}*/


	
}
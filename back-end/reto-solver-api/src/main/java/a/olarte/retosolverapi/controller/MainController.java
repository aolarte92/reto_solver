package a.olarte.retosolverapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String swagger (){
		return "redirect:/swagger-ui.html";
	}
	
}

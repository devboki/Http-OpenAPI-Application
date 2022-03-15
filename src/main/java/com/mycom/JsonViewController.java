package com.mycom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JsonViewController {
	
	@GetMapping("/check")
	public String bNoCheckForm() {
		return "checkForm";
	}
}

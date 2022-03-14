package com.mycom;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class JsonController {

	private final BuisnessService buisnessService;
	
	@GetMapping("/param")
	public String getParam(@RequestParam("bNo") String bNo) {
		System.out.println("b_no : " + bNo);
		return "test";
	} 
//	http://localhost:8099/param/?bNo=1234567890
//	b_no : 1234567890
	
	@GetMapping("/model")
	public String getModel(@ModelAttribute ResultDTO dto) {
		System.out.println("b_no : " + dto.getBNo());
		System.out.println("tax_type : " + dto.getTaxType());
		return "test";
	}
//	http://localhost:8099/model/?bNo=1234567890&statusCode=OK&taxType=부가가치세 일반과세자
//	b_no : 1234567890
//	tax_type : 부가가치세 일반과세자
	
	@PostMapping("/service")
	public ResultDTO getService(@RequestBody Bno bNo) throws IOException {
		ResultDTO dto = buisnessService.check(bNo);
		return dto;
	}
//	POST :: http://localhost:8099/service :: Body { "b_no":"5298600830" }
//	{ "tax_type": "국세청에 등록되지 않은 사업자등록번호입니다.",
//	  "b_no": "5298600830" }

}

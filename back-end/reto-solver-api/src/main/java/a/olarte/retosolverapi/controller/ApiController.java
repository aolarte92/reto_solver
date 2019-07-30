package a.olarte.retosolverapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import a.olarte.retosolverapi.service.CompanyService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CompanyService companyService;
	
	@GetMapping("/traces")
	@ApiOperation(value = "getAllTraces" , notes="Retorna listado de las ejecuciones realizadas")	
	public ResponseEntity<?> getAllTraces (){
		return new ResponseEntity<>(companyService.getAllTraces(), HttpStatus.OK);
	}
	
	@PostMapping("/test2")
	public ResponseEntity<?> test (
			@RequestParam(value="doc") String doc,
			@RequestParam(value="file") MultipartFile input
			) throws IOException{
		
		try {
			return new ResponseEntity<>(companyService.createTrace(doc, input), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(500));
		}
	}
	
	@GetMapping("/traces2")
	@ApiOperation(value = "getAllTraces" , notes="Retorna listado de las ejecuciones realizadas")	
	public ResponseEntity<?> getAllTraces2 (){
		int[][]  alumnosfxniveleidioma = {{7,14,8,3},{6,19,7},{3,13,4,1,2}};
		return new ResponseEntity<>(alumnosfxniveleidioma, HttpStatus.OK);
	}
}

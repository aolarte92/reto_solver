package a.olarte.retosolverapi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

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
	
	@PostMapping("/reto")
	public void test (
			@RequestParam(value="doc") String doc,
			@RequestParam(value="file") MultipartFile input,
			HttpServletResponse response
			) throws IOException{
		response.setContentType(ContentType.TEXT_PLAIN.getMimeType());
		response.setHeader("Content-Disposition", "attachment; filename=prueba_output.txt");
		try {
			ServletOutputStream out = response.getOutputStream();
			out.print(companyService.createTrace(doc, input));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}

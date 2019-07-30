package a.olarte.retosolverapi.controller;

import java.io.IOException;
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
	public void reto (
			@RequestParam(value="doc") String doc,
			@RequestParam(value="file") MultipartFile input,
			HttpServletResponse response
			) throws IOException {
		
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=prueba_output.txt");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(companyService.createTrace(doc, input));
			response.flushBuffer();
//			return new ResponseEntity<>(companyService.createTrace(doc, input), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
			response.flushBuffer();
//			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}

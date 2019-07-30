package a.olarte.retosolverapi.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.fabric.xmlrpc.base.Array;

import a.olarte.retosolverapi.enumerated.DirectoryType;
import a.olarte.retosolverapi.helper.FileHelper;
import a.olarte.retosolverapi.model.TraceModel;
import a.olarte.retosolverapi.model.builder.TraceBuilder;
import a.olarte.retosolverapi.repository.TraceRepository;

@Service
public class CompanyService {

	@Autowired
	TraceRepository traceRepository;
	@Autowired
	AmazonService amazonService;

	private TraceModel saveTrace(TraceModel trace) {
		return traceRepository.save(trace);
	}

	public List<TraceModel> getAllTraces() {
		return traceRepository.findAll();
	}

	@Transactional
	public TraceModel createTrace(String doc, MultipartFile fileInput) throws Exception {

		String fileUrl = null;
		if (!fileInput.isEmpty())
			fileUrl = amazonService.uploadFile(fileInput, DirectoryType.INPUT_DIRECTORY.getUrl());

		// TODO: mensaje validacion inputfile
		String msg = "ok";
		List<Integer> elements = this.readInputFile(FileHelper.multipartToFile(fileInput, "prueba_input.txt"));
		validateElements(elements);
		TraceModel tm = new TraceBuilder()
				.setDocumentNumber(doc)
				.setFileInputUrl(fileUrl)
				.setMsg(msg)
				.build();

		return this.saveTrace(tm);
	}

	private List<Integer> readInputFile(File filetext) throws IOException {
		List<Integer> elements = new ArrayList<>();	
		Stream<String> multilineas = Files.lines(filetext.toPath());
		for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();) {
			elements.add(Integer.parseInt(iterator.next()));
		}
		multilineas.close();
		System.out.println(elements.toString());

		return elements;
	}
	
	private String validateElements(List<Integer> elements) throws Exception {
		int T = elements.get(0), day = 1;
		StringBuffer days = new StringBuffer();
		if(T < 1 || T > 500)
			throw new Exception("Restricción: 1 ≤ T ≤ 500");
		for (int i = 1; i < elements.size(); i += (elements.get(i) + 1)) {
			if(elements.get(i) < 1 || elements.get(i) > 100) {
				throw new Exception(String.format("Restricción: 1 ≤ N ≤ 100 en la linea %s",i));
			}else {
				days.append(String.format("Case #%s: %s\n", day, validateElementsDay(elements.get(i), elements.subList(i+1, i + 1 + elements.get(i)), day)));
			}
			day++;
				
		} 
		return days.toString();
	}
	
	private int validateElementsDay(int N, List<Integer> Wi, int day) throws Exception {
		
		if(Wi.size() != N)
			throw new Exception("Restricción: No se representan los N elementos");
		Collections.reverse(Wi);
		int max = Wi.stream().mapToInt(Integer::intValue).max().getAsInt();
		int min = Wi.stream().mapToInt(Integer::intValue).min().getAsInt();
		if( min < 1 || max > 100)
			throw new Exception(String.format("Restricción: 1 ≤ Wi ≤ 100 en el dia  %s",day));
		int sum = Wi.stream().mapToInt(Integer::intValue).sum();
		if(sum < 50)
			throw new Exception(String.format("Restricción: el peso de los elementos del dia %s NO alcanza las 50 libras",day));
		
		System.out.println(String.format("Case #%s = [%s] %s = sum:%s, max:%s, min:%s",day,N,Wi.toString(),sum,max,min));
		return 1;
	}

	private void writeOutputFile(String days) throws IOException {
		File filetext = File.createTempFile("prueba_output", ".txt");
		FileWriter writer = new FileWriter(filetext);
		writer.append(days);
		writer.close();
	}
}

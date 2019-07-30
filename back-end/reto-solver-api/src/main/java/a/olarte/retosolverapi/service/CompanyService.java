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

	private static final double PESO_MIN = 50.0;
	
	private TraceModel saveTrace(TraceModel trace) {
		return traceRepository.save(trace);
	}

	public List<TraceModel> getAllTraces() {
		return traceRepository.findAll();
	}

	@Transactional
	public String createTrace(String doc, MultipartFile fileInput) throws Exception {
		String fileUrl = null;
		if (!fileInput.isEmpty())
			fileUrl = amazonService.uploadFile(fileInput, DirectoryType.INPUT_DIRECTORY.getUrl());
		List<Integer> elements = this.readInputFile(FileHelper.multipartToFile(fileInput, "prueba_input.txt"));
		String outputString = validateElements(elements);
		TraceModel tm = new TraceBuilder()
				.setDocumentNumber(doc)
				.setFileInputUrl(fileUrl)
				.setMsg(outputString)
				.build();
		tm = this.saveTrace(tm);
		return outputString;
	}
	private List<Integer> readInputFile(File filetext) throws IOException {
		List<Integer> elements = new ArrayList<>();	
		Stream<String> multilineas = Files.lines(filetext.toPath());
		for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();)
			elements.add(Integer.parseInt(iterator.next()));
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
			if(elements.get(i) < 1 || elements.get(i) > 100) 
				throw new Exception(String.format("Restricción: 1 ≤ N ≤ 100 en la linea %s",i));
			else 
				days.append(String.format("Case #%s: %s\n", day, validateElementsDay(elements.get(i), elements.subList(i+1, i + 1 + elements.get(i)), day)));
			day++;
		} 
		return days.toString();
	}
	
	private int validateElementsDay(int N, List<Integer> Wi, int day) throws Exception {
		if(Wi.size() != N)
			throw new Exception("Restricción: No se representan los N elementos");
		Collections.sort(Wi,Collections.reverseOrder());
		int max = Wi.stream().mapToInt(Integer::intValue).max().getAsInt();
		int min = Wi.stream().mapToInt(Integer::intValue).min().getAsInt();
		if( min < 1 || max > 100)
			throw new Exception(String.format("Restricción: 1 ≤ Wi ≤ 100 en el dia  %s",day));
		int sum = Wi.stream().mapToInt(Integer::intValue).sum();
		if(sum < 50)
			throw new Exception(String.format("Restricción: el peso de los elementos del dia %s NO alcanza las 50 libras",day));
		
		System.out.println(String.format("Case #%s = [%s] %s = sum:%s, max:%s, min:%s",day,N,Wi.toString(),sum,max,min));
		
		return getMaximumtrips(new ArrayList<Integer>(Wi));
	}
	/**
	 * recibe arreglo ordenado de mayor a menor y calcula las posibles viajes dividiendo el peso minimo (50)
	 * @param Wi
	 * @return
	 */
	private int getMaximumtrips(List<Integer> Wi) {
		int count = 0, div = 1;
		double peso = 50;
		while( count < Wi.size()) {
			peso = (double)(PESO_MIN/(double)div);
			if(Wi.get(count) >= peso) {
				if((Wi.size() - count) > div-1) {
					count ++;
					for(int j = 1; j < div; j++) Wi.remove(Wi.size()-1);
				}else
					break;
			}else 
				div ++;
		}
		return count;
	}

	
}

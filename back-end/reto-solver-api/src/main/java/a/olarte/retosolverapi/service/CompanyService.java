package a.olarte.retosolverapi.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
	public TraceModel createTrace(String doc, MultipartFile fileInput) throws IllegalStateException, IOException {

		String fileUrl = null;
		if (!fileInput.isEmpty())
			fileUrl = amazonService.uploadFile(fileInput, DirectoryType.INPUT_DIRECTORY.getUrl());

		// TODO: mensaje validacion inputfile
		String msg = this.validateInputFile(FileHelper.multipartToFile(fileInput, "prueba_input.txt"));

		TraceModel tm = new TraceBuilder()
				.setDocumentNumber(doc)
				.setFileInputUrl(fileUrl)
				.setMsg(msg)
				.build();

		return this.saveTrace(tm);
	}

	private String validateInputFile(File filetext) throws IOException {
		List<Integer> elementos = new ArrayList<>();	
		int T = 0, x = 0;
		Stream<String> multilineas = Files.lines(filetext.toPath());
		for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();) {
			x = Integer.parseInt(iterator.next());
			elementos.add(x);
		}
		multilineas.close();
		System.out.println(elementos.toString());

		return "ok";
	}

	private void writeOutputFile() throws IOException {
		File filetext = File.createTempFile("prueba_output", ".txt");

		List<String> lineas = Arrays.asList("Welcome", "To java 8", "Bartolomé");
		FileWriter writer = new FileWriter(filetext);
		for (String linea : lineas) {
			writer.append(linea + "\n");
		}
		writer.close();
	}
}

package com.sosna.ollama.ocr.web;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sosna.ollama.ocr.rto.ArtificialIntelligenceModel;
import com.sosna.ollama.ocr.storadge.StorageFileNotFoundException;
import com.sosna.ollama.ocr.storadge.StorageService;

@Controller
public class FileUploadController {
	private final StorageService storageService;
	private ArtificialIntelligenceModel artificialIntelligenceModel;

	public FileUploadController(StorageService storageService,
			ArtificialIntelligenceModel artificialIntelligenceModel) {
		this.storageService = storageService;
		this.artificialIntelligenceModel = artificialIntelligenceModel;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		var answ = "kk";
		model.addAttribute("answer", answ);

		/*
		 * model.addAttribute("files", storageService.loadAll() .map(path ->
		 * MvcUriComponentsBuilder .fromMethodName(FileUploadController.class,
		 * "serveFile", path.getFileName().toString()) .build().toUri().toString())
		 * .collect(Collectors.toList()));
		 */

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model )
			throws IOException {

		storageService.store(file);
		var answ = artificialIntelligenceModel.ask("ocr", file);
		model.addAttribute("answer", answ);

		return "view";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}

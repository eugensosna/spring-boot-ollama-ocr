package com.sosna.ollama.ocr.storadge;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {


	void init();

	Path  store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	public Resource loadAsResource(String filename);

	void deleteAll();
	
	public String getExtension(String fileName);

}

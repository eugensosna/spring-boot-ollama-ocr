package com.sosna.ollama.ocr.rto;

import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import  org.springframework.core.io.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.client.DefaultChatClient.DefaultChatClientRequestSpec;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.io.ByteArrayResource;


@Component
public class ArtificialIntelligenceModel {
	private static final Logger logger = LoggerFactory.getLogger(ArtificialIntelligenceModel.class);


	private final ChatClient chatClient;

	public ArtificialIntelligenceModel(ChatClient.Builder chatClient) {
		this.chatClient = chatClient.build();
	}

	public static Resource convertMultipartFileToResource(MultipartFile file) throws IOException {
		return new ByteArrayResource(file.getBytes()) {
			@Override
			public String getFilename() {
				return file.getOriginalFilename();
			}
		};
	}

	public String ask(String prompt, MultipartFile file) throws IOException {
		
		String userAsking = "Please look at this image and extract all the text content.";
		var userMessage = new UserMessage(userAsking);
		if (file!=null) {
			
			
			var data = new ByteArrayResource(file.getBytes());
			List<Media> medias = new ArrayList<Media>();
			var media = Media.builder().data(data).mimeType(MimeTypeUtils.IMAGE_PNG).build(); 
			medias.add(media);
			userMessage = new UserMessage(userAsking, medias);

		}
		var promFile = new Prompt(userMessage);
		
		String  response = chatClient.prompt(promFile)
				.system("Look at this image and extract all the text content. Provide the output as plain text, maintaining the original layout and line breaks where appropriate. Include all visible text from the image.")
				.call().content();
		
		return response;

	}
}

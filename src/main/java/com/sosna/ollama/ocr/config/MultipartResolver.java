package com.sosna.ollama.ocr.config;

@Bean
public MultipartResolver multipartResolver() {
    CommonsMultipartResolver multipartResolver
      = new CommonsMultipartResolver();
    multipartResolver.setMaxUploadSize(5242880);
    return multipartResolver;
}
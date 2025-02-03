# Spring Boot OCR with Ollama AI

## �요 Project Overview
Spring Boot application for Optical Character Recognition (OCR) using Ollama AI, extracting text from images.

## 🛠 Prerequisites
- Java 17+
- Maven
- Docker
- Ollama AI

## 🔧 Technology Stack
- Spring Boot
- Ollama AI
- OCR Processing
- Image Processing Libraries

## 🚀 Setup Instructions

### Clone Repository
```bash
git clone https://eugensosna/spring-boot-ollama-ocr.git
cd spring-boot-ollama-ocr
```

### Configure Ollama
```bash
# Install Ollama
ollama pull llava:13b
```

### Application Configuration
`application.properties`:
```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=llava:13b
```

### Build and Run
```bash
./gradlew bootRun
```

## 🤝 Contributing
1. Fork repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create pull request

## 📄 License
MIT


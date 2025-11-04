# JPedal Spring Boot Example
An example project demonstrating how to use JPedal with the Spring Boot framework.

This project creates a microservice that converts PDF files into GIFs.

## Get started
Launch the microservice:
```bash
./mvnw spring-boot:run
```

Send a POST request will the desired PDF file:
```bash
curl -X POST http://localhost:8080/pdf/convert -F file=@Users/Shared/inputFile.pdf
```

The converted file will be in the `output` folder.

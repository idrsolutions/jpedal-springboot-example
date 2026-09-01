# JPedal Spring Boot Example
An example project demonstrating how to use [JPedal](https://www.idrsolutions.com/jpedal/) with the Spring Boot framework.

This project creates a microservice that converts PDF files into PNGs.

## Get started
Launch the microservice:
```bash
./mvnw spring-boot:run
```

Send a POST request with the desired PDF file, page number, and output file:
```bash
curl -X POST http://localhost:8080/pdf/convert -F file=@/Users/Shared/inputFile.pdf -F page=1 -o output.png
```

Copyright 2026 IDRsolutions

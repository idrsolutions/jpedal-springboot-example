/*
 * Copyright (C) 2025 IDRsolutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.idrsolutions.example;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * PdfController handles HTTP requests related to PDF processing operations.
 * <p>
 * It exposes REST endpoints for converting uploaded PDF files into image files.
 * The class delegates the PDF rendering logic to {@link PdfService}.
 */
@RestController
@RequestMapping("/pdf")
public class PdfController {

    /** Service that handles PDF rendering and conversion operations. */
    private final PdfService pdfService;

    /**
     * Constructor for dependency injection of the PdfService.
     *
     * @param pdfService service responsible for rendering PDF pages to images
     */
    public PdfController(final PdfService pdfService) {
        this.pdfService = pdfService;
    }

    /**
     * Converts a specific page of an uploaded PDF file into a PNG file.
     *
     * @param file the uploaded PDF file
     * @param page the page number to render (page numbers start from 1)
     * @return a ResponseEntity containing the PNG as a file resource
     * @throws Exception if file operations or rendering fail
     */
    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> convertPdf(@RequestParam("file") final MultipartFile file,
            @RequestParam("page") final int page) throws Exception {

        // Create a temporary file to store the uploaded PDF and save the uploaded content there
        final File temp = File.createTempFile("upload", ".pdf");
        file.transferTo(temp);

        // Render to PNG
        final File output = pdfService.renderPdfToImage(temp, page);

        // Create a Spring resource pointing to the PNG which can be used for the download
        final InputStreamResource resource = new InputStreamResource(new FileInputStream(output));

        // Build HTTP response headers for the download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + output.getName() + "\"")
                .contentLength(output.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}

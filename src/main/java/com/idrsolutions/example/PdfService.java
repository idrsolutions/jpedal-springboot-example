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

import com.idrsolutions.image.JDeli;
import com.idrsolutions.image.png.options.PngEncoderOptions;
import org.jpedal.examples.images.ConvertPagesToImages;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

/**
 * PdfService provides functionality for converting pages of a PDF file into PNG files.
 * <p>
 * This service encapsulates the logic for rendering a specific page of a PDF
 * into a {@link BufferedImage} using JPedal, and writing that image to disk as a PNG file.
 */
@Service
public class PdfService {

    /**
     * Renders a specific page of a PDF file to a PNG and saves it to disk.
     *
     * @param pdfFile the input PDF file to render
     * @param page the page number to render (1-based index)
     * @return a File object pointing to the generated PNG image
     * @throws Exception if reading, rendering, or writing the file fails
     */
    public File renderPdfToImage(final File pdfFile, final int page) throws Exception {
        // Create the output directory where images will be saved
        final File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Initialize JPedal
        final ConvertPagesToImages convert = new ConvertPagesToImages(pdfFile.getAbsolutePath());

        // Decode the PDF file
        if (!convert.openPDFFile()) {
            convert.closePDFfile();
        }

        // Convert to image
        final BufferedImage bi = convert.getPageAsImage(page);

        // Create the output file
        final File out = Paths.get(outputDir.getAbsolutePath(),
                pdfFile.getName() + "-" + page + ".png").toFile();

        // Write out as PNG
        final PngEncoderOptions options = new PngEncoderOptions();
        JDeli.write(bi, options, out);

        // Clean up
        convert.closePDFfile();

        return out;
    }

}

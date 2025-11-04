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
import com.idrsolutions.image.gif.options.GifEncoderOptions;
import org.jpedal.examples.images.ConvertPagesToImages;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

@Service
public class PdfService {

    public void renderPdfToImage(final File pdfFile, final File outputDir) throws Exception {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        final ConvertPagesToImages convert = new ConvertPagesToImages(pdfFile.getAbsolutePath());

        if (convert.openPDFFile()) {
            for (int page = 1; page <= convert.getPageCount(); page++) {
                final BufferedImage bi = convert.getPageAsImage(page);

                final File out = Paths.get(outputDir.getAbsolutePath(), page + ".gif").toFile();

                final GifEncoderOptions options = new GifEncoderOptions();
                JDeli.write(bi, options, out);
            }
        }

        convert.closePDFfile();
    }
}

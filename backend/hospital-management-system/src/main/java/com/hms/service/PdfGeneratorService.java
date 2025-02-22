package com.hms.service;

import com.hms.dto.PdfBillDTO;
import com.hms.util.HtmlTemplateProcessor;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class PdfGeneratorService {

    @Autowired
    private HtmlTemplateProcessor htmlTemplateProcessor;

    /**
     * Generate PDF from HTML template and BillDTO.
     *
     * @param templatePath Path to HTML template
     * @param outputPath   Path to save the generated PDF
     * @param billDTO      Data object containing dynamic values
     * @return File containing the generated PDF
     * @throws IOException, DocumentException In case of errors
     */
    public File generatePdfFromTemplate(String templatePath, String outputPath, PdfBillDTO billDTO) throws Exception {
        // ✅ Generate HTML content with dynamic values
        String htmlContent = htmlTemplateProcessor.generateHtmlFromTemplate(templatePath, billDTO);

        // ✅ Generate PDF using Flying Saucer
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        File file = new File(outputPath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            renderer.createPDF(outputStream);
        }

        return file;
    }
}

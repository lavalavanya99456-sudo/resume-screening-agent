package com.lavanya.resume_screening_agent.parser;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResumeParser {

    public String extractText(File file) throws IOException {

        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".pdf")) {
            return extractFromPdf(file);
        }

        if (fileName.endsWith(".docx")) {
            return extractFromDocx(file);
        }

        if (fileName.endsWith(".txt")) {
            return java.nio.file.Files.readString(file.toPath());
        }

        throw new IllegalArgumentException(
                "Unsupported file type. Please use PDF, DOCX, or TXT."
        );
    }

    private String extractFromPdf(File file) throws IOException {
        try (var document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractFromDocx(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

            return extractor.getText();
        }
    }
}

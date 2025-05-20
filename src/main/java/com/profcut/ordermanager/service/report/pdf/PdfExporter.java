package com.profcut.ordermanager.service.report.pdf;

import com.profcut.ordermanager.service.report.ReportExporter;
import com.profcut.ordermanager.service.report.value.ReportedOrder;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

import java.io.ByteArrayOutputStream;

@Component
@RequiredArgsConstructor
public class PdfExporter implements ReportExporter {

    private final XDocReportRegistry reportRegistry = XDocReportRegistry.getRegistry();
    private final ResourceLoader resourceLoader;

    @Override
    @SneakyThrows
    public ByteArrayResource generateOrderReport(ReportedOrder reportedOrder) {
        var templateUrl = ResourceLoader.CLASSPATH_URL_PREFIX + "report/order-specification.tmpl.docx";
        try (var template = resourceLoader.getResource(templateUrl).getInputStream()) {
            var report = reportRegistry.loadReport(template, TemplateEngineKind.Velocity);
            var context = report.createContext();
            context.put("order", reportedOrder);
            var pdfContent = new ByteArrayOutputStream();
            report.convert(context, Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF), pdfContent);
            return new ByteArrayResource(pdfContent.toByteArray());
        }
    }

    @Override
    public MimeType getSupportedMimeType() {
        return MediaType.APPLICATION_PDF;
    }

    @PreDestroy
    public void destroy() {
        reportRegistry.dispose();
    }
}

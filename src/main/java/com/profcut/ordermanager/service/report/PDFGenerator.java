package com.profcut.ordermanager.service.report;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.profcut.ordermanager.service.report.value.ReportedOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PDFGenerator {

    private final TemplateEngine templateEngine;

    public ByteArrayResource generatePdfFromOrder(ReportedOrder order) {
        Context context = new Context();
        context.setVariable("order", order);

        String htmlContent = templateEngine.process("order", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(htmlContent, null);
        builder.toStream(outputStream);
        builder.useFont(() -> getClass().getResourceAsStream("/fonts/DejaVuSans.ttf"),
                "DejaVu Sans",
                400,
                BaseRendererBuilder.FontStyle.NORMAL,
                true);
        builder.defaultTextDirection(BaseRendererBuilder.TextDirection.LTR);
        try {
            builder.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }
}

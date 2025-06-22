package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.service.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui")
@Tag(name = "report-ui-api")
public class ReportsApi {

    private final ReportService reportService;

    @GetMapping(value = "/orders/{orderId}/specification", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(description = "Получение PDF спецификации заказа")
    public ResponseEntity<Resource> generateOrderReport(@PathVariable("orderId") UUID orderId,
                                                        @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader) {
        var report = reportService.generateOrderReport(orderId, MimeType.valueOf(acceptHeader));
        var contentDisposition = ContentDisposition.attachment().filename("спецификация.pdf", StandardCharsets.UTF_8).build();
        var headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(report.contentLength())
                .body(report);
    }

    @GetMapping(value = "/orders/{orderId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateOrderPdf(@PathVariable("orderId") UUID orderId) {
        var report = reportService.getPdfReport(orderId);

        var contentDisposition = ContentDisposition.attachment().filename("спецификация.pdf", StandardCharsets.UTF_8).build();
        var headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(report.contentLength())
                .body(report);
    }
}

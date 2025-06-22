package com.profcut.ordermanager.service.report;

import com.profcut.ordermanager.domain.exceptions.UnsupportedExportTypeException;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.service.report.mapper.ReportedOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReportService {

    private final OrderService orderService;
    private final Map<MimeType, ReportExporter> reportExporters;
    private final ReportedOrderMapper reportedOrderMapper;
    private final PDFGenerator pdfGenerator;

    public ReportService(OrderService orderService,
                         List<ReportExporter> reportExporters,
                         ReportedOrderMapper reportedOrderMapper,
                         PDFGenerator pdfGenerator) {
        this.orderService = orderService;
        this.reportExporters = reportExporters.stream().collect(Collectors.toMap(ReportExporter::getSupportedMimeType, Function.identity()));
        this.reportedOrderMapper = reportedOrderMapper;
        this.pdfGenerator = pdfGenerator;
    }

    public ByteArrayResource generateOrderReport(UUID orderId, MimeType exportType) {
        if (!reportExporters.containsKey(exportType)) {
            throw new UnsupportedExportTypeException(exportType);
        }
        var order = orderService.findOrderById(orderId);
        var reportedOrder = reportedOrderMapper.apply(order);
        log.info("Reported order: {}", reportedOrder);
        return reportExporters.get(exportType).generateOrderReport(reportedOrder);
    }

    public ByteArrayResource getPdfReport(UUID orderId){
        var order = orderService.findOrderById(orderId);
        var reportedOrder = reportedOrderMapper.apply(order);
        return pdfGenerator.generatePdfFromOrder(reportedOrder);
    }
}

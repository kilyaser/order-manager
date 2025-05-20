package com.profcut.ordermanager.service.report;

import com.profcut.ordermanager.service.report.value.ReportedOrder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.MimeType;

public interface ReportExporter {

    ByteArrayResource generateOrderReport(ReportedOrder reportedOrder);

    MimeType getSupportedMimeType();
}

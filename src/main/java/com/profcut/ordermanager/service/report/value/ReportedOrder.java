package com.profcut.ordermanager.service.report.value;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReportedOrder {

    private String orderNumber;
    private String contractNumber;
    private String totalPrice;
    private String vatSum;
    private String currentSum;
    private String contractDate;
    private String currentDate;
    private String counterpartyName;

    private List<ReportedOrderItem> orderItems = new ArrayList<>();
}

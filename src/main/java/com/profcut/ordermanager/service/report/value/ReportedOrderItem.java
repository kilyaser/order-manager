package com.profcut.ordermanager.service.report.value;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportedOrderItem {

    private String productName;
    private String quantity;
    private String pricePerProduct;
    private String totalPrice;
}

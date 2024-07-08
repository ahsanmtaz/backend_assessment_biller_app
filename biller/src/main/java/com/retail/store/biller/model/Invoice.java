package com.retail.store.biller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    private UUID invoiceId;
    private User user;
    private List<InvoiceItem> items;
    private Double totalAmount;
    private Double percentageDiscountAmount;
    private Double flatDiscountAmount;
    private Double totalDiscountAmount;
    private Double netPayableAmount;
}

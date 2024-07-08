package com.retail.store.biller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
    private Product product;
    private Integer noOfUnits;

    public Double getUnitPrice() {
        return product.getUnitPrice();
    }
}

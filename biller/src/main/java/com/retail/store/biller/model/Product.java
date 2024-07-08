package com.retail.store.biller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private UUID productId;
    private String productName;
    private String productLabel;
    private String productDescription;
    private Double unitPrice;
    private ProductType productType;

    public boolean isDiscountAvailable() {
        return this.productType.getIsDiscountApplicable();
    }
}

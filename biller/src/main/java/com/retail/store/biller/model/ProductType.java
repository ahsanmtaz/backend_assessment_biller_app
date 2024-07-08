package com.retail.store.biller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ProductType {
    private Integer typeId;
    private String typeDescription;
    private Boolean isDiscountApplicable = false;
}

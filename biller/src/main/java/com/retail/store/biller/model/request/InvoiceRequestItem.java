package com.retail.store.biller.model.request;

import com.retail.store.biller.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRequestItem {
    private UUID productId;
    private Integer noOfUnits;
}

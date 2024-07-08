package com.retail.store.biller.model.request;

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
public class InvoiceRequest {
    private UUID userId;
    private UUID invoiceId;
    private List<InvoiceRequestItem> items;
}

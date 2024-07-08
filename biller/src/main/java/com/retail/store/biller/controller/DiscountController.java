package com.retail.store.biller.controller;

import com.retail.store.biller.model.Invoice;
import com.retail.store.biller.model.request.InvoiceRequest;
import com.retail.store.biller.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @GetMapping("/isAlive")
    public String isAlive() {
        return "Yes";
    }

    @PostMapping("/apply")
    public Invoice applyDiscount(@RequestBody InvoiceRequest invoiceRequest) {
        return discountService.applyDiscountOnInvoice(invoiceRequest);
    }
}

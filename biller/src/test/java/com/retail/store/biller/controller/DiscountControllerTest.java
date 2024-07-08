package com.retail.store.biller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.store.biller.model.Invoice;
import com.retail.store.biller.model.request.InvoiceRequest;
import com.retail.store.biller.model.request.InvoiceRequestItem;
import com.retail.store.biller.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiscountController.class)
class DiscountControllerTest {

    @InjectMocks
    DiscountController discountController;

    @Mock
    DiscountService discountService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void isAlive() throws Exception {
        mockMvc.perform(get("/discounts/isAlive")).andExpect(status().isOk());
    }

    @Test
    void applyDiscount() throws Exception {

        UUID affiliateUserId = UUID.fromString("5a8ca3de-b202-48a3-aa52-6c8406021c36");

        InvoiceRequestItem nonGroceryItem1 = InvoiceRequestItem.builder().productId(UUID.fromString("2657765a-a4f8-47eb-9880-148b41063ccc"))
                .noOfUnits(5).build();

        InvoiceRequestItem groceryItem1 = InvoiceRequestItem.builder().productId(UUID.fromString("15e63c30-e490-44a0-b94b-2591a1a5461b"))
                .noOfUnits(8).build();

        List<InvoiceRequestItem> invoiceRequestItems = new ArrayList<>(Arrays.asList(new InvoiceRequestItem[] { nonGroceryItem1, groceryItem1 }));

        InvoiceRequest affiliateInvoiceRequest = InvoiceRequest.builder().invoiceId(UUID.randomUUID())
                .userId(affiliateUserId)
                .items(invoiceRequestItems).build();

        Invoice invoiceResponse = discountService.applyDiscountOnInvoice(affiliateInvoiceRequest);

        when(discountService.applyDiscountOnInvoice(any(InvoiceRequest.class))).thenReturn(invoiceResponse);

        String requestJson = new ObjectMapper().writeValueAsString(affiliateInvoiceRequest);

        mockMvc.perform(post("/discounts/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)).andExpect(status().isOk());
    }
}
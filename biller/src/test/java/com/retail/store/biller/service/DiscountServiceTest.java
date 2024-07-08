package com.retail.store.biller.service;

import com.retail.store.biller.model.*;
import com.retail.store.biller.model.request.InvoiceRequest;
import com.retail.store.biller.model.request.InvoiceRequestItem;
import com.retail.store.biller.repository.GenericDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class,
        ProductService.class, DiscountService.class, GenericDAO.class})
class DiscountServiceTest {

    @InjectMocks
    DiscountService discountService;

    @Mock
    UserService userService;

    @Mock
    ProductService productService;

    @Mock
    GenericDAO genericDAO;

    @BeforeEach
    void setup() {
        this.userService.genericDAO = genericDAO;
        this.productService.genericDAO = genericDAO;
    }

    @Test
    void applyDiscountOnInvoice_affiliate() {

        UUID affiliateUserId = UUID.fromString("5a8ca3de-b202-48a3-aa52-6c8406021c36");

        User user = User.builder().userId(affiliateUserId).userFirstName("John").userLastName("Doe").userType(Enums.UserType.affiliate).build();
        when(userService.getUser(any(UUID.class))).thenReturn(user);

        ProductType nonGrocery = new ProductType(1, "non-grocery", true);
        ProductType grocery = new ProductType(2, "grocery", false);
        List<Product> products = Arrays.asList(new Product[]{new Product(UUID.fromString("2657765a-a4f8-47eb-9880-148b41063ccc"), "Product1", "ProductLabel1", "", 200.0, nonGrocery),
                new Product(UUID.fromString("15e63c30-e490-44a0-b94b-2591a1a5461b"), "Product2", "ProductLabel2", "", 150.0, grocery)});

        when(genericDAO.getProducts()).thenReturn(products);
        when(productService.getProduct(UUID.fromString("2657765a-a4f8-47eb-9880-148b41063ccc"))).thenReturn(products.get(0));
        when(productService.getProduct(UUID.fromString("15e63c30-e490-44a0-b94b-2591a1a5461b"))).thenReturn(products.get(1));

        InvoiceRequestItem nonGroceryItem1 = InvoiceRequestItem.builder().productId(UUID.fromString("2657765a-a4f8-47eb-9880-148b41063ccc"))
                .noOfUnits(5).build(); // discount applicable

        InvoiceRequestItem groceryItem1 = InvoiceRequestItem.builder().productId(UUID.fromString("15e63c30-e490-44a0-b94b-2591a1a5461b"))
                .noOfUnits(8).build(); // discount not-applicable

        List<InvoiceRequestItem> invoiceRequestItems = new ArrayList<>(Arrays.asList(new InvoiceRequestItem[] { nonGroceryItem1, groceryItem1 }));

        UUID invoiceId = UUID.randomUUID();
        InvoiceRequest affiliateInvoiceRequest = InvoiceRequest.builder().invoiceId(invoiceId)
                .userId(affiliateUserId)
                .items(invoiceRequestItems).build();

        Invoice invoiceResponse = discountService.applyDiscountOnInvoice(affiliateInvoiceRequest);

        assertEquals(invoiceId, invoiceResponse.getInvoiceId());
        assertEquals(Enums.UserType.affiliate, invoiceResponse.getUser().getUserType());
        assertEquals(2200.0, invoiceResponse.getTotalAmount());
        assertEquals(100.0, invoiceResponse.getPercentageDiscountAmount());
        assertEquals(105.0, invoiceResponse.getFlatDiscountAmount());
        assertEquals(205.0, invoiceResponse.getTotalDiscountAmount());
        assertEquals(1995.0, invoiceResponse.getNetPayableAmount());
    }

    @Test
    void getDiscountedAmount_employee() {
        UUID employeeUserId = UUID.fromString("8d1e6d4e-bb41-4f89-955c-027863290d3c");
        User user = User.builder().userId(employeeUserId).userFirstName("John").userLastName("Doe").userType(Enums.UserType.employee).build();
        when(userService.getUser(any(UUID.class))).thenReturn(user);
        User employeeUser = userService.getUser(UUID.fromString("8d1e6d4e-bb41-4f89-955c-027863290d3c"));
        double discountAmount = discountService.getDiscountedAmount(employeeUser, 50.0);
        assertEquals(50.0 * 0.30, discountAmount);
    }
}
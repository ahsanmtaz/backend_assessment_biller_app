package com.retail.store.biller.service;

import com.retail.store.biller.model.*;
import com.retail.store.biller.model.request.InvoiceRequest;
import com.retail.store.biller.model.request.InvoiceRequestItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DiscountService implements BaseService {

    private final double FLAT_DISCOUNT_CUTOFF = 100.0;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public Invoice applyDiscountOnInvoice(InvoiceRequest invoiceRequest) {

        Invoice invoice = null;
        User user = userService.getUser(invoiceRequest.getUserId());

        if(user != null) {

            double totalDiscountAmount = 0.0;
            double netPayableAmount = 0.0;
            double totalAmount = 0.0;
            double flatDiscountAmount = 0.0;
            double percentageDiscountAmount = 0.0;
            List<InvoiceItem> invoicedItems = new ArrayList<>();

            for(InvoiceRequestItem invoiceRequestItem : invoiceRequest.getItems()) {

                Product product = productService.getProduct(invoiceRequestItem.getProductId());

                if(product != null) {
                    InvoiceItem invoiceItem = new InvoiceItem();
                    invoiceItem.setProduct(product);
                    invoiceItem.setNoOfUnits(invoiceRequestItem.getNoOfUnits());

                    totalAmount += invoiceItem.getNoOfUnits() * invoiceItem.getUnitPrice();

                    if (invoiceItem.getProduct().isDiscountAvailable()) {
                        double discountedAmount = getDiscountedAmount(user, invoiceItem.getNoOfUnits() * invoiceItem.getUnitPrice());
                        totalDiscountAmount += discountedAmount;
                        percentageDiscountAmount += discountedAmount;
                        netPayableAmount += (invoiceItem.getNoOfUnits() * invoiceItem.getUnitPrice()) - discountedAmount;
                    } else {
                        netPayableAmount += invoiceItem.getNoOfUnits() * invoiceItem.getUnitPrice();
                    }

                    invoicedItems.add(invoiceItem);
                }
            }

            // Flat discount on every 100$
            if(netPayableAmount > FLAT_DISCOUNT_CUTOFF) {
                flatDiscountAmount = (Math.floor(netPayableAmount / FLAT_DISCOUNT_CUTOFF)) * Enums.DiscountValue.on100Dollars.getValue();
                totalDiscountAmount += flatDiscountAmount;
                netPayableAmount -= flatDiscountAmount;
            }

            invoice = new Invoice();
            invoice.setInvoiceId(invoiceRequest.getInvoiceId());
            invoice.setUser(user);
            invoice.setItems(invoicedItems);
            invoice.setTotalAmount(totalAmount);
            invoice.setPercentageDiscountAmount(percentageDiscountAmount);
            invoice.setFlatDiscountAmount(flatDiscountAmount);
            invoice.setTotalDiscountAmount(totalDiscountAmount);
            invoice.setNetPayableAmount(netPayableAmount);

        } else {
            throw new IllegalArgumentException("Invalid Data!");
        }

        return invoice;
    }

    double getDiscountedAmount(User user, double amount) {
        double result = 0.0;
        Enums.DiscountPercentage discountPercentage = Enums.DiscountPercentage.valueOf(user.getUserType().name());
        if(user.getUserType().equals(Enums.UserType.customer)
            && userService.isCustomerOf2Years(user)) {
            result = amount * (discountPercentage.getValue());
        } else {
            result = amount * (discountPercentage.getValue());
        }
        return result;
    }
}

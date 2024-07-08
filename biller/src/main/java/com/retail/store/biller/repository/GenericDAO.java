package com.retail.store.biller.repository;

import com.retail.store.biller.model.Enums;
import com.retail.store.biller.model.Product;
import com.retail.store.biller.model.ProductType;
import com.retail.store.biller.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class GenericDAO {

    public User getUser(UUID userId, Enums.UserType userType) {
        return new User(userId, "John", "Doe", userType);
    }

    public List<Product> getProducts() {
        ProductType nonGrocery = new ProductType(1, "non-grocery", true);
        ProductType grocery = new ProductType(2, "grocery", false);
        return Arrays.asList(new Product[]{new Product(UUID.fromString("2657765a-a4f8-47eb-9880-148b41063ccc"), "Product1", "ProductLabel1", "", 200.0, nonGrocery),
                new Product(UUID.fromString("15e63c30-e490-44a0-b94b-2591a1a5461b"), "Product2", "ProductLabel2", "", 150.0, grocery)});
    }
}

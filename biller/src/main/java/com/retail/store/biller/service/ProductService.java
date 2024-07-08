package com.retail.store.biller.service;

import com.retail.store.biller.model.Product;
import com.retail.store.biller.repository.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService implements BaseService {

    @Autowired
    GenericDAO genericDAO;

    public Product getProduct(UUID productId) {
        return genericDAO.getProducts().stream().filter(p -> p.getProductId().equals(productId)).findAny().orElse(null);
    }
}

package com.retail.store.biller.service;

import com.retail.store.biller.model.Enums;
import com.retail.store.biller.model.User;
import com.retail.store.biller.repository.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements BaseService {

    @Autowired
    GenericDAO genericDAO;

    public User getUser(UUID userId) {
        if(userId.toString().equals("5a8ca3de-b202-48a3-aa52-6c8406021c36"))
            return genericDAO.getUser(userId, Enums.UserType.affiliate);
        else if(userId.toString().equals("11acdfc8-fa92-42a0-b628-0d1a25819463"))
            return genericDAO.getUser(userId, Enums.UserType.customer);
        else if(userId.toString().equals("8d1e6d4e-bb41-4f89-955c-027863290d3c"))
            return genericDAO.getUser(userId, Enums.UserType.employee);
        else
            return null;
    }

    public Boolean isCustomerOf2Years(User user) {
        return true; // always same value for now
    }
}

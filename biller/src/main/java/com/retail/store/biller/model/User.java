package com.retail.store.biller.model;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class User {
    private UUID userId;
    private String userFirstName;
    private String userLastName;
    Enums.UserType userType;
}

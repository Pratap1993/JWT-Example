package com.chagu.restservice.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDetailsRequest {

    private String city;

    private String state;

    private String streetName;

    private BigInteger postalCode;

    private String addressType;

}

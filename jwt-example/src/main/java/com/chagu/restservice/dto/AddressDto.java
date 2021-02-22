package com.chagu.restservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDto {

    private Long id;

    private String addressId;

    private String city;

    private String state;

    private String streetName;

    private BigInteger postalCode;

    private String addressType;

    private UserDto userDetails;

}

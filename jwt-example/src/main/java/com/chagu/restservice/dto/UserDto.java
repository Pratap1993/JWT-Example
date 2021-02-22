package com.chagu.restservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto implements Serializable {

    private static final long serialVersionUID = -8014803773716129022L;

    private Long id;

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String encryptedPassword;

    private String emailVerificationToken;

    private Boolean emailVerificationStatus = false;

    private List<AddressDto> addresses = new ArrayList<>();

}

package com.chagu.restservice.model.request;

import com.chagu.restservice.entity.AddressEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDetailsRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Set<AddressEntity> addresses = new HashSet<>();

}

package com.chagu.restservice.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginRequest {

    private String email;

    private String password;

}

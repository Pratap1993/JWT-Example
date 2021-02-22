package com.chagu.restservice.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SecurityConstants {

    EXPIRATION_TIME("864000000"),
    TOKEN_PREFIX("ChaguRest "),
    HEADER_STRING("Authorization"),
    SIGN_UP_URL("/user/saveUser"),
    TOKEN_SECRET("chagu5458");

    @NonNull
    private String securityConstants;

    public String getConstant() {
        return securityConstants;
    }

}

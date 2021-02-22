package com.chagu.restservice.exception;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing Required Field !!!"),
    RECORD_ALREADY_EXISTS("Record Already Exists !!!"),
    INTERNAL_SERVER_ERROR("Internal Server Error !!!"),
    NO_RECORD_FOUND("Searched Record Not Found !!!"),
    AUTHENTICATION_FAILED("Authentication Failed !!!"),
    COULD_NOT_UPDATE_RECORD("Could Not Update The Record !!!"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email Address couldn't be verified !!!");

    @NonNull
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

}

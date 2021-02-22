package com.chagu.restservice.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class OperationStatusResponse {

    @NonNull
    private String operationResult;

    @NonNull
    private String operationName;

}

package com.chagu.restservice.exception;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class CustomExceptionBean {

    @NonNull
    private Date timeStamp;

    @NonNull
    private String message;

}

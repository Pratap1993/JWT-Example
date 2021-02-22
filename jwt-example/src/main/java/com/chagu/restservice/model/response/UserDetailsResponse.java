package com.chagu.restservice.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDetailsResponse extends ResourceSupport {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

}

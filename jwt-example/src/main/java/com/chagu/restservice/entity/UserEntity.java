package com.chagu.restservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1101779661753701027L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @NotEmpty
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @NotEmpty
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;

    @ToString.Exclude
    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();

}

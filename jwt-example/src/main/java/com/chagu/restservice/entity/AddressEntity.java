package com.chagu.restservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;

@Entity(name = "address")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = -2483356011914553187L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String addressId;

    @NotEmpty
    @Size(min = 1, max = 50, message = "City name must be between 1 and 50 characters")
    private String city;

    @NotEmpty
    @Size(min = 1, max = 50, message = "State name must be between 1 and 50 characters")
    private String state;

    @NotEmpty
    @Size(min = 1, max = 100, message = "Street name must be between 1 and 100 characters")
    private String streetName;

    @Column(nullable = false)
    private BigInteger postalCode;

    @Column(nullable = false)
    private String addressType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;

}

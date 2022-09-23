package com.mapping.onetoone;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Setter
@Getter
//@NoArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate // To update database value of only those fields which have been updated
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "pincode")
    private String pincode;

    @OneToOne(mappedBy = "address", fetch = FetchType.EAGER)
    private Employee employee;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }
}

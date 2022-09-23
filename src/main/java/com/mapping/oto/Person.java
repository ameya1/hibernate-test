package com.mapping.oto;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Setter
@Getter
@Builder
@DynamicUpdate // To update database value of only those fields which have been updated
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private Passport passport;
}

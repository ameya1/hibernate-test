package com.model;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Builder
@Entity
@Table(name = "customers")
public class Customers implements Serializable {

    @Id
    @Column(nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String city;

    @Column
    private String name;
}

package com.mapping.otm;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Setter
@Getter
@Builder
@DynamicUpdate // To update database value of only those fields which have been updated
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Integer year;

    /**
     * if required a column user_id in car table
     */
    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/



}

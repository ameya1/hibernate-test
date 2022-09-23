package com.mapping.otm;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user_table")
@Setter
@Getter
@Builder
@DynamicUpdate // To update database value of only those fields which have been updated
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * if required a column user_id in car table
     */
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Car> cars = new LinkedList<>();*/

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_car",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
    private List<Car> cars = new LinkedList<>();

    public User(String name) {
        this.name = name;
        this.cars = new LinkedList<>();
    }
}

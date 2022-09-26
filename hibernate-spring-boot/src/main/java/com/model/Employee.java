package com.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "employee")
@Builder
@AllArgsConstructor
@DynamicUpdate
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "empid_generator", initialValue = 1, allocationSize = 1, sequenceName = "empid_seq")
    Long id;

    /*@Column(nullable = false)
    String name;*/

    @Embedded
    private Name name;

    @Column(nullable = false)
    String email;

    LocalDate doj;

    Double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="department_id")
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Address> address;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<Project> projects;

    public Employee(){}

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name=" + name +
                ", email='" + email + '\'' +
                ", doj=" + doj +
                ", salary=" + salary +
                ", department=" + department +
                ", address=" + address +
                '}';
    }
}

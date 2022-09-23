package com.mapping.onetoone;

import com.mapping.manytomany.Phone;
import com.mapping.onetomany.Department;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "employee")
@Setter
@Getter
@Builder
@DynamicUpdate // To update database value of only those fields which have been updated
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    /*@ManyToMany(mappedBy = "employees")
    private List<Phone> phones = new LinkedList<>();
*/
    /*@ManyToOne
    @JoinColumn(name="department_id")
    private Department department;*/


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "phone_employee",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id", referencedColumnName = "id"))
    private List<Phone> phones = new LinkedList<>();

    public Employee(String name, Double salary, String email) {
        this.name = name;
        this.salary = salary;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                '}';
    }
}

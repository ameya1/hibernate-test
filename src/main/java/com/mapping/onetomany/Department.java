package com.mapping.onetomany;

import com.mapping.onetoone.Employee;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "department")
@Setter
@Getter
@Builder
@AllArgsConstructor
@DynamicUpdate // To update database value of only those fields which have been updated
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "department_employee",
            joinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private List<Employee> employees;// = new LinkedList<>();

    public Department() {
        this.employees = new LinkedList<>();
    }

    public Department(String name, String code, List<Employee> employees) {
        this.name = name;
        this.code = code;
        this.employees = employees;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        employees.forEach(employee -> sb.append("emp id : " + employee.getId() + " "));
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", employees='" + sb + '\'' +
                '}';
    }
}

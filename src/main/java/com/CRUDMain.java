package com;

import com.mapping.manytomany.Phone;
import com.mapping.mtm.Course;
import com.mapping.mtm.Student;
import com.mapping.onetomany.Department;
import com.mapping.onetoone.Address;
import com.mapping.onetoone.Employee;
import com.mapping.otm.Car;
import com.mapping.otm.User;
import com.mapping.oto.Passport;
import com.mapping.oto.Person;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Log4j2
public class CRUDMain {
    public static void main(String[] args) {
        Class[] classes = {Employee.class, Address.class, Department.class,
                Phone.class, Person.class, Passport.class,
                User.class, Car.class, Student.class, Course.class};
        try(Session session = HibernateUtil.getSessionFactory(classes).openSession()) {
            //persist(session);
            /*getById(session, 117980L);
            update(session, 117980L);
            */
            //oneToManySave(session);
            //getDepartment(session, 8L);
            saveStudent(session);
        } catch (HibernateException e) {
            log.error("Exception : {}", e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * - Persists object in database.
     * - Returns void
     * - Throws exception if row with same id already exists
     * @param session
     */
    public static void persist(Session session) {
        session.beginTransaction();
        for(long i = 0; i < 10000; i++) {
            String name = UUID.randomUUID().toString().substring(0, 10);
            Employee e = createEmployee();
            session.persist(e);
        }
        session.getTransaction().commit();
    }

    /**
     * - Persists object in database.
     * - Returns id of the object
     * - Throws exception if row with same id already exists
     * @param session
     */
    public static void save(Session session) {
        session.beginTransaction();
        for(long i = 0; i < 10000; i++) {
            String name = UUID.randomUUID().toString().substring(0, 10);
            Employee e = createEmployee();
            session.save(e);
        }
        session.getTransaction().commit();
    }

    /**
     * - Persists / Updates object in database.
     * - Returns void
     * @param session
     */
    public static void saveOrUpdate(Session session) {
        session.beginTransaction();
        for(long i = 0; i < 10000; i++) {
            String name = UUID.randomUUID().toString().substring(0, 10);
            Employee e = createEmployee();
            session.saveOrUpdate(e);
        }
        session.getTransaction().commit();
    }


    public static Employee getById(Session session, Long id) {
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        if(employee != null)
            log.info("Employee : {}", employee);
        session.getTransaction().commit();
        return employee;
    }

    public static void update(Session session, Long id) {
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        if(employee != null) {
            employee.setEmail("sean@g.com");
            session.update(employee);
        }
        session.getTransaction().commit();
    }

    public static void oneToOnePersist(Session session) {
        session.beginTransaction();
        String name = UUID.randomUUID().toString().substring(0, 10);
        Employee e = createEmployee();
        Address a = createAddress();
        a.setEmployee(e);
        e.setAddress(a);
        session.save(e);
        session.getTransaction().commit();
    }

    public static Address createAddress() {
        return Address.builder()
                .city(UUID.randomUUID().toString().substring(0, 10))
                .pincode(UUID.randomUUID().toString().substring(0, 10))
                .state(UUID.randomUUID().toString().substring(0, 10))
                .street(UUID.randomUUID().toString().substring(0, 10))
                .build();
    }

    public static void oneToManySave(Session session) {
        session.beginTransaction();
        Department department = createDepartment();
        session.save(department);
        session.getTransaction().commit();
    }

    public static Employee createEmployee() {
        String name = UUID.randomUUID().toString().substring(0, 10);
        return Employee.builder()
                .address(createAddress())
                .name(name)
                .salary(new Random().nextDouble() * 10000)
                .email(name+"@abc.com")
                .build();
    }

    public static Department createDepartment() {
        String name = UUID.randomUUID().toString().substring(0, 10);
        Employee e1 = createEmployee();
        Employee e2 = createEmployee();
        Department d = Department.builder()
                .name(name)
                .code(name.substring(0, 5))
                .employees(Arrays.asList(e1, e2))
                .build();//new Department(name, name.substring(0, 4));
        /*d.getEmployees().add(e1);
        d.getEmployees().add(e2);*/
        /*e1.setDepartment(d);
        e2.setDepartment(d);*/
        /*d.getEmployees().add(e1);
        d.getEmployees().add(e2);*/
        return d;
    }

    public static void getDepartment(Session session, Long id) {
        Department d = session.get(Department.class, id);
        log.info(d);
    }

    public static void manyToMany(Session session) {
        session.beginTransaction();
        Employee employee = createEmployee();
        employee.setPhones(Arrays.asList(createPhone(), createPhone()));
        session.save(employee);
        session.getTransaction().commit();
    }

    public static Phone createPhone() {
        return Phone.builder()
                .number(new Random().toString())
                .build();
    }


    public static void savePassport(Session session) {
        session.beginTransaction();
        Person person = createPerson();
        Passport passport = createPassport();
        passport.setPerson(person);
        person.setPassport(passport);
        session.save(person);
        session.getTransaction().commit();
    }

    public static Passport createPassport() {
        return Passport.builder()
                .code(UUID.randomUUID().toString().substring(0, 10))
                .build();
    }

    public static Person createPerson() {
        String name = UUID.randomUUID().toString().substring(0, 10);
        return Person.builder()
                .name(name)
                .email(name.substring(0, 5))
                .build();
    }

    public static void saveUser(Session session) {
        session.beginTransaction();
        User user = createUser();
        Car c1 = createCar();
        Car c2 = createCar();
        /*c1.setUser(user);
        c2.setUser(user);*/
        user.getCars().add(c1);
        user.getCars().add(c2);
        session.save(user);
        session.getTransaction().commit();
    }

    public static User createUser() {
        String name = UUID.randomUUID().toString().substring(0, 10);
        /*return User.builder()
                .name(name)
                .build();*/
        return new User(name);
    }

    public static Car createCar() {
        String name = UUID.randomUUID().toString().substring(0, 6);
        return Car.builder()
                .model(name)
                .year(LocalDate.EPOCH.getYear())
                .build();
    }

    public static void saveStudent(Session session) {
        session.beginTransaction();
        Student student = createStudent();
        Course c1 = createCourse();
        Course c2 = createCourse();
        student.setCourses(Arrays.asList(c1, c2));
        session.save(student);
        session.getTransaction().commit();
    }

    public static Student createStudent() {
        String name = UUID.randomUUID().toString().substring(0, 10);
        return Student.builder()
                .name(name)
                .email(name.substring(0, 6) + "@abc.com")
                .build();
    }

    public static Course createCourse() {
        String name = UUID.randomUUID().toString().substring(0, 10);
        return Course.builder()
                .name(name)
                .code(name.substring(0, 6))
                .build();
    }
}

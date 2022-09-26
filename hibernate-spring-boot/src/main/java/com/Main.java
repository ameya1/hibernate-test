package com;

import com.model.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Main<T> {
    public static void main(String[] args) {
        Session session = getSessionFactory().openSession();
        String result = session.createNativeQuery("select version()").getSingleResult().toString();
        System.out.println("Result : " + result);

        createEmployee(session);
        //Employee e = getEmployee(session, 799921817485148161l);
        //System.out.println(e);
        //updateEmployee(session, 799040637301522433l);
        //deleteEmployee(session, 799040637301522433l);
    }

    public static void createEmployee(Session session) {
        Name name = Name.builder()
                .firstName("Jess")
                .middleName("Barry")
                .lastName("Hill")
                .build();

        Name name2 = Name.builder()
                .firstName("Cara")
                .middleName("Jose")
                .lastName("Elliot")
                .build();

        Department department = Department.builder()
                .name("HR").build();

        //Department department = (Department) get(Department.class, session, 799358030986772481l);

        Employee employee = Employee.builder()
                .name(name)
                .doj(LocalDate.of(2010, 12, 23))
                .salary(90000D)
                .email("jessh@mail.com")
                .department(department)
                .build();

        Employee employee2 = Employee.builder()
                .name(name2)
                .doj(LocalDate.of(2010, 12, 23))
                .salary(90000D)
                .email("carae@mail.com")
                .department(department)
                .build();

        Address a1 = address("NYC", "21st Street", "NY", "USA", "34524", employee);
        Address a2 = address("MUM", "Link Road", "MH", "IND", "665241", employee2);

        Project p1 = Project.builder().name("Hibernate").employees(List.of(employee, employee2)).build();
        Project p2 = Project.builder().name("Cassandra").employees(List.of(employee, employee2)).build();

        employee.setAddress(List.of(a1));
        employee2.setAddress(List.of(a2));

        employee.setProjects(List.of(p1, p2));
        employee2.setProjects(List.of(p1, p2));

        session.beginTransaction();
        session.save(employee);
        session.save(employee2);
        session.getTransaction().commit();

    }

    public static Address address(String city, String street, String state, String country, String zipCode, Employee employee) {
        return Address.builder()
                .city(city)
                .state(state)
                .country(country)
                .zipCode(zipCode)
                .employee(employee)
                .street(street)
                .build();
    }

    public static Employee getEmployee(Session session, Long id) {
        return session.get(Employee.class, id);
    }

    public static Object get(Class clazz, Session session, Long id) {
        return session.get(clazz, id);
    }

    public static void updateEmployee(Session session, Long id) {
        Employee e = getEmployee(session, id);
        if(e != null) {
            e.setSalary(80_000D);
            session.beginTransaction();
            session.update(e);
            session.getTransaction().commit();
        }
    }

    public static void deleteEmployee(Session session, Long id) {
        Employee e = getEmployee(session, id);
        if(e != null) {
            e.setSalary(80_000D);
            session.beginTransaction();
            session.delete(e);
            session.getTransaction().commit();
        }
    }

    public static DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://192.168.0.106:26257/bank");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");

        hikariConfig.setMaximumPoolSize(40);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("springHikariCP");

        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("hibernate.hbm2ddl.auto", "update");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    public static JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL10Dialect");
        return hibernateJpaVendorAdapter;
    }

    public static EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPackagesToScan(new String[] {"com.model"});
        emf.setPersistenceUnitName("default");
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    public static SessionFactory getSessionFactory() {
        if (entityManagerFactory().unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManagerFactory().unwrap(SessionFactory.class);
    }
}

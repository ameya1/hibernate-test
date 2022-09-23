package com;

import com.model.Account;
import com.model.Customers;
import com.model.Employee;
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
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Session session = getSessionFactory().openSession();
        String result = session.createNativeQuery("select version()").getSingleResult().toString();
        System.out.println("Result : " + result);

        //Customers customer = Customers.builder().city("New York").name("Tiara").build();

        /*Account account = Account.builder().accountNumber(565644144L).name("Flora").build();

        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();*/

        Employee employee = Employee.builder()
                .name("Jenny")
                .doj(LocalDate.of(2010, 12, 23))
                .salary(90000D)
                .email("mira@mail.com")
                .build();
        session.beginTransaction();
        Long id = (Long) session.save(employee);
        session.getTransaction().commit();
        System.out.printf("emp_id is " + id);
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

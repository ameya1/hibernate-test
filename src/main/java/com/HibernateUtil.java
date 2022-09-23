package com;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class HibernateUtil {
    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    static {}

    public static SessionFactory getSessionFactory(Class... classes) {
        try{
            if(sessionFactory == null) {
                Map<String, String> dbSettings = new HashMap<>();
                dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/advance_sql");
                dbSettings.put(Environment.USER, "root");
                dbSettings.put(Environment.PASS, "root");
                dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                dbSettings.put(Environment.HBM2DDL_AUTO, "update");
                //dbSettings.put(Environment.SHOW_SQL, "true");
                dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                dbSettings.put("hibernate.hikari.connectionTimeout", "20000");
                // Minimum number of ideal connections in the pool
                dbSettings.put("hibernate.hikari.minimumIdle", "10");
                // Maximum number of actual connection in the pool
                dbSettings.put("hibernate.hikari.maximumPoolSize", "20");
                // Maximum time that a connection is allowed to sit ideal in the pool
                dbSettings.put("hibernate.hikari.idleTimeout", "300000");

                serviceRegistry = new StandardServiceRegistryBuilder().applySettings(dbSettings).build();
                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                for (Class c : classes)
                    metadataSources.addAnnotatedClass(c);
                Metadata metadata = metadataSources.getMetadataBuilder().build();
                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            }
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
            e.printStackTrace();
            if (sessionFactory != null)
                sessionFactory.close();
            if(serviceRegistry != null)
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
        return sessionFactory;
    }
}

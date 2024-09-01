package com.example.auth.controllers;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/checker")
public class CheckerController {

    private final Environment environment;
    private DataSource dataSource;

    @Autowired
    public CheckerController(Environment environment, DataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    @GetMapping("/env")
    public List<String> getEnv() {
        List<String> properties = new ArrayList<>();

        var propsNames = List.of("spring.application.name", "db-url", "spring.datasource.url");
        
        propsNames.forEach(propName -> properties.add(propName + ": " + environment.getProperty(propName)));

        return properties;
    }

    @GetMapping("/db-test")
    public String testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return "Database connection successful: " + connection.getMetaData().getURL();
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}

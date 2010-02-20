package com.ordecon.schmoo;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.io.File;

/**
 * @author Ivan Stojic
 */
public class SeedDatabase {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure(new File("hibernate.cfg.xml"));
        SchemaExport se = new SchemaExport(configuration);
        se.create(true, true);
    }
}

package com.ex;

import com.ex.controller.Application;
import org.apache.log4j.BasicConfigurator;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        BasicConfigurator.configure();

        Application app = new Application();
        app.run(configuration);
    }
}

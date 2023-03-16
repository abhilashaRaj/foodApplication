package com.incture.FoodApp;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

    public class HibernateUtil {
        private static SessionFactory SessionFactory;

        public static SessionFactory getSessionFactory() {

            if (SessionFactory == null) {
                try {
                    Configuration cfg = new Configuration();
                    cfg.configure("hibernate.cfg.xml");
                    SessionFactory = cfg.buildSessionFactory();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return SessionFactory;
        }
    }


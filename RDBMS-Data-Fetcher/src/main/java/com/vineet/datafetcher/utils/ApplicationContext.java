package com.vineet.datafetcher.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationContext {

    public static void initialize(String fileName) {
        System.out.println("Attempting to read properties file from :" + fileName);
        try {
            appProperties.load(new FileReader(new File(fileName)));
            System.out.println("Properties Init Complete !! Prop Size :" + appProperties.size());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties appProperties = new Properties();

    public static String getProperty(String name) {
        return appProperties.getProperty(name);
    }

    public static Integer getIntProperty(String name) {
        return Integer.parseInt(appProperties.getProperty(name));
    }

}

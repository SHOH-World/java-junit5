package org.co.shoh.HelperClasses;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyHelper {
    public static Properties loadProperties(String pathToProperty) {
        Properties propertyFile = new Properties();
        try (FileInputStream filePathToProperty = new FileInputStream(pathToProperty)) {
            propertyFile.load(filePathToProperty);
        } catch (Exception e) {
            propertyFile = null;
            System.out.println("IO Exception in Property Helper(path to property is '" + pathToProperty + "'): " + e.getMessage());
        }
        return propertyFile;
    }

}

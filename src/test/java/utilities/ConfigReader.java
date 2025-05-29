package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static FileInputStream input;
    private static Properties properties;

    static{
        String path = System.getProperty("user.dir")+"/src/test/resources/configurations/Configuration.properties";
        try {
            input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("Path to properties file is invalid or file is missing.");
        } catch (IOException e) {
            System.out.println("Failed to load properties file.");
        } finally {
            try {
                assert input != null;
                input.close();
            } catch (IOException e) {
                System.out.println("Failed to close FileInputStream.");
            }
        }
    }

    /**
     * This method accepts keys from properties file as a parameter
     * and returns corresponding values as a String.
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}

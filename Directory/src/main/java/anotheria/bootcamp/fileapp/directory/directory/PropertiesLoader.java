package anotheria.bootcamp.fileapp.directory.directory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class load properties from properties file.
 * Default path is /resources
 * Path can be changed in constructor with parameters
 *
 *  @author Yevgenii Nikonchuk.
 */
public class PropertiesLoader {

    /**
     * Path to properties file
     */
    private static String path = "/Users/admin/IdeaProjects/NetworkProblemAdvanced/Directory/src/main/resources/directory.properties";

    /**
     * Constructor.
     * Object without parameters shouldn't be created.
     */
    private PropertiesLoader(){}

    /**
     * Constructor.
     * @param path initialize path to the properties file.
     */
    public PropertiesLoader(String path){
        this.path = path;
    }

    /**
     * Loads properties from property file.
     * @return Properties object that contains properties from file.
     */
    public static Properties getProperties(){
        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(path);

            properties.load(in);
            return properties;
        } catch (IOException e){
            throw new RuntimeException("There is no such properties file.");
        }
    }
}

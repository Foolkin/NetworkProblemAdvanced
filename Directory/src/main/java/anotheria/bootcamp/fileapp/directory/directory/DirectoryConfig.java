package anotheria.bootcamp.fileapp.directory.directory;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * This class configure current directory using ConfigureMe.
 */
@ConfigureMe(name = "directory-config")
public class DirectoryConfig {

    /**
     * Singleton instance of DirectoryConfig
     */
    private static volatile DirectoryConfig instance;
    /**
     * Root directory.
     */
    @Configure
    private String directory;

    /**
     * Constructor.
     * Initialize directory by default configuration.
     */
    private DirectoryConfig() {
        ConfigurationManager.INSTANCE.configure(this);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Returns {@link anotheria.bootcamp.fileapp.directory.directory.DirectoryConfig} instance.
     *
     * @return {@link anotheria.bootcamp.fileapp.directory.directory.DirectoryConfig}
     */
    public static DirectoryConfig getInstance() {
        DirectoryConfig localInstance = instance;
        if(localInstance == null){
            synchronized (DirectoryConfig.class){
                localInstance = instance;
                if(localInstance == null)
                    instance = localInstance = new DirectoryConfig();
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        return "DirectoryConfig{" +
                "directory='" + directory + '\'' +
                '}';
    }
}

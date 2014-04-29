package directory;

import java.io.File;
import java.util.Properties;

/**
 * This class contains methods to show current directory, files in it and methods to put files.
 *
 * @author Yevgenii Nikonchuk.
 */
public class Directory {

    /**
     * Root directory.
     */
    private String directory;
    /**
     * Constructor.
     * Initialize root folder.
     */
    public Directory(String PROPERTY_NAME) {
        this.directory = PROPERTY_NAME;
        initDirectory();
    }

    /**
     * Initialize root directory.
     * Create directory if it not exist.
     */
    public void initDirectory(){
        makeDir(getRootDir());
    }

    /**
     * Looks for a file in current directory.
     * @param fileName name of file.
     * @return File if it is exist, and null if not exist.
     */
    public File getFile(String fileName){
        for(File file : getRootListFiles()) {
            if (file.getName().equals(fileName))
                return file;
        }
        return null;
    }

    /**
     * Checks is file contains in current directory.
     * @param fileName name of file.
     * @return true if it is exist, and false if not exist.
     */
    public boolean containsFile(String fileName){
        for(File f : getRootListFiles()){
            if(f.getName().equals(fileName))
                return true;
        }
        return false;
    }

    /**
     * Returns list of files and folders in root directory in string array.
     * @return String[] list of files.
     */
    public String[] getRootList(){
        File file = new File(getRootDir());
        return file.list();
    }

    /**
     * Returns list of files and folders in root directory in File array.
     * @return File[] list of files.
     */
    public File[] getRootListFiles(){
        File file = new File(getRootDir());
        return file.listFiles();
    }


    /**
     * Returns servers root directory from properties file.
     * @return root  directory.
     */
    public String getRootDir() {
        Properties properties = PropertiesLoader.getProperties();
        return properties.getProperty(directory);
    }

    /**
     * Create a directory if it wasn't created.
     *
     * @param dir directory name.
     */
    public void makeDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}

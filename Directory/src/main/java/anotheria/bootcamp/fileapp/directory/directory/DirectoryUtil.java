package anotheria.bootcamp.fileapp.directory.directory;

import java.io.File;
/**
 * This class contains methods to show current directory, files in it and methods to put files.
 *
 * @author Yevgenii Nikonchuk.
 */
public final class DirectoryUtil {

    /**
     * Initialize root directory.
     * Create directory if it not exist.
     */
    public static void initDirectory(){
        makeDir(DirectoryConfig.getInstance().getDirectory());
    }

    /**
     * Looks for a file in current directory.
     * @param fileName name of file.
     * @return File if it is exist, and null if not exist.
     */
    public static File getFile(String fileName){
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
    public static boolean containsFile(String fileName){
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
    public static String[] getRootList(){
        File file = new File(DirectoryConfig.getInstance().getDirectory());
        return file.list();
    }

    /**
     * Returns list of files and folders in root directory in File array.
     * @return File[] list of files.
     */
    public static File[] getRootListFiles(){
        File file = new File(DirectoryConfig.getInstance().getDirectory());
        return file.listFiles();
    }


    /**
     * Returns servers root directory from properties file.
     * @return root  directory.
     */
    public static String getRootDir() {
        return DirectoryConfig.getInstance().getDirectory();
    }

    /**
     * Create a directory if it wasn't created.
     *
     * @param dir directory name.
     */
    public static void makeDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * Private constructor.
     */
    private DirectoryUtil() {
        throw new AssertionError("Shouldn't be instantiated");
    }
}

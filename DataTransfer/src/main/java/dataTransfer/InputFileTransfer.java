package dataTransfer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * This class used for receiving files.
 *
 *  @author Yevgenii Nikonchuk.
 */
public class InputFileTransfer implements Runnable{

    /**
     * Socket that used for receive data
     */
    private Socket socket;
    private String directory;
    private File file;

    public InputFileTransfer(Socket socket, String directory){
        this.socket = socket;
        this.directory = directory;
    }

    @Override
    public void run() {
        try
        {
            InputStream inStream = socket.getInputStream();
            BufferedInputStream bufInStream = new BufferedInputStream(inStream);

            socket.setSoTimeout(100);

            file = getFileInfo(bufInStream);

            writeFile(bufInStream, file);
        } catch (SocketTimeoutException e) {
            System.out.println("There is no such file, check file name\n or use DIR to see file list");
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    /**
     * This method writes file on disk from inputStream
     * @param inStream input stream that contains data
     * @param file file object that contains file info
     */
    public void writeFile(InputStream inStream, File file){
        try(
                FileOutputStream fileOutStream = new FileOutputStream(directory + file.getName());
                BufferedOutputStream buffOutStream = new BufferedOutputStream(fileOutStream);
        ){
            long bytes = file.length();
            while(bytes > 0) {
                bytes--;
                buffOutStream.write(inStream.read());
                buffOutStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }

    /**
     * This method return file object that contains information about the data that send follows it.
     * @return file object, that contains info
     */
    public File getFileInfo(InputStream inputStream) throws IOException {
        try {
            ObjectInputStream objIn = new ObjectInputStream(inputStream);
            File file = (File) objIn.readObject();
            return file;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("problem with read object", e);
        }
    }
}

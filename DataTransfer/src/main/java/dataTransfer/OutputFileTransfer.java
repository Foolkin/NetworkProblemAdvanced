package dataTransfer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This file used for send file to client.
 *
 *  @author Yevgenii Nikonchuk.
 */
public class OutputFileTransfer implements Runnable {

    /**
     * Socket that used to send data to the client.
     */
    private Socket socket;
    /**
     * File that contains info about sent data.
     */
    private File file;

    /**
     * Constructor. Initialize socket and file
     * @param socket Socket that used to send data to the client.
     * @param file File that contains info about sent data.
     */
    public OutputFileTransfer(Socket socket, File file) {
        this.socket = socket;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            OutputStream outStream = socket.getOutputStream();
            BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream);

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            sendFileInfo(bufOutStream, file);
            int b;
            while ((b = bis.read()) >= 0) {
                bufOutStream.write(b);
                bufOutStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't send file", e);
        }
    }

    /**
     * This method sends file object that contains information about sending data.
     * @param outStream stream that will send file object.
     * @param file file object that contains information.
     */
    public void sendFileInfo(OutputStream outStream, File file) {
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(outStream);
            objOut.writeObject(file);
            objOut.flush();
        } catch (IOException e) {
            throw new RuntimeException("Can't send file", e);
        }
    }
}

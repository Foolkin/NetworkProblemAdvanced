package anotheria.bootcamp.fileapp.dataTransfer;

import anotheria.bootcamp.fileapp.directory.Constatns.PropertyNames;
import anotheria.bootcamp.fileapp.directory.directory.Directory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class used for send list of files to the client.
 * Implements Runnable.
 *
 *  @author Yevgenii Nikonchuk.
 */
public class DirectoryOutputTransfer implements Runnable{

    /**
     * Socket that used to send data to the client.
     */
    private Socket socket;

    /**
     * Constructor.
     * Initialize socket.
     * @param socket Socket that used to send data to the client.
     */
    public DirectoryOutputTransfer(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {
        Directory dir = new Directory(PropertyNames.SERVER_ROOT_DIR);
        try
        {
            PrintWriter bufOutStream = new PrintWriter(socket.getOutputStream(),true);
            String s = "";
            for(String file : dir.getRootList()) {
                s += file + " ";
            }
            bufOutStream.println(s);
        } catch (IOException e) {
            throw new RuntimeException("socket output stream problem", e);
        }
    }
}

package dataTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

/**
 * This class used for receive list of files from server.
 * Implements Runnable.
 *
 *  @author Yevgenii Nikonchuk.
 */
public class DirectoryInputTransfer implements Runnable {
    /**
     * Socket that used to receive data from server.
     */
    private Socket socket;

    /**
     * Constructor.
     * Initialize socket.
     *
     * @param socket Socket that used to receive data from server.
     */
    public DirectoryInputTransfer(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringTokenizer tokenizer = new StringTokenizer(bufReader.readLine(), " ");

            directoryOutput(tokenizer);
        } catch (SocketTimeoutException e) {
            System.out.println("can't get a directory");
        } catch (IOException e) {
            throw new RuntimeException("can't receive directory from server", e);
        }
    }

    /**
     * This method output string that separate by StringTokenizer
     * @param tokenizer StringTokenizer object that contains string with separators
     */
    public void directoryOutput(StringTokenizer tokenizer){
        while (tokenizer.hasMoreTokens())
            System.out.println(tokenizer.nextToken());
    }
}

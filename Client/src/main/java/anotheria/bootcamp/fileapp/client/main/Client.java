package anotheria.bootcamp.fileapp.client.main;

import anotheria.bootcamp.fileapp.client.commandDataSync.CommandDataSynchronizer;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class Client connects to the command and sockets. He can exchange following commands with the server:
 * DIR - returns a list of files.
 * PUT filename - uploads a file with given name to the server.
 * GET filename - downloads a file with given name from the server.
 * All data sends over data socket.
 *
 * @author Yevgenii Nikonchuk.
 */
public class Client {

    /**
     * Socket that used to send commands to the server.
     */
    Socket commandSocket;

    /**
     * Socket that used to send
     */
    Socket dataSocket;

    /**
     * Constructor. Initialize command and data sockets.
     */
    public Client(){
        try{
            commandSocket = new Socket("localhost", 8189);
            dataSocket = new Socket("localhost", 8086);
        } catch (IOException e){
            throw new RuntimeException("can't connect to server", e);
        }
    }

    /**
     * This method starts commandDataSync between client and server.
     */
    public void start(){
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new CommandDataSynchronizer(commandSocket, dataSocket));

        executor.shutdown();
    }

    /**
     * Entry point.
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}

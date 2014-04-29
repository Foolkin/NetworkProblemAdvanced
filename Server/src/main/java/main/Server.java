package main;

import session.Session;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server listens to two tcp socket. One for commands, one for data transfer.
 * The server supports multiple clients (multithreaded).
 */
public class Server {

    /**
     * Socket that used to receive commands from client.
     */
    ServerSocket commandSocket;

    /**
     * Socket that used for data transfer.
     */
    ServerSocket dataSocket;

    /**
     * Constructor. Initialize command and data sockets.
     */
    public Server(){
        try {
            commandSocket = new ServerSocket(8189);
            dataSocket = new ServerSocket(8086);
        } catch (IOException e){
            throw new RuntimeException("can't create ServerSocket", e);
        }
    }

    /**
     * Starts session between server and client.
     */
    public void start(){
        try{
            while(true) {
                Socket incomingCommand = commandSocket.accept();
                Socket incomingData = dataSocket.accept();

                ExecutorService executor = Executors.newCachedThreadPool();

                executor.execute(new Session(incomingCommand, incomingData));

                executor.shutdown();
            }
        } catch (IOException e){
            throw new RuntimeException("Can't accept socket");
        }
//        finally {
//            try {
//                commandSocket.close();
//                dataSocket.close();
//            }catch (IOException e){
//                throw new RuntimeException("can't close socket", e);
//            }
//        }
    }

    /**
     * Entry point.
     * @param args
     */
    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }
}

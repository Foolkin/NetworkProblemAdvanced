package listeners;

import dataTransfer.DirectoryOutputTransfer;
import dataTransfer.InputFileTransfer;
import dataTransfer.OutputFileTransfer;
import directory.Directory;
import pojos.Command;
import session.Session;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  This class performs data transfer based on command.
 */
public class DataListener implements Runnable {

    /**
     * Socket that used to transfer data.
     */
    private Socket dataSocket;

    /**
     * Session object.
     */
    private Session session;

    /**
     * Constructor. Initialize dataSocket and session objects.
     * @param dataSocket Socket that used transfer data.
     * @param session session.
     */
    public DataListener(Socket dataSocket, Session session) {
        this.dataSocket = dataSocket;
        this.session = session;
    }


    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Directory directory = session.getDirectory();

        try {

            boolean done = false;

            while (!done) {
              synchronized (this){ wait();}

                Command command = session.getCommand();

                switch (command.getCommandName()) {
                    case DIR:
                        executor.execute(new DirectoryOutputTransfer(dataSocket));
                        break;
                    case PUT:
                        executor.execute(new InputFileTransfer(dataSocket, directory.getRootDir()));
                        break;
                    case GET:
                        executor.execute(new OutputFileTransfer(dataSocket, directory.getFile(command.getFileName())));
                        break;
                    case BYE:
                        done = true;
                        dataSocket.shutdownInput();
                        dataSocket.shutdownOutput();
                        break;
                    default:
                        System.out.println("there is no such command");
                        continue;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread incorrect interrupted", e);
        } catch (IOException e){
            throw new RuntimeException("can't shutdown socket I/O", e);
        }
    }
}

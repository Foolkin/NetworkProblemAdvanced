package anotheria.bootcamp.fileapp.client.listeners;

import anotheria.bootcamp.fileapp.client.commandDataSync.CommandDataSynchronizer;
import anotheria.bootcamp.fileapp.dataTransfer.DirectoryInputTransfer;
import anotheria.bootcamp.fileapp.dataTransfer.InputFileTransfer;
import anotheria.bootcamp.fileapp.dataTransfer.OutputFileTransfer;
import anotheria.bootcamp.fileapp.directory.directory.Directory;
import anotheria.bootcamp.fileapp.commands.pojos.Command;
import anotheria.bootcamp.fileapp.commands.pojos.PutCommand;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class performs data transfer based on command.
 *
 *  @author Yevgenii Nikonchuk.
 */
public class DataListener implements Runnable {

    /**
     * Socket that used to transfer data.
     */
    private Socket dataSocket;

    /**
     * commandDataSync.CommandDataSynchronizer object.
     */
    private CommandDataSynchronizer commandDataSynchronizer;

    /**
     * Constructor. Initialize dataSocket and commandDataSync objects.
     * @param dataSocket Socket that used transfer data.
     * @param commandDataSynchronizer commandDataSync.
     */
    public DataListener(Socket dataSocket, CommandDataSynchronizer commandDataSynchronizer) {
        this.dataSocket = dataSocket;
        this.commandDataSynchronizer = commandDataSynchronizer;
    }


    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Directory directory = commandDataSynchronizer.getDirectory();
        try {

            while (true) {
                synchronized (this){ wait();}
                Command command = commandDataSynchronizer.getCommand();

                switch (command.getType()) {
                    case DIR:
                        executor.execute(new DirectoryInputTransfer(dataSocket));
                        break;
                    case PUT:
                        PutCommand put = (PutCommand)command;
                        if(!directory.containsFile(put.getFileName())) {
                            System.out.println("There is no such file, check file name.");
                            continue;
                        }
                        executor.execute(new OutputFileTransfer(dataSocket, directory.getFile(put.getFileName())));
                        break;
                    case GET:
                        executor.execute(new InputFileTransfer(dataSocket, directory.getRootDir()));
                        break;
                    case BYE:
                        return;
                    default:
                        System.out.println("there is no such command");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread incorrect interrupted", e);
        } finally {
            executor.shutdown();
        }
    }
}

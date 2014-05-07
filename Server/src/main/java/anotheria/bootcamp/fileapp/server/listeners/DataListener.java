package anotheria.bootcamp.fileapp.server.listeners;

import anotheria.bootcamp.fileapp.directory.directory.DirectoryUtil;
import anotheria.bootcamp.fileapp.server.commandDataSync.CommandDataSynchronizer;
import anotheria.bootcamp.fileapp.dataTransfer.DirectoryOutputTransfer;
import anotheria.bootcamp.fileapp.dataTransfer.InputFileTransfer;
import anotheria.bootcamp.fileapp.dataTransfer.OutputFileTransfer;
import anotheria.bootcamp.fileapp.commands.pojos.Command;
import anotheria.bootcamp.fileapp.commands.pojos.GetCommand;

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
     * CommandDataSynchronizer object.
     */
    private CommandDataSynchronizer commandDataSynchronizer;

    /**
     * Constructor. Initialize dataSocket and commandDataSynchronizer objects.
     * @param dataSocket Socket that used transfer data.
     * @param commandDataSynchronizer commandDataSynchronizer.
     */
    public DataListener(Socket dataSocket, CommandDataSynchronizer commandDataSynchronizer) {
        this.dataSocket = dataSocket;
        this.commandDataSynchronizer = commandDataSynchronizer;
    }


    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {

            boolean done = false;

            while (!done) {
              synchronized (this){ wait();}

                Command command = commandDataSynchronizer.getCommand();

                switch (command.getType()) {
                    case DIR:
                        executor.execute(new DirectoryOutputTransfer(dataSocket));
                        break;
                    case PUT:
                        executor.execute(new InputFileTransfer(dataSocket, DirectoryUtil.getRootDir()));
                        break;
                    case GET:
                        GetCommand get = (GetCommand)command;
                        if(!DirectoryUtil.containsFile(get.getFileName()))
                            continue;
                        executor.execute(new OutputFileTransfer(dataSocket, DirectoryUtil.getFile(get.getFileName())));
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
        } finally {
            executor.shutdown();
        }
    }
}

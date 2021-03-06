package anotheria.bootcamp.fileapp.server.commandDataSync;

import anotheria.bootcamp.fileapp.commands.pojos.Command;
import anotheria.bootcamp.fileapp.directory.directory.DirectoryUtil;
import anotheria.bootcamp.fileapp.server.listeners.CommandListener;
import anotheria.bootcamp.fileapp.server.listeners.DataListener;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class synchronizes commands and data transfer.
 * This class contains two threads:
 * commandListener - used to receiving commands.
 * dataListener - used to transfer data.
 */
public class CommandDataSynchronizer implements Runnable {

    /**
     * Command that should be execute
     */
    private volatile Command command;

    /**
     * Command handler.
     */
    private Runnable commandListener;

    /**
     * Data handler.
     */
    private Runnable dataListener;

    /**
     * Constructor. Initialize command and data handlers.
     * @param commandSocket socket that used to receive
     * @param dataSocket socket that used to transfer data
     */
    public CommandDataSynchronizer(Socket commandSocket, Socket dataSocket) {
        commandListener = new CommandListener(commandSocket, this);
        dataListener = new DataListener(dataSocket, this);
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(commandListener);
        executor.execute(dataListener);

        executor.shutdown();
    }

    /**
     * This method check if current directoryUtil contains file
     * @param fileName file.
     * @return true if directoryUtil contains file.
     */
    public boolean fileExist(String fileName) {
        return DirectoryUtil.containsFile(fileName);
    }

    /**
     * This method notify data handler.
     */
    public void notifyDataListener() {
        synchronized (dataListener) {
            dataListener.notify();
        }
    }

    /**
     * Set command.
     * @param command command.
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Return command.
     * @return command.
     */
    public Command getCommand() {
        return command;
    }
}

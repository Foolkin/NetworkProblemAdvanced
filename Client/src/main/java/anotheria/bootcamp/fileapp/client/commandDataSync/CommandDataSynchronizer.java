package anotheria.bootcamp.fileapp.client.commandDataSync;

import anotheria.bootcamp.fileapp.directory.Constatns.PropertyNames;
import anotheria.bootcamp.fileapp.directory.directory.Directory;
import anotheria.bootcamp.fileapp.client.listeners.CommandSender;
import anotheria.bootcamp.fileapp.client.listeners.DataListener;
import anotheria.bootcamp.fileapp.commands.pojos.Command;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class synchronizes commands and data transfer.
 * This class contains two threads:
 * commandListener - used to receiving commands.
 * dataListener - used to transfer data.
 *
 *  @author Yevgenii Nikonchuk.
 */
public class CommandDataSynchronizer implements Runnable {

    /**
     * Command that should be execute
     */
    private Command command;

    /**
     * Directory in which will be stored files.
     */
    private Directory directory;

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
        commandListener = new CommandSender(commandSocket, this);
        dataListener = new DataListener(dataSocket, this);
        directory = new Directory(PropertyNames.CLIENT_ROOT_DIR);
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(commandListener);
        executor.execute(dataListener);

        System.out.println("connected to Anotheria bootcamp server.\n enter BYE to quit.");

        executor.shutdown();
    }

    /**
     * This method notify data handler.
     */
    public void notifyDataListener(){
        synchronized (dataListener) {
            dataListener.notify();
        }
    }

    /**
     * This method check if current directory contains file
     * @param fileName file.
     * @return true if directory contains file.
     */
    public boolean fileExist(String fileName){
        return directory.containsFile(fileName);
    }

    /**
     * Returns current directory.
     * @return directory.
     */
    public Directory getDirectory(){
        return directory;
    }

    /**
     * Set command.
     * @param command command.
     */
    public synchronized void setCommand(Command command){
        this.command = command;
    }

    /**
     * Return command.
     * @return command.
     */
    public synchronized Command getCommand(){
        return command;
    }
}

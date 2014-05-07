package anotheria.bootcamp.fileapp.server.listeners;

import anotheria.bootcamp.fileapp.server.commandDataSync.CommandDataSynchronizer;
import anotheria.bootcamp.fileapp.commands.Constants.Commands;
import anotheria.bootcamp.fileapp.commands.pojos.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;


/**
 * This class receive commands from client and run required action
 */
public class CommandListener implements Runnable {

    /**
     * Socket that used to send command to the server.
     */
    private Socket commandSocket;

    /**
     * CommandDataSynchronizer object.
     */
    private CommandDataSynchronizer commandDataSynchronizer;

    /**
     * Constructor. Initialize commandSocket and commandDataSynchronizer objects.
     * @param commandSocket Socket that used to send command to the server.
     * @param commandDataSynchronizer commandDataSynchronizer.
     */
    public CommandListener(Socket commandSocket, CommandDataSynchronizer commandDataSynchronizer){
        this.commandSocket = commandSocket;
        this.commandDataSynchronizer = commandDataSynchronizer;
    }

    @Override
    public void run() {
        try (InputStream inStream = commandSocket.getInputStream();
             ObjectInputStream objectIn = new ObjectInputStream(inStream);
        ){
            boolean done = false;

            while (!done) {
                Command command = (Command) objectIn.readObject();

                commandDataSynchronizer.setCommand(command);
                commandDataSynchronizer.notifyDataListener();

                if(command.getType() == Commands.BYE) {
                    done = true;
                    commandSocket.shutdownInput();
                }
            }

        } catch (IOException e){
            throw new RuntimeException("Can't get input stream from command socket", e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Can't cast object from socket to Command", e);
        }
    }
}

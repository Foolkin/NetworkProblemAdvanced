package anotheria.bootcamp.fileapp.client.listeners;

import anotheria.bootcamp.fileapp.client.commandDataSync.CommandDataSynchronizer;
import anotheria.bootcamp.fileapp.commands.pojos.ByeCommand;
import anotheria.bootcamp.fileapp.commands.pojos.Command;
import anotheria.bootcamp.fileapp.commands.pojos.DirCommand;
import anotheria.bootcamp.fileapp.commands.pojos.GetCommand;
import anotheria.bootcamp.fileapp.commands.pojos.PutCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class sends command pojo to the server.
 *
 * @author Yevgenii Nikonchuk.
 */
public class CommandSender implements Runnable {

    /**
     * Socket that used to send command to the server.
     */
    Socket commandSocket;

    /**
     * commandDataSync.CommandDataSynchronizer object.
     */
    CommandDataSynchronizer commandDataSynchronizer;

    /**
     * Constructor. Initialize commandSocket and commandDataSync objects.
     *
     * @param commandSocket Socket that used to send command to the server.
     * @param commandDataSynchronizer       commandDataSync.
     */
    public CommandSender(Socket commandSocket, CommandDataSynchronizer commandDataSynchronizer) {
        this.commandSocket = commandSocket;
        this.commandDataSynchronizer = commandDataSynchronizer;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream objectOut = new ObjectOutputStream(commandSocket.getOutputStream());
                Scanner consoleIn = new Scanner(System.in);
        ) {
            boolean done = false;

            while (!done) {
                Command command;
                String s = consoleIn.next();

                switch (s) {
                    case ("DIR"):
                        DirCommand dir = new DirCommand();
                        command = dir;
                        break;
                    case ("PUT"):
                        PutCommand put = new PutCommand();
                        put.setFileName(consoleIn.next());
                        command = put;
                        break;
                    case ("GET"):
                        GetCommand get = new GetCommand();
                        get.setFileName(consoleIn.next());
                        command = get;
                        break;
                    case ("BYE"):
                        ByeCommand bye = new ByeCommand();
                        command = bye;
                        done = true;
                        break;
                    default:
                        System.out.println("there is no such command.");
                        continue;
                }

//                if (command.getType() == Commands.PUT && !commandDataSync.fileExist(command.getFileName())) {
//                    System.out.println("There is no such file, check file name.");
//                    continue;
//                }
                objectOut.writeObject(command);
                commandDataSynchronizer.setCommand(command);
                commandDataSynchronizer.notifyDataListener();
            }
        } catch (IOException e) {
            throw new RuntimeException("command socket outputStream problem", e);
        }
    }
}

package listeners;

import Constants.Commands;
import pojos.Command;
import session.Session;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
     * session.Session object.
     */
    Session session;

    /**
     * Constructor. Initialize commandSocket and session objects.
     * @param commandSocket Socket that used to send command to the server.
     * @param session session.
     */
    public CommandSender(Socket commandSocket, Session session) {
        this.commandSocket = commandSocket;
        this.session = session;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream objectOut = new ObjectOutputStream(commandSocket.getOutputStream());
                Scanner consoleIn = new Scanner(System.in);
        ) {
            boolean done = false;

            while (!done) {
                Command command = new Command();
                String s = consoleIn.next();

                switch (s){
                    case ("DIR"):
                        command.setCommandName(Commands.DIR);
                        break;
                    case ("PUT"):
                        command.setCommandName(Commands.PUT);
                        command.setFileName(consoleIn.next());
                        break;
                    case ("GET"):
                        command.setCommandName(Commands.GET);
                        command.setFileName(consoleIn.next());
                        break;
                    case ("BYE"):
                        command.setCommandName(Commands.BYE);
                        done = true;
                        break;
                    default:
                        System.out.println("there is no such command.");
                        continue;
                }

                if(command.getCommandName() == Commands.PUT && !session.fileExist(command.getFileName())) {
                    System.out.println("There is no such file, check file name.");
                    continue;
                }
                objectOut.writeObject(command);
                session.setCommand(command);
                session.notifyDataListener();
            }
        } catch (IOException e) {
            throw new RuntimeException("command socket outputStream problem", e);
        }
    }
}
